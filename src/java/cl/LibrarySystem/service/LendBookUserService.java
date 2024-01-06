package cl.LibrarySystem.service;

import cl.LibrarySystem.pojo.LendBookUser;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public interface LendBookUserService {
    boolean lendBook(LendBookUser lendBookUser);

    Page<LendBookUser> limitLendBookUser(int pageNum, int pageSize);

    List<LendBookUser> vagueSearch(Integer userId, String username, String status);

    List<LendBookUser> selectAllUser();

    boolean returnBook(int userId,String bookIsbn);

    List<LendBookUser> queryListLendBookUser(int userId);

    boolean delUserByUserIdAndIsbn(Integer userId,String isbn);

    void updateStatus();
}
