package cl.LibrarySystem.pojo;

import com.alibaba.excel.annotation.ExcelProperty;
import com.baomidou.mybatisplus.annotation.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;


@Data
@AllArgsConstructor
@NoArgsConstructor
@TableName("Book")
public class Book {

    @TableId(type = IdType.ASSIGN_ID)
    private Integer id;

    @ExcelProperty(value = "书名",index = 0)
    @TableField("name")
    private String name;

    @ExcelProperty(value = "作者",index = 1)
    @TableField("author")
    private String author;

    @ExcelProperty(value = "类型",index = 2)
    @TableField("type")
    private String type;

    @ExcelProperty(value = "出版社",index = 3)
    @TableField("publish")
    private String publish;

    @ExcelProperty(value = "ISBN编号",index = 4)
    @TableField("ISBN")
    private String ISBN;

    @ExcelProperty(value = "单价",index = 5)
    @TableField("price")
    private double price;

    @ExcelProperty(value = "库存",index = 6)
    @TableField("nums")
    private Integer nums;

    @TableField(fill = FieldFill.INSERT)
    @Version  // 乐观锁注解
    private Integer version;


  /*
    字段添加填充内容
    @TableField(fill = FieldFill.INSERT)
    private Data createTime;
    @TableField(fill = FieldFill.INSERT_UPDATE)
    private Data updateTime;
    */
}

