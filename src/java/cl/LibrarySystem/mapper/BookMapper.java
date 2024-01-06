package cl.LibrarySystem.mapper;

import cl.LibrarySystem.pojo.Book;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Update;
import org.springframework.stereotype.Repository;

@Repository
public interface BookMapper extends BaseMapper<Book> {

    @Update("update book set nums = nums + 1 where ISBN = #{bookIsbn}")
    int updateBookNum(@Param("bookIsbn") String bookIsbn);
}
