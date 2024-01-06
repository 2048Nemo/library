package cl.LibrarySystem.service.impl;

import cl.LibrarySystem.Utils.DateUtils;
import cl.LibrarySystem.controller.EmailController;
import cl.LibrarySystem.mapper.LendBookUserMapper;
import cl.LibrarySystem.mapper.LossBookMapper;
import cl.LibrarySystem.pojo.LendBookUser;
import cl.LibrarySystem.service.LendBookUserService;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;

@Service
public class LendBookUserServiceImpl implements LendBookUserService {

    @Autowired
    LendBookUserMapper lendBookUserMapper;

    @Autowired
    EmailController emailController;

    @Autowired
    LossBookMapper lossBookMapper;

    @Override
    public boolean lendBook(LendBookUser lendBookUser) {
        List<LendBookUser> userLendBookNums = lendBookUserMapper.selectUserLendBookNumsByUserId(lendBookUser.getUserId());
        if (userLendBookNums.size() > 10)
            return false;
        int insert = lendBookUserMapper.insert(lendBookUser);
        return insert == 0 ? false : true;
    }

    @Override
    public Page<LendBookUser> limitLendBookUser(int pageNum, int pageSize) {
        updateStatus();
        Page<LendBookUser> page = new Page<>(pageNum,pageSize);
        Page<LendBookUser> bookUserPage = lendBookUserMapper.selectPage(page, null);
        return bookUserPage;
    }

    @Override
    public List<LendBookUser> vagueSearch(Integer userId, String username, String status) {
        QueryWrapper<LendBookUser> wrapper = new QueryWrapper<>();
        if (userId != null)
            wrapper.eq("user_id",userId);
        if (username != null && username != "")
            wrapper.like("username",username);
        if (status != null && status != "")
            wrapper.like("lend_book_status",status);
        List<LendBookUser> users = lendBookUserMapper.selectList(wrapper);
        return users;
    }

    @Override
    public List<LendBookUser> selectAllUser() {
        List<LendBookUser> users = lendBookUserMapper.selectList(null);
        return users;
    }

    @Override
    public boolean returnBook(int userId, String bookIsbn) {
        int returnBook = lendBookUserMapper.returnBook(userId, bookIsbn);
        /*
         存在挂失清理，但是挂失的图书已经找且归还
         */
        int isExit = lossBookMapper.selectUserByUserId(userId,bookIsbn);
        if(isExit != 0) // 存在这种情况
        {
            lossBookMapper.delBookByUserId(userId,bookIsbn);
        }
        return returnBook == 0 ? false : true;
    }

    @Override
    public List<LendBookUser> queryListLendBookUser(int userId) {
        updateStatus();
        List<LendBookUser> lendBookUsers = lendBookUserMapper.queryUsersListByUserId(userId);
        return lendBookUsers;
    }

    @Override
    public boolean delUserByUserIdAndIsbn(Integer userId, String isbn) {
        int res = lendBookUserMapper.delUserByUserIdAndIsbn(userId,isbn);
        return res == 0?false:true;
    }

    // 更新图书
    @Override
    public void updateStatus() {
        List<LendBookUser> lendBookUsers = lendBookUserMapper.selectList(null);
        for(int i  = 0;i < lendBookUsers.size(); i++) {
            LendBookUser lendBookUser = lendBookUsers.get(i);
            if (DateUtils.judgeDateExcess(lendBookUser.getLendBookEndDate())) {
                lendBookUser.setLendBookStatus("超时");
                emailController.remindEmail(lendBookUser.getUserId());
                lendBookUserMapper.updateById(lendBookUser);
            }
        }
        }
}
