package com.bmmuradov.fragments;


import android.app.ProgressDialog;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;

import com.bmmuradov.core.ContactsData;
import com.bmmuradov.adapters.ListViewContactsAdapter;
import com.bmmuradov.activities.R;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

public class ContactsFragment extends Fragment {

    //properties
    ArrayList<ContactsData> listContacts;
    //DatabaseReference myDb;
    ListView listView;
    View rootView;
    ProgressDialog pd;


    //constructor
    public ContactsFragment() {
    }


    //methods
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        rootView= inflater.inflate(R.layout.fragment_contacts, container, false);
        listContacts = new ArrayList<ContactsData>();
        init();
        pd = new ProgressDialog(getActivity());
        pd.setMessage("Loading Contacts, please wait ...");
        pd.show();
        loadData();
        listView= (ListView)rootView.findViewById(R.id.lv_contacts);
        return rootView;
    }

    private void init() {
        Log.e("inside init", "init called");
    }


    private void loadData() {
        Log.e("inside loadData", "loadData called");
        DatabaseReference contactItems=FirebaseDatabase.getInstance().getReference().child("contact_list");
        ValueEventListener listener=new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                //clear the list
                listContacts.clear();
                //filling model arraylist of contacts
                for(DataSnapshot ds: dataSnapshot.getChildren()) {
                    //System.out.println(key);
                    ContactsData newContact = ds.getValue(ContactsData.class);
                    Log.e("contact_name ", newContact.getEmail()+"");
                    Log.e("contact_uid", newContact.getuId()+"");
                    //ContactsData temp= new ContactsData();
                    //temp.setName(newContact);
                    listContacts.add(newContact);
                }
                Log.e("inside datasnapshot", "DONE for loop");
                ListViewContactsAdapter adapter = new ListViewContactsAdapter(getActivity(), listContacts);
                listView.setAdapter(adapter);
                Log.e("inside datasnapshot", "done setting adapter");

                // for debugging puropses...
                Log.e("list_contact_size", listContacts.size()+"");
               // for(int i=0; i<listContacts.size(); i++) {
               //     Log.e("list_items", listContacts.get(i).name);
               // }
                Log.e("inside datasnapshot", "done w/method datachange");

            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
                System.out.println("The read failed: " + databaseError.getCode());
            }
        };
        contactItems.addListenerForSingleValueEvent(listener);
        pd.dismiss();
    }


}