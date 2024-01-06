package cl.LibrarySystem.mapper;

import cl.LibrarySystem.pojo.LossBook;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Delete;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import org.springframework.stereotype.Repository;

@Repository
public interface LossBookMapper extends BaseMapper<LossBook> {

    @Delete("delete from lossBook where user_id = #{userId} and book_isbn = #{isbn}")
    int delBookByUserId(@Param("userId") Integer userId, @Param("isbn") String isbn);

    @Select("select price from book where ISBN = #{bookIsbn}")
    double selectPriceByIsbn(@Param("bookIsbn") String bookIsbn);

    @Select("select * from lossBook where user_id = #{userId} and book_isbn = #{isbn}")
    int selectUserByUserId(@Param("userId") int userId,@Param("isbn") String isbn);
}
