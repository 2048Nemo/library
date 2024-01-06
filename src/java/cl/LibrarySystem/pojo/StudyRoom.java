package cl.LibrarySystem.pojo;

import com.baomidou.mybatisplus.annotation.*;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiOperation;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;


@TableName("t_studyroom")
@ApiModel("自习室类")
@Data
@AllArgsConstructor
@NoArgsConstructor
public class StudyRoom {
     @TableId(type = IdType.ASSIGN_ID)
    private Integer tId;   // 编号
    private String tFloor;  // 楼层
    private String tSeatNumber;  // 座位号
    private Integer tDueUserId;  // 预定人编号
    @TableField(fill = FieldFill.INSERT)
    private String tStatus;  // 座位状态

    public StudyRoom(String tFloor, String tSeatNumber) {
        this.tFloor = tFloor;
        this.tSeatNumber = tSeatNumber;
    }
}
