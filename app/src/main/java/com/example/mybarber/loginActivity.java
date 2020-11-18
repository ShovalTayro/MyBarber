package com.example.mybarber;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;

public class loginActivity extends AppCompatActivity {

    EditText email;
    EditText password;

    Button login;
    Button back;

    private ProgressBar progressBar;
    private FirebaseAuth mAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState)

    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        getViews();

        mAuth = FirebaseAuth.getInstance();

        buttons();
    }

    public void getViews()
    {
        email = (EditText) findViewById(R.id.editTextTextEmail);
        password = (EditText) findViewById(R.id.editTextNumberPassword);
        login = (Button) findViewById(R.id.login_button);
        back = (Button) findViewById(R.id.back);

        progressBar = findViewById(R.id.progressBar2);
    }
    private void buttons()
    {
    login.setOnClickListener(new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            String Email = email.getText().toString().trim();
            String pass = password.getText().toString().trim();

            if (TextUtils.isEmpty(Email) || TextUtils.isEmpty(pass) ) {
                email.setError("Some fields are missing");
                return;
            }else {
                progressBar.setVisibility(View.VISIBLE);
                mAuth.signInWithEmailAndPassword(Email,pass).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()) {
                            Toast.makeText(loginActivity.this, "User is logging", Toast.LENGTH_SHORT).show();
                            Intent i = new Intent(loginActivity.this, user_loged_activity.class);
                            startActivity(i);
                        } else {
                            Toast.makeText(loginActivity.this, "ERROR " + task.getException().getMessage(), Toast.LENGTH_SHORT).show();
                            progressBar.setVisibility(View.INVISIBLE);
                        }
                    }
                });
            }
        }
    });
        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(loginActivity.this,MainActivity.class));
            }
        });
    }
}