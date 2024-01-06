package cl.LibrarySystem.service.impl;

import cl.LibrarySystem.mapper.UserMapper;
import cl.LibrarySystem.pojo.User;
import cl.LibrarySystem.pojo.vo.UserVo;
import cl.LibrarySystem.service.UserService;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class UserServiceImpl implements UserService  {

    @Autowired
    UserMapper userMapper;

    @Override
    public User login(int id) {
        User user = userMapper.selectUserByUserId(id);
       return user;
    }

    @Override
    public boolean registerUser(UserVo userVo) {
        User user = new User();
        boolean exist = judgeExist(userVo.getUserId());  // 保证角色ID唯一
        if (exist == false)
            return false;
        BeanUtils.copyProperties(userVo,user);
        System.out.println(user);
        int insert = userMapper.insert(user);
        return insert == 0 ? false: true;
    }

    @Override
    public boolean alterMessage(User user) {
        int update = userMapper.updateById(user);
        return update == 0 ? false : true;
    }

    @Override
    public User selectUserMsg(int id) {
        User user = userMapper.selectUserByUserId(id);
        return user;
    }

    // 查询用户id是否存在
    private boolean judgeExist(int id)
    {
        User user = userMapper.selectUserByUserId(id);
        System.out.println("------------");
        System.out.println(user);
        if (user == null)
            return true;
        else return false;
    }
}
