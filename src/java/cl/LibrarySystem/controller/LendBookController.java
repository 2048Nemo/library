package cl.LibrarySystem.controller;

import cl.LibrarySystem.pojo.LendBook;
import cl.LibrarySystem.result.ErrorCodeEnums;
import cl.LibrarySystem.result.ResponseResult;
import cl.LibrarySystem.service.LendBookService;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/lendBook")
public class LendBookController {

    @Autowired
    private LendBookService lendBookService;

    @ApiOperation("获得借阅次数最高的五本图书")
    @ResponseBody
    @GetMapping("/limitLendBooks")
    public ResponseResult limitLendBook()
    {
        List<LendBook> books = null;
        try {
            books = lendBookService.getTopNums();
        }catch (Exception E)
        {
            return ResponseResult.fail(ErrorCodeEnums.DATABASE_OPERA_EXCEPTION);
        }
        if (books ==  null)
            return ResponseResult.fail(ErrorCodeEnums.DATABASE_NO_DATA_EXCEPTION);
        else return ResponseResult.success(books);
    }
}
