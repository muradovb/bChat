package com.bmmuradov.activities;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.ScrollView;
import android.widget.TextView;

import com.bmmuradov.core.MessagesData;
import com.bmmuradov.core.User;
import com.firebase.client.Firebase;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class AllChatActivity extends AppCompatActivity{
    final static String type = "all";
    LinearLayout layout;
    ImageButton sendButton;
    EditText messageArea;
    ScrollView scrollView;
    private DatabaseReference dbReference;
    private DatabaseReference dbReferenceUsers;
    ImageButton backButton;
    TextView senderName;
    String senderNameMess;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_chat);
        init();
    }

    private void init() {
        sendButton = (ImageButton)findViewById(R.id.img_bttn_send);
        messageArea = (EditText)findViewById(R.id.et_message);
        scrollView = (ScrollView)findViewById(R.id.sv_chat_activity);
        layout = (LinearLayout) findViewById(R.id.lin_lay_chat_act);
        backButton= (ImageButton) findViewById(R.id.img_bttn_back);
        senderName = (TextView) findViewById(R.id.tv_message_sender);
        senderName.setText(type);
        Firebase.setAndroidContext(this);
        dbReference = FirebaseDatabase.getInstance().getReference().child("all_team_messages");
        dbReferenceUsers = FirebaseDatabase.getInstance().getReference().child("users");
        backButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

        //getting name of current user from database
        dbReferenceUsers.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                for (DataSnapshot ds : dataSnapshot.getChildren()) {
                    User tempUser = ds.getValue(User.class);
                    if (tempUser.getUserId().equals(FirebaseAuth.getInstance().getCurrentUser().getUid())) {
                        senderNameMess = tempUser.getName();
                    }
                }
            }
            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });

        sendButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String messageText = messageArea.getText().toString();

                if(!messageText.equals("")){

                    MessagesData newData = new MessagesData();
                    newData.setMessage(messageText);
                    newData.setSender(FirebaseAuth.getInstance().getCurrentUser().getUid());
                    newData.setReceiver(type);
                    newData.setSenderName(senderNameMess);
                    dbReference.push().setValue(newData);
                    messageArea.setText(""); //empty the message field
                }
            }
        });


        ChildEventListener messageListener = new ChildEventListener() {
            @Override
            public void onChildAdded(@NonNull com.google.firebase.database.DataSnapshot dataSnapshot, @Nullable String s) {

                String currUid = FirebaseAuth.getInstance().getCurrentUser().getUid();
                MessagesData newData = dataSnapshot.getValue(MessagesData.class);
                String message = newData.getMessage().toString();

                if((newData.getSender().equals(currUid))) {
                    addMessageBox("You:-\n" + message, 1);
                }
                else if((newData.getReceiver().equals(type))) {
                    addMessageBox(newData.getSenderName()+ ":" +"\n" + message, 2);
                }
            }

            @Override
            public void onChildChanged(@NonNull com.google.firebase.database.DataSnapshot dataSnapshot, @Nullable String s) {

            }

            @Override
            public void onChildRemoved(@NonNull com.google.firebase.database.DataSnapshot dataSnapshot) {

            }

            @Override
            public void onChildMoved(@NonNull com.google.firebase.database.DataSnapshot dataSnapshot, @Nullable String s) {

            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        };
        dbReference.addChildEventListener(messageListener);
    }


    public void addMessageBox(String message, int type){
        TextView textView = new TextView(this);
        textView.setText(message);
        LinearLayout.LayoutParams lp2 = new LinearLayout.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        lp2.weight = 1.0f;

        if(type == 1) {
            lp2.gravity = Gravity.RIGHT;
            textView.setBackgroundResource(R.drawable.bubble_in);
        }
        else {
            lp2.gravity = Gravity.LEFT;
            textView.setBackgroundResource(R.drawable.bubble_out);
        }
        textView.setLayoutParams(lp2);
        layout.addView(textView);
        scrollView.fullScroll(View.FOCUS_DOWN);
    }

    @Override
    public void onBackPressed() {

    }

}
