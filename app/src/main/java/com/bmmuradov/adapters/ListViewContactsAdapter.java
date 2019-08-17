package com.bmmuradov.adapters;

import android.content.Context;
import android.content.Intent;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;

import com.bmmuradov.activities.ChatActivity;
import com.bmmuradov.activities.R;
import com.bmmuradov.viewholders.ViewHolderContact;
import com.bmmuradov.core.ContactsData;

import java.util.ArrayList;

public class ListViewContactsAdapter extends BaseAdapter{
        private static ArrayList<ContactsData> listContact;
        private Context context;
        private LayoutInflater mInflater;

        public ListViewContactsAdapter(Context context, ArrayList<ContactsData> results){
            listContact = results;
            mInflater = LayoutInflater.from(context);
            this.context=context;
        }

        @Override
        public int getCount() {
                return listContact.size();
        }

        @Override
        public Object getItem(int arg0) {
            return listContact.get(arg0);
        }

        @Override
        public long getItemId(int arg0) {
            return arg0;
        }


        public View getView(final int position, View convertView, ViewGroup parent) {
            final ViewHolderContact holder;
            Log.e("inside getView()", "started");
            if(convertView == null){
                convertView = mInflater.inflate(R.layout.contact_item, null);
                holder = new ViewHolderContact(convertView);
                holder.setTxtemail(convertView.findViewById(R.id.tv_contact_item_email).toString());
                holder.setTxtphone(convertView.findViewById(R.id.tv_contact_item_phone).toString());
                holder.setTxtusername(convertView.findViewById(R.id.tv_username).toString());
                convertView.setTag(holder);
                convertView.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        String pressedId = listContact.get(position).uId;
                        String username = listContact.get(position).username;
                        Intent intent = new Intent(context, ChatActivity.class);
                        intent.putExtra("pressedId", pressedId);
                        intent.putExtra("username", username);
                        context.startActivity(intent);
                        v.setEnabled(false);
                    }
                });
            }
            else {
                holder = (ViewHolderContact) convertView.getTag();
            }
            convertView.setEnabled(true);
            holder.setTxtemail(listContact.get(position).getEmail());
            Log.e("inside adapter", listContact.get(position).getEmail()+"");
            holder.setTxtphone(listContact.get(position).getPhone());
            holder.setTxtusername((listContact.get(position).getUsername()));
            Log.e("inside getView()", "ended");
            return convertView;
        }
}
