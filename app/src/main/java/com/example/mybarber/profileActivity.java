package com.example.mybarber;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import androidx.appcompat.app.AppCompatActivity;

public class profileActivity extends AppCompatActivity {
    private Button home;
    private Button chooseTurn;
    private Button privateArea;
    private Button history;
    private TextView welcome;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_loged_activity);
        findViews();
        myActivate();
    }
    //set buttons &the text view
    private void findViews() {
        //welcome = findViewById(R.id.welcomeText);
        home = findViewById(R.id.home);
        privateArea = findViewById(R.id.privateArea);
        history= fin;
    }

    //activate views &buttons
    private void myActivate() {
        login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(MainActivity.this, loginActivity.class);
                startActivity(i);
            }
        });
        register.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(MainActivity.this, registerActivity.class);
                // i.putExtra("user_id", user.getUid());
                startActivity(i);
            }
        });
    }
}