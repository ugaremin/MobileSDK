package com.example.mobilesdkdemo;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.TextView;

import com.example.mobilesdkdemo.Model.Messages;
import com.rbbn.cpaas.mobile.messaging.api.Conversation;
import com.rbbn.cpaas.mobile.messaging.api.FetchCallback;
import com.rbbn.cpaas.mobile.messaging.api.FetchResult;
import com.rbbn.cpaas.mobile.messaging.api.InboundMessage;
import com.rbbn.cpaas.mobile.messaging.api.Message;
import com.rbbn.cpaas.mobile.messaging.api.MessageDeliveryStatus;
import com.rbbn.cpaas.mobile.messaging.api.MessageState;
import com.rbbn.cpaas.mobile.messaging.api.OutboundMessage;
import com.rbbn.cpaas.mobile.messaging.chat.api.ChatGroupParticipant;
import com.rbbn.cpaas.mobile.messaging.chat.api.ChatListener;
import com.rbbn.cpaas.mobile.messaging.chat.api.ChatService;
import com.rbbn.cpaas.mobile.utilities.exception.MobileError;

import java.util.ArrayList;
import java.util.List;

public class MessageActivity extends AppCompatActivity {

    ImageButton btn_send;
    EditText txt_send;
    TextView username;
    private ArrayList<Messages> mesages;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_message);

        btn_send=findViewById(R.id.btn_send);
        txt_send=findViewById(R.id.txt_send);
        username=findViewById(R.id.username);

        mesages=new ArrayList<>();
        readAllMessages();

    }

    private void readAllMessages(){

        ChatService chatService = CPaaSMeneger.getInstance().getCpaas().getChatService();

        chatService.setChatListener(new ChatListener() {
            @Override
            public void inboundChatMessageReceived(InboundMessage inboundMessage) {

            }

            @Override
            public void chatDeliveryStatusChanged(String s, MessageDeliveryStatus messageDeliveryStatus, String s1) {

            }

            @Override
            public void chatParticipantStatusChanged(ChatGroupParticipant chatGroupParticipant, String s) {

            }

            @Override
            public void outboundChatMessageSent(OutboundMessage outboundMessage) {

            }

            @Override
            public void isComposingReceived(String s, MessageState messageState, long l) {

            }

            @Override
            public void groupChatSessionInvitation(List<ChatGroupParticipant> list, String s, String s1) {

            }

            @Override
            public void groupChatEventNotification(String s, String s1, String s2) {

            }
        });

        Conversation conversation = CPaaSMeneger.getInstance().getCpaas().getChatService().createConversation("conversation");
        conversation.fetchMessages(new FetchCallback<List<Message>>() {
            @Override
            public void onSuccess(FetchResult<List<Message>> fetchResult) {

            }

            @Override
            public void onFail(MobileError mobileError) {

            }
        });




    }
}