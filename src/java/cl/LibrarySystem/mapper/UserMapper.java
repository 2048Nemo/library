package cl.LibrarySystem.mapper;

import cl.LibrarySystem.pojo.User;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import org.springframework.stereotype.Repository;

@Repository
public interface UserMapper extends BaseMapper<User> {

    @Select("select * from users where user_id = #{id}")
    User selectUserByUserId(@Param("id") int id);
}
