package com.zebra.jamesswinton.contextualvoicedemo;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;
import androidx.recyclerview.widget.LinearLayoutManager;

import android.content.ActivityNotFoundException;
import android.content.Intent;
import android.os.Bundle;
import android.speech.RecognizerIntent;
import android.text.TextUtils;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.zebra.jamesswinton.contextualvoicedemo.databinding.ActivityMainBinding;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Locale;
import java.util.Random;

import static android.speech.RecognizerIntent.EXTRA_RESULTS;
import static android.view.inputmethod.EditorInfo.IME_ACTION_SEND;

public class MainActivity extends AppCompatActivity {

    // Debugging
    private static final String TAG = "MainActivity";

    // Constants
    private static final int VOICE_RECOGNITION_INTENT = 1;
    private static final String WELCOME = "Hi there, how may I assist?";

    // Private Variables
    private ActivityMainBinding mDataBinding = null;
    private MessageAdapter mMessageAdapter = null;

    /**
     * LifeCycle methods
     */

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mDataBinding = DataBindingUtil.setContentView(this, R.layout.activity_main);

        // Init Message Adapter
        mMessageAdapter = new MessageAdapter();
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this);
        linearLayoutManager.setStackFromEnd(true);
        mDataBinding.chatBoxRecyclerView.setLayoutManager(linearLayoutManager);
        mDataBinding.chatBoxRecyclerView.setAdapter(mMessageAdapter);

        // Show Welcome Message
        mMessageAdapter.addMessage(new Message(Message.MESSAGE_TYPE.RECEIVED, WELCOME));

        // Set Text Listener
        mDataBinding.requestEdittext.setOnEditorActionListener((textView, i, keyEvent) -> {
            if (i == IME_ACTION_SEND) {
                // Validate Request
                if (TextUtils.isEmpty(textView.getText()) || textView.getText() == null) {
                    Log.w(TAG, "Request was empty... ignoring.");
                    return false;
                }

                // Get Text
                String request = textView.getText().toString();

                // Handle Request
                handleRequest(request);

                // Handle Response
                handleResponse(request);

                // Remove Text
                textView.setText("");

                // Return true (we've consumed the action)
                return false;
            }

            // Return False (we haven't consumed the action)
            return false;
        });

        // Set Microphone Listener
        mDataBinding.requestVoiceButton.setOnClickListener(view -> startVoiceRecognition());
    }

    /**
     * Support Methods
     */

    private void startVoiceRecognition() {
        // Build Speech Recognition Intent
        Intent intent = new Intent(RecognizerIntent.ACTION_RECOGNIZE_SPEECH);
        intent.putExtra(RecognizerIntent.EXTRA_LANGUAGE_MODEL, RecognizerIntent.LANGUAGE_MODEL_FREE_FORM);
        intent.putExtra(RecognizerIntent.EXTRA_LANGUAGE, Locale.getDefault());
        intent.putExtra(RecognizerIntent.EXTRA_PROMPT, "What can I help with?");

        // Start Voice Recognition Activity or Inform Unavailability
        try {
            startActivityForResult(intent, VOICE_RECOGNITION_INTENT);
        } catch (ActivityNotFoundException a) {
            Toast.makeText(getApplicationContext(),
                    "Sorry! Your device doesn't support speech input", Toast.LENGTH_LONG)
                    .show();
        }
    }

    /**
     * Handle Voice Recognition Intent
     */

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        // Validate Result
        if (resultCode != RESULT_OK ) {
            Log.e(TAG, "Failed to get result for request: " + requestCode);
            return;
        }

        // Validate Data
        if (data == null) {
            Log.e(TAG, "Message: " + requestCode + " did not contain any data.");
            return;
        }

        // Handle Voice Recognition Intent
        if (requestCode == VOICE_RECOGNITION_INTENT) {
            // Get Results as List
            List<String> recognitionResult = data.getStringArrayListExtra(EXTRA_RESULTS);

            // Validate Result
            if (recognitionResult == null || recognitionResult.isEmpty()) {
                Log.e(TAG, "No voice recognition results found.");
                return;
            }

            // Handle Result (Pass first result as this is Google's best guess)
            handleRequest(recognitionResult.get(0));

            // Handle Response
            handleResponse(recognitionResult.get(0));
        }
    }

    /**
     * Message Handling
     */

    private void handleRequest(String request) {
        // Add Request to Recycler View
        mMessageAdapter.addMessage(new Message(Message.MESSAGE_TYPE.SENT, request));

        // Scroll RecyclerView To Bottom
        mDataBinding.chatBoxRecyclerView.scrollToPosition(mMessageAdapter.getItemCount() - 1);
    }

    private void handleResponse(String request) {
        // Handle Various Responses
        if (request.contains("hello") || request.contains("hi") || request.contains("hey")) {
            // Get Response Options
            List<String> helloResponses = Arrays.asList(getResources()
                    .getStringArray(R.array.hello_responses));

            // Build Response
            String response = helloResponses.get(new Random().nextInt(helloResponses.size()));

            // Show Response
            mMessageAdapter.addMessage(new Message(Message.MESSAGE_TYPE.RECEIVED, response));
        }

        if (request.contains("where") && request.contains("beans")) {
            // Get Response Options
            List<String> beansResponses = Arrays.asList(getResources()
                    .getStringArray(R.array.beans_responses));

            // Build Response
            String response = beansResponses.get(new Random().nextInt(beansResponses.size()));

            // Show Response
            mMessageAdapter.addMessage(new Message(Message.MESSAGE_TYPE.RECEIVED, response));

            // Scroll RecyclerView To Bottom
            mDataBinding.chatBoxRecyclerView.scrollToPosition(mMessageAdapter.getItemCount() - 1);

        } else if (request.contains("where") && request.contains("jam")) {
            // Get Response Options
            List<String> jamResponses = Arrays.asList(getResources()
                    .getStringArray(R.array.jam_responses));

            // Build Response
            String response = jamResponses.get(new Random().nextInt(jamResponses.size()));

            // Show Response
            mMessageAdapter.addMessage(new Message(Message.MESSAGE_TYPE.RECEIVED, response));

            // Scroll RecyclerView To Bottom
            mDataBinding.chatBoxRecyclerView.scrollToPosition(mMessageAdapter.getItemCount() - 1);

        } else if (request.contains("where") && request.contains("beer")) {
            // Get Response Options
            List<String> beerResponses = Arrays.asList(getResources()
                    .getStringArray(R.array.beer_responses));

            // Build Response
            String response = beerResponses.get(new Random().nextInt(beerResponses.size()));

            // Show Response
            mMessageAdapter.addMessage(new Message(Message.MESSAGE_TYPE.RECEIVED, response));

            // Scroll RecyclerView To Bottom
            mDataBinding.chatBoxRecyclerView.scrollToPosition(mMessageAdapter.getItemCount() - 1);

        } else if (request.contains("where") && request.contains("bread")) {
            // Get Response Options
            List<String> breadResponses = Arrays.asList(getResources()
                    .getStringArray(R.array.bread_responses));

            // Build Response
            String response = breadResponses.get(new Random().nextInt(breadResponses.size()));

            // Show Response
            mMessageAdapter.addMessage(new Message(Message.MESSAGE_TYPE.RECEIVED, response));

            // Scroll RecyclerView To Bottom
            mDataBinding.chatBoxRecyclerView.scrollToPosition(mMessageAdapter.getItemCount() - 1);

        } else if (request.contains("where") && request.contains("toiletries")) {
            // Get Response Options
            List<String> toiletriesResponses = Arrays.asList(getResources()
                    .getStringArray(R.array.toiletries_responses));

            // Build Response
            String response = toiletriesResponses.get(new Random().nextInt(toiletriesResponses.size()));

            // Show Response
            mMessageAdapter.addMessage(new Message(Message.MESSAGE_TYPE.RECEIVED, response));

            // Scroll RecyclerView To Bottom
            mDataBinding.chatBoxRecyclerView.scrollToPosition(mMessageAdapter.getItemCount() - 1);

        } else {

            // Build Response
            String response = "Sorry, I'm not sure how to help with that. Please try a colleague in-store.";

            // Show Response
            mMessageAdapter.addMessage(new Message(Message.MESSAGE_TYPE.RECEIVED, response));

            // Scroll RecyclerView To Bottom
            mDataBinding.chatBoxRecyclerView.scrollToPosition(mMessageAdapter.getItemCount() - 1);
        }
    }
}
