package cl.LibrarySystem.pojo.vo;

import io.swagger.models.auth.In;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class UserVo {

    private Integer userId;

    private String username;

    private String password;

    private String email;

    private String token;

    private Integer limit;
}
