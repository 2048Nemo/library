package cl.LibrarySystem.handler;

import com.baomidou.mybatisplus.core.handlers.MetaObjectHandler;
import org.apache.ibatis.reflection.MetaObject;
import org.springframework.stereotype.Component;

import java.time.LocalDate;
import java.util.Calendar;
import java.util.Date;

@Component
public class MyMetaObjectHandler implements MetaObjectHandler {
    @Override
    public void insertFill(MetaObject metaObject) {

        this.setFieldValByName("version", new Integer(1), metaObject);
        this.setFieldValByName("tStatus", "空", metaObject);
        this.setFieldValByName("tDueDate", new Date(new Date().getTime() + 8 * 60 * 60 * 1000), metaObject);
        this.setFieldValByName("tEndDate", getDate(), metaObject);
        this.setFieldValByName("enrollDate", new Date(new Date().getTime() + 8 * 60 * 60 * 1000), metaObject);
        this.setFieldValByName("userLimit", 0, metaObject);
        this.setFieldValByName("lendBookStartDate", new Date(new Date().getTime() + 8 * 60 * 60 * 1000), metaObject);
        this.setFieldValByName("lendBookStatus", "借阅中", metaObject);
        this.setFieldValByName("lendNum",1,metaObject);
        this.setFieldValByName("nums",1,metaObject);
    }

    @Override
    public void updateFill(MetaObject metaObject) {

    }

    private Date getDate() {
        Calendar cal = Calendar.getInstance();
        cal.set(Calendar.SECOND,0);
        cal.set(Calendar.MINUTE,0);
        cal.set(Calendar.HOUR_OF_DAY,0);
        cal.add(Calendar.DATE,1);
        return new Date(cal.getTime().getTime() + 8 * 60 * 60 * 1000);
    }
}
