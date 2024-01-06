package cl.LibrarySystem.pojo.vo;

import cl.LibrarySystem.pojo.Book;
import cl.LibrarySystem.pojo.LendBookUser;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class LendBookUserVo {
    private Book book;
    private LendBookUser lendBookUser;
}
