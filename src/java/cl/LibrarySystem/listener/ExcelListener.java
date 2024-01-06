package cl.LibrarySystem.listener;

import cl.LibrarySystem.pojo.Book;
import com.alibaba.excel.context.AnalysisContext;
import com.alibaba.excel.event.AnalysisEventListener;
import lombok.SneakyThrows;
import org.springframework.stereotype.Component;

import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Component
public class ExcelListener<T> extends AnalysisEventListener<T> {

    Map<String, Object> map = new HashMap<>();

    public void setMap(Map map) {
        this.map = map;
    }

    List<T> dataList = new ArrayList<>();


    /*一行一行的读取excel 中的内容，从第二行读取*/
    @Override
    public void invoke(T data, AnalysisContext analysisContext) {
        System.out.println("每一行的内容" + data);
        dataList.add(data);
    }

    /*读取表头中的内容*/
    @Override
    public void invokeHeadMap(Map<Integer, String> headMap, AnalysisContext context) {
        System.out.println("表头的内容为" + headMap);
    }

    /*读取完成之后执行的方法*/
    @SneakyThrows
    @Override
    public void doAfterAllAnalysed(AnalysisContext analysisContext) {
        Object name = map.get("name");
        Object data = map.get("data");
        if (data instanceof Book){
            insertBookData();
        }

    }

    @SneakyThrows
    public void insertBookData() {
        System.out.println(dataList);
        Object service = map.get("service");
        Method method = service.getClass().getMethod("importBooks", List.class);
        method.invoke(service,dataList);
    }
}
