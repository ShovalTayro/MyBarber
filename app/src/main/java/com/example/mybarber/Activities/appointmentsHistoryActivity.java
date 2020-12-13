package com.example.mybarber.Activities;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.mybarber.Adapter.appointmentAdapter;
import com.example.mybarber.Objects.Appointment;
import com.example.mybarber.R;
import com.example.mybarber.fireBase.appointmentFB;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

public class appointmentsHistoryActivity extends AppCompatActivity {
    private Button back;
    private FirebaseAuth fa;
    private RecyclerView recyclerView;
    private LinearLayoutManager linearLayoutManager;
    private List<Appointment> appointmentList;
    private String phone, fName, lName;
    private com.example.mybarber.Adapter.appointmentAdapter appointmentsAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        //initialization
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_appointments_history);
        Intent iin= getIntent();
        //get extras from profile activity
        Bundle b = iin.getExtras();
        if(b!=null) {
            fName =(String) b.get("firstName");
            lName =(String) b.get("lastName");
            phone =(String) b.get("phone");
        }
        findViews();
        myActivate();
    }

    //set buttons &the text view
    private void findViews() {
        back = findViewById(R.id.back_button4);
        recyclerView = (RecyclerView) findViewById(R.id.recV2);
    }

    //activate views &buttons
    private void myActivate() {
        linearLayoutManager = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(linearLayoutManager);
        recyclerView.setHasFixedSize(true);
        appointmentList = new ArrayList<>();
        appointmentsAdapter = new appointmentAdapter(appointmentsHistoryActivity.this);
        recyclerView.setAdapter(appointmentsAdapter);
        appointmentFB appointment = new appointmentFB();
        //get all appointments from FB
        FirebaseDatabase.getInstance().getReference().child("appointment").addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                //go through all appointments
                for (DataSnapshot s : snapshot.getChildren()) {
                    //if this is user appointment add to list
                    if (s.child("phone").getValue(String.class).equals(phone)) {
                        appointmentList.add(s.getValue(Appointment.class));
                    }
                }
                appointmentsAdapter.setAppointmentsList(appointmentList);
                appointmentsAdapter.notifyDataSetChanged();
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
            }
        });
        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(appointmentsHistoryActivity.this, profileActivity.class);
                i.putExtra("firstName", fName);
                i.putExtra("lastName", lName);
                i.putExtra("phone", phone);
                startActivity(i);
            }
        });
    }
}
