package com.example.mobilesdkdemo.Adapter;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.mobilesdkdemo.MessageActivity;
import com.example.mobilesdkdemo.Model.Chat;
import com.example.mobilesdkdemo.R;

import java.util.ArrayList;

public class ChatAdapter extends RecyclerView.Adapter<ChatAdapter.MyViewHolder> {

    private ArrayList<Chat> chatts;
    private Context cContext;


    public ChatAdapter(ArrayList<Chat> chatts, Context cContext) {

        this.chatts = chatts;
        this.cContext = cContext;
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        View view = LayoutInflater.from(cContext).inflate(R.layout.user_item,parent,false);
        return new MyViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ChatAdapter.MyViewHolder holder, int position) {

        holder.username.setText(chatts.get(position).getUsename());
        holder.lastMessage.setText(chatts.get(position).getLastMessage());
       // holder.profile_image.setImageResource(R.mipmap.ic_launcher);

        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent intent=new Intent(cContext, MessageActivity.class);
                cContext.startActivity(intent);
            }
        });

    }

    @Override
    public int getItemCount() {
        return chatts.size();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {
        TextView username, lastMessage, lastMessageTime;
       // ImageView profile_image;
        public MyViewHolder(View itemView) {
            super(itemView);
         //   profile_image=itemView.findViewById(R.id.profil_image);
            username=itemView.findViewById(R.id.username);
            lastMessage=itemView.findViewById(R.id.last_message);

        }
    }
}
