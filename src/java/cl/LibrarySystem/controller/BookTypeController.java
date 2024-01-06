package cl.LibrarySystem.controller;


import cl.LibrarySystem.pojo.BookType;
import cl.LibrarySystem.result.ErrorCodeEnums;
import cl.LibrarySystem.result.ResponseResult;
import cl.LibrarySystem.service.BookTypeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import java.sql.SQLException;
import java.util.List;

import static cl.LibrarySystem.result.ErrorCodeEnums.DATABASE_OPERA_EXCEPTION;

@RestController
@RequestMapping("/booktype")
public class BookTypeController {

    @Autowired
   BookTypeService bookTypeService;

    @ResponseBody
    @GetMapping("/getBookType")
    public ResponseResult getBookType(){
        List<BookType> bookType = null;
        try {
            bookType = bookTypeService.getBookType();
        }catch (Exception E){
           return ResponseResult.fail(DATABASE_OPERA_EXCEPTION);
        }
        if(bookType.size() > 0)
            return  ResponseResult.success(bookType);
        else return ResponseResult.fail(DATABASE_OPERA_EXCEPTION);
    }
}
