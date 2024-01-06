package cl.LibrarySystem.service;

import cl.LibrarySystem.pojo.Book;
import cl.LibrarySystem.pojo.LendBook;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public interface LendBookService {

    List<LendBook> getTopNums();

    boolean addNum(Book book);
}
