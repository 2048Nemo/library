package cl.LibrarySystem.controller;

import cl.LibrarySystem.Utils.DateUtils;
import cl.LibrarySystem.Utils.SetExcelResponseUtils;
import cl.LibrarySystem.pojo.LendBookUser;
import cl.LibrarySystem.pojo.vo.LendBookUserVo;
import cl.LibrarySystem.pojo.vo.LendUserDateStringVo;
import cl.LibrarySystem.result.ErrorCodeEnums;
import cl.LibrarySystem.result.ResponseResult;
import cl.LibrarySystem.service.BookService;
import cl.LibrarySystem.service.LendBookService;
import cl.LibrarySystem.service.LendBookUserService;
import com.alibaba.excel.EasyExcel;
import com.alibaba.excel.support.ExcelTypeEnum;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import io.swagger.annotations.ApiOperation;
import lombok.SneakyThrows;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletResponse;
import java.util.*;

@RestController
@RequestMapping("/lendBookUser")
public class LendBookUserController {

    @Autowired
    LendBookUserService lendBookUserService;

    @Autowired
    BookService bookService;  // 在借阅和归还的时候使用

    @Autowired
    LendBookService lendBookService;  // 在借阅时候使用

    @ApiOperation("借阅图书")
    @ResponseBody
    @PostMapping("/lendBook")
    public ResponseResult lendBook(@RequestBody LendBookUserVo lendBookUserVo) {
        System.out.println(lendBookUserVo);
        lendBookUserVo.getLendBookUser().setLendBookName(lendBookUserVo.getBook().getName());
        lendBookUserVo.getLendBookUser().setBookIsbn(lendBookUserVo.getBook().getISBN());
        Integer bookNum = 0;
        try{
           bookNum  = bookService.getBookNumsById(lendBookUserVo.getBook().getId());
        }catch (Exception E){
            return ResponseResult.fail(ErrorCodeEnums.DATABASE_OPERA_EXCEPTION);
        }
        if (bookNum == 0)
            return ResponseResult.fail(ErrorCodeEnums.LACK_BOOK_NUM);
        boolean res = false;
        try {
            res = lendBookUserService.lendBook(lendBookUserVo.getLendBookUser());
        } catch (Exception E) {
            E.printStackTrace();
            return ResponseResult.fail(ErrorCodeEnums.DATABASE_OPERA_EXCEPTION);
        }
        if (res == true) {
            bookService.lendBookSuccessSubNum(lendBookUserVo.getBook().getId());
            lendBookService.addNum(lendBookUserVo.getBook());
            return ResponseResult.success("借阅成功");
        } else return ResponseResult.fail(ErrorCodeEnums.LEND_BOOK_NUM_CEILING_EXCEPTION);
    }

    @ApiOperation("分页查询，显示借阅的人员")
    @ResponseBody
    @GetMapping("/limitLendBookUser")
    public ResponseResult gerLendBookUser(@RequestParam("pageNum") int pageNum,
                                          @RequestParam("pageSize") int pageSize) {
        Page<LendBookUser> lendBookUserPage = null;
        try {
            lendBookUserPage = lendBookUserService.limitLendBookUser(pageNum, pageSize);
        } catch (Exception E) {
            return ResponseResult.fail(ErrorCodeEnums.DATABASE_OPERA_EXCEPTION);
        }
        List<LendBookUser> lendBookUsers = lendBookUserPage.getRecords();
        List<LendUserDateStringVo> lendUsers = new ArrayList<>();
        // 将日期转为字符串
        lendBookUsers.forEach(item -> {
            LendUserDateStringVo lendUserDateStringVo = new LendUserDateStringVo(item.getId(), item.getUsername(), item.getUserId(),
                    item.getLendBookName(), item.getBookIsbn(),DateUtils.dateToString(item.getLendBookStartDate()),
                    DateUtils.dateToString(item.getLendBookEndDate()), item.getLendBookStatus());
            lendUsers.add(lendUserDateStringVo);
        });
        Map<String, Object> map = new HashMap<>();
        map.put("users", lendUsers);
        map.put("total", lendBookUserPage.getTotal());
        return ResponseResult.success(map);
    }

