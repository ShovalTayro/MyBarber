package com.example.mybarber;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.HashMap;

public class registerActivity extends AppCompatActivity {

    private EditText email;
    private EditText password;
    private EditText firstName;
    private EditText lastName;
    private EditText phone_number;
    private String userID ;


    private Button register_button;
    private Button back;

    private FirebaseAuth mAuth;
    private FirebaseFirestore fStore ;
    private ProgressBar progressBar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);
        run();
        mAuth = FirebaseAuth.getInstance();
        fStore = FirebaseFirestore.getInstance();
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
                  //  progressBar.setVisibility(View.VISIBLE);
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
                                //store user in firebase
                                userID = mAuth.getCurrentUser().getUid();
                                DocumentReference documentReference = fStore.collection("Users").document(userID);
                                HashMap<String,Object> user = new HashMap<>();
                                user.put("fName", fName);
                                user.put("lName", lName);
                                user.put("phoneNumber", phone);
                                user.put("email", Email);
                                documentReference.set(user).addOnSuccessListener(new OnSuccessListener<Void>() {
                                    @Override
                                    public void onSuccess(Void aVoid) {
                                        Log.d("TAG","onSuccess: user profile is created for "+ userID);
                                    }
                                });
                                Intent i = new Intent(registerActivity.this, user_loged_activity.class);
                                Toast.makeText(registerActivity.this, "User has created", Toast.LENGTH_SHORT).show();
                                i.putExtra(fName,fName);
                                i.putExtra(lName,lName);
                                startActivity(i);
                            } else {
                                Toast.makeText(registerActivity.this, "ERROR " + task.getException().getMessage(), Toast.LENGTH_SHORT).show();
                               // progressBar.setVisibility(View.INVISIBLE);
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