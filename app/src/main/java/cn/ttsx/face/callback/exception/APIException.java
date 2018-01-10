package cn.ttsx.face.callback.exception;

/**
 * author: XL
 * versionName:
 * creatTime:2017/6/2
 * describe:
 */

public class APIException extends Exception {

    private String mCode;
    private String mMsg;

    public APIException(String code, String msg) {
        super(msg);
        getErrorDesc(code,msg);
    }

    protected String getErrorDesc(String code, String msg){
        this.mMsg = msg;
        this.mCode = code;
        return msg;
    }

    public String getCode() {
        return mCode;
    }

    public String getMsg() {
        return mMsg;
    }
}
