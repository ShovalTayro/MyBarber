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
    //private Button history;
    // private TextView welcome;
    String fName , lName;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        //initialization
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_loged_activity);
        Intent iin= getIntent();
        //get extras from register/login activity
        Bundle b = iin.getExtras();
        if(b!=null) {
             fName =(String) b.get("firstName");
            lName =(String) b.get("lastName");
        }
        findViews();
         myActivate();
    }

    //set buttons &the text view
    private void findViews() {
        //welcome = findViewById(R.id.welcomeText);
        home = findViewById(R.id.home);
        privateArea = findViewById(R.id.privateArea);
        chooseTurn= findViewById(R.id.chooseTurn);
    }

    //activate views &buttons
    private void myActivate() {
        home.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(profileActivity.this, MainActivity.class);
                startActivity(i);
            }
        });
        privateArea.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //go to private area
                //Intent i = new Intent(profileActivity.this, registerActivity.class);
                // i.putExtra("user_id", user.getUid());
               // startActivity(i);
            }
        });
        chooseTurn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(profileActivity.this, selectAppointmentActivity.class);
                i.putExtra("firstName", fName);
                i.putExtra("lastName", lName);
                startActivity(i);
            }
        });
    }
}