package com.bmmuradov.fragments;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;
import com.bmmuradov.adapters.ListViewMessagesAdapter;
import com.bmmuradov.core.MessagesData;
import com.bmmuradov.activities.R;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

public class MessagesFragment extends Fragment{

    ArrayList<MessagesData> listMessages;
    DatabaseReference myDb;
    DatabaseReference myDbUsers;

    //constructor
    public MessagesFragment() {

    }

    //methods
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        View rootView = inflater.inflate(R.layout.fragment_messages, container, false);
        listMessages = new ArrayList<>();

        ListView lv = (ListView) rootView.findViewById(R.id.lv_messages);
        fillArray();
        lv.setAdapter(new ListViewMessagesAdapter(getActivity(), listMessages));
        return rootView;
    }

    private void fillArray() {

        //reference myDb points to all database
        myDb = FirebaseDatabase.getInstance().getReference().child("messages");

        myDb.addValueEventListener(new ValueEventListener() {
            //LOADING THE LATEST MESSAGES FROM DATABASE
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                for(DataSnapshot ds: dataSnapshot.getChildren()) {
                    MessagesData tempData = ds.getValue(MessagesData.class);
                    if(tempData.isChatted()==true) {
                        listMessages.add(tempData);
                    }
                }
            }
            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
    }


}
