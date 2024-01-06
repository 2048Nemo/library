package cl.LibrarySystem.pojo;

import com.baomidou.mybatisplus.annotation.*;
import io.swagger.annotations.ApiModel;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.apache.ibatis.annotations.Param;

import java.util.Date;

@Data
@AllArgsConstructor
@NoArgsConstructor
@ApiModel("预定座位人员信息实体类")
@TableName("user_seat")
public class UserSeat {


    @TableId(type = IdType.ASSIGN_ID)
    private Integer tId;
    private Integer tUserId;
    private String tUsername;

    private String tDueSeatnumber;

    private String email;

    @TableField(fill = FieldFill.INSERT)
    private Date tEndDate;
}
