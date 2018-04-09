package com.example.mac.back.data;

/**
 * Created by mac on 2018/4/4.
 */

public abstract class Handler {

    protected Handler successor;

    public Handler getSuccessor() {
        return successor;
    }

    public void setSuccessor(Handler successor) {
        this.successor = successor;
    }

    public abstract String handler(String condition);

}
