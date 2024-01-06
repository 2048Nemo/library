package cl.LibrarySystem.service.impl;

import cl.LibrarySystem.mapper.LendBookMapper;
import cl.LibrarySystem.pojo.Book;
import cl.LibrarySystem.pojo.LendBook;
import cl.LibrarySystem.service.LendBookService;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class LendBookServiceImpl implements LendBookService {
    @Autowired
    LendBookMapper lendBookMapper;

    @Override
    public List<LendBook> getTopNums() {
        return lendBookMapper.selectTopLimit();
    }

    @Override
    public boolean addNum(Book book) {

        // 是否被借阅过
        LendBook isExit = lendBookMapper.selectLendBookByIsbn(book.getISBN());
        int res = 0;
        if (isExit == null)
        {
            LendBook lendBook = new LendBook(book.getName(),book.getAuthor(),book.getType(),book.getISBN());
            res = lendBookMapper.insert(lendBook);
        }else {
             res = lendBookMapper.addNumsByIsbn(book.getISBN());
        }
        return res == 0 ? false:true;
    }
}
