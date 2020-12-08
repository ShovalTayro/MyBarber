package com.example.mybarber;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.example.mybarber.Adapter.appointmentAdapter;
import com.example.mybarber.Adapter.haircutAdapter;
import com.example.mybarber.Objects.Appointment;
import com.example.mybarber.Objects.hairCut;
import com.example.mybarber.fireBase.appointmentFB;
import com.example.mybarber.fireBase.hairCutsFB;
import com.example.mybarber.fireBase.initializeFB;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Toast;

public class selectAppointmentActivity extends AppCompatActivity {
    Spinner spinnerAppointment;
    Spinner spinnerHaircut;
    private Button select;
    private  Button back;
    private List<Appointment> appointmentList;
    private List<hairCut> haircutList;
    private FirebaseAuth mAuth;
    private String fName ,lName;
    private String phone;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        //initialization
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_select_appointment);
        Intent iin= getIntent();
        //get extras from register/login activity
        Bundle b = iin.getExtras();
        if(b!=null) {
            fName =(String) b.get("firstName");
            lName =(String) b.get("lastName");
            phone =(String) b.get("phone");
        }
        findViews();
        mAuth = FirebaseAuth.getInstance();
        myActivate();
    }
    //set buttons &the text view
    private void findViews() {
        spinnerAppointment = (Spinner) findViewById(R.id.spinnerAppointment);
        spinnerHaircut = (Spinner) findViewById(R.id.spinnerHaircut);
        select = findViewById(R.id.select_button);
        back = findViewById(R.id.back_button);
    }
    //activate views &buttons
    private void myActivate() {
        //take all available haircuts from firebase
        appointmentList = new ArrayList<>();
        appointmentFB appointment = new appointmentFB();
        FirebaseDatabase.getInstance().getReference().child("appointment").addListenerForSingleValueEvent(new ValueEventListener() {
          //if there is any change update
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                for (DataSnapshot s : snapshot.getChildren()) {
                    if (s.child("name").getValue(String.class).equals("Available")) {
                        appointmentList.add(s.getValue(Appointment.class));
                    }
                }
                ArrayAdapter<Appointment> adapter = new ArrayAdapter<Appointment>(selectAppointmentActivity.this, android.R.layout.simple_spinner_dropdown_item, appointmentList);
                adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                spinnerAppointment.setAdapter(adapter);
            }
            @Override
            public void onCancelled(@NonNull DatabaseError error) {
            }
        });
        spinnerAppointment.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                String appointmentSelected =parent.getSelectedItem().toString();
                }
                @Override
                public void onNothingSelected(AdapterView<?> parent) {
                }
        });

        haircutList = new ArrayList<>();
        hairCutsFB haircuts = new hairCutsFB();
        DatabaseReference dr2 = haircuts.allHairCuts();
        dr2.addListenerForSingleValueEvent(new ValueEventListener() {
            //if there is any change update
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                for (DataSnapshot s : snapshot.getChildren()) {
                    haircutList.add(s.getValue(hairCut.class));
                }
                ArrayAdapter<hairCut> adapter = new ArrayAdapter<hairCut>(selectAppointmentActivity.this, android.R.layout.simple_spinner_dropdown_item, haircutList);
                adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                spinnerHaircut.setAdapter(adapter);
            }
            @Override
            public void onCancelled(@NonNull DatabaseError error) {
            }
        });
        spinnerHaircut.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                hairCut haircut = (hairCut) parent.getSelectedItem();
            }
            @Override
            public void onNothingSelected(AdapterView<?> parent) {
            }
        });

        select.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String appointmentID = spinnerAppointment.getSelectedItem().toString();
                String haircut = spinnerHaircut.getSelectedItem().toString();
                String haircutID = haircut.substring(0, haircut.indexOf(','));
                appointmentFB app = new appointmentFB();
                /*?????????????????????how to get the user name????????????????????????????????*/
                app.getAppointmendByID(appointmentID).child("name").setValue((fName + " " + lName));
                app.getAppointmendByID(appointmentID).child("haircut").setValue(haircutID);
                app.getAppointmendByID(appointmentID).child("phone").setValue(phone);
                Toast.makeText(getApplicationContext(),"selected successful", Toast.LENGTH_LONG).show();
            }
        });

        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(selectAppointmentActivity.this,profileActivity.class);
                i.putExtra("phone", phone);
                startActivity(i);
            }
        });
    }
}