package cl.LibrarySystem.mapper;

import cl.LibrarySystem.pojo.LendBookUser;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Delete;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface LendBookUserMapper extends BaseMapper<LendBookUser> {

    @Select("select * from t_lendbookuser where user_id = #{userId}")
    List<LendBookUser> selectUserLendBookNumsByUserId(@Param("userId") int id);

    @Delete("delete from t_lendbookuser where user_id = #{id} and book_isbn = #{bookIsbn}")
    int returnBook(@Param("id") int id,@Param("bookIsbn") String bookIsbn);

    @Select("select * from t_lendbookuser where user_id = #{userId}")
    List<LendBookUser> queryUsersListByUserId(int userId);

    @Delete("delete from t_lendbookuser where user_id = #{userId} and book_isbn = #{isbn}")
    int delUserByUserIdAndIsbn(@Param("userId") Integer userId, @Param("isbn") String isbn);
}
