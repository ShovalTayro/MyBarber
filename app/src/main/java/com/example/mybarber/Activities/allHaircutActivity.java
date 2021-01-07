package com.example.mybarber.Activities;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.mybarber.Adapter.haircutAdapter;
import com.example.mybarber.Objects.hairCut;
import com.example.mybarber.R;
import com.example.mybarber.fireBase.hairCutsFB;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

public class allHaircutActivity extends AppCompatActivity {
    private Button addHaircuts, back;
    private FirebaseAuth fa;
    private RecyclerView recyclerView;
    private List<hairCut> haircutsList;
    private com.example.mybarber.Adapter.haircutAdapter haircutAdapter;
    private String fName;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        //initialization
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_all_haircut);
        Intent iin= getIntent();
        Bundle b = iin.getExtras();
        if(b!=null) {
            fName =(String) b.get("firstName");
        }
        findViews();
        myActivate();
    }

    //set buttons &the text view
    private void findViews()
    {
        recyclerView = (RecyclerView) findViewById(R.id.manager_gallery);
        addHaircuts = findViewById(R.id.addHaircut);
        back = findViewById(R.id.back_button3);
    }

    //activate views &buttons
    private void myActivate() {

        RecyclerView.LayoutManager layoutManager = new GridLayoutManager(this, 3);
        recyclerView.setLayoutManager(layoutManager);
        recyclerView.setHasFixedSize(true);
        haircutsList = new ArrayList<>();

        haircutAdapter = new haircutAdapter(this);
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
                startActivity(new Intent(allHaircutActivity.this, managerActivity.class).putExtra("firstName" , fName));
            }
        });

        addHaircuts.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(allHaircutActivity.this, addHaircutActivity.class);
                i.putExtra("firstName" , fName);
                startActivity(i);
            }
        });
    }
}
