package com.bmmuradov.activities;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.bmmuradov.core.ContactsData;
import com.bmmuradov.core.User;
import com.firebase.client.Firebase;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;


public class RegisterActivity extends AppCompatActivity {

    //properties
    private EditText email, password, phone, username;
    private Button registerButton;
    private FirebaseAuth myAuth;
    private Firebase myDb;
    private FirebaseAuth.AuthStateListener authListener;
    private FirebaseAuth authorizer;
    //methods
    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        authorizer=FirebaseAuth.getInstance();
        setContentView(R.layout.activity_register);
        //binding xml elements...
        email = findViewById(R.id.et_email_reg);
        password = findViewById(R.id.et_password_reg);
        phone = findViewById(R.id.et_phone_number_reg);
        username = findViewById(R.id.et_username_reg);
        registerButton = findViewById(R.id.bttn_register_reg);
        authListener = new FirebaseAuth.AuthStateListener() {
            @Override
            public void onAuthStateChanged(@NonNull FirebaseAuth firebaseAuth) {
                if(firebaseAuth.getCurrentUser() != null) {
                    Log.e("inside LoginActivity", "listener called");
                    startActivity(new Intent(RegisterActivity.this, ProfileActivity.class));
                }
            }
        };
        Firebase.setAndroidContext(this);
        myDb = new Firebase("https://crocchat-c5b43.firebaseio.com/");
        registerButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final String mail;
                final String pass;
                final String phonenum;
                final String usname;
                mail = email.getText().toString().trim();
                pass = password.getText().toString().trim();
                phonenum = phone.getText().toString().trim();
                usname = username.getText().toString().trim();

                boolean valid = validate();

                myAuth = FirebaseAuth.getInstance();
                if (valid) {
                    final ProgressDialog pd = new ProgressDialog(RegisterActivity.this);
                    pd.setMessage("Registering, please wait ...");
                    pd.show();

                    myAuth.createUserWithEmailAndPassword(mail, pass).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                        @Override
                        public void onComplete(@NonNull Task<AuthResult> task) {
                            Log.e("inside onComplete", "initial");
                            if (task.isSuccessful()) {
                                Toast.makeText(getApplicationContext(), "user has registered", Toast.LENGTH_SHORT);
                                Log.e("inside onComplete", "isSuccessful");
                                FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
                                if (user != null) {
                                    // User is signed in
                                    String uid = user.getUid();
                                    //showing username in allchat
                                    User newuser= new User(usname, pass, uid );
                                    newuser.setUsername(usname);
                                    ContactsData newContact= new ContactsData(mail, phonenum, uid, usname, false);
                                    myDb.child("users").push().setValue(newuser);
                                    myDb.child("contact_list").push().setValue(newContact);
                                } else {
                                    // No user is signed in
                                }

                            } else {
                                Toast.makeText(getApplicationContext(), "unsuccessfull", Toast.LENGTH_SHORT);
                                Log.e("inside onComplete", "unsuccessful");
                            }
                        }
                    });


                }
            }
        });
    }

    @Override
    protected void onStart() {
        super.onStart();
        authorizer.addAuthStateListener(authListener);
    }
    @Override
    protected void onDestroy() {
        super.onDestroy();
        finishActivity(0);
    }

    public boolean validate() {
        boolean valid = true;

        if (email.length()==0 || email.equals(null)) {
            email.setError("please, enter a valid e-mail address");
            valid = false;
        }
        if (password.length()==0 || password.equals(null)) {
            password.setError("password length cannot be less than 6");
            valid=false;
        }
        if(phone.length()==0 || phone.equals(null)) {
            phone.setError("phone number cannot be empty");
            valid=false;
        }

        if(username.length()==0 || username.equals(null)) {
            username.setError("username cannot be empty");
            valid=false;
        }

        return valid;
    }

    @Override
    protected void onStop() {
        super.onStop();
        finish();
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        finish();
    }

}
