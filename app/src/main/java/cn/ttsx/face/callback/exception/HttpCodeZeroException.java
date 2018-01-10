package cn.ttsx.face.callback.exception;

/**
 * author: XL
 * versionName:
 * creatTime:2017/6/2
 * describe:
 */

public class HttpCodeZeroException extends APIException {
    public HttpCodeZeroException(String status, String msg) {
        super(status,msg);
    }
}
