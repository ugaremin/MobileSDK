package com.example.mobilesdkdemo;

import android.content.Context;
import android.content.Intent;
import android.hardware.Camera;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Toast;

import com.rbbn.cpaas.mobile.call.IncomingCall;
import com.rbbn.cpaas.mobile.call.api.CallInterface;
import com.rbbn.cpaas.mobile.call.api.CallService;
import com.rbbn.cpaas.mobile.call.api.CallState;
import com.rbbn.cpaas.mobile.call.api.IncomingCallInterface;
import com.rbbn.cpaas.mobile.call.api.MediaAttributes;
import com.rbbn.cpaas.mobile.call.api.ProcessListener;
import com.rbbn.cpaas.mobile.call.api.RTPStatisticsHandler;
import com.rbbn.cpaas.mobile.core.webrtc.view.VideoView;
import com.rbbn.cpaas.mobile.utilities.exception.MobileException;
import com.rbbn.cpaas.mobile.utilities.webrtc.ChangeListenerKey;
import com.rbbn.cpaas.mobile.utilities.webrtc.CodecType;
import com.rbbn.cpaas.mobile.utilities.webrtc.VideoViewType;

import java.beans.PropertyChangeListener;
import java.util.List;
import java.util.Map;


public class CallFragment extends Fragment {

    MyCallService myCallService=new MyCallService();
    EditText targetCall;
    Button btn_call;
    RadioGroup radioGroup;
    RadioButton radioButton;
    boolean check;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {


        View view= inflater.inflate(R.layout.fragment_call, container, false);


        targetCall=view.findViewById(R.id.targetCall);
        btn_call=view.findViewById(R.id.btn_call);
        radioGroup=view.findViewById(R.id.radioCallType);

        btn_call.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                choseeCallType();
            }
        });

        return view;
    }

    private void startDoubleCall(boolean checkType){

        try {
            myCallService.CreateCall(targetCall.getText().toString(),checkType);
        } catch (MobileException e) {
            e.printStackTrace();
        }

    }

    private void startSingleCall(){
        try {
            myCallService.CreateSingleCall(targetCall.getText().toString());
        } catch (MobileException e) {
            e.printStackTrace();
        }
    }

    private void choseeCallType(){

        PermissionsHelper.requestVideoCallPermissions(getActivity());
        PermissionsHelper.hasVideoCallPermissions(getContext());
        PermissionsHelper.requestAudioCallPermission(getActivity());
        PermissionsHelper.hasAudioCallPermission(getContext());

        if (PermissionsHelper.hasVideoCallPermissions(getContext())
                && PermissionsHelper.hasAudioCallPermission(getContext())){

            int selectedId=radioGroup.getCheckedRadioButtonId();
            radioButton=getView().findViewById(selectedId);
            if (radioButton.getText().equals("video")){
               // startVideoCall(true);
                Intent intent=new Intent(getContext(), InOutGoingVideoCallActivity.class);
                intent.putExtra("target", targetCall.getText());
                startActivity(intent);
            }
            if (radioButton.getText().equals("Audio(Double M Line)")){
                check=false;
                startDoubleCall(false);
            }

            if (radioButton.getText().equals("Audio(Single M Line)")){
                check=false;
                startSingleCall();
            }


        }
        else {
          //      Toast.makeText(getContext(),"give permissions",Toast.LENGTH_SHORT).show();
        }

    }


}