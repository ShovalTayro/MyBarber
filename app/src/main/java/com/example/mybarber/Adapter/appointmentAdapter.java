package com.example.mybarber.Adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.mybarber.Objects.Appointment;
import com.example.mybarber.Objects.hairCut;
import com.example.mybarber.R;

import java.sql.Time;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class appointmentAdapter extends RecyclerView.Adapter<appointmentAdapter.ViewHolder> {
    private List<Appointment> appointmentsList=new ArrayList();
    private Context info;

    public appointmentAdapter(Context info)
    {
        this.info = info;
    }

    public void setAppointmentsList(List<Appointment> appointments)
    {
        this.appointmentsList=appointments;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        int layout = R.layout.appointment_list;
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


        public ViewHolder(View itemView) {
            super(itemView);
            // Initiate view
            appointmentName =(TextView)itemView.findViewById(R.id.name);
            appointmentDate =(TextView)itemView.findViewById(R.id.date);
            appointmentTime =(TextView)itemView.findViewById(R.id.time);
            appointmentHaircut = (TextView)itemView.findViewById(R.id.haircut);
        }

        public void showCallDetails(Appointment appointment){
            String name = appointment.getName();
            String date = appointment.getDate();
            String time = appointment.getTime();
            String haircut = appointment.getHaircut();
            appointmentName.setText(name);
            appointmentDate.setText(date);
            appointmentTime.setText(time);
            appointmentHaircut.setText(haircut);



        }
    }
}