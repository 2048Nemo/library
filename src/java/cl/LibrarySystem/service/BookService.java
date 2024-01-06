package cl.LibrarySystem.service;

import cl.LibrarySystem.pojo.Book;
import cl.LibrarySystem.pojo.vo.BookVo;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import org.springframework.stereotype.Component;

import java.util.List;


@Component
public interface BookService {

    List<Book> selectAllBooks();

    Page<Book> getBookByLimit(int pageNum, int pageSize);

    List<Book> getBooksByVagueSearch(String bookName, String author,String type);

    List<Book> getBookByType(String type);

    Boolean insertNewBook(BookVo book);

    Boolean update(BookVo bookVo);

    Boolean deleteDataById(Integer id);

    Boolean deleteBooksByIds(List<Book> book);

    Boolean importBooks(List<Book> books);

    int getBookNumsById(int id);

    void lendBookSuccessSubNum(Integer id);

    boolean returnBookAddNum(String bookIsbn);
}
