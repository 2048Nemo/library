package cl.LibrarySystem.controller;

import cl.LibrarySystem.pojo.StudyRoom;
import cl.LibrarySystem.pojo.vo.SeatAndUserVo;
import cl.LibrarySystem.result.ErrorCodeEnums;
import cl.LibrarySystem.result.ResponseResult;
import cl.LibrarySystem.service.StudyRoomService;
import cl.LibrarySystem.service.UserSeatService;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/studyRoom")
public class StudyRoomController {

    @Autowired
    StudyRoomService studyRoomService;

    @Autowired
    UserSeatService userSeatService;

    @GetMapping("/insert")
    public void insert()
    {
        studyRoomService.insert();
    }


    @ResponseBody
    @GetMapping("/getSeatByLimit")
    @ApiOperation("分页查询，显示数据")
    public ResponseResult getSeatByLimit(@RequestParam(value = "pageNum") int pageNum,
                                         @RequestParam(value = "pageSize") int pageSize){
        Page<StudyRoom> studyRooms = studyRoomService.getStudyRoomByLimit(pageNum, pageSize);
        Map<String,Object> map = new HashMap<>();
        List<SeatAndUserVo> seatAndUserVos  = userSeatService.getSeatUser(studyRooms.getRecords());
        map.put("total",studyRooms.getTotal());
        map.put("seatAndUsers", seatAndUserVos);
        return ResponseResult.success(map);
    }

    @ResponseBody
    @GetMapping("/getSeatByEmpty")
    @ApiOperation("分页查询，显示数据")
    public ResponseResult getSeatByEmpty(@RequestParam(value = "pageNum") int pageNum,
                                         @RequestParam(value = "pageSize") int pageSize){
        Page<StudyRoom> studyRooms = studyRoomService.getStudyRoomByEmpty(pageNum, pageSize);
        Map<String,Object> map = new HashMap<>();
        List<SeatAndUserVo> seatAndUserVos  = userSeatService.getSeatUser(studyRooms.getRecords());
        map.put("total",studyRooms.getTotal());
        map.put("seatAndUsers", seatAndUserVos);
        return ResponseResult.success(map);
    }

    @ApiOperation("取消预定")
    @ResponseBody
    @PostMapping("/resetSeat")
    public ResponseResult resetSeat(@RequestBody SeatAndUserVo seatAndUserVo){
        System.out.println(seatAndUserVo);
        Boolean resStudy = false;
        Boolean resSeatUser = false;
        try{
            resStudy = studyRoomService.resetSeat(seatAndUserVo.getStudyRoom().getTId());
            resSeatUser = userSeatService.delSeatUser(seatAndUserVo.getUserSeat().getTId());
        }catch (Exception e){
            return ResponseResult.fail(ErrorCodeEnums.DATABASE_OPERA_EXCEPTION);
        }
        System.out.println(resStudy);
        System.out.println(resSeatUser);
        if(resSeatUser == true && resStudy == true)
            return ResponseResult.success("取消成功");
        else return ResponseResult.fail(ErrorCodeEnums.DATABASE_NO_DATA_EXCEPTION);
    }

    @ApiOperation("预定座位")
    @ResponseBody
    @PostMapping("/dueSeat")
    public ResponseResult dueSeat(@RequestBody SeatAndUserVo seatAndUserVo){
        System.out.println(seatAndUserVo);
        Boolean resStudy = false;
        Boolean resUser = false;
           try {
              resStudy =  studyRoomService.dueSeat(seatAndUserVo);
              if (resStudy == false)
                  return ResponseResult.fail(ErrorCodeEnums.REPEAT_DATA_EXCEPTION);
              resUser = userSeatService.dueSeatUser(seatAndUserVo);
           }catch (Exception E){
               return ResponseResult.fail(ErrorCodeEnums.DATABASE_OPERA_EXCEPTION);
           }
           if(resStudy == true && resUser == true)
        return ResponseResult.success("预定成功");
           else return ResponseResult.fail(ErrorCodeEnums.REPEAT_DATA_EXCEPTION);
    }

    @ApiOperation("模糊查询")
    @ResponseBody
    @GetMapping("/vagueQuery")
    public ResponseResult vagueQuery(@RequestParam(value = "seatId") String seatId,
                                     @RequestParam(value = "status") String status)
    {
        List<SeatAndUserVo> seatAndUserVoList = null;
      try {
          List<StudyRoom> studyRooms = studyRoomService.vagueSearch(seatId, status);
           seatAndUserVoList = userSeatService.getSeatUser(studyRooms);
      }catch (Exception E)
      {
          return ResponseResult.fail(ErrorCodeEnums.DATABASE_OPERA_EXCEPTION);
      }
      Map<String,Object> map = new HashMap<>();
      map.put("seatAndUser",seatAndUserVoList);
      map.put("total",seatAndUserVoList.size());
        return ResponseResult.success(map);
    }

    @ApiOperation("查询自己预定的信息")
    @GetMapping("/queryOwnMsg")
    @ResponseBody
    public ResponseResult queryOwmMsg(@RequestParam("userId") int userId)
    {
        SeatAndUserVo seatAndUserVo = null;
      try{
           seatAndUserVo = studyRoomService.queryOwnMsg(userId);
      }catch (Exception E)
      {
          return ResponseResult.fail(ErrorCodeEnums.DATABASE_NO_DATA_EXCEPTION);
      }
        return ResponseResult.success(seatAndUserVo);
    }
}
