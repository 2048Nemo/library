package cl.LibrarySystem.service;

import cl.LibrarySystem.pojo.User;
import cl.LibrarySystem.pojo.vo.UserVo;
import org.springframework.stereotype.Component;

@Component
public interface UserService  {
    User login(int id);

    boolean registerUser(UserVo userVo);

    boolean alterMessage(User user);

    User selectUserMsg(int id);
}
