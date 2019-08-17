package com.bmmuradov.viewholders;

import android.view.View;
import android.widget.TextView;

import com.bmmuradov.activities.R;

//view holder for contacts
public class ViewHolderContact {



        //properties
        private TextView txtemail;
        private TextView txtphone;
        private TextView txtusername;

        //constructors
        public ViewHolderContact(View  view) {
            txtemail= (TextView) view.findViewById(R.id.tv_contact_item_email);
            txtphone=(TextView) view.findViewById(R.id.tv_contact_item_phone);
            txtusername=(TextView) view.findViewById(R.id.tv_username);
        }

        //methods
        public TextView getTxtemail() {
            return txtemail;
        }

        public void setTxtemail(String txtemail) {
            this.txtemail.setText(txtemail);
        }
        public TextView getTxtphone() {
            return txtphone;
        }

        public void setTxtphone(String txtphone) {
            this.txtphone.setText(txtphone);
        }
        public TextView getTxtusername() {
            return txtusername;
        }

        public void setTxtusername(String txtusername) {
            this.txtusername.setText(txtusername);
        }

}
