package cl.LibrarySystem.interceptor;

import cl.LibrarySystem.pojo.User;
import cl.LibrarySystem.service.UserService;
import com.auth0.jwt.JWT;
import com.auth0.jwt.JWTVerifier;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.exceptions.JWTDecodeException;
import com.auth0.jwt.exceptions.JWTVerificationException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.StringUtils;
import org.springframework.web.method.HandlerMethod;
import org.springframework.web.servlet.HandlerInterceptor;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class JwtInterceptor implements HandlerInterceptor {

    @Autowired
    private UserService userService;

    @Override
    public boolean preHandle(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse, Object object) {

         /*
        手动排除路径 webConfig 不起作用
        */
        System.out.println(httpServletRequest.getServletPath());
        if (httpServletRequest.getServletPath().equals("/user/login"))
            return true;
        if (httpServletRequest.getServletPath().equals("/registerEmail"))
            return true;
        if (httpServletRequest.getServletPath().equals("/user/register"))
            return true;
        if (httpServletRequest.getServletPath().equals("/book/export"))
            return true;
        if (httpServletRequest.getServletPath().equals("/book/import"))
            return true;
        if (httpServletRequest.getServletPath().equals("/swagger-ui.html"))
            return true;
        if (httpServletRequest.getServletPath().equals("/lendBookUser/export"))
            return true;
        if(httpServletRequest.getServletPath().equals("/webjars/springfox-swagger-ui/springfox.css"))
            return true;
        if (httpServletRequest.getServletPath().equals("/webjars/springfox-swagger-ui/swagger-ui.css"))
            return true;

        String token = httpServletRequest.getHeader("token");// 从 http 请求头中取出 token
        token = token.substring(1,token.length() - 1);  // 切除引号
        System.out.println(httpServletRequest.getHeader("token"));

        // 如果不是映射到方法直接通过
        if (!(object instanceof HandlerMethod)) {
            return true;
        }
        if (StringUtils.isEmpty(token)) {
            throw new RuntimeException("无token，请重新登录");
        }
        String id;
        Integer userId;
        try {
            id = JWT.decode(token).getAudience().get(0);
            userId = Integer.parseInt(id);
        } catch (JWTDecodeException j) {
            throw new RuntimeException("401");
        }
        User user = userService.selectUserMsg(userId);
        if (user == null) {
            throw new RuntimeException("用户不存在，请重新登录");
        }
        // 用户密码加签 验证 token
        JWTVerifier jwtVerifier = JWT.require(Algorithm.HMAC256(user.getPassword())).build();
        try {
            jwtVerifier.verify(token);
        } catch (JWTVerificationException e) {
            throw new RuntimeException("401");
        }
        return true;
    }

}
