package com.example.mobilesdkdemo.Adapter;

import android.content.Context;

import com.example.mobilesdkdemo.Model.Chat;
import com.example.mobilesdkdemo.Model.Messages;

import java.util.ArrayList;

public class MessagesAdapter {

    private ArrayList<Messages> messages;
    private Context cContext;

    public MessagesAdapter(ArrayList<Chat> messages, Context cContext) {
        this.cContext=cContext;

    }


}
