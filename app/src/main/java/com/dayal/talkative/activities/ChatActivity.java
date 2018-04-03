package com.dayal.talkative.activities;

import android.content.Intent;
import android.os.Handler;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.NavUtils;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import com.dayal.talkative.R;
import com.dayal.talkative.data.ChatAdapter;
import com.dayal.talkative.model.Message;
import com.dayal.talkative.util.ProgressGenerator;
import com.parse.FindCallback;
import com.parse.LogOutCallback;
import com.parse.ParseException;
import com.parse.ParseQuery;
import com.parse.ParseUser;
import com.parse.SaveCallback;

import java.util.ArrayList;
import java.util.List;

import hani.momanii.supernova_emoji_library.Helper.EmojiconEditText;

public class ChatActivity extends AppCompatActivity {
    private EmojiconEditText editMsg;
    private FloatingActionButton sendFab;
    private String currentUserId;
    private ListView listView;
    public static final String USER_KEY_ID = "userId";
    private Handler handler = new Handler();
    private ArrayList<Message> messages;
    private ChatAdapter chatAdapter;

    private static final int MAX_CHAT = 70;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_chat);

        getCurrUser();
        handler.postDelayed(runnable, 100);
    }

    Runnable runnable = new Runnable() {
        @Override
        public void run() {
            refreshMessages();
            handler.postDelayed(this, 100);
        }
    };
    private void getCurrUser() {
        currentUserId = ParseUser.getCurrentUser().getObjectId();
        messagePosting();
    }

    private void messagePosting() {
        editMsg = (EmojiconEditText)findViewById(R.id.edit_msg);
        sendFab = (FloatingActionButton)findViewById(R.id.send_fab);
        listView = (ListView)findViewById(R.id.listView);
        messages = new ArrayList<Message>();
        chatAdapter = new ChatAdapter(ChatActivity.this,currentUserId,messages);
        listView.setAdapter(chatAdapter);

        sendFab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (!editMsg.getText().toString().equals("")){
                    Message msg = new Message ();
                    msg.setUserId(currentUserId);
                    msg.setBody(editMsg.getText().toString());
                    msg.saveInBackground(new SaveCallback() {
                        @Override
                        public void done(ParseException e) {
                               receiveMessages();
                        }
                    });
                }
               editMsg.setText("");
            }
        });
    }

    private void refreshMessages(){
        receiveMessages();

    }

    private void receiveMessages() {
        ParseQuery<Message> query = ParseQuery.getQuery(Message.class);
        query.setLimit(MAX_CHAT);
        query.orderByAscending("createdAt");

        query.findInBackground(new FindCallback<Message>() {
            @Override
            public void done(List<Message> mMessages, ParseException e) {
                if (e == null){
                    messages.clear();
                    messages.addAll(mMessages);
                    chatAdapter.notifyDataSetChanged();
                    listView.invalidate();      // redraw listView
                }else{
                    // TODO error
                }
            }
        });
    }
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.chat_menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.logout:
                 ParseUser.logOutInBackground(new LogOutCallback() {
                     @Override
                     public void done(ParseException e) {
                             if (e == null)
                                 startActivity(new Intent(ChatActivity.this,MainActivity.class));
                     }
                 });
                return true;

            default:
                // If we got here, the user's action was not recognized.
                // Invoke the superclass to handle it.
                return super.onOptionsItemSelected(item);
        }
    }

}
