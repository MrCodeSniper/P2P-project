package com.example.mac.back.event;

import com.example.mac.back.bean.User;

/**
 * Created by mac on 2018/3/8.
 */

public class MessageEvent{
    private String message;
    private User user;
    public  MessageEvent(String message,User user){
        this.user=user;
        this.message=message;
    }

    public MessageEvent(String message) {
        this.message = message;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }
}


