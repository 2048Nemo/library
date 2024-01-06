package cl.LibrarySystem.Utils;

import javax.servlet.http.HttpServletResponse;
import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;

public class SetExcelResponseUtils {
    private SetExcelResponseUtils(){}

    /**
     *
     * @param response  响应结果对象
     * @param filename  文件名
     */
    public static void setResponse(HttpServletResponse response,String filename) throws UnsupportedEncodingException {
        response.setContentType("application/vnd.openxmlformats-officedocument.spreadsheetml.sheet");
        response.setCharacterEncoding("utf-8");
        String fileName = URLEncoder.encode(filename, "UTF-8");
        response.setHeader("Content-Disposition", "attachment;filename=" + fileName + ".xlsx");
    }
}
