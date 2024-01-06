package cl.LibrarySystem.service;

import cl.LibrarySystem.pojo.LossBook;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import org.springframework.stereotype.Component;

@Component
public interface LossBookService {
    boolean lossBook(LossBook lossBook);

    Page<LossBook> getBookByLimit(int pageNum, int pageSize);

    boolean disposeBook(Integer userId,String isbn);
}
