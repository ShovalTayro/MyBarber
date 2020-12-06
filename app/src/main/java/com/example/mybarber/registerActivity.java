package com.example.mybarber;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.example.mybarber.fireBase.usersFB;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

import java.util.regex.Matcher;
import java.util.regex.Pattern;


public class registerActivity extends AppCompatActivity {
    private EditText emailEditor;
    private EditText passwordEditor;
    private EditText firstName;
    private EditText lastName;
    private EditText phone_number;
    private Button register_button;
    private Button back;
    private FirebaseAuth mAuth;
    private usersFB root;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        //initialization
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);
        findViews();
        mAuth = FirebaseAuth.getInstance();
        myActivate();
    }
    //set buttons &the text view
    public void findViews() {
        register_button = (Button) findViewById(R.id.login_button);
        back = (Button) findViewById(R.id.back);
        emailEditor = (EditText) findViewById(R.id.editTextTextEmail);
        phone_number = (EditText) findViewById(R.id.editTextPhoneNumber);
        passwordEditor = (EditText) findViewById(R.id.editTextNumberPassword);
        firstName = (EditText) findViewById(R.id.editTextTexttFrirstName);
        lastName = (EditText) findViewById(R.id.editTextTexttLastName);
    }
    //activate views &buttons
    private void myActivate() {

        register_button.setOnClickListener(new View.OnClickListener() {
                 @Override
             public void onClick(View v) {
                //create the info as a string without any leading and trailing white space
                String fName = firstName.getText().toString().trim();
                String lName = lastName.getText().toString().trim();
                String Email = emailEditor.getText().toString().trim();
                String phone = phone_number.getText().toString().trim();
                String pass = passwordEditor.getText().toString().trim();

                //if info is missing
                     /*
                     if (!validInfo(fName,lName, Email, phone, pass)) {
                    //email.setError("Some fields are missing");
                    return;
                     }
                     */
                     //create account
                  //  else {
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
                                //add user to fireBase
                                root = new usersFB();
                                root.addUserToDB(fName,lName,phone,Email,pass);
                               Intent i = new Intent(registerActivity.this, profileActivity.class);
                                Toast.makeText(registerActivity.this, "User has created", Toast.LENGTH_SHORT).show();
                                i.putExtra("firstName",fName);
                                i.putExtra("lastName",lName);
                                i.putExtra("phone", phone);
                                startActivity(i);

                            } else {
                                Toast.makeText(registerActivity.this, "ERROR " + task.getException().getMessage(), Toast.LENGTH_SHORT).show();
                            }
                        }
                    });
                //}
            }
        });

        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(registerActivity.this,MainActivity.class));
            }
        });
         }
/*
    //we use this web for validation of creating account
    //https://www.taimoorsikander.com/registration-form-validation-in-android-studio/
    private boolean validInfo(String fName, String lName, String email, String phone, String pass) {

        // if there is an empty field
        if (email.isEmpty() || pass.isEmpty() || phone.isEmpty() || fName.isEmpty() || lName.isEmpty()){
            return false;
        }

        // email validation
        String regex = "^([_a-zA-Z0-9-]+(\\.[_a-zA-Z0-9-]+)*@[a-zA-Z0-9-]+(\\.[a-zA-Z0-9-]+)*(\\.[a-zA-Z]{1,6}))?$";
        Pattern pattern = Pattern.compile(regex);
        Matcher matcher = pattern.matcher(email);
        if (!matcher.matches()) {
            emailEditor.setError("Email is invalid");
            return false;
        }

        // check if password is at least 8 digits
        if (!validatePassword(pass)) {
            passwordEditor.setError("Password length must be at least 6");
            return false;
        }

        // check if first name contains only letters
        if (!fName.matches("[a-zA-Z]+")) {
            firstName.setError("First name is invaild, can't contain digits");
            return false;
        }

        // check if last name contains only letters
        if (!lName.matches("[a-zA-Z]+")) {
            lastName.setError("Last name is invaild, can't contain digits");
            return false;
        }

        // check if phone number contains only numbers & in the right length
        if (!phone.matches("[0-9]+") || phone.length() != 10) {
            phone_number.setError("Phone must contain only digits, and 10 digits");
            return false;
        }

        return true;
    }
        private static boolean validatePassword(String password) {
            Pattern pattern;
            Matcher matcher;
            final String PASSWORD_PATTERN = "^(?=.*[0-9])(?=.*[A-Z])(?=.* [@#$%^&+=!])(?=\\S+$).{4,}$";
            pattern = Pattern.compile(PASSWORD_PATTERN);
            matcher = pattern.matcher(password);

            return matcher.matches();
        }
*/
}
