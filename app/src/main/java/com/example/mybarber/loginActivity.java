package com.example.mybarber;

//import android.app.AlertDialog;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class loginActivity extends AppCompatActivity {

    private EditText email;
    private EditText password;
    private Button login;
    private Button back;
    private TextView forgot_pass;
    private FirebaseAuth mAuth;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        //initialization
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        findViews();
        mAuth = FirebaseAuth.getInstance();
        myActivate();
    }
    //set buttons &the text view
    public void findViews() {
        email = (EditText) findViewById(R.id.editTextTextEmail);
        password = (EditText) findViewById(R.id.editTextNumberPassword);
        login = (Button) findViewById(R.id.login_button);
        back = (Button) findViewById(R.id.back);
        forgot_pass = (TextView) findViewById(R.id.forgot_password1);
    }
    //activate views &buttons
    private void myActivate() {
    login.setOnClickListener(new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            //create the info as a string without any leading and trailing white space
            String Email = email.getText().toString().trim();
            String pass = password.getText().toString().trim();
            //if input is empty
            if (TextUtils.isEmpty(Email) || TextUtils.isEmpty(pass) ) {
                email.setError("Some fields are missing");
                return;
            }
            //try to login
            else {
              mAuth.signInWithEmailAndPassword(Email,pass).addOnCompleteListener(new OnCompleteListener<AuthResult>() {

                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        //if succeed to login alter
                        if (task.isSuccessful()) {
                            Toast.makeText(loginActivity.this, "User is logging", Toast.LENGTH_SHORT).show();
                            Intent i = new Intent(loginActivity.this, profileActivity.class);
                            //get the userName
                            FirebaseUser user = mAuth.getCurrentUser();
                             //////////////maybe doesn't work//////////
                               String fName= user.getDisplayName();
                               i.putExtra("firstName", fName);
                            startActivity(i);
                        } else {
                            Toast.makeText(loginActivity.this, "ERROR " + task.getException().getMessage(), Toast.LENGTH_SHORT).show();
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
     forgot_pass.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {
            EditText resetMail = new EditText(v.getContext());
                AlertDialog.Builder passwordResetDialog = new  AlertDialog.Builder(v.getContext());
                passwordResetDialog.setTitle("Reset Password ?");
                passwordResetDialog.setMessage("Enter your email to received reset Link. ");
                passwordResetDialog.setView(resetMail);
                passwordResetDialog.setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        String mail = resetMail.getText().toString().trim();
                        mAuth.sendPasswordResetEmail(mail).addOnSuccessListener(new OnSuccessListener<Void>() {
                            @Override
                            public void onSuccess(Void aVoid) {
                                Toast.makeText(loginActivity.this,"reset Link sent to your Email. ",Toast.LENGTH_SHORT).show();
                            }
                        }).addOnFailureListener(new OnFailureListener() {
                            @Override
                            public void onFailure(@NonNull Exception e) {
                                Toast.makeText(loginActivity.this, " Error ! reset link isn't sent "+ e.getMessage(),Toast.LENGTH_SHORT).show();
                            }
                        });
                    }
                });
                passwordResetDialog.setNegativeButton("no", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {

                    }
                });
                passwordResetDialog.create().show();
            }
        });
    }
}