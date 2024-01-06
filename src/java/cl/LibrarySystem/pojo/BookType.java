package cl.LibrarySystem.pojo;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.fasterxml.jackson.annotation.JsonIgnore;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@TableName("book_type")
@ApiModel("图书类型实体类")
public class BookType {
    @JsonIgnore
    @ApiModelProperty("编号")
    @TableId(type = IdType.AUTO)
    private int id;
    private String type;
}
