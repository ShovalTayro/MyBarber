package com.example.mybarber.Activities;

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

import com.example.mybarber.R;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

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
                            //check if the user is admin
                            FirebaseDatabase.getInstance().getReference().child("Admin").addListenerForSingleValueEvent(new ValueEventListener() {
                                //if found admin get him from FB
                                @Override
                                public void onDataChange(@NonNull DataSnapshot snapshot) {
                                    //go through all admins
                                    for (DataSnapshot s : snapshot.getChildren()) {
                                        //if admin is the user who tru to log in
                                        if (s.child("email").getValue(String.class).equals(Email)) {
                                            Intent i = new Intent(loginActivity.this, managerActivity.class);
                                            i.putExtra("firstName" , s.child("fName").getValue(String.class));
                                            i.putExtra("lastName" , s.child("lName").getValue(String.class));
                                            startActivity(i);
                                        }
                                    }
                                }
                                @Override
                                public void onCancelled(@NonNull DatabaseError error) {

                                }
                            });
                            //check if user
                            FirebaseDatabase.getInstance().getReference().child("users").addListenerForSingleValueEvent(new ValueEventListener() {
                                //if found the user get his name from FB
                                @Override
                                public void onDataChange(@NonNull DataSnapshot snapshot) {
                                    //go through all users
                                    for (DataSnapshot s : snapshot.getChildren()) {
                                        //if this is the user who try to log in
                                        if (s.child("email").getValue(String.class).equals(Email)) {
                                            //go to user profile
                                            Intent i = new Intent(loginActivity.this, profileActivity.class);
                                            i.putExtra("firstName" , s.child("fName").getValue(String.class));
                                            i.putExtra("lastName" , s.child("lName").getValue(String.class));
                                            i.putExtra("phone", s.child("phone").getValue(String.class));
                                            startActivity(i);
                                        }
                                    }
                                }
                                @Override
                                public void onCancelled(@NonNull DatabaseError error) {

                                }
                            });
                       }
                        //on failure to login
                        else {
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
                startActivity(new Intent(loginActivity.this, MainActivity.class));
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