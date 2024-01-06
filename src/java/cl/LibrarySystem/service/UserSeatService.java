package cl.LibrarySystem.service;

import cl.LibrarySystem.pojo.StudyRoom;
import cl.LibrarySystem.pojo.UserSeat;
import cl.LibrarySystem.pojo.vo.SeatAndUserVo;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public interface UserSeatService {
    List<SeatAndUserVo> getSeatUser(List<StudyRoom> studyRooms);

    Boolean dueSeatUser(SeatAndUserVo seatAndUserVo);

    Boolean delSeatUser(int id);

    List<SeatAndUserVo> vagueSearch(List<StudyRoom> studyRooms);

    UserSeat selectUserSeatByUserId(Integer userId);


}
