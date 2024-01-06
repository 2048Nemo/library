package cl.LibrarySystem.result;


public enum ErrorCodeEnums {
    SYSTEM_EXCEPTION("SYSTEM_EXCEPTION", "系统异常", ResultCode.SYSTEM_ERROR),
    INTERFACE_EXCEPTION("INTERFACE_EXCEPTION", "接口异常", ResultCode.SYSTEM_ERROR),
    PARAM_VALIDATE_EXCEPTION("PARAM_VALIDATE_EXCEPTION", "参数校验异常", ResultCode.PARAM_VALIDATE_ERROR),
    USER_NOT_LOGIN_EXCEPTION("USER_NOT_LOGIN_EXCEPTION", "用户未登录", ResultCode.NOT_LOGIN_ERROR),
    USER_REPEAT_EXCEPTION("USER_REPEAT_EXCEPTION", "用户表存在重复用户信息", ResultCode.NOT_LOGIN_ERROR),
    DATABASE_OPERA_EXCEPTION("DATABASE_OPERA_EXCEPTION","数据库操作失败",ResultCode.DB_OPERATE_ERROR),
    ADD_DATA_INPUT_OPERA_EXCEPTION("ADD_DATA_INPUT_OPERA_EXCEPTION","输入的数据类型错误",ResultCode.ADD_DATA_ERROR),
    EXCEL_EXPORT_EXCEPTION("EXCEL_EXPORT_EXCEPTION", "EXCEL导出异常", ResultCode.EXCEL_OPERATE_ERROR),
    EXCEL_IMPORT_EXCEPTION("EXCEL_IMPORT_EXCEPTION", "EXCEL导入异常", ResultCode.EXCEL_OPERATE_ERROR),
    REPEAT_DATA_EXCEPTION(" REPEAT_DATA_EXCEPTION","用户重复预定",ResultCode.REPEAT_DUE_SEAT),
    DATABASE_NO_DATA_EXCEPTION("DATABASE_NO_DATA_EXCEPTION","数据库无此数据",ResultCode.DATABASE_NO_DATA),
    REPEAT_USER_ID_EXCEPTION("REPEAT_USER_EXCEPTION","用户ID重复",ResultCode.REPEAT_USER_ID),
    USERNAME_OR_PASSWORD_ERROR_EXCEPTION("USERNAME_OR_PASSWORD_ERROR_EXCEPTION","用户名或密码错误",ResultCode.USERNAME_OR_PASSWORD_ERROR),
    LEND_BOOK_NUM_CEILING_EXCEPTION("LEND_BOOK_NUM_CEILING_EXCEPTION","借阅的图书数量达到上限",ResultCode.LEND_BOOK_NUM_CEILING),
    LACK_BOOK_NUM("LACK_BOOK_NUM","图书库存不足",ResultCode.LACK_BOOK_NUM),
    USER_NO_REGISTER_EXCEPTION("USER_NO_REGISTER_EXCEPTION","用户为注册",ResultCode.USER_NO_REGISTER),
    USERNAME_OR_ID_NULL("USERNAME_OR_ID_NULL","用户名或者ID为空",ResultCode.USERNAME_OR_ID_NULL),
    EXCEL_IMPORT_DATATYPE_EXCEPTION("EXCEL_IMPORT_TYPE_EXCEPTION","EXCEL导入数据类型不对",ResultCode.EXCEL_DATATYPE_ERROR),
    EXCEL_IMPORT_TYPE_EXCEPTION("EXCEL_IMPORT_TYPE_EXCEPTION","文件类型不对",ResultCode.EXCEL_TYPE_ERROR);

    /**
     * 错误名字
     */
    private String name;

    /**
     * 错误描述
     */
    private String desc;

    /**
     * 结果编码
     */
    private int code;

    ErrorCodeEnums(String name, String desc, int code) {
        this.name = name;
        this.desc = desc;
        this.code = code;
    }

    public String getName() {
        return name;
    }

    public String getDesc() {
        return desc;
    }

    public int getCode() {
        return code;
    }
}


