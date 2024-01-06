package cl.LibrarySystem.mapper;

import cl.LibrarySystem.pojo.UserSeat;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import org.springframework.stereotype.Repository;

@Repository
public interface UserSeatMapper extends BaseMapper<UserSeat> {

    @Select("select * from user_seat where t_user_id = ${id}")
     UserSeat getUserSeat(@Param("id") int id);

    @Select("select * from user_seat where t_user_id = #{userId}")
    UserSeat queryOwn(@Param("userId") int userId);
}
