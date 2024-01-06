package cl.LibrarySystem.service.impl;

import cl.LibrarySystem.mapper.BookMapper;
import cl.LibrarySystem.pojo.Book;
import cl.LibrarySystem.pojo.vo.BookVo;
import cl.LibrarySystem.service.BookService;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class BookServiceImpl implements BookService {

    @Autowired
    private BookMapper bookMapper;


    @Override
    public List<Book> selectAllBooks() {
        QueryWrapper<Book> wrapper = new QueryWrapper<>();
        wrapper.select("id,name,author,type,publish,ISBN,price,nums");
        List<Book> books = bookMapper.selectList(wrapper);
        return books;
    }

    // 分页查询
    @Override
    public Page<Book> getBookByLimit(int pageNum, int pageSize) {
        Page<Book> page = new Page<>(pageNum, pageSize);
        Page<Book> bookPage = bookMapper.selectPage(page, null);
        return bookPage;
    }

    // 模糊查询
    @Override
    public List<Book> getBooksByVagueSearch(String bookName, String author, String type) {
        List<Book> books = new ArrayList<>();
        QueryWrapper<Book> wrapper = new QueryWrapper<>();
        if (bookName != null && bookName != "")
            wrapper.like("name", bookName);
        if (author != null && author != "" )
            wrapper.like("author", author);
        if (type != null && type != "")
            wrapper.like("type", type);
        books = bookMapper.selectList(wrapper);
        return books;
    }

    // 类型查询
    @Override
    public List<Book> getBookByType(String type) {
        QueryWrapper<Book> wrapper = new QueryWrapper<>();
        wrapper.eq("type", type);
        List<Book> books = bookMapper.selectList(wrapper);
        return books;
    }

    // 添加新的图书
    @Override
    public Boolean insertNewBook(BookVo book) {
        Boolean res = false;
        QueryWrapper<Book> wrapper = new QueryWrapper();
        Book newBook = new Book();
        HashMap<String, Object> map = new HashMap<>();
        map.put("isbn", book.getISBN());
        List<Book> books = bookMapper.selectByMap(map);
        if (books.size() != 0)   // ISBN重复
        {
           newBook = books.get(0);
           newBook.setNums(newBook.getNums() + book.getNums());
            int updateById = bookMapper.updateById(newBook);
            res = updateById == 0?false:true;
        }
         else {
            BeanUtils.copyProperties(book, newBook);
            int insert = bookMapper.insert(newBook);
            if (insert != 0)
                res = true;
            else res = false;
        }
        return res;
    }

    // 修改图书信息
    @Override
    public Boolean update(BookVo bookVo) {
        Map<String, Object> map = new HashMap<>();
        map.put("isbn", bookVo.getISBN());
        List<Book> books = bookMapper.selectByMap(map);
        Book book = books.get(0);
        BeanUtils.copyProperties(bookVo, book);
        int update = bookMapper.updateById(book);
        if (update == 0)
            return false;
        else return true;
    }


    // 删除一本图书
    @Override
    public Boolean deleteDataById(Integer id) {
        int delete = bookMapper.deleteById(id);
        if (delete == 0)
            return false;
        else return true;
    }

    // 删除多本图书
    @Override
    public Boolean deleteBooksByIds(List<Book> book) {
        int res = bookMapper.deleteBatchIds(book);
        if (res == 0)
            return false;
        else return true;
    }


    // 导入图书
    @Override
    public Boolean importBooks(List<Book> books) {
        Boolean res = false;
        for(Book book:books){
            Map<String,Object> map = new HashMap<>();
            map.put("ISBN",book.getISBN());
            List<Book> list = bookMapper.selectByMap(map);
            if (list.size() == 0)
            {
                int insert = bookMapper.insert(book);
                if (insert == 0)
                    res = false;
                else res = true;
            }else{
                QueryWrapper<Book> wrapper = new QueryWrapper();
                wrapper.eq("isbn",book.getISBN());
                int update = bookMapper.update(book, wrapper);
                if (update == 0)
                    res = false;
                else res = true;
            }
        }
        return res;
    }

    @Override
    public int getBookNumsById(int id) {
        Book book = bookMapper.selectById(id);
        return book.getNums();
    }

    @Override
    public void lendBookSuccessSubNum(Integer id) {
        Book book = bookMapper.selectById(id);
        book.setNums(book.getNums() - 1);
        bookMapper.updateById(book);
    }

    @Override
    public boolean returnBookAddNum(String bookIsbn) {
        int update = bookMapper.updateBookNum(bookIsbn);
        return update == 0 ? false:true;
    }
}
