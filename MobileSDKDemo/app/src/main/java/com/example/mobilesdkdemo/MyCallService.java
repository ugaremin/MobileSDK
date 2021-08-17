package com.example.mobilesdkdemo;

import android.content.Context;
import android.content.Intent;
import android.hardware.Camera;
import android.util.Log;
import android.widget.Toast;

import com.rbbn.cpaas.mobile.call.IncomingCall;
import com.rbbn.cpaas.mobile.call.OutgoingCall;
import com.rbbn.cpaas.mobile.call.api.CallApplicationListener;
import com.rbbn.cpaas.mobile.call.api.CallInterface;
import com.rbbn.cpaas.mobile.call.api.CallService;
import com.rbbn.cpaas.mobile.call.api.CallState;
import com.rbbn.cpaas.mobile.call.api.IncomingCallInterface;
import com.rbbn.cpaas.mobile.call.api.MediaAttributes;
import com.rbbn.cpaas.mobile.call.api.OutgoingCallCreationCallback;
import com.rbbn.cpaas.mobile.call.api.OutgoingCallInterface;
import com.rbbn.cpaas.mobile.call.api.ProcessListener;
import com.rbbn.cpaas.mobile.call.api.RTPStatisticsHandler;
import com.rbbn.cpaas.mobile.core.webrtc.view.VideoView;
import com.rbbn.cpaas.mobile.messaging.chat.api.ChatService;
import com.rbbn.cpaas.mobile.utilities.Globals;
import com.rbbn.cpaas.mobile.utilities.exception.MobileError;
import com.rbbn.cpaas.mobile.utilities.exception.MobileException;
import com.rbbn.cpaas.mobile.utilities.webrtc.ChangeListenerKey;
import com.rbbn.cpaas.mobile.utilities.webrtc.CodecType;
import com.rbbn.cpaas.mobile.utilities.webrtc.VideoViewType;

import java.beans.PropertyChangeListener;
import java.util.List;
import java.util.Map;

public class MyCallService  {

    CallService callService = CPaaSMeneger.getInstance().getCpaas().getCallService();

    public void CreateCall(String callee, boolean callType) throws MobileException {

            callService.createOutgoingCall(callee, new OutgoingCallCreationCallback() {

            @Override
            public void callCreated(OutgoingCallInterface outgoingCallInterface) {

                CallManager.getInstance().addCallToList(outgoingCallInterface);   // add the callList

                outgoingCallInterface.establishCall(callType);
                outgoingCallInterface.getId();

                Log.i("Call Status","DoubleCallCreationSuccess");
            }

            @Override
            public void callCreationFailed(MobileError mobileError) {

                Log.e("Call Status", "DoubleCallCreationFailed: " + mobileError.getErrorMessage());

            }
        });

    }

    public void CreateVideoCall(String callee, boolean callType,VideoView remoteVideoView, VideoView localVideoView) throws MobileException {

        callService.createOutgoingCall(callee, new OutgoingCallCreationCallback() {

            @Override
            public void callCreated(OutgoingCallInterface outgoingCallInterface) {

                CallManager.getInstance().addCallToList(outgoingCallInterface);  // add the callList

                outgoingCallInterface.setRemoteVideoView(remoteVideoView);
                outgoingCallInterface.setLocalVideoView(localVideoView);
                outgoingCallInterface.establishCall(callType);
                outgoingCallInterface.getId();
                CallManager.getInstance().addCallToList(outgoingCallInterface);

                Log.i("Call Status","VideoCallCreationSuccess");
            }

            @Override
            public void callCreationFailed(MobileError mobileError) {

                Log.e("Call Status", "VideoCallCreationFailed: " + mobileError.getErrorMessage());

            }
        });

    }

    public void CreateSingleCall(String callee) throws MobileException {

        callService.createOutgoingCall(callee, new OutgoingCallCreationCallback() {


            @Override
            public void callCreated(OutgoingCallInterface outgoingCallInterface) {

                CallManager.getInstance().addCallToList(outgoingCallInterface);   // add the callList

                outgoingCallInterface.establishAudioCall();
                outgoingCallInterface.getId();

                Log.i("Call Status","SingleCallCreationSuccess");
            }

            @Override
            public void callCreationFailed(MobileError mobileError) {

                Log.e("Call Status", "SingleCallCreationFailed: " + mobileError.getErrorMessage());

            }
        });

    }


}
