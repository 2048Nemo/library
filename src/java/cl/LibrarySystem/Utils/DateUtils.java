package cl.LibrarySystem.Utils;

import java.text.SimpleDateFormat;
import java.util.Date;

public class DateUtils {
    private DateUtils(){}
    public static String dateToString(Date date)
    {
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        return sdf.format(date);
    }

    // 借阅
    public static boolean judgeDateExcess(Date date)
    {
        return date.compareTo(new Date()) >= 0 ?false : true;
    }

}
