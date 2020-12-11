package com.example.mybarber;

import android.app.DatePickerDialog;
import android.app.TimePickerDialog;
import android.content.Intent;
import android.os.Bundle;
import android.text.InputType;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.TimePicker;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.example.mybarber.Objects.Appointment;
import com.example.mybarber.fireBase.appointmentFB;
import com.example.mybarber.fireBase.hairCutsFB;
import com.google.firebase.auth.FirebaseAuth;

import java.util.Calendar;

public class addAppointmentActivity extends AppCompatActivity {
    DatePickerDialog picker;
    TextView date;
    TextView time;
    private Button selectDate;
    private Button selectHour;
    private Button add;
    private  Button back;
    private FirebaseAuth mAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        //initialization
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_appointments);
        findViews();
        mAuth = FirebaseAuth.getInstance();
        myActivate();
    }
    //set buttons &the text view
    private void findViews() {
        date = findViewById(R.id.dateView);
        time = findViewById(R.id.timeView);
        selectDate = findViewById(R.id.dateButton);
        selectHour = findViewById(R.id.timeButton);
        add = findViewById(R.id.add_button);
        back = findViewById(R.id.back_button);
    }
    //activate views &buttons
    private void myActivate() {
        selectDate.setOnClickListener(new View.OnClickListener() {
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

        selectHour.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final Calendar myCalender = Calendar.getInstance();
                int hour = myCalender.get(Calendar.HOUR_OF_DAY);
                int minute = myCalender.get(Calendar.MINUTE);
                TimePickerDialog.OnTimeSetListener myTimeListener = new TimePickerDialog.OnTimeSetListener() {
                    @Override
                    public void onTimeSet(TimePicker view, int hour, int minute) {
                        if (view.isShown()) {
                            myCalender.set(Calendar.HOUR, hour);
                            myCalender.set(Calendar.MINUTE, minute);
                            time.setText(hour + ":" + minute);
                        }
                    }
                };
                TimePickerDialog timePickerDialog = new TimePickerDialog(addAppointmentActivity.this, android.R.style.Theme_Holo_Light_Dialog_NoActionBar, myTimeListener, hour, minute, true);
                timePickerDialog.setTitle("Choose hour:");
                timePickerDialog.getWindow().setBackgroundDrawableResource(android.R.color.transparent);
                timePickerDialog.show();
            }
        });
        add.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //create the info as a string without any leading and trailing white space
                String stringDate = date.getText().toString().trim();
                String stringHour = time.getText().toString().trim();
                //if info is missing
                if (TextUtils.isEmpty(stringDate) || TextUtils.isEmpty(stringHour)) {
                    date.setError("Some fields are missing");
                    return;
                }
                //add appointment to firebase & alter
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
