package cl.LibrarySystem.controller;

import cl.LibrarySystem.Utils.SetExcelResponseUtils;
import cl.LibrarySystem.listener.ExcelListener;
import cl.LibrarySystem.pojo.Book;
import cl.LibrarySystem.pojo.vo.BookVo;
import cl.LibrarySystem.result.ErrorCodeEnums;
import cl.LibrarySystem.result.ResponseResult;
import cl.LibrarySystem.service.BookService;
import com.alibaba.excel.EasyExcel;
import com.alibaba.excel.support.ExcelTypeEnum;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import io.swagger.annotations.ApiOperation;
import lombok.SneakyThrows;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletResponse;
import java.io.InputStream;
import java.util.*;


@RestController
@RequestMapping("/book")
public class BookController {

    @Autowired
    private BookService bookService;

    @ApiOperation("分页查询")
    @GetMapping("/LimitBook")
    @ResponseBody
    public ResponseResult getBookByLimit(@RequestParam(value = "pageNum") int pageNum,
                                         @RequestParam(value = "pageSize") int pageSize) {
        Page<Book> bookByLimit = bookService.getBookByLimit(pageNum, pageSize);
        List<Book> bookList = bookByLimit.getRecords();
        long totalPageSizes = bookByLimit.getTotal();
        Map<String, Object> mapData = new HashMap<>();
        mapData.put("books", bookList);
        mapData.put("total", totalPageSizes);
        return ResponseResult.success(mapData);
    }

    @ApiOperation("模糊查询")
    @GetMapping("/getBooksByVagueSearch")
    @ResponseBody
    public ResponseResult getBooksByVagueSearch(@RequestParam(value = "name") String name,
                                                @RequestParam(value = "author") String author,
                                                @RequestParam(value = "type") String type) {
        System.out.println(name != "");
        System.out.println(author != "");
        System.out.println(type != "");
        List<Book> books = null;
        try {
            books = bookService.getBooksByVagueSearch(name, author, type);
        } catch (Exception e) {
            return ResponseResult.fail(ErrorCodeEnums.DATABASE_OPERA_EXCEPTION);
        }
        Map<String, Object> booksBySearch = new HashMap<>();
        booksBySearch.put("books", books);
        booksBySearch.put("total", books.size());
        return ResponseResult.success(booksBySearch);
    }

    @ApiOperation("用类型查找图书")
    @GetMapping("/getBookByType")
    @ResponseBody
    public ResponseResult getBookByType(@RequestParam(value = "type") String type) {
        List<Book> books = null;
        try {
            books = bookService.getBookByType(type);
        } catch (Exception E) {
            return ResponseResult.fail(ErrorCodeEnums.DATABASE_OPERA_EXCEPTION);
        }
        if (books.size() == 0)
            return ResponseResult.fail(ErrorCodeEnums.DATABASE_OPERA_EXCEPTION);
        Map<String, Object> bookMap = new HashMap<>();
        bookMap.put("books", books);
        bookMap.put("total", books.size());
        return ResponseResult.success(bookMap);
    }

    @ApiOperation(("添加图书"))
    @PostMapping("/InsertNewBook")
    @ResponseBody
    public ResponseResult InsertNewBook(@RequestBody BookVo book) {
        Boolean res = false;
        try {
            res = bookService.insertNewBook(book);
        } catch (Exception E) {
            return ResponseResult.fail(ErrorCodeEnums.DATABASE_OPERA_EXCEPTION);
        }
        if (res == false)
            return ResponseResult.fail(ErrorCodeEnums.DATABASE_OPERA_EXCEPTION);
        else return ResponseResult.success("添加成功");
    }

    @ApiOperation("修改图书")
    @PostMapping("/updateBook")
    @ResponseBody
    public ResponseResult update(@RequestBody BookVo bookVo) {
        Boolean res = false;
        try {
            res = bookService.update(bookVo);
        } catch (Exception E) {
            return ResponseResult.fail(ErrorCodeEnums.DATABASE_OPERA_EXCEPTION);
        }
        if (res == true)
            return ResponseResult.success("修改成功");
        else return ResponseResult.fail(ErrorCodeEnums.DATABASE_OPERA_EXCEPTION);

    }

    @ApiOperation("删除图书")
    @GetMapping("/deleteBookById")
    public ResponseResult deleteBookById(@RequestParam("id") Integer id) {
        Boolean res = false;
        try {
            res = bookService.deleteDataById(id);
        } catch (Exception E) {
            return ResponseResult.fail(ErrorCodeEnums.DATABASE_OPERA_EXCEPTION);
        }
        if (res == true)
            return ResponseResult.success("删除成功");
        else return ResponseResult.fail(ErrorCodeEnums.DATABASE_OPERA_EXCEPTION);
    }

    @ApiOperation("删除多个图书")
    @PostMapping("/deleteBooksByIds")
    @ResponseBody
    public ResponseResult deleteBooksByIds(@RequestBody List<Book> book) {
        Boolean res = false;
        try {
            res = bookService.deleteBooksByIds(book);
        } catch (Exception e) {
            return ResponseResult.fail(ErrorCodeEnums.DATABASE_OPERA_EXCEPTION);
        }
        if (res == true)
            return ResponseResult.success("删除成功");
        else return ResponseResult.fail(ErrorCodeEnums.DATABASE_OPERA_EXCEPTION);
    }

    // 导出数据
    @SneakyThrows
    @ApiOperation("导出数据")
    @GetMapping("/export")
    @ResponseBody
    public void exportBookDataToExcel(HttpServletResponse response) {
        List<Book> books = null;
        try {
            books = bookService.selectAllBooks();
        } catch (Exception E) {
        }
        SetExcelResponseUtils.setResponse(response, "图书信息");
        // 设置忽视写入数据库的字段名
        Set<String> excludeColumnFiledNames = new HashSet<>();
        excludeColumnFiledNames.add("version");
        excludeColumnFiledNames.add("id");
        EasyExcel.write(response.getOutputStream())
                .head(Book.class)
                .excelType(ExcelTypeEnum.XLSX)
                .sheet("图书信息")
                .excludeColumnFieldNames(excludeColumnFiledNames)
                .doWrite(books);
    }

    @SneakyThrows
    @ApiOperation("导入数据")
    @PostMapping("/import")
    public ResponseResult importDataByExcel(@RequestParam MultipartFile file) {
        String filename = file.getOriginalFilename();
        System.out.println(filename);
        String[] split = filename.split("\\.");
        if (split[split.length - 1].toLowerCase().equals("xlsx") == false &&
                split[split.length - 1].toLowerCase().equals("xls") == false)
        {
            System.out.println("不是excel文件");
            return ResponseResult.fail(ErrorCodeEnums.EXCEL_IMPORT_TYPE_EXCEPTION);
        }
        else
        try {
            ExcelListener excelListener = new ExcelListener();
            Map<String,Object> map = new HashMap<>();
            map.put("name","Book");
            map.put("data",new Book());
            map.put("service",bookService);
            excelListener.setMap(map);
            InputStream inputStream = file.getInputStream();
            EasyExcel.read(inputStream,Book.class,excelListener).sheet().doRead();
        }catch (Exception E){
            return ResponseResult.fail(ErrorCodeEnums.EXCEL_IMPORT_DATATYPE_EXCEPTION);
        }
       return ResponseResult.success("文件导入成功");
    }
}
