package com.example.mybarber;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class registerActivity extends AppCompatActivity {

    private EditText id;
    private EditText email;
    private EditText password;
    private EditText firstName;
    private EditText lastName;
    private Button register_button;
    private Button back;
    private FirebaseAuth mAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);
        run();
        viewsAndButtons();

    }
    public void run()
    {
        register_button = (Button)findViewById(R.id.register_button);
        back = (Button)findViewById(R.id.back);
        id = (EditText)findViewById(R.id.editTextTextId);
        email =(EditText)findViewById(R.id.editTextTextEmail);
        password =(EditText)findViewById(R.id.editTextNumberPassword);
        firstName =(EditText)findViewById(R.id.editTextTexttFrirstName);
        lastName =(EditText)findViewById(R.id.editTextTexttLastName);
    }

    private void viewsAndButtons()
    {
        mAuth = FirebaseAuth.getInstance();
        register_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (isGoodInfo(id.getText().toString(), email.getText().toString(), password.getText().toString()
                        , firstName.getText().toString(), lastName.getText().toString())) {
                    createAccount(id.getText().toString(),email.getText().toString(), password.getText().toString(), firstName.getText().toString(), lastName.getText().toString());
                } else {
                    Toast.makeText(registerActivity.this, "file is empty / has already user.", Toast.LENGTH_LONG).show();
                }
            }

        });
    }
    private boolean isGoodInfo(String id, String email, String pass, String firstName, String lastName) {
        if (id.isEmpty() || email.isEmpty() || pass.isEmpty() || firstName.isEmpty() || lastName.isEmpty()) {
            return false;
        }
        return true;
    }
    private void createAccount(String id, String email, String pass, String firstName, String lastName)
    {

    }



}