package cl.LibrarySystem.controller;

import cl.LibrarySystem.pojo.LossBook;
import cl.LibrarySystem.result.ErrorCodeEnums;
import cl.LibrarySystem.result.ResponseResult;
import cl.LibrarySystem.service.LendBookUserService;
import cl.LibrarySystem.service.LossBookService;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/lossBook")
public class LossBookController {

    @Autowired
    private LossBookService lossBookService;

    @Autowired
    private LendBookUserService lendBookUserService;

    @ApiOperation("挂失图书")
    @PostMapping("/lossBook")
    @ResponseBody
    public ResponseResult lossBook(@RequestBody LossBook lossBook) {
        boolean res = false;
        try {
            res = lossBookService.lossBook(lossBook);
        } catch (Exception e) {
            e.printStackTrace();
            return ResponseResult.fail(ErrorCodeEnums.DATABASE_OPERA_EXCEPTION);
        }
        if (res == true)
            return ResponseResult.success("挂失成功");
        else return ResponseResult.fail(ErrorCodeEnums.DATABASE_OPERA_EXCEPTION);
    }

    @ApiOperation("分页查询")
    @GetMapping("/LimitLossBook")
    @ResponseBody
    public ResponseResult getLossBookByLimit(@RequestParam(value = "pageNum") int pageNum,
                                         @RequestParam(value = "pageSize") int pageSize) {
        Page<LossBook> bookByLimit = lossBookService.getBookByLimit(pageNum, pageSize);
        List<LossBook> bookList = bookByLimit.getRecords();
        long totalPageSizes = bookByLimit.getTotal();
        Map<String, Object> mapData = new HashMap<>();
        mapData.put("books", bookList);
        mapData.put("total", totalPageSizes);
        return ResponseResult.success(mapData);
    }

    @ApiOperation("虚假挂失处理")
    @GetMapping("/dispose")
    @ResponseBody
    public ResponseResult disposeBook(@RequestParam("userId") Integer userId,
                                      @RequestParam("isbn") String isbn)
    {
        boolean res = false;
        try{
            res = lossBookService.disposeBook(userId,isbn);
            if (res == false)
                return ResponseResult.fail(ErrorCodeEnums.DATABASE_NO_DATA_EXCEPTION);
            res = lendBookUserService.delUserByUserIdAndIsbn(userId,isbn);
        }catch (Exception E)
        {
            E.printStackTrace();
            return ResponseResult.fail(ErrorCodeEnums.DATABASE_OPERA_EXCEPTION);
        }
        return ResponseResult.success("处理成功");
    }
}
