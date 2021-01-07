package com.example.mybarber.Activities;
/*
import android.os.Bundle;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.snackbar.Snackbar;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.view.View;

import android.view.Menu;
import android.view.MenuItem;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }
}
*/
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.example.mybarber.R;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.HashMap;
import java.util.Map;

public class MainActivity extends AppCompatActivity {
    private Button login;
    private Button register;
    FirebaseFirestore db = FirebaseFirestore.getInstance();
   // private TextView myBarber;
    /*private TextView welcome;
    private TextView loginT;
    */
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        //initialization
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        findViews();
        myActivate();
        // logedInModifier();
    }
    /*
    @Override
    protected void onResume() {
        super.onResume();
        mAuth = FirebaseAuth.getInstance();
        //FirebaseUser user = myInfo.getCurrentUser();
        //if logged in
        if (user != null) {
            String name = user.getDisplayName();
            welcome.setText("Hello, " + name + " :)");
            loginT.setText("Profile");
        } else {
            welcome.setText("Hello, Guest :)");
            loginT.setText("Login/Register");
        }
    }
*/

    //set buttons &the text view
    private void findViews() {
        //welcome = findViewById(R.id.welcomeText);
        login = (Button)findViewById(R.id.login_button);
      //  myBarber = (TextView)findViewById(R.id.myBarber2);
        register = (Button)findViewById(R.id.register_button);

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
                startActivity(i);
            }
        });
    }
/*
    private void logedInModifier() {
        mAuth = FirebaseAuth.getInstance();
        FirebaseUser user = mAuth.getCurrentUser();
        if (user != null) // user is logged in
        {
            String disp_name = user.getDisplayName();
            welcome.setText("Hello, " + disp_name + " :)");
            login.setText("Profile");
        } else {
            welcome.setText("Hello, Guest :)");
            login.setText("Login/Register");
        }
    }

 */
}