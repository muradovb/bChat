package com.bmmuradov.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;

import com.bmmuradov.activities.R;
import com.bmmuradov.viewholders.ViewHolderMessage;
import com.bmmuradov.core.MessagesData;

import java.util.ArrayList;

public class ListViewMessagesAdapter extends BaseAdapter {

    private static ArrayList<MessagesData> listMessages;

    private LayoutInflater mInflater;

    public ListViewMessagesAdapter(Context cntxt, ArrayList<MessagesData> results){
        listMessages = results;
        mInflater = LayoutInflater.from(cntxt);
    }

    @Override
    public int getCount() {
        return listMessages.size();
    }

    @Override
    public Object getItem(int arg0) {
        return listMessages.get(arg0);
    }

    @Override
    public long getItemId(int arg0) {
        return arg0;
    }


    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolderMessage holder;
        if(convertView == null){
            convertView = mInflater.inflate(R.layout.messages_item, null);
            holder = new ViewHolderMessage(convertView);
            holder.setTxtContactName(convertView.findViewById(R.id.tv_contact_name).toString());
            holder.setTxtMessage(convertView.findViewById(R.id.tv_message).toString()) ;
            holder.setTxtTime(convertView.findViewById(R.id.tv_time).toString());
            convertView.setTag(holder);
        } else {
            holder = (ViewHolderMessage) convertView.getTag();
        }

        holder.setTxtContactName(listMessages.get(position).getSender());
        holder.setTxtTime(( String.valueOf(listMessages.get(position).getTime())));
        holder.setTxtMessage(listMessages.get(position).getMessage());

        return convertView;
    }

}
