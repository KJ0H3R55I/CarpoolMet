package com.example.carpool;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

public class ChatAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    private static final int TYPE_USER = 0;
    private static final int TYPE_DRIVER = 1;

    private List<String> messages;

    public ChatAdapter(List<String> messages) {
        this.messages = messages;
    }

    @Override
    public int getItemViewType(int position) {
        String message = messages.get(position);
        return message.startsWith("You:") ? TYPE_USER : TYPE_DRIVER;
    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        if (viewType == TYPE_USER) {
            View view = LayoutInflater.from(parent.getContext())
                    .inflate(R.layout.item_chat_user, parent, false);
            return new UserViewHolder(view);
        } else {
            View view = LayoutInflater.from(parent.getContext())
                    .inflate(R.layout.item_chat_driver, parent, false);
            return new BotViewHolder(view);
        }
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {
        String message = messages.get(position).replaceFirst("^(You: |Driver: )", "");

        if (holder instanceof UserViewHolder) {
            ((UserViewHolder) holder).userMessage.setText(message);
        } else if (holder instanceof BotViewHolder) {
            ((BotViewHolder) holder).DriverMessage.setText(message);
        }
    }

    @Override
    public int getItemCount() {
        return messages.size();
    }

    static class UserViewHolder extends RecyclerView.ViewHolder {
        TextView userMessage;

        public UserViewHolder(View itemView) {
            super(itemView);
            userMessage = itemView.findViewById(R.id.userMessage);
        }
    }

    static class BotViewHolder extends RecyclerView.ViewHolder {
        TextView DriverMessage;

        public BotViewHolder(View itemView) {
            super(itemView);
            DriverMessage = itemView.findViewById(R.id.botMessage);
        }
    }
}
