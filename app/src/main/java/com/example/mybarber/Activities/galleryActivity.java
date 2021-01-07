package com.example.mybarber.Activities;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.RelativeLayout;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.mybarber.Adapter.haircutAdapter;
import com.example.mybarber.Adapter.haircutClientAdapter;
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

public class galleryActivity extends AppCompatActivity {
    private FirebaseAuth fa;
    private RecyclerView recyclerView;
    private List<hairCut> haircutsList;
    private com.example.mybarber.Adapter.haircutClientAdapter haircutclientAdapter;
    private String fName, lName, phone;
    private Button back;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        //initialization
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_gallery);
        Intent iin= getIntent();
        Bundle b = iin.getExtras();
        if(b!=null) {
            fName = (String) b.get("firstName");
            lName = (String) b.get("lastName");
            phone = (String) b.get("phone");
        }
        findViews();
        myActivate();
    }

    //set buttons &the text view
    private void findViews()
    {
        recyclerView = (RecyclerView) findViewById(R.id.imagegallery);
        back = findViewById(R.id.back_button_gallery);
    }

    //activate views &buttons
    private void myActivate() {

        RecyclerView.LayoutManager layoutManager = new GridLayoutManager(this, 3);
        recyclerView.setLayoutManager(layoutManager);
        recyclerView.setHasFixedSize(true);
        haircutsList = new ArrayList<>();

        haircutclientAdapter = new haircutClientAdapter(this);
        recyclerView.setAdapter(haircutclientAdapter);
        hairCutsFB haircut = new hairCutsFB();
        DatabaseReference dr = haircut.allHairCuts();
        dr.addListenerForSingleValueEvent(new ValueEventListener() {
            //update haircuts
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                for (DataSnapshot s : snapshot.getChildren()) {
                    haircutsList.add(s.getValue(hairCut.class));
                }
                haircutclientAdapter.setHaircutsList(haircutsList);
                haircutclientAdapter.notifyDataSetChanged();
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
            }
        });


        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(galleryActivity.this, profileActivity.class);
                i.putExtra("firstName", fName);
                i.putExtra("lastName", lName);
                i.putExtra("phone", phone);
                startActivity(i);
            }
        });

    }
}
