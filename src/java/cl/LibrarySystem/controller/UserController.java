package cl.LibrarySystem.controller;

import cl.LibrarySystem.Utils.JWTUtils;
import cl.LibrarySystem.pojo.User;
import cl.LibrarySystem.pojo.vo.UserVo;
import cl.LibrarySystem.result.ErrorCodeEnums;
import cl.LibrarySystem.result.ResponseResult;
import cl.LibrarySystem.service.UserService;
import com.auth0.jwt.JWT;
import com.auth0.jwt.exceptions.JWTDecodeException;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping("/user")
public class UserController {

    @Autowired
    UserService userService;

    @ApiOperation("登录")
    @ResponseBody
    @GetMapping("/login")
    public ResponseResult login(@RequestParam("id") int id,
                                @RequestParam("password") String password) {
        User user = userService.login(id);
        if (user == null)
            return ResponseResult.fail(ErrorCodeEnums.USER_NO_REGISTER_EXCEPTION);
        Map<String, Object> map = new HashMap<>();
        if (user.getPassword().equals(password) && user.getUserId() == id) {
            // 设置token
            String token = JWTUtils.getToken(user.getUserId().toString(), password);
            map.put("token",token);
            map.put("userId",id);
            map.put("limit",user.getUserLimit());
          return ResponseResult.success(map);
        }
        else
        return ResponseResult.fail(ErrorCodeEnums.USERNAME_OR_PASSWORD_ERROR_EXCEPTION);
    }

    @ApiOperation("注册")
    @ResponseBody
    @PostMapping("/register")
    public ResponseResult register(@RequestBody UserVo userVo) {
        System.out.println(userVo);
        if (userVo.getUserId() == null || userVo.getUsername() == null || userVo.getUsername() == "")
            return ResponseResult.fail(ErrorCodeEnums.USERNAME_OR_PASSWORD_ERROR_EXCEPTION);
        boolean res = false;
        try {
            res = userService.registerUser(userVo);
        } catch (Exception E) {
            E.printStackTrace();
            return ResponseResult.fail(ErrorCodeEnums.DATABASE_OPERA_EXCEPTION);
        }
        if (res == false)
            return ResponseResult.fail(ErrorCodeEnums.REPEAT_USER_ID_EXCEPTION);
        else return ResponseResult.success("注册成功");
    }

    @ApiOperation("更改信息")
    @ResponseBody
    @PostMapping("/alterMessage")
    public ResponseResult alterMessage(@RequestBody User user) {
        System.out.println(user);
        boolean res = false;
        try {
            res = userService.alterMessage(user);
        } catch (Exception E) {
            E.printStackTrace();
            return ResponseResult.fail(ErrorCodeEnums.DATABASE_OPERA_EXCEPTION);
        }
        return ResponseResult.success("更改成功");
    }

    @ApiOperation("获得用户信息")
    @ResponseBody
    @GetMapping("/selectUserMsg")
    public ResponseResult selectUserMsg(@RequestParam("id") int id) {
        User user = null;
        try {
            user = userService.selectUserMsg(id);
        } catch (Exception E) {
            return ResponseResult.fail(ErrorCodeEnums.DATABASE_OPERA_EXCEPTION);
        }
        return ResponseResult.success(user);
    }

    @ApiOperation("得到用户ID通过token")
    @ResponseBody
    @GetMapping("/getIdByToken")
    public ResponseResult getUserIdByToken(@RequestParam("token") String token)
    {
        token = token.substring(1,token.length() - 1);  // 切除引号
        String id = null;
        try {
            id = JWT.decode(token).getAudience().get(0);
        } catch (JWTDecodeException j) {
            return ResponseResult.fail(ErrorCodeEnums.USER_NOT_LOGIN_EXCEPTION);
        }
        User user = userService.selectUserMsg(Integer.parseInt(id));
        return ResponseResult.success(user);
    }
}
