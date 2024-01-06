package cl.LibrarySystem.service.impl;

import cl.LibrarySystem.mapper.StudyRoomMapper;
import cl.LibrarySystem.mapper.UserSeatMapper;
import cl.LibrarySystem.pojo.StudyRoom;
import cl.LibrarySystem.pojo.UserSeat;
import cl.LibrarySystem.pojo.vo.SeatAndUserVo;
import cl.LibrarySystem.service.UserSeatService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.*;

@Service
public class UserSeatServiceImpl implements UserSeatService {

    @Autowired
    UserSeatMapper userSeatMapper;

    @Autowired
    StudyRoomMapper studyRoomMapper;

    // 查询
    @Override
    public List<SeatAndUserVo> getSeatUser(List<StudyRoom> studyRooms) {
        List<SeatAndUserVo> seatAndUserVos = new ArrayList<>();
        studyRooms.forEach(item -> {
            UserSeat userSeat = new UserSeat();
            try {
                userSeat = userSeatMapper.getUserSeat(item.getTDueUserId());
            }catch (Exception E){
            }
            SeatAndUserVo seatAndUserVo = new SeatAndUserVo(item, userSeat);
            seatAndUserVos.add(new SeatAndUserVo(item, userSeat));
        });
        return seatAndUserVos;
    }
    // 预定
    @Override
    public Boolean dueSeatUser(SeatAndUserVo seatAndUserVo) {
        StudyRoom studyRoom = seatAndUserVo.getStudyRoom();
        UserSeat userSeat = seatAndUserVo.getUserSeat();
        Map<String, Object> map = new HashMap<>();
        map.put("t_user_id", userSeat.getTUserId());
        List<UserSeat> seats = userSeatMapper.selectByMap(map);
        if (seats.size() != 0)
            return false;
        else {
            userSeat.setTDueSeatnumber(studyRoom.getTSeatNumber());
            int insert = userSeatMapper.insert(userSeat);
            return insert == 0 ? false : true;
        }
    }

    // 取消预定
    @Override
    public Boolean delSeatUser(int id) {
        int delete = userSeatMapper.deleteById(id);
        return delete == 0 ? false : true;
    }

    // 模糊查询
    @Override
    public List<SeatAndUserVo> vagueSearch(List<StudyRoom> studyRooms) {

        return null;
    }

    @Override
    public UserSeat selectUserSeatByUserId(Integer userId) {
        UserSeat userSeat = userSeatMapper.getUserSeat(userId);
        return userSeat;
    }
}
