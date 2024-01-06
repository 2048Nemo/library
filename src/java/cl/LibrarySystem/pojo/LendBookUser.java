package cl.LibrarySystem.pojo;

import com.alibaba.excel.annotation.ExcelProperty;
import com.baomidou.mybatisplus.annotation.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.beans.factory.annotation.Value;

import java.util.Date;

@Data
@AllArgsConstructor
@NoArgsConstructor
@TableName("t_lendBookUser")
public class LendBookUser {

    @TableId(type = IdType.ASSIGN_ID)
    private Integer id;

    private String username;

    private Integer userId;

    private String lendBookName;

    private String bookIsbn;

    @TableField(fill = FieldFill.INSERT)
    private Date lendBookStartDate;

    private Date lendBookEndDate;

    @TableField(fill = FieldFill.INSERT)
    private String lendBookStatus;


}
