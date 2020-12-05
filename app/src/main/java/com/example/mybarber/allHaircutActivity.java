package com.example.mybarber;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListAdapter;
import android.widget.ListView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.mybarber.Adapter.haircutAdapter;
import com.example.mybarber.Objects.hairCut;
import com.example.mybarber.fireBase.hairCutsFB;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

public class allHaircutActivity extends AppCompatActivity {
    private Button addHaircuts;
    private Button back;
    private FirebaseAuth fa;
    private RecyclerView recyclerView;
    private LinearLayoutManager linearLayoutManager;
    private List<hairCut> haircutsList;
    private com.example.mybarber.Adapter.haircutAdapter haircutAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        //initialization
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_all_haircut);
        findViews();
        myActivate();
    }

    //set buttons &the text view
    private void findViews() {
        addHaircuts = findViewById(R.id.addHaircut_button);
        back = findViewById(R.id.back_button3);
        recyclerView = (RecyclerView) findViewById(R.id.recV);
    }

    //activate views &buttons
    private void myActivate() {

        addHaircuts.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(allHaircutActivity.this, addHaircutActivity.class);
                startActivity(i);
            }
        });

        linearLayoutManager = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(linearLayoutManager);
        recyclerView.setHasFixedSize(true);
        haircutsList = new ArrayList<>();

        haircutAdapter = new haircutAdapter(allHaircutActivity.this);
        recyclerView.setAdapter(haircutAdapter);
        hairCutsFB haircut = new hairCutsFB();
        DatabaseReference dr = haircut.allHairCuts();
        dr.addListenerForSingleValueEvent(new ValueEventListener() {
            //update haircuts
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                for (DataSnapshot s : snapshot.getChildren()) {
                    haircutsList.add(s.getValue(hairCut.class));
                }
                haircutAdapter.setHaircutsList(haircutsList);
                haircutAdapter.notifyDataSetChanged();
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
            }
        });

        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(allHaircutActivity.this,managerActivity.class));
            }
        });
    }
}
