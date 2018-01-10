package cn.ttsx.face.callback;

import java.io.Serializable;

/**
 * author: XL
 * versionName:
 * creatTime:2017/6/1
 * describe:
 */

public class HttpBean<T> implements Serializable {
    private String msg;
    private String code;//0：失败  1：成功  2：未登录 3：无权限
    private T data;


    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public T getData() {
        return data;
    }

    public void setData(T data) {
        this.data = data;
    }


}
