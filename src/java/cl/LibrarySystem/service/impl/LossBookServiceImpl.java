package cl.LibrarySystem.service.impl;

import cl.LibrarySystem.mapper.LossBookMapper;
import cl.LibrarySystem.pojo.Book;
import cl.LibrarySystem.pojo.LossBook;
import cl.LibrarySystem.service.LossBookService;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class LossBookServiceImpl implements LossBookService {

    @Autowired
    LossBookMapper lossBookMapper;

    @Override
    public boolean lossBook(LossBook lossBook) {

        // 得到书的价格
      double price =  lossBookMapper.selectPriceByIsbn(lossBook.getBookIsbn());
      lossBook.setBookPrice(price);
        int insert = lossBookMapper.insert(lossBook);
        return insert == 0 ? false:true;
    }

    @Override
    public Page<LossBook> getBookByLimit(int pageNum, int pageSize) {
        Page<LossBook> page = new Page<>(pageNum, pageSize);
        Page<LossBook> bookPage = lossBookMapper.selectPage(page, null);
        return bookPage;
    }

    @Override
    public boolean disposeBook(Integer userId,String isbn) {
        int res = lossBookMapper.delBookByUserId(userId,isbn);
        return res == 0 ? false:true;
    }
}
