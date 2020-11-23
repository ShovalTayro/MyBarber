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

public class registerActivity extends AppCompatActivity {

    private EditText email;
    private EditText password;
    private EditText firstName;
    private EditText lastName;
    private EditText phone_number;

    private Button register_button;
    private Button back;

    private FirebaseAuth mAuth;
    private ProgressBar progressBar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);
        run();
        mAuth = FirebaseAuth.getInstance();
        viewsAndButtons();
    }

    public void run() {
        progressBar = findViewById(R.id.progressBar2);

        register_button = (Button) findViewById(R.id.login_button);
        back = (Button) findViewById(R.id.back);

        email = (EditText) findViewById(R.id.editTextTextEmail);
        phone_number = (EditText) findViewById(R.id.editTextPhoneNumber);
        password = (EditText) findViewById(R.id.editTextNumberPassword);
        firstName = (EditText) findViewById(R.id.editTextTexttFrirstName);
        lastName = (EditText) findViewById(R.id.editTextTexttLastName);
    }
    private void viewsAndButtons() {
        register_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //GET INFO FROM THE USER
                String fName = firstName.getText().toString().trim();
                String lName = lastName.getText().toString().trim();
                String Email = email.getText().toString().trim();
                String phone = phone_number.getText().toString().trim();
                String pass = password.getText().toString().trim();

                //if info is missing
                if (TextUtils.isEmpty(Email) || TextUtils.isEmpty(pass) || TextUtils.isEmpty(fName)
                        || TextUtils.isEmpty(lName) || TextUtils.isEmpty(phone)) {
                    email.setError("Some fields are missing");
                    return;
                } else {
                    progressBar.setVisibility(View.VISIBLE);
                    mAuth.createUserWithEmailAndPassword(Email, pass).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                        @Override
                        public void onComplete(@NonNull Task<AuthResult> task) {
                            if (task.isSuccessful()) {
                                Toast.makeText(registerActivity.this, "User has created", Toast.LENGTH_SHORT).show();
                                Intent i = new Intent(registerActivity.this, user_loged_activity.class);

                                i.putExtra(fName,fName);
                                i.putExtra(lName,lName);

                                startActivity(i);
                            } else {
                                Toast.makeText(registerActivity.this, "ERROR " + task.getException().getMessage(), Toast.LENGTH_SHORT).show();
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
                startActivity(new Intent(registerActivity.this,MainActivity.class));
            }
        });
         }
}