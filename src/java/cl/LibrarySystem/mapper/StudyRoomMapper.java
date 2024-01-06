package cl.LibrarySystem.mapper;

import cl.LibrarySystem.pojo.StudyRoom;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;
import org.springframework.stereotype.Repository;

@Repository
public interface StudyRoomMapper extends BaseMapper<StudyRoom> {
    @Update("UPDATE t_studyRoom set t_due_user_id = null,t_status = '空' where t_id = #{id}")
    int resetDueSeat(@Param("id") int id);

    @Update("UPDATE t_studyRoom set t_due_user_id = null,t_status = '空' where t_due_user_id = #{userId}")
    void resetDueSeatByUserId(@Param("userId") Integer userId);

    @Select("select * from t_studyRoom where t_due_user_id = #{userId}")
    StudyRoom queryOwm(@Param("userId") int userId);
}
