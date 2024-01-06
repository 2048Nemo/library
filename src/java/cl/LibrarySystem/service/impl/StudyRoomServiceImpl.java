package cl.LibrarySystem.service.impl;

import cl.LibrarySystem.mapper.StudyRoomMapper;
import cl.LibrarySystem.mapper.UserSeatMapper;
import cl.LibrarySystem.pojo.StudyRoom;
import cl.LibrarySystem.pojo.UserSeat;
import cl.LibrarySystem.pojo.vo.SeatAndUserVo;
import cl.LibrarySystem.service.StudyRoomService;
import cn.hutool.core.lang.func.VoidFunc;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.sql.Wrapper;
import java.util.*;

@Service
public class StudyRoomServiceImpl implements StudyRoomService {

    @Autowired
    StudyRoomMapper studyRoomMapper;

    @Autowired
    UserSeatMapper userSeatMapper;

    // 分页查询
    @Override
    public Page<StudyRoom> getStudyRoomByLimit(int pageNum, int pageSize) {
        update();
        Page<StudyRoom> page = new Page<>(pageNum, pageSize);
        Page<StudyRoom> studyRooms = studyRoomMapper.selectPage(page, null);
        return studyRooms;
    }

    // 查询座位状态
    @Override
    public List<StudyRoom> querySeatStatus(String status) {
        Map<String, Object> map = new HashMap<>();
        map.put("t_status", status);
        List<StudyRoom> seats = studyRoomMapper.selectByMap(map);
        return seats;
    }

    // 取消预定/离开自习室
    @Override
    public Boolean resetSeat(int id) {
        int update = studyRoomMapper.resetDueSeat(id);
        if (update == 0)
            return false;
        else return true;
    }

    // 预定座位
    @Override
    public Boolean dueSeat(SeatAndUserVo seatAndUserVo) {
        StudyRoom studyRoom = seatAndUserVo.getStudyRoom();
        UserSeat userSeat = seatAndUserVo.getUserSeat();
        Map<String, Object> map = new HashMap<>();
        map.put("t_due_user_id", studyRoom.getTDueUserId());
        List<StudyRoom> rooms = studyRoomMapper.selectByMap(map);
        if (rooms.size() != 0)
            return false;
        else {
            studyRoom.setTDueUserId(userSeat.getTUserId());
            int update = studyRoomMapper.updateById(studyRoom);
            return update == 0 ? false : true;
        }
    }

    // 模糊查询
    @Override
    public  List<StudyRoom> vagueSearch( String sId, String status) {
       QueryWrapper<StudyRoom> wrapper = new QueryWrapper<>();
        if (sId != null && sId != "")
            wrapper.like("t_seat_number",sId);
        if (status != null && status != "");
            wrapper.like("t_status",status);
        List<StudyRoom> studyRooms = studyRoomMapper.selectList(wrapper);
        return studyRooms;
    }

    @Override
    public void insert() {
        for (int i = 1; i <= 30; i++) {
            studyRoomMapper.insert(new StudyRoom("一楼", "1-1-" + i));
            studyRoomMapper.insert(new StudyRoom("一楼", "1-2-" + i));
            studyRoomMapper.insert(new StudyRoom("二楼", "2-1-" + i));
            studyRoomMapper.insert(new StudyRoom("二楼", "2-2-" + i));
            studyRoomMapper.insert(new StudyRoom("三楼", "3-1-" + i));
            studyRoomMapper.insert(new StudyRoom("三楼", "3-2-" + i));
        }
    }

    @Override
    public SeatAndUserVo queryOwnMsg(int userId) {
      StudyRoom seat =   studyRoomMapper.queryOwm(userId);
      UserSeat user = userSeatMapper.queryOwn(userId);
        return new SeatAndUserVo(seat,user);
    }

    // 空座位
    @Override
    public Page<StudyRoom> getStudyRoomByEmpty(int pageNum, int pageSize) {
        update();
        QueryWrapper wrapper = new QueryWrapper();
        wrapper.eq("t_status","空");
        Page<StudyRoom> page = new Page<>(pageNum, pageSize);
        Page<StudyRoom> studyRooms = studyRoomMapper.selectPage(page, wrapper);
        return studyRooms;
    }

    // 座位默认预定一天
    private void update()
    {
        List<UserSeat> list = userSeatMapper.selectList(null);
        for (int i = 0; i < list.size(); i++)
        {
            UserSeat userSeat = list.get(i);
            Calendar nowDate = Calendar.getInstance();
            Calendar endDate = Calendar.getInstance();
            endDate.setTime(userSeat.getTEndDate());
            endDate.set(Calendar.SECOND,0);
            endDate.set(Calendar.MINUTE,0);
            endDate.set(Calendar.HOUR_OF_DAY,0);
            if (endDate.compareTo(nowDate) < 1)
            {
                Integer userId = userSeat.getTUserId();
                userSeatMapper.deleteById(userSeat);
                studyRoomMapper.resetDueSeatByUserId(userId);
            }
        }
    }
}
