package cl.LibrarySystem.service;

import cl.LibrarySystem.pojo.StudyRoom;
import cl.LibrarySystem.pojo.vo.SeatAndUserVo;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public interface StudyRoomService {
    Page<StudyRoom> getStudyRoomByLimit(int pageNum, int pageSize);

    List<StudyRoom> querySeatStatus(String status);

    Boolean resetSeat(int id);

    Boolean dueSeat(SeatAndUserVo seatAndUserVo);

    List<StudyRoom> vagueSearch( String seatId, String status);
    void insert();

    SeatAndUserVo queryOwnMsg(int userId);

    Page<StudyRoom> getStudyRoomByEmpty(int pageNum, int pageSize);
}
