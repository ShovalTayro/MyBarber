package com.example.mybarber;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.example.mybarber.Objects.Appointment;
import com.example.mybarber.fireBase.AdminFB;
import com.example.mybarber.fireBase.usersFB;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

import java.util.ArrayList;
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
    private AdminFB root2;
    private Spinner userSpinner;
   private  String authSelected;
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
        userSpinner= (Spinner) findViewById(R.id.userSpinner);
    }
    //activate views &buttons
    private void myActivate() {
        ArrayList<String> userAuth = new ArrayList<String>();
        userAuth.add("Admin");
        userAuth.add("Client");
        //make adapter to connect between the spinner to useerAuth
        ArrayAdapter<String> adapter = new ArrayAdapter<String>(registerActivity.this, android.R.layout.simple_spinner_dropdown_item, userAuth);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        userSpinner.setAdapter(adapter);

        //activate spinner
        userSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                 authSelected =parent.getSelectedItem().toString();
            }
            @Override
            public void onNothingSelected(AdapterView<?> parent) {
            }
        });

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
                            if (task.isSuccessful()) {
                                //check if user/admin and add him
                                if(authSelected.equals("Admin")){
                                    //check if there is admin
                                    root2= new AdminFB();
                                    root2.getAdmin();
                                   if(root2==null) {
                                       //create admin
                                       //    root2 = new AdminFB();
                                       //add user to fireBase
                                       root2.addAdminToDB(fName, lName, phone, Email, pass);
                                       Intent i = new Intent(registerActivity.this, managerActivity.class);
                                       Toast.makeText(registerActivity.this, "Admin has created", Toast.LENGTH_SHORT).show();
                                       i.putExtra("firstName", fName);
                                       i.putExtra("lastName", lName);
                                       // i.putExtra("phone", phone);
                                       startActivity(i);
                                   }
                                    else {
                                        //check if stuck and maybe refresh login
                                        Toast.makeText(registerActivity.this, "Error, there's already Admin", Toast.LENGTH_LONG).show();
                                    }
                                }
                                else {
                                    //create user
                                    root = new usersFB();
                                    //add user to fireBase
                                    root.addUserToDB(fName,lName,phone,Email,pass);
                                    Intent i = new Intent(registerActivity.this, profileActivity.class);
                                    Toast.makeText(registerActivity.this, "User has created", Toast.LENGTH_SHORT).show();
                                    i.putExtra("firstName",fName);
                                    i.putExtra("lastName",lName);
                                    i.putExtra("phone", phone);
                                    startActivity(i);
                                }
                                //failure to register
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
