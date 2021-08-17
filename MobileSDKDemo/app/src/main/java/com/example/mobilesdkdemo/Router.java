package com.example.mobilesdkdemo;

import android.content.Context;

public class Router {

    private  static Router instance = new Router();
    private  Context context;
    public static Router getInstance(){
        if (instance==null){
            instance=new Router();
        }

        return instance;
    }

    private Router (){}

    public Context getContext() {
        return context;
    }

    public void setContext(Context context) {
        this.context = context;
    }
}
