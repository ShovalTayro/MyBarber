package com.example.mybarber;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;

import androidx.appcompat.app.AppCompatActivity;

public class allHaircutActivity extends AppCompatActivity {
    private Button addHaircuts;
    ListView list;
    //need to read all haircuts from database
    String haircutsList[] = {"haircut 1" , "haircut 2", "haircut 3", "haircut 4"};

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_all_haircut);
        findViews();
        myActivate();
    }

    //set buttons &the text view
    private void findViews() {
        addHaircuts = findViewById(R.id.addHaircut_button);
        list = findViewById(R.id.list_item);
        /*TextView title = new TextView(this);
        title.setText("All My Haircuts");
        list.addHeaderView(title);
         */

        ArrayAdapter<String> arrayAdapter = new ArrayAdapter<String>(this, R.layout.haircuts_list,
        R.id.textView, haircutsList);
        list.setAdapter(arrayAdapter);
    }

    //activate views &buttons
    private void myActivate() {
        addHaircuts.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                  Intent i = new Intent(allHaircutActivity.this , addHaircutActivity.class);
                  startActivity(i);
            }
        });
    }
}
