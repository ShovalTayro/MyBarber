package com.example.mybarber.Adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.mybarber.Objects.Appointment;
import com.example.mybarber.R;

import java.util.ArrayList;
import java.util.List;

public class appointmentManagerAdapter extends RecyclerView.Adapter<appointmentManagerAdapter.ViewHolder> {
    private List<Appointment> appointmentsList=new ArrayList();
    private Context info;
    //constructor
    public appointmentManagerAdapter(Context info) {
        this.info = info;
    }

    public void setAppointmentsList(List<Appointment> appointments) {
        this.appointmentsList=appointments;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        int layout = R.layout.appointment_list_manager;
        View v = LayoutInflater.from(parent.getContext()).inflate(layout, parent, false);
        return new ViewHolder(v);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        Appointment appointments = appointmentsList.get(position);
        holder.showCallDetails(appointments);
    }

    @Override
    public int getItemCount() {
        return appointmentsList.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        private TextView appointmentName;
        private TextView appointmentDate;
        private TextView appointmentTime;
        private TextView appointmentHaircut;
        private TextView appointmentPhone;

        public ViewHolder(View itemView) {
            super(itemView);
            // Initiate view
            appointmentName =(TextView)itemView.findViewById(R.id.name);
            appointmentDate =(TextView)itemView.findViewById(R.id.date);
            appointmentTime =(TextView)itemView.findViewById(R.id.time);
            appointmentHaircut = (TextView)itemView.findViewById(R.id.haircut);
            appointmentPhone = (TextView)itemView.findViewById(R.id.phone);
        }

        public void showCallDetails(Appointment appointment){
            String name = appointment.getName();
            String date = appointment.getDate();
            String time = appointment.getTime();
            String haircut = appointment.getHaircut();
            String phone = appointment.getPhone();
            appointmentName.setText(name);
            appointmentDate.setText(date);
            appointmentTime.setText(time);
            appointmentHaircut.setText(haircut);
            appointmentPhone.setText(phone);
        }

    }
}