package cl.LibrarySystem.service.impl;

import cl.LibrarySystem.mapper.BookTypeMapper;
import cl.LibrarySystem.pojo.BookType;
import cl.LibrarySystem.service.BookTypeService;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class BookTypeServiceImpl implements BookTypeService {

    @Autowired
    BookTypeMapper bookTypeMapper;

    // 得到所有书籍类型
    @Override
    public List<BookType> getBookType() {
        List<BookType> bookTypes = bookTypeMapper.selectList(null);
        return bookTypes;
    }
}
