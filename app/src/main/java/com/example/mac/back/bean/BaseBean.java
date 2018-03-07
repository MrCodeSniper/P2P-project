package com.example.mac.back.bean;

/**
 * Created by mac on 2018/3/2.
 */

public class BaseBean {


    public int getCode() {
        return code;
    }

    public void setCode(int code) {
        this.code = code;
    }

    /**
     * success : true
     */
    private int code;

    private boolean success;

    private String msg;


    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

    public boolean isSuccess() {
        return success;
    }

    public void setSuccess(boolean success) {
        this.success = success;
    }
}
