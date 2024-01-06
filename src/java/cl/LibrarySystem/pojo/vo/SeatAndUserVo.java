package cl.LibrarySystem.pojo.vo;

import cl.LibrarySystem.pojo.StudyRoom;
import cl.LibrarySystem.pojo.UserSeat;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
@Data
@AllArgsConstructor
@NoArgsConstructor
public class SeatAndUserVo {
    private StudyRoom studyRoom;
    private UserSeat userSeat;
}
