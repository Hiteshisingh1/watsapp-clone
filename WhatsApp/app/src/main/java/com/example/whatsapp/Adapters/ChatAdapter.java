package com.example.whatsapp.Adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.whatsapp.Models.MessageModel;
import com.example.whatsapp.R;
import com.github.pgreze.reactions.ReactionPopup;
import com.github.pgreze.reactions.ReactionsConfig;
import com.github.pgreze.reactions.ReactionsConfigBuilder;
import com.google.firebase.auth.FirebaseAuth;

import java.util.ArrayList;

public class ChatAdapter extends RecyclerView.Adapter{
ArrayList<MessageModel> messageModels;
Context context;

  int SENDER_VIEW_TYPE = 1;
  int RECEIVER_VIEW_TYPE =2;

    public ChatAdapter(ArrayList<MessageModel> messageModels, Context context) {
        this.messageModels = messageModels;
        this.context = context;
    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        if(viewType == SENDER_VIEW_TYPE) {
            View view = LayoutInflater.from(context).inflate(R.layout.sample_sender , parent , false);
        return new SenderViewHolder(view);
        }
        else {
            View view = LayoutInflater.from(context).inflate(R.layout.sample_reciever , parent , false);
            return new RecieverViewHolder(view);

        }
    }

    @Override
    public int getItemViewType(int position) {

        if (messageModels.get(position).getuId().equals(FirebaseAuth.getInstance().getUid()))
        {
            return SENDER_VIEW_TYPE;
        }
        else {
            return RECEIVER_VIEW_TYPE;
        }
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {
        MessageModel messageModel = messageModels.get(position);

        ReactionsConfig config = new ReactionsConfigBuilder(context)
                .withReactions(new int[]{
                        R.drawable.ic_fb_like,
                        R.drawable.ic_fb_love,
                        R.drawable.ic_fb_laugh,
                        R.drawable.ic_fb_wow,
                        R.drawable.ic_fb_sad,
                        R.drawable.ic_fb_angry,
                        R.drawable.ic_fb_care
                })
                .build();
        ReactionPopup popup = new ReactionPopup(context, config, (pos) -> {
            return true; // true is closing popup, false is requesting a new selection
        });

        if(holder.getClass() == SenderViewHolder.class){
            ((SenderViewHolder)holder).senderMsg.setText(messageModel.getMessage());
        }
        else {
            ((RecieverViewHolder)holder).receiverMsg.setText(messageModel.getMessage());
        }

    }

    @Override
    public int getItemCount() {
        return messageModels.size();
    }

    public  class RecieverViewHolder extends RecyclerView.ViewHolder{

        TextView receiverMsg , receiverTime;

        public RecieverViewHolder(@NonNull View itemView) {
            super(itemView);
            receiverMsg = itemView.findViewById(R.id.recieverText);
            receiverTime = itemView.findViewById(R.id.recieverTime);

        }
    }
    public class SenderViewHolder extends RecyclerView.ViewHolder{


        TextView senderMsg , senderTime;

        public SenderViewHolder(@NonNull View itemView) {
            super(itemView);
            senderMsg = itemView.findViewById(R.id.senderText);
            senderTime = itemView.findViewById(R.id.senderTime);
        }
    }
}
