package com.bmmuradov.activities;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;

public class LoginActivity extends AppCompatActivity {

    //properties
    private Button loginBttn;
    private ImageButton backBttn;
    private EditText mailField;
    private EditText passwordField;
    private FirebaseAuth authorizer;
    private FirebaseAuth.AuthStateListener authListener;
    private String TAG = "messed up";

    //methods
    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        authorizer = FirebaseAuth.getInstance();
        loginBttn= findViewById(R.id.bttn_login);
        //set on click listener to login button
        loginBttn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startProcess();
            }
        });
        authListener = new FirebaseAuth.AuthStateListener() {
            @Override
            public void onAuthStateChanged(@NonNull FirebaseAuth firebaseAuth) {
                if(firebaseAuth.getCurrentUser() != null) {
                    Log.e("inside LoginActivity", "listener called");
                    startActivity(new Intent(LoginActivity.this, ProfileActivity.class));
                }
            }
        };

        mailField= findViewById(R.id.et_email);
        passwordField= findViewById(R.id.et_password);
    }

    @Override
    protected void onStart() {
        super.onStart();
        authorizer.addAuthStateListener(authListener);
    }

    private void startProcess() {


        //get data&convert to string
        String email = mailField.getText().toString();
        String password = passwordField.getText().toString();

        if(TextUtils.isEmpty(email)||TextUtils.isEmpty(password)) {
            Toast.makeText(LoginActivity.this, "fields are empty", Toast.LENGTH_SHORT).show();
        } else {
            final ProgressDialog pd = new ProgressDialog(LoginActivity.this);
            pd.setMessage("Logging In");
            pd.show();
            //setting up the authorizer instance
            authorizer.signInWithEmailAndPassword(email, password).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                @Override
                public void onComplete(@NonNull Task<AuthResult> task) {
                    if (!task.isSuccessful()) {
                        pd.dismiss();
                        Toast.makeText(LoginActivity.this, "username or password is wrong", Toast.LENGTH_SHORT).show();
                    }
                }
            });

        }

    }
    @Override
    protected void onStop() {
        super.onStop();
        finish();
    }


}
