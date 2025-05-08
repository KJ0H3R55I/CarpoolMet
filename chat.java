package com.example.carpool;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageButton;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

public class chat extends AppCompatActivity {

    private RecyclerView chatRecyclerView;
    private EditText messageInput;
    private ImageButton sendButton;

    private ChatAdapter chatAdapter;
    private ArrayList<String> chatMessages;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.chat_page);

        // Bottom nav buttons
        ImageButton homeButton = findViewById(R.id.home);
        homeButton.setOnClickListener(v -> {
            Intent intent = new Intent(chat.this, login_page.class);
            startActivity(intent);
        });

        ImageButton rideButton = findViewById(R.id.ride);
        rideButton.setOnClickListener(v -> {
            Intent intent = new Intent(chat.this, MainActivity.class);
            startActivity(intent);
        });

        ImageButton chatButton = findViewById(R.id.chat);
        chatButton.setOnClickListener(v -> {
            Intent intent = new Intent(chat.this, chat.class);
            startActivity(intent);
        });

        ImageButton mapButton = findViewById(R.id.maps);
        mapButton.setOnClickListener(v -> {
            Intent intent = new Intent(chat.this, Maps.class);
            startActivity(intent);
        });

        ImageButton profileButton = findViewById(R.id.account);
        profileButton.setOnClickListener(v -> {
            Intent intent = new Intent(chat.this, register.class);
            startActivity(intent);
        });

        // Chat logic
        chatRecyclerView = findViewById(R.id.chatRecyclerView);
        messageInput = findViewById(R.id.messageInput);
        sendButton = findViewById(R.id.sendButton);

        chatMessages = new ArrayList<>();
        chatAdapter = new ChatAdapter(chatMessages);
        chatRecyclerView.setLayoutManager(new LinearLayoutManager(this));
        chatRecyclerView.setAdapter(chatAdapter);

        sendButton.setOnClickListener(v -> {
            String message = messageInput.getText().toString().trim();
            if (!message.isEmpty()) {
                chatMessages.add("You: " + message);
                chatAdapter.notifyItemInserted(chatMessages.size() - 1);
                chatRecyclerView.scrollToPosition(chatMessages.size() - 1);
                messageInput.setText("");

                // Simulate auto-reply after 1 second
                chatRecyclerView.postDelayed(() -> {
                    String reply = getAutoReply(message);
                    chatMessages.add("Driver: " + reply);
                    chatAdapter.notifyItemInserted(chatMessages.size() - 1);
                    chatRecyclerView.scrollToPosition(chatMessages.size() - 1);
                }, 1000);
            }
        });
    }

    // Auto-reply logic
    private String getAutoReply(String message) {
        message = message.toLowerCase();
        if (message.contains("hello") || message.contains("hi")) {
            return "Driver: Hello i am on my way";
        } else if (message.contains("book") || message.contains("ride")) {
            return "Driver: To book a ride, please go to the Ride tab.";
        } else if (message.contains("thanks") || message.contains("thank you")) {
            return "Driver: You're welcome!";
        } else if (message.contains("bye") || message.contains("goodbye")) {
            return "I'm not sure how to respond to that. Try asking about rides or say hello.";
        } else if (message.contains("long") || message.contains("when")) {
            return "Driver: ill be there in 8 minitues.";
        }
        return message;
    }
}