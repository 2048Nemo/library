package cl.LibrarySystem.service;

import cl.LibrarySystem.pojo.BookType;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public interface BookTypeService {
    public List<BookType> getBookType();
}
