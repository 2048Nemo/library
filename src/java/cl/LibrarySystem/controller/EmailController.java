package cl.LibrarySystem.controller;

import cl.LibrarySystem.pojo.User;
import cl.LibrarySystem.pojo.UserSeat;
import cl.LibrarySystem.result.ResponseResult;
import cl.LibrarySystem.service.UserSeatService;
import cl.LibrarySystem.service.UserService;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class EmailController {

    @Autowired
    UserService userService;

    @Autowired
    UserSeatService userSeatService;

    @Autowired(required = false)
    private JavaMailSender sender; // 引入Spring Mail依赖后，会自动装配到IOC容器
    //存储随机生成的校验码
    private String cdoe_store = null;

    @ApiOperation("注册发送验证码")
    @ResponseBody
    @GetMapping("/registerEmail")
    public ResponseResult registerEmail(@RequestParam("email") String emailRegister) {
        String code = (Math.random() + "").substring(2, 8);	//生成6位随机数
        cdoe_store = code;
        String email = emailRegister;	//你想发给哪个邮箱
        SimpleMailMessage message = new SimpleMailMessage();
        message.setSubject("提示"); // 发送邮件的主题
        message.setText("您正在注册登录，您的验证码是:" + code); // 邮件的内容
        message.setTo(email); // 指定要接收邮件的用户邮箱账号
        message.setFrom("206031489@qq.com"); // 邮件发送方，也就是配置文件中的邮件地址。
        sender.send(message); // 调用send方法发送邮件
        return ResponseResult.success(code);
    }

    @ApiOperation("提醒归还")
    @ResponseBody
    @GetMapping("/remind")
    public ResponseResult remindEmail(@RequestParam("userId") Integer userId) {
        User user = userService.selectUserMsg(userId);
        if (user == null)
            return ResponseResult.fail("邮件发送失败,邮箱错误");
        try {

            String email = user.getEmail();	//你想发给哪个邮箱
            SimpleMailMessage message = new SimpleMailMessage();
            message.setSubject("图书馆提示"); // 发送邮件的主题
            message.setText("您所借阅的图书将要到达指定时间，请尽快归还"); // 邮件的内容
            message.setTo(email); // 指定要接收邮件的用户邮箱账号
            message.setFrom("206031489@qq.com"); // 邮件发送方，也就是配置文件中的邮件地址。
            sender.send(message); // 调用send方法发送邮件
        }catch (Exception E)
        {
            return ResponseResult.fail("邮件发送失败,邮箱错误");
        }
        return ResponseResult.success( "邮件发送成功!");
    }
    @ApiOperation("提醒座位时间到")
    @ResponseBody
    @GetMapping("/remindSeat")
    public ResponseResult remindSeatEmail(@RequestParam("userId") Integer userId) {
        UserSeat user = userSeatService.selectUserSeatByUserId(userId);
        if (user == null)
            return ResponseResult.fail("邮件发送失败,邮箱错误");
        try {
            String email = user.getEmail();	//你想发给哪个邮箱
            SimpleMailMessage message = new SimpleMailMessage();
            message.setSubject("图书馆提示"); // 发送邮件的主题
            message.setText("您所预定的座位将要到达预定时间，如果需要，请在官网继续预定"); // 邮件的内容
            message.setTo(email); // 指定要接收邮件的用户邮箱账号
            message.setFrom("206031489@qq.com"); // 邮件发送方，也就是配置文件中的邮件地址。
            sender.send(message); // 调用send方法发送邮件
        }catch (Exception E)
        {
            return ResponseResult.fail("邮件发送失败,邮箱错误");
        }
        return ResponseResult.success( "邮件发送成功!");
    }
}
