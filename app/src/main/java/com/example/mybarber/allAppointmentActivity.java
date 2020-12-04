package com.example.mybarber;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.mybarber.Adapter.appointmentAdapter;
import com.example.mybarber.Adapter.haircutAdapter;
import com.example.mybarber.Objects.Appointment;
import com.example.mybarber.Objects.hairCut;
import com.example.mybarber.fireBase.appointmentFB;
import com.example.mybarber.fireBase.hairCutsFB;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

public class allAppointmentActivity extends AppCompatActivity {
    private Button addAppointment;
    private Button back;
    private FirebaseAuth fa;
    private RecyclerView recyclerView;
    private LinearLayoutManager linearLayoutManager;
    private List<Appointment> appointmentList;
    private com.example.mybarber.Adapter.appointmentAdapter appointmentsAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_all_appointments);
        findViews();
        myActivate();
    }

    //set buttons &the text view
    private void findViews() {
        addAppointment = findViewById(R.id.addAppointment_button);
        back = findViewById(R.id.back_button2);
        recyclerView = (RecyclerView) findViewById(R.id.recV);
    }

    //activate views &buttons
    private void myActivate() {
        addAppointment.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(allAppointmentActivity.this, addAppointmentActivity.class);
                startActivity(i);
            }
        });
        linearLayoutManager = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(linearLayoutManager);
        recyclerView.setHasFixedSize(true);
        appointmentList = new ArrayList<>();

        appointmentsAdapter = new appointmentAdapter(allAppointmentActivity.this);
        recyclerView.setAdapter(appointmentsAdapter);
        appointmentFB appointment = new appointmentFB();
        DatabaseReference dr = appointment.allAppointments();
        dr.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                for (DataSnapshot s : snapshot.getChildren()) {
                    appointmentList.add(s.getValue(Appointment.class));
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
                startActivity(new Intent(allAppointmentActivity.this,managerActivity.class));
            }
        });
    }
}
