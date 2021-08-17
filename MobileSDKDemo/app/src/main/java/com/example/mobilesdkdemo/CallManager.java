package com.example.mobilesdkdemo;

import com.rbbn.cpaas.mobile.call.api.CallInterface;

import java.util.ArrayList;

public class CallManager {

    private static CallManager instance = new CallManager();

    public static CallManager getInstance(){

        if (instance==null){
            instance=new CallManager();
        }
        return instance;
    }

    private ArrayList<CallInterface> callList = new ArrayList<>();

    public void addCallToList(CallInterface callInterface){
        callList.add(callInterface);
    }

    public ArrayList<CallInterface> getCallList(){
        return callList;
    }


}
