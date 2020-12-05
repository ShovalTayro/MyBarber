package com.example.mybarber;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import androidx.appcompat.app.AppCompatActivity;

public class managerActivity extends AppCompatActivity {
    private Button calendar;
    private Button haircuts;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        //initialization
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_manager);
        findViews();
        myActivate();
    }

    //set buttons &the text view
    private void findViews() {
        calendar = findViewById(R.id.calendar_button);
        haircuts = findViewById(R.id.haircuts_button);
    }

    //activate views &buttons
    private void myActivate() {
        calendar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                  Intent i = new Intent(managerActivity.this, allAppointmentActivity.class);
                  startActivity(i);
            }
        });
        haircuts.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(managerActivity.this, allHaircutActivity.class);
                startActivity(i);
            }
        });
    }
}