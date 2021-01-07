package com.example.mybarber.Activities;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.example.mybarber.R;
import com.google.firebase.auth.FirebaseAuth;

public class managerActivity extends AppCompatActivity {
    private Button calendar;
    private Button haircuts;
    private Button  logout;
    private ImageButton messages;
    private TextView welcome;
    private FirebaseAuth firebaseAuth;
    String fName;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        //initialization
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_manager);
        firebaseAuth= firebaseAuth.getInstance();
        Intent iin= getIntent();
        //get extras from register/login activity
        Bundle b = iin.getExtras();
        if(b!=null) {
            fName =(String) b.get("firstName");
        }
        findViews();
        myActivate();
    }

    //set buttons &the text view
    private void findViews() {
        welcome = findViewById(R.id.welcome);
        calendar = findViewById(R.id.calendar_button);
        haircuts = findViewById(R.id.haircuts_button);
        logout = findViewById(R.id.logout_button);
        messages = findViewById(R.id.message_button);
    }

    //activate views &buttons
    private void myActivate() {
        calendar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(managerActivity.this, allAppointmentActivity.class);
                i.putExtra("firstName" , fName);
                startActivity(i);
            }
        });
        logout.setOnClickListener((new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                firebaseAuth.signOut();
                finish();
                startActivity(new Intent(managerActivity.this, MainActivity.class ));
            }
        }));
        haircuts.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(managerActivity.this, allHaircutActivity.class);
                i.putExtra("firstName" , fName);
                startActivity(i);
            }
        });
        messages.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(managerActivity.this, allMessageActivity.class);
                i.putExtra("firstName" , fName);
                startActivity(i);
            }
        });
       //welcome.append(fName);
    }
}