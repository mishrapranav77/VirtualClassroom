package com.example.virtualclassroom;

import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.FirebaseApp;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class LoginActivity extends AppCompatActivity {
    private static final String TAG = "virtualclasslogin";
    EditText name, password;
    TextView mRegister;
    TextView info;
    Button login;
    int counter = 5;
    private FirebaseAuth auth;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        FirebaseApp.initializeApp(this);

        setContentView(R.layout.activity_login);
        name=(EditText)findViewById(R.id.UserName);
        password=(EditText)findViewById(R.id.password);
        login=(Button)findViewById(R.id.login);
        mRegister=(TextView)findViewById(R.id.mRegister);
        info=(TextView)findViewById(R.id.attempt);
        info.setText("No of attempts remaining: 5");
        Log.i("hello","beforeinit");

        //FirebaseApp.initializeApp(this);
        Log.i("hello","initialize");
        auth = FirebaseAuth.getInstance();
        Log.i("hello","auth");

        /*if (auth.getCurrentUser() != null) {
            startActivity(new Intent(LoginActivity.this, StudentMenuActivity.class));
            finish();
        }*/
        login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                validate(name.getText().toString(), password.getText().toString());
                Log.i("hello","loginclick");

            }
        });
        mRegister.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(LoginActivity.this, RegisterActivity.class));
                Log.i("hello","register click");

            }
        });
    }
    private void validate(String email, String password){
        auth.signInWithEmailAndPassword(email, password)
                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()) {
                            // Sign in success, update UI with the signed-in user's information
                            Log.d(TAG, "signInWithEmail:success");
                            FirebaseUser user = auth.getCurrentUser();
                            updateUI(user);
                        } else {
                            // If sign in fails, display a message to the user.
                            Log.w(TAG, "signInWithEmail:failure", task.getException());
                            Toast.makeText(LoginActivity.this, "Authentication failed.",
                                    Toast.LENGTH_SHORT).show();

                            counter--;
                            info.setText("No of attempts remaining:" + String.valueOf(counter));
                            if (counter == 0) {
                                login.setEnabled(false);
                            }
                            //updateUI(null);
                        }

                    }
                });


    }
    @Override
    public void onStart() {
        super.onStart();
        // Check if user is signed in (non-null) and update UI accordingly.
        FirebaseUser currentUser = auth.getCurrentUser();
        if (currentUser!=null){
        updateUI(currentUser);}
    }

    private void updateUI(FirebaseUser currentUser) {
        Intent intent = new Intent(LoginActivity.this, StudentMenuActivity.class);
        startActivity(intent);
    }
}
