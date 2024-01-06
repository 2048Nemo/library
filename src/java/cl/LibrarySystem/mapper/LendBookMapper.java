package cl.LibrarySystem.mapper;

import cl.LibrarySystem.pojo.Book;
import cl.LibrarySystem.pojo.LendBook;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface LendBookMapper extends BaseMapper<LendBook> {

    @Select("SELECT * from lendbook ORDER BY lend_num desc limit 5")
    List<LendBook> selectTopLimit();

    @Update("update lendbook set lend_num = lend_num + 1 where book_isbn = #{isbn}" )
    int addNumsByIsbn(@Param("isbn") String isbn);

    @Select("select * from lendbook where book_isbn = #{isbn}")
    LendBook selectLendBookByIsbn(@Param("isbn") String isbn);

}
