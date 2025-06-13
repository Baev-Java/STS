package com.example.chatapp.adapters;

import android.graphics.Bitmap;
import android.view.LayoutInflater;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.chatapp.databinding.ItemContainerRecivedMessageBinding;
import com.example.chatapp.databinding.ItemContainerSentMessageBinding;
import com.example.chatapp.models.ChatMessage;

import java.util.List;

public class ChatAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder>{

    private final List<ChatMessage> chatMessages;
    private final Bitmap receiverProfileImage;
    private final String senderId;
    public static final int VIEW_TYPE_SENT = 1;
    public static  final int VIEW_TYPE_RECEIVED = 2;

    public ChatAdapter(List<ChatMessage> chatMessage, Bitmap receiverProfileImage, String senderId) {
        this.chatMessages = chatMessage;
        this.receiverProfileImage = receiverProfileImage;
        this.senderId = senderId;
    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        if(viewType == VIEW_TYPE_SENT){
            return new SentMessageViewHolder(
                    ItemContainerSentMessageBinding.inflate(
                            LayoutInflater.from(parent.getContext()),
                            parent,
                            false
                    )
            );
        }else{
            return new ReceiverMessageViewHolder(
                    ItemContainerRecivedMessageBinding.inflate(
                            LayoutInflater.from(parent.getContext()),
                            parent,
                            false
                    )
            );
        }

    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {
        if(getItemViewType(position) == VIEW_TYPE_SENT){
            ((SentMessageViewHolder) holder).setData(chatMessages.get(position));
        }else{
            ((ReceiverMessageViewHolder) holder).setData(chatMessages.get(position), receiverProfileImage);
        }
    }

    @Override
    public int getItemCount() {
        return chatMessages.size();
    }

    @Override //chatMessages.get(position).senderId != null &&
    public int getItemViewType(int position) {//отправленное сообщение или полученное
        if(chatMessages.get(position).senderId.equals(senderId)){
            return VIEW_TYPE_SENT;
        }else {
            return VIEW_TYPE_RECEIVED;
        }
    }//chatMessages.get(position).senderId.equals(senderId) выдаёт ошибку, сам метод отвечает за проверку является сообщение отправленным или полученным

    static class SentMessageViewHolder extends RecyclerView.ViewHolder{

        private final ItemContainerSentMessageBinding binding;

        SentMessageViewHolder(ItemContainerSentMessageBinding itemContainerSentMessageBinding){
            super(itemContainerSentMessageBinding.getRoot());
            binding = itemContainerSentMessageBinding;
        }

        void setData(ChatMessage chatMessage){
            binding.textMessage.setText(chatMessage.message);
            binding.textDataTime.setText(chatMessage.dataTime);
        }
    }

    static class ReceiverMessageViewHolder extends RecyclerView.ViewHolder{

        private final ItemContainerRecivedMessageBinding binding;

        ReceiverMessageViewHolder(ItemContainerRecivedMessageBinding itemContainerRecivedMessageBinding){
            super(itemContainerRecivedMessageBinding.getRoot());
            binding = itemContainerRecivedMessageBinding;
        }

        void setData(ChatMessage chatMessage, Bitmap receiverProfileImage){
            binding.textMessage.setText(chatMessage.message);
            binding.textDataTime.setText(chatMessage.dataTime);
            binding.imageProfile.setImageBitmap(receiverProfileImage);
        }
    }
}
