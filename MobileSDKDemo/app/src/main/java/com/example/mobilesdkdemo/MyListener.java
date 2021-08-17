package com.example.mobilesdkdemo;

import android.app.Application;
import android.content.Intent;
import android.util.Log;

import com.rbbn.cpaas.mobile.call.api.CallApplicationListener;
import com.rbbn.cpaas.mobile.call.api.CallInterface;
import com.rbbn.cpaas.mobile.call.api.CallState;
import com.rbbn.cpaas.mobile.call.api.IncomingCallInterface;
import com.rbbn.cpaas.mobile.call.api.MediaAttributes;
import com.rbbn.cpaas.mobile.call.api.OutgoingCallInterface;
import com.rbbn.cpaas.mobile.utilities.exception.MobileError;

import java.util.Map;

public class MyListener extends Application implements CallApplicationListener {


    @Override
    public void incomingCall(IncomingCallInterface incomingCallInterface) {

            Log.e("In Coming Call", "In Coming Call is Worked");

            CallManager.getInstance().addCallToList(incomingCallInterface);

            Intent intent = new Intent(Router.getInstance().getContext(), InComingVideoCall.class);
            intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_NEW_TASK);
            Router.getInstance().getContext().startActivity(intent);

    }

    @Override
    public void callStatusChanged(CallInterface callInterface, CallState callState) {

    }

    @Override
    public void mediaAttributesChanged(CallInterface callInterface, MediaAttributes mediaAttributes) {

    }

    @Override
    public void callAdditionalInfoChanged(CallInterface callInterface, Map<String, String> map) {

    }

    @Override
    public void errorReceived(CallInterface callInterface, MobileError mobileError) {

    }

    @Override
    public void errorReceived(MobileError mobileError) {

    }

    @Override
    public void establishCallSucceeded(OutgoingCallInterface outgoingCallInterface) {

    }

    @Override
    public void establishCallFailed(OutgoingCallInterface outgoingCallInterface, MobileError mobileError) {

    }

    @Override
    public void acceptCallSucceed(IncomingCallInterface incomingCallInterface) {

    }

    @Override
    public void acceptCallFailed(IncomingCallInterface incomingCallInterface, MobileError mobileError) {

    }

    @Override
    public void rejectCallSucceeded(IncomingCallInterface incomingCallInterface) {

    }

    @Override
    public void rejectCallFailed(IncomingCallInterface incomingCallInterface, MobileError mobileError) {

    }

    @Override
    public void ignoreSucceed(IncomingCallInterface incomingCallInterface) {

    }

    @Override
    public void ignoreFailed(IncomingCallInterface incomingCallInterface, MobileError mobileError) {

    }

    @Override
    public void forwardCallSucceeded(IncomingCallInterface incomingCallInterface) {

    }

    @Override
    public void forwardCallFailed(IncomingCallInterface incomingCallInterface, MobileError mobileError) {

    }

    @Override
    public void videoStopSucceed(CallInterface callInterface) {

    }

    @Override
    public void videoStopFailed(CallInterface callInterface, MobileError mobileError) {

    }

    @Override
    public void videoStartSucceed(CallInterface callInterface) {

    }

    @Override
    public void videoStartFailed(CallInterface callInterface, MobileError mobileError) {

    }

    @Override
    public void muteCallSucceed(CallInterface callInterface) {

    }

    @Override
    public void muteCallFailed(CallInterface callInterface, MobileError mobileError) {

    }

    @Override
    public void unMuteCallSucceed(CallInterface callInterface) {

    }

    @Override
    public void unMuteCallFailed(CallInterface callInterface, MobileError mobileError) {

    }

    @Override
    public void holdCallSucceed(CallInterface callInterface) {

    }

    @Override
    public void holdCallFailed(CallInterface callInterface, MobileError mobileError) {

    }

    @Override
    public void unHoldCallSucceed(CallInterface callInterface) {

    }

    @Override
    public void unHoldCallFailed(CallInterface callInterface, MobileError mobileError) {

    }

    @Override
    public void endCallSucceeded(CallInterface callInterface) {
        Log.i("End Call","End Call Successfully");
    }

    @Override
    public void endCallFailed(CallInterface callInterface, MobileError mobileError) {

    }

    @Override
    public void ringingFeedbackSucceeded(IncomingCallInterface incomingCallInterface) {

    }

    @Override
    public void ringingFeedbackFailed(IncomingCallInterface incomingCallInterface, MobileError mobileError) {

    }

    @Override
    public void transferCallSucceed(CallInterface callInterface) {

    }

    @Override
    public void transferCallFailed(CallInterface callInterface, MobileError mobileError) {

    }

    @Override
    public void notifyCallProgressChange(CallInterface callInterface) {

    }

}
