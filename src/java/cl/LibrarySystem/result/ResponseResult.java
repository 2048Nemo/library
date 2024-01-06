package cl.LibrarySystem.result;

import java.io.Serializable;
import java.util.List;

public class ResponseResult<T> implements Serializable {

    private static final long serialVersionUID = 958295628567280402L;

    /**
     * 结果编码
     */
    private int code;

    /**
     * 返回值
     */
    private T data;

    /**
     * 错误提示
     */
    private String msg;


    /**
     * 默认响应成功
     */
    public ResponseResult() {
        code = ResultCode.SUCCESS;
    }

    /**
     * 正常响应成功
     *
     * @param data 响应数据
     */
    public ResponseResult(T data) {
        this(ResultCode.SUCCESS, data, null);
    }

    /**
     * 设置响应结果
     *
     * @param code 响应码
     * @param data 响应数据
     */
    public ResponseResult(int code, T data, String msg) {
        this.code = code;
        this.msg = msg;
        this.data = data;
    }

    public ResponseResult(int code,String msg) {
        this.code = code;
        this.msg = msg;
    }


    /**
     * @Method: success
     * @Description: 默认执行成功
     * @Params: []
     * @History:
     **/
    public static <T> ResponseResult<T> success() {
        return new ResponseResult<T>();
    }


    /**
     *
     * @param msg  带提示信息
     * @param <T>
     * @return
     */
    public static <T> ResponseResult<T> success(String msg){
        return new ResponseResult<T>(0,msg);
    }
    /**
     * @Method: success
     * @Description:  默认执行成功带结果
     * @Params: [data]
     * @History:
     **/
    public static <T> ResponseResult<T> success(T data) {
        return new ResponseResult<T>(0,data,"操作成功");
    }

    /**
     * @Method: fail
     * @Description:  默认执行失败
     * @Params: []
     * @History:
     **/
    public static <T> ResponseResult<T> fail() {
        return new ResponseResult<T>();
    }

    /**
     * @Method: fail
     * @Description:  默认执行失败带消息
     * @Params: [msg]
     * @History:
     **/
    public static <T> ResponseResult<T> fail(String msg) {
        return new ResponseResult<T>(ErrorCodeEnums.SYSTEM_EXCEPTION.getCode(), msg);
    }

    /**
     *
     * @param errorType  自定义异常错误
     * @param <T>
     * @return
     */
    public static <T> ResponseResult<T> fail(ErrorCodeEnums errorType) {
        return new ResponseResult<T>(errorType.getCode(),errorType.getDesc());
    }
    public static <T> boolean isSuccess(ResponseResult<T> result) {
        return 0 == result.getCode();
    }

    public static <T> boolean isFail(ResponseResult<T> result) {
        return !isSuccess(result);
    }

    public int getCode() {
        return code;
    }

    public void setCode(int code) {
        this.code = code;
    }

    public T getData() {
        return data;
    }

    public void setData(T data) {
        this.data = data;
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

    @Override
    public String toString() {
        return "ResponseResult{" +
                "code=" + code +
                ", data=" + data +
                ", msg='" + msg + '\'' +
                '}';
    }
}
