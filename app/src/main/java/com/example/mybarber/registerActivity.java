package com.example.mybarber;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.example.mybarber.fireBase.usersFB;
import com.example.mybarber.fireBase.usersFB;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;


public class registerActivity extends AppCompatActivity {

    private EditText email;
    private EditText password;
    private EditText firstName;
    private EditText lastName;
    private EditText phone_number;

    private Button register_button;
    private Button back;

    private FirebaseAuth mAuth;
    private usersFB root;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);
        run();
        mAuth = FirebaseAuth.getInstance();
        viewsAndButtons();
    }

    public void run() {
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
                    mAuth.createUserWithEmailAndPassword(Email, pass).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                     //creating user
                        @Override
                        public void onComplete(@NonNull Task<AuthResult> task) {
                            FirebaseUser currentUser = mAuth.getCurrentUser();
                            if (task.isSuccessful()) {
                                currentUser.sendEmailVerification().addOnSuccessListener(new OnSuccessListener<Void>() {
                                    @Override
                                    public void onSuccess(Void aVoid) {
                                        Toast.makeText(registerActivity.this, "Verification Email has sent. ", Toast.LENGTH_SHORT).show();
                                    }
                                }).addOnFailureListener(new OnFailureListener() {
                                    @Override
                                    public void onFailure(@NonNull Exception e) {
                                        Log.d("tag", "OnFailure: Email has not sent. " + e.getMessage());
                                    }
                                });
                                //here need to create admin / user registration
                                root = new usersFB();
                                root.addUserToDB(fName,lName,phone,Email,pass);
                               Intent i = new Intent(registerActivity.this, profileActivity.class);
                                Toast.makeText(registerActivity.this, "User has created", Toast.LENGTH_SHORT).show();
                                i.putExtra(fName,fName);
                                i.putExtra(lName,lName);
                                startActivity(i);

                            } else {
                                Toast.makeText(registerActivity.this, "ERROR " + task.getException().getMessage(), Toast.LENGTH_SHORT).show();
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