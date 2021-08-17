package com.example.mobilesdkdemo;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.rbbn.cpaas.mobile.call.api.CallInterface;
import com.rbbn.cpaas.mobile.call.api.IncomingCallInterface;

public class InComingVideoCall extends AppCompatActivity {

    Button btn_accept, btn_reject;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_in_coming_video_call);

        btn_accept=findViewById(R.id.btn_accept);
        btn_reject=findViewById(R.id.btn_reject);



        // accept the video call
        btn_accept.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
               // CallInterface activeCall = CallManager.getInstance().getCallList().get(0);
               // IncomingCallInterface incomingCall = (IncomingCallInterface) activeCall;
               // incomingCall.acceptCall(true);
               // incomingCall.acceptCall(false);
                Intent intent = new Intent(InComingVideoCall.this,InOutGoingVideoCallActivity.class);
                intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_NEW_TASK);
                startActivity(intent);


            }
        });

        // reject the video call
        btn_reject.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                CallInterface activeCall = CallManager.getInstance().getCallList().remove(0);
                IncomingCallInterface incomingCall = (IncomingCallInterface) activeCall;
                incomingCall.rejectCall();

                Intent intent = new Intent(Router.getInstance().getContext(), MainActivity.class);
                intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_NEW_TASK);
                Router.getInstance().getContext().startActivity(intent);

            }
        });
    }


}