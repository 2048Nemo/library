package cl.LibrarySystem.pojo.vo;

import com.alibaba.excel.annotation.ExcelProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.apache.ibatis.annotations.Delete;

import java.text.SimpleDateFormat;
import java.util.Date;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class LendUserDateStringVo {

    private Integer id;

    @ExcelProperty(value = "姓名",index = 0)
    private String username;

    @ExcelProperty(value = "学号",index = 1)
    private Integer userId;

    @ExcelProperty(value = "图书",index = 2)
    private String lendBookName;

    @ExcelProperty(value = "ISBN编号",index = 3)
    private String bookIsbn;

    @ExcelProperty(value = "借阅时间",index = 4)
    private String lendBookStartDate;

    @ExcelProperty(value = "预计归还时间",index = 5)
    private String lendBookEndDate;

    @ExcelProperty(value = "借阅状态",index = 6)
    private String lendBookStatus;

}
