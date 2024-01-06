package cl.LibrarySystem.result;


public interface ResultCode {

    /**
     * 成功
     */
    int SUCCESS = 0;

    /**
     * 系统异常
     */
    int SYSTEM_ERROR = 1;

    /**
     * 参数校验失败
     */
    int PARAM_VALIDATE_ERROR = 2;

    /**
     * 用户未登录
     */
    int NOT_LOGIN_ERROR = 3;

    /**
     * 数据库操作失败
     */
    int DB_OPERATE_ERROR = 4;

    /**
     * 导入文件类型失败
     */
    int EXCEL_TYPE_ERROR = 5;

    /**
     * 导入EXCEL数据类型失败
     */
    int EXCEL_DATATYPE_ERROR = 6;

    /**
     * EXCEL 操作失败
     */
    int EXCEL_OPERATE_ERROR = 7;

    /**
     * 添加数据类型错误
     */
    int ADD_DATA_ERROR = 8;

    /**
     *  重复预定
     */
    int REPEAT_DUE_SEAT = 9;

    /**
     * 数据库暂无该数据
     */
    int DATABASE_NO_DATA = 10;

    /**
     * 用户id重复
     */
     int REPEAT_USER_ID = 11;

    /**
     * 用户名或密码错误
     */
    int USERNAME_OR_PASSWORD_ERROR = 12;

    /**
     * 图书库存不足
     */
    int LACK_BOOK_NUM = 13;

    /**
     * 借书数量达到上限
     */
    int LEND_BOOK_NUM_CEILING = 14;

    /**
     * 用户未注册
     */
    int USER_NO_REGISTER = 15;

    /**
     * 用户名或者id为空
     */
    int USERNAME_OR_ID_NULL = 16;
}
