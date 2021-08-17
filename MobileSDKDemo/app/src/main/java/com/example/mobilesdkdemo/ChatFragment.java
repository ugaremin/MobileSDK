package com.example.mobilesdkdemo;

import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.mobilesdkdemo.Adapter.ChatAdapter;
import com.example.mobilesdkdemo.Model.Chat;
import com.rbbn.cpaas.mobile.messaging.api.Conversation;
import com.rbbn.cpaas.mobile.messaging.api.FetchConversationsCallback;
import com.rbbn.cpaas.mobile.messaging.api.InboundMessage;
import com.rbbn.cpaas.mobile.messaging.api.MessageDeliveryStatus;
import com.rbbn.cpaas.mobile.messaging.api.MessageState;
import com.rbbn.cpaas.mobile.messaging.api.OutboundMessage;
import com.rbbn.cpaas.mobile.messaging.chat.api.ChatGroupParticipant;
import com.rbbn.cpaas.mobile.messaging.chat.api.ChatListener;
import com.rbbn.cpaas.mobile.messaging.chat.api.ChatService;
import com.rbbn.cpaas.mobile.utilities.exception.MobileError;

import java.util.ArrayList;
import java.util.List;

public class ChatFragment extends Fragment {

    private ArrayList<Chat> chatts;
    private RecyclerView recyclerView;
    private ChatAdapter chattAdapter;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View view= inflater.inflate(R.layout.fragment_chat, container, false);

        recyclerView=view.findViewById(R.id.recyclerview);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        chatts=new ArrayList<>();
        readUsers();
        return view;
    }


    private void readUsers() {

        ChatService chatService = CPaaSMeneger.getInstance().getCpaas().getChatService();

        chatService.setChatListener(new ChatListener() {
            @Override
            public void inboundChatMessageReceived(InboundMessage inboundMessage) {

                Log.i("CPaaS.ChatService", "New message from " + inboundMessage.getSenderAddress());

            }

            @Override
            public void chatDeliveryStatusChanged(String s, MessageDeliveryStatus messageDeliveryStatus, String s1) {
                Log.i("CPaaS.ChatService", "Message delivery status changed to " + messageDeliveryStatus.getString());
            }

            @Override
            public void chatParticipantStatusChanged(ChatGroupParticipant chatGroupParticipant, String s) {

            }

            @Override
            public void outboundChatMessageSent(OutboundMessage outboundMessage) {
                Log.i("CPaaS.ChatService", "Message is sent to " + outboundMessage.getDestinationAddress());

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


        chatService.fetchConversations(new FetchConversationsCallback(){
            @Override
            public void onSuccess(List<Conversation> list) {
                for (int i = 0; i<list.size(); i++) {
                    int index_at=list.get(i).getParticipant().indexOf("@");
                   // String cUser=list.get(i).getParticipant().substring(0,index_at);
                    chatts.add(new Chat(list.get(i).getParticipant().substring(0,index_at),list.get(i).getLastText()));
                    recyclerView.setAdapter(new ChatAdapter(chatts, getContext()));
                }
            }

            @Override
            public void onFail(MobileError mobileError) {

            }
        });

    }



}