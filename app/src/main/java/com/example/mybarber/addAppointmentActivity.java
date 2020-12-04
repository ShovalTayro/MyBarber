package com.example.mybarber;

import android.app.DatePickerDialog;
import android.content.Intent;
import android.os.Bundle;
import android.text.InputType;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.example.mybarber.Objects.Appointment;
import com.example.mybarber.fireBase.appointmentFB;
import com.example.mybarber.fireBase.hairCutsFB;
import com.google.firebase.auth.FirebaseAuth;

import java.util.Calendar;

public class addAppointmentActivity extends AppCompatActivity {
    DatePickerDialog picker;
    EditText date;
    EditText hour;
    private Button add;
    private  Button back;
    private FirebaseAuth mAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_appointments);
        findViews();
        mAuth = FirebaseAuth.getInstance();
        myActivate();
    }

    private void findViews()
    {
        date = findViewById(R.id.date_editText);
        date.setInputType(InputType.TYPE_NULL);
        hour = findViewById(R.id.time_editText);
        add = findViewById(R.id.add_button);
        back = findViewById(R.id.back_button);
    }

    private void myActivate()
    {
        date.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final Calendar cldr = Calendar.getInstance();
                int day = cldr.get(Calendar.DAY_OF_MONTH);
                int month = cldr.get(Calendar.MONTH);
                int year = cldr.get(Calendar.YEAR);
                // date picker dialog
                picker = new DatePickerDialog(addAppointmentActivity.this,
                        new DatePickerDialog.OnDateSetListener() {
                    @Override
                    public void onDateSet(DatePicker view, int year, int monthOfYear, int dayOfMonth) {
                        date.setText(dayOfMonth + "-" + (monthOfYear + 1) + "-" + year);
                    }
                    }, year, month, day);
                    picker.show();
;           }
        });

        add.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String stringDate = date.getText().toString().trim();
                String stringHour = hour.getText().toString().trim();
                //if info is missing
                if (TextUtils.isEmpty(stringDate) || TextUtils.isEmpty(stringHour)) {
                    date.setError("Some fields are missing");
                    return;
                }
                else{
                    appointmentFB appointment = new appointmentFB();
                    appointment.addAppointmentAdmin(stringDate, stringHour);
                    Toast.makeText(getApplicationContext(),"added successful", Toast.LENGTH_LONG).show();
                    //startActivity(i);
                }
            }
        });

        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(addAppointmentActivity.this,allAppointmentActivity.class));
            }
        });
    }
}
