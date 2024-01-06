package cl.LibrarySystem.pojo;

import com.alibaba.excel.annotation.ExcelProperty;
import com.baomidou.mybatisplus.annotation.*;
import io.swagger.models.auth.In;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.omg.CORBA.PUBLIC_MEMBER;

@NoArgsConstructor
@AllArgsConstructor
@Data
@TableName("lendbook")
public class LendBook {

    @TableId(type = IdType.ASSIGN_ID)
    private Integer id;

    @ExcelProperty(value = "图书", index = 0)
    private String bookName;

    @ExcelProperty(value = "作者", index = 1)
    private String author;

    @ExcelProperty(value = "类型", index = 2)
    private String bookType;

    @ExcelProperty(value = "ISBN编号", index = 3)
    private String bookIsbn;

    @ExcelProperty(value = "借阅书次数", index = 4)
    @TableField(fill = FieldFill.INSERT)
    private Integer lendNum;

    public LendBook(String name, String author, String type, String isbn)
    {
        this.bookName = name;
        this.author = author;
        this.bookType = type;
        this.bookIsbn = isbn;
    }
}
