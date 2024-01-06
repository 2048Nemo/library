package cl.LibrarySystem.pojo.vo;


import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class BookVo {

    private String name;

    private String author;

    private String type;

    private String publish;

    private String ISBN;

    private Integer nums;

   private double price;

}