    @ApiOperation("模糊查询")
    @ResponseBody
    @GetMapping("/vagueSearch")
    public ResponseResult vagueSearch(@RequestParam("userId") Integer userId,
                                      @RequestParam("username") String username,
                                      @RequestParam("status") String status) {
        System.out.println(userId);
        List<LendBookUser> list = null;
        try {
            list = lendBookUserService.vagueSearch(userId, username, status);
        } catch (Exception E) {
            return ResponseResult.fail(ErrorCodeEnums.DATABASE_OPERA_EXCEPTION);
        }
        List<LendUserDateStringVo> users = new ArrayList<>();

        list.forEach(item -> {
            LendUserDateStringVo lendUserDateStringVo = new LendUserDateStringVo(item.getId(), item.getUsername(), item.getUserId(),
                    item.getLendBookName(), item.getBookIsbn(),DateUtils.dateToString(item.getLendBookStartDate()),
                    DateUtils.dateToString(item.getLendBookEndDate()), item.getLendBookStatus());
            users.add(lendUserDateStringVo);
        });
        Map<String, Object> map = new HashMap<>();
        map.put("users", users);
        map.put("total", users.size());
        return ResponseResult.success(map);
    }

    // 导出数据
    @SneakyThrows
    @ApiOperation("导出数据")
    @GetMapping("/export")
    @ResponseBody
    public void exportBookDataToExcel(HttpServletResponse response) {
        List<LendBookUser> list = null;
        try {
            list = lendBookUserService.selectAllUser();
        } catch (Exception E) {
        }
        List<LendUserDateStringVo> users = new ArrayList<>();
        list.forEach(item -> {
            LendUserDateStringVo lendUserDateStringVo = new LendUserDateStringVo(item.getId(), item.getUsername(), item.getUserId(),
                    item.getLendBookName(), item.getBookIsbn(),DateUtils.dateToString(item.getLendBookStartDate()),
                    DateUtils.dateToString(item.getLendBookEndDate()), item.getLendBookStatus());
            users.add(lendUserDateStringVo);
        });
        SetExcelResponseUtils.setResponse(response, "借阅人员信息");
        // 设置忽视写入数据库的字段名
        Set<String> excludeColumnFiledNames = new HashSet<>();
        excludeColumnFiledNames.add("id");
        EasyExcel.write(response.getOutputStream())
                .head(LendUserDateStringVo.class)
                .excelType(ExcelTypeEnum.XLSX)
                .sheet("图书信息")
                .excludeColumnFieldNames(excludeColumnFiledNames)
                .doWrite(users);
    }

    @ApiOperation("归还图书")
    @ResponseBody
    @GetMapping("/returnBook")
    public ResponseResult returnBook(@RequestParam("userId") int userId,
                                     @RequestParam("bookIsbn") String bookIsbn) {
        boolean res = false;
        try {
            res = lendBookUserService.returnBook(userId,bookIsbn);
            if (res == false)
                return ResponseResult.fail(ErrorCodeEnums.DATABASE_NO_DATA_EXCEPTION);
            res = bookService.returnBookAddNum(bookIsbn);
        } catch (Exception E) {
            return ResponseResult.fail(ErrorCodeEnums.DATABASE_OPERA_EXCEPTION);
        }
        return ResponseResult.success("归还成功");
    }

    @ApiOperation("查询自己的借阅信息")
    @GetMapping("/queryOwnLendBook")
    @ResponseBody
    public ResponseResult queryOwnBook(@RequestParam("userId") int userId)
    {
        List<LendBookUser> lendBookUsers = null;
        try {
            lendBookUsers = lendBookUserService.queryListLendBookUser(userId);
        }catch (Exception E)
        {
            E.printStackTrace();
            return ResponseResult.fail(ErrorCodeEnums.DATABASE_OPERA_EXCEPTION);
        }
        List<LendUserDateStringVo> users = new ArrayList<>();
        lendBookUsers.forEach(item -> {
            LendUserDateStringVo lendUserDateStringVo = new LendUserDateStringVo(item.getId(), item.getUsername(), item.getUserId(),
                    item.getLendBookName(), item.getBookIsbn(),DateUtils.dateToString(item.getLendBookStartDate()),
                    DateUtils.dateToString(item.getLendBookEndDate()), item.getLendBookStatus());
            users.add(lendUserDateStringVo);
        });
        if (lendBookUsers == null)
             return ResponseResult.fail(ErrorCodeEnums.DATABASE_NO_DATA_EXCEPTION);
         else return ResponseResult.success(users);
    }

}