package com.example.mobilesdkdemo.Model;

import android.widget.ImageView;

public class Chat {

    private String username, lastMessage;


    public Chat(String username, String lastMessage) {

        this.username=username;
        this.lastMessage=lastMessage;

    }

    public String getUsename() {
        return username;
    }

    public void setUsename(String usename) {
        this.username = usename;
    }

    public String getLastMessage() {
        return lastMessage;
    }

    public void setLastMessage(String lastMessage) {
        this.lastMessage = lastMessage;
    }


}
