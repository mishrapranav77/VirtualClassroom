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
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class LoginTeacherActivity extends AppCompatActivity {

    EditText name2, password2;
    TextView mRegister2;
    TextView info2;
    Button login2;
    int counter2 = 5;
    private FirebaseAuth auth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login_teacher);

        name2=(EditText)findViewById(R.id.UserName2);
        password2=(EditText)findViewById(R.id.password2);
        login2=(Button)findViewById(R.id.login2);
        mRegister2=(TextView)findViewById(R.id.mRegister2);
        info2=(TextView)findViewById(R.id.attempt2);
        info2.setText("No of attempts remaining: 5");

        auth = FirebaseAuth.getInstance();

        login2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                validate(name2.getText().toString(), password2.getText().toString());
                //Log.i("hello","loginclick");

            }
        });

        mRegister2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(LoginTeacherActivity.this, RegisterActivity.class));
                //Log.i("hello","register click");

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
                            //Log.d(TAG, "signInWithEmail:success");
                            FirebaseUser user = auth.getCurrentUser();
                            updateUI(user);
                        } else {
                            // If sign in fails, display a message to the user.
                           // Log.w(TAG, "signInWithEmail:failure", task.getException());
                            Toast.makeText(LoginTeacherActivity.this, "Authentication failed.",
                                    Toast.LENGTH_SHORT).show();

                            counter2--;
                            info2.setText("No of attempts remaining:" + String.valueOf(counter2));
                            if (counter2 == 0) {
                                login2.setEnabled(false);
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
        Intent intent = new Intent(LoginTeacherActivity.this, TeacherMenuActivity.class);
        startActivity(intent);
    }
}
