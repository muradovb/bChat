package com.bmmuradov.viewholders;

import android.view.View;
import android.widget.TextView;

import com.bmmuradov.activities.R;

public class ViewHolderMessage {
    //properties
    private TextView txtMessage;

    private TextView txtContactName;

    private TextView txtTime;

    //constructors
    public ViewHolderMessage(View view) {
        txtMessage=view.findViewById(R.id.tv_message);
        txtContactName=view.findViewById(R.id.tv_contact_name);
        txtTime=view.findViewById(R.id.tv_time);
    }

    //methods
    public TextView getTxtContactName() {
        return txtContactName;
    }

    public void setTxtContactName(String txtContactName) {
        this.txtContactName.setText(txtContactName);
    }

    public TextView getTxtTime() {
        return txtTime;
    }

    public void setTxtTime(String txtTime) {
        this.txtTime.setText(txtTime);
    }

    public TextView getTxtMessage() {
        return txtMessage;
    }

    public void setTxtMessage(String txtMessage) {
        this.txtMessage.setText(txtMessage);
    }

}
