package com.example.mybarber.Activities;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.example.mybarber.R;
import com.example.mybarber.fireBase.hairCutsFB;
import com.google.firebase.auth.FirebaseAuth;

public class addHaircutActivity extends AppCompatActivity {
    EditText haircut;
    EditText price;
    private Button add;
    private  Button back;
    private FirebaseAuth mAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        //initialization
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_haircut);
        findViews();
        mAuth = FirebaseAuth.getInstance();
        myActivate();
    }
    //set buttons &the text view
    private void findViews() {
        haircut = findViewById(R.id.haircut_editText);
        price = findViewById(R.id.price_editText);
        add = findViewById(R.id.add_button);
        back = findViewById(R.id.back_button);
    }
    //activate views & buttons
    private void myActivate() {

        add.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //create the info as a string without any leading and trailing white space
                String haircutName = haircut.getText().toString().trim();
                String priceNum = price.getText().toString().trim();

                //if info is missing
                if (TextUtils.isEmpty(haircutName) || TextUtils.isEmpty(priceNum)) {
                    haircut.setError("Some fields are missing");
                    return;
                }
                //create a new haircut
                else{
                    ////////check if exist//////////
                    hairCutsFB haircut = new hairCutsFB();
                     haircut.addHairCut(haircutName, priceNum);
                    Toast.makeText(getApplicationContext(),"added successful", Toast.LENGTH_LONG).show();
                }
            }
        });

        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(addHaircutActivity.this, allHaircutActivity.class));
            }
        });
    }
}