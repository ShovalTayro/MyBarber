package com.example.mybarber;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.example.mybarber.Objects.hairCut;
import com.example.mybarber.fireBase.hairCutsFB;
import com.example.mybarber.fireBase.initializeFB;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class addHaircutActivity extends AppCompatActivity {

    EditText haircut;
    EditText price;
    private Button add;
    private  Button back;

    private FirebaseAuth mAuth;
    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_haircut);
        findViews();
       mAuth = FirebaseAuth.getInstance();
        myActivate();
    }

    private void findViews()
    {
        haircut = findViewById(R.id.haircut_editText);
        price = findViewById(R.id.price_editText);
        add = findViewById(R.id.add_button);
        back = findViewById(R.id.back_button);
    }

    private void myActivate()
    {
        add.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String haircutName = haircut.getText().toString().trim();
                String priceNum = price.getText().toString().trim();

                //if info is missing
                if (TextUtils.isEmpty(haircutName) || TextUtils.isEmpty(priceNum)) {
                    haircut.setError("Some fields are missing");
                    return;
                }
                else{
                    hairCutsFB haircut = new hairCutsFB();
                     haircut.addHairCut(haircutName, priceNum);
                    Toast.makeText(getApplicationContext(),"added successful", Toast.LENGTH_LONG).show();
                }
            }
        });

        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(addHaircutActivity.this,allHaircutActivity.class));
            }
        });
    }
}