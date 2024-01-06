package cl.LibrarySystem.pojo;

import com.baomidou.mybatisplus.annotation.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

@Data
@AllArgsConstructor
@NoArgsConstructor
@TableName("users")
public class User {

    @TableId(type = IdType.ASSIGN_ID)
    private Integer id;

    private Integer userId;

    private String username;

    private String password;

    private String email;

    @TableField(value = "enrollDate" ,fill = FieldFill.INSERT)
    private Date enrollDate;

    @TableField(value = "userLimit",fill = FieldFill.INSERT)
    private Integer userLimit;
}
