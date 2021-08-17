package com.example.mobilesdkdemo;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Chronometer;
import android.widget.Toast;

import com.rbbn.cpaas.mobile.call.OutgoingCall;
import com.rbbn.cpaas.mobile.call.api.CallInterface;
import com.rbbn.cpaas.mobile.call.api.CallService;
import com.rbbn.cpaas.mobile.call.api.IncomingCallInterface;
import com.rbbn.cpaas.mobile.call.api.OutgoingCallInterface;
import com.rbbn.cpaas.mobile.core.webrtc.view.VideoView;
import com.rbbn.cpaas.mobile.utilities.exception.MobileException;

public class InOutGoingVideoCallActivity extends AppCompatActivity {

    Button btn_EndCall, btn_hold, btn_mute, btn_video;
    VideoView localVideoView,remoteVideoView;
    boolean muteStatus, holdStatus, videoStatus;
    Chronometer chronometer;

    MyCallService myCallService=new MyCallService();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_in_out_going_video_call);

        // set the video views
        localVideoView=findViewById(R.id.localVideoView);
        remoteVideoView=findViewById(R.id.remoteVideoView);

        // get the callee
        Bundle getTarget=getIntent().getExtras();
        String remoteUser="";

        // accept the call
        if (getTarget==null){
            remoteUser = "remote";
            CallInterface activeCall = CallManager.getInstance().getCallList().get(0);
            IncomingCallInterface incomingCall = (IncomingCallInterface) activeCall;
            incomingCall.acceptCall(true);
            incomingCall.setRemoteVideoView(remoteVideoView);
            incomingCall.setLocalVideoView(localVideoView);

        }

        else{
            remoteUser=getTarget.getCharSequence("target").toString();
            // start the video call
            try {
                myCallService.CreateVideoCall(remoteUser, true,remoteVideoView,localVideoView);
            } catch (MobileException e) {
                e.printStackTrace();
            }
        }


        // set the chronometer
        chronometer=findViewById(R.id.chorometer);
        chronometer.start();


        // set the button status
        muteStatus=true;
        holdStatus=true;
        videoStatus=true;

        // set the button
        btn_EndCall=findViewById(R.id.btn_endCall);
        btn_hold=findViewById(R.id.btn_hold);
        btn_mute=findViewById(R.id.btn_mute);
        btn_video=findViewById(R.id.btn_video);


        // end call
        btn_EndCall.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (getTarget==null){
                    CallInterface activeCall = CallManager.getInstance().getCallList().remove(0);
                    IncomingCallInterface incomingCall = (IncomingCallInterface) activeCall;
                    try {
                        incomingCall.endCall();
                    } catch (MobileException e) {
                        e.printStackTrace();
                    }
                }
                else{
                    CallInterface activeCall = CallManager.getInstance().getCallList().remove(0);
                    OutgoingCallInterface outgoingCall = (OutgoingCallInterface) activeCall;
                    try {
                        outgoingCall.endCall();
                    } catch (MobileException e) {
                        e.printStackTrace();
                    }

                }

                chronometer.stop();
                Intent intent = new Intent(InOutGoingVideoCallActivity.this,MainActivity.class);
                intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_NEW_TASK);
                startActivity(intent);


            }
        });

        // mute or un mute
        btn_mute.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (getTarget==null){

                    CallInterface activeCall = CallManager.getInstance().getCallList().get(0);
                    IncomingCallInterface incomingCall = (IncomingCallInterface) activeCall;
                    muteStatus = !muteStatus;
                    Toast.makeText(InOutGoingVideoCallActivity.this, "mute status changed the "
                            + muteStatus, Toast.LENGTH_SHORT).show();
                    if (muteStatus){
                        incomingCall.unMute();
                        btn_mute.setBackgroundResource(R.drawable.ic_mic_on);

                    }
                    else {
                        btn_mute.setBackgroundResource(R.drawable.ic_mic_off);
                        incomingCall.isMute();

                    }
                }
                else {
                    CallInterface activeCall = CallManager.getInstance().getCallList().get(0);
                    OutgoingCallInterface outgoingCall = (OutgoingCallInterface) activeCall;
                    muteStatus = !muteStatus;
                    Toast.makeText(InOutGoingVideoCallActivity.this, "mute status changed the "
                            + muteStatus, Toast.LENGTH_SHORT).show();
                    if (muteStatus){
                        outgoingCall.unMute();
                        btn_mute.setBackgroundResource(R.drawable.ic_mic_on);

                    }
                    else {
                        btn_mute.setBackgroundResource(R.drawable.ic_mic_off);
                        outgoingCall.mute();

                    }
                }


            }
        });

        // hold or un hold
        btn_hold.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (getTarget==null){

                    CallInterface activeCall = CallManager.getInstance().getCallList().get(0);
                    IncomingCallInterface incomingCall = (IncomingCallInterface) activeCall;

                    holdStatus = !holdStatus;
                    Toast.makeText(InOutGoingVideoCallActivity.this, "hold status changed the "
                            + holdStatus, Toast.LENGTH_SHORT).show();
                    if (holdStatus){
                        btn_hold.setBackgroundResource(R.drawable.ic_hold_on);
                        incomingCall.unHoldCall();
                    }
                    else {
                        btn_hold.setBackgroundResource(R.drawable.ic_hold_off);
                        incomingCall.holdCall();
                    }
                }
                else{
                    CallInterface activeCall = CallManager.getInstance().getCallList().get(0);
                    OutgoingCallInterface outgoingCall = (OutgoingCallInterface) activeCall;

                    holdStatus = !holdStatus;
                    Toast.makeText(InOutGoingVideoCallActivity.this, "hold status changed the "
                            + holdStatus, Toast.LENGTH_SHORT).show();
                    if (holdStatus){
                        btn_hold.setBackgroundResource(R.drawable.ic_hold_on);
                        outgoingCall.unHoldCall();
                    }
                    else {
                        btn_hold.setBackgroundResource(R.drawable.ic_hold_off);
                        outgoingCall.holdCall();
                    }
                }

            }
        });

        // video start or video stop
        btn_video.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (getTarget==null){

                    CallInterface activeCall = CallManager.getInstance().getCallList().get(0);
                    IncomingCallInterface incomingCall = (IncomingCallInterface) activeCall;

                    videoStatus = !videoStatus;
                    Toast.makeText(InOutGoingVideoCallActivity.this, "video status changed the "
                            + videoStatus, Toast.LENGTH_SHORT).show();

                    if (videoStatus){
                        btn_video.setBackgroundResource(R.drawable.ic_video_on);
                        incomingCall.videoStart();

                    }
                    else {
                        btn_video.setBackgroundResource(R.drawable.ic_video_off);
                        incomingCall.videoStop();

                    }
                }
                else{
                    CallInterface activeCall = CallManager.getInstance().getCallList().get(0);
                    OutgoingCallInterface outgoingCall = (OutgoingCallInterface) activeCall;

                    videoStatus = !videoStatus;
                    Toast.makeText(InOutGoingVideoCallActivity.this, "video status changed the "
                            + videoStatus, Toast.LENGTH_SHORT).show();

                    if (videoStatus){
                        btn_video.setBackgroundResource(R.drawable.ic_video_on);
                        outgoingCall.videoStart();

                    }
                    else {
                        btn_video.setBackgroundResource(R.drawable.ic_video_off);
                        outgoingCall.videoStop();

                    }
                }


            }
        });

    }
}