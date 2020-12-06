package com.example.mybarber.fireBase;

import com.example.mybarber.Objects.Appointment;
import com.google.firebase.database.DatabaseReference;

public class appointmentFB extends initializeFB {
    /*
    public void addAppointmentClient(String name , String date, String time, hairCut haircut)
    {
        Appointment appointment = new Appointment(name,date,time,haircut);
        //ref.child("appointment").child(getUid()).setValue(appointment);
    }
    */

    public void addAppointmentAdmin(String date, String time) {
        Appointment appointment = new Appointment("Available",date,time, "-", "-");
        ref.child("appointment").child(appointment.toString()).setValue(appointment);
    }

    public DatabaseReference allAppointmentByClient(String phone) {
        return (DatabaseReference) ref.child("appointment").orderByChild("email").equalTo(phone);
    }

    public DatabaseReference getAppointmendByID(String id) {
        return ref.child("appointment").child(id);
    }

    public DatabaseReference allAppointments() {
        return ref.child("appointment");
    }
}
