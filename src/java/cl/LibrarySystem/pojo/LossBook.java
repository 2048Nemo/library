package cl.LibrarySystem.pojo;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@TableName("lossbook")
public class LossBook {

    @TableId(type = IdType.ASSIGN_ID)
    private Integer id;

    private String userName;

    private Integer userId;

    private String bookName;

    private String bookIsbn;

    private Double bookPrice;
}
