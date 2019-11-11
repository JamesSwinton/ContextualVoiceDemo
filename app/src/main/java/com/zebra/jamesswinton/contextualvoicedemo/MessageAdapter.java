package com.zebra.jamesswinton.contextualvoicedemo;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import androidx.recyclerview.widget.RecyclerView.ViewHolder;

import java.util.ArrayList;
import java.util.List;

import static com.zebra.jamesswinton.contextualvoicedemo.Message.MESSAGE_TYPE.SENT;

public class MessageAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    // Debugging
    private static final String TAG = "MessageAdapter";

    // Constants
    private static final int SENT_MESSAGE_VIEW_TYPE = 0;
    private static final int RECEIVED_MESSAGE_VIEW_TYPE = 1;

    // Private Variables
    private List<Message> messages;

    public MessageAdapter() {
        this.messages = new ArrayList<>();
    }

    /**
     * RecyclerView Methods
     */

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        // Build Layout Inflater
        LayoutInflater layoutInflater = LayoutInflater.from(parent.getContext());

        // Handle View Type
        switch (viewType) {
            case SENT_MESSAGE_VIEW_TYPE:
                return new SentMessageViewHolder(layoutInflater.inflate(
                        R.layout.layout_chat_box_message_sent, parent, false));
            case RECEIVED_MESSAGE_VIEW_TYPE:
                return new ReceivedMessageViewHolder(layoutInflater.inflate(
                        R.layout.layout_chat_box_message_received, parent, false));
        }

        // Default to Received Layout
        return new ReceivedMessageViewHolder(layoutInflater.inflate(
                R.layout.layout_chat_box_message_received, parent, false));
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        // Get Message
        String message = messages.get(position).getMessageText();

        // Handle View Types
        switch (holder.getItemViewType()) {
            case SENT_MESSAGE_VIEW_TYPE:
                Log.i(TAG, "Showing Sent Message");

                // Cast ViewHolder to SentMessageViewHolder
                SentMessageViewHolder sentMessageViewHolder = (SentMessageViewHolder) holder;

                // Show Message
                sentMessageViewHolder.messageTextView.setText(message);
                break;
            case RECEIVED_MESSAGE_VIEW_TYPE:
                Log.i(TAG, "Showing Received Message");

                // Cast ViewHolder to SentMessageViewHolder
                ReceivedMessageViewHolder receivedMessageViewHolder = (ReceivedMessageViewHolder) holder;

                // Show Message
                receivedMessageViewHolder.messageTextView.setText(message);
                break;
        }
    }

    @Override
    public int getItemViewType(int position) {
        return messages.get(position).getMessageType() == SENT ?
                SENT_MESSAGE_VIEW_TYPE : RECEIVED_MESSAGE_VIEW_TYPE;
    }

    @Override
    public int getItemCount() {
        return messages.size();
    }

    /**
     * View Holders
     */

    private static class SentMessageViewHolder extends RecyclerView.ViewHolder {

        // Variables
        private TextView messageTextView;

        // Constructor
        SentMessageViewHolder(View view) {
            super(view);
            messageTextView = view.findViewById(R.id.message_text_view);
        }
    }

    private static class ReceivedMessageViewHolder extends RecyclerView.ViewHolder {

        // Variables
        private TextView messageTextView;

        // Constructor
        ReceivedMessageViewHolder(View view) {
            super(view);
            messageTextView = view.findViewById(R.id.message_text_view);
        }
    }

    /**
     * Support Methods
     */

    public void addMessage(Message message) {
        // Add message to list
        this.messages.add(message);

        // Reload Adapter
        this.notifyItemInserted(messages.size() - 1);
    }
}
