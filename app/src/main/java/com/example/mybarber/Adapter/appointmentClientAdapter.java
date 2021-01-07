package com.example.mybarber.Adapter;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.mybarber.Activities.profileActivity;
import com.example.mybarber.Objects.Appointment;
import com.example.mybarber.R;
import com.example.mybarber.fireBase.appointmentFB;
import com.example.mybarber.fireBase.messageFB;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

import static android.widget.Toast.LENGTH_LONG;

public class appointmentClientAdapter extends RecyclerView.Adapter<appointmentClientAdapter.ViewHolder> {
    private List<Appointment> appointmentsList=new ArrayList();
    private Context info;
    //constructor
    public appointmentClientAdapter(Context info) {
        this.info = info;
    }

    public void setAppointmentsList(List<Appointment> appointments) {
        this.appointmentsList=appointments;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        int layout = R.layout.appointment_list_client;
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
        private TextView appointmentDate;
        private TextView appointmentTime;
        private TextView appointmentHaircut;
        private ImageButton deleteButtom;
        String mesName;
        String mesDate;
        String mesHour;

        public ViewHolder(View itemView) {
            super(itemView);
            // Initiate view
            appointmentDate =(TextView)itemView.findViewById(R.id.date);
            appointmentTime =(TextView)itemView.findViewById(R.id.time);
            appointmentHaircut = (TextView)itemView.findViewById(R.id.haircut);
            deleteButtom = itemView.findViewById(R.id.imageButton);
            deleteButtom.setOnClickListener(new View.OnClickListener(){
                @Override
                public void onClick(View v){
                    remove(getAdapterPosition());
                }
            });
        }

        public void showCallDetails(Appointment appointment){
            String date = appointment.getDate();
            String time = appointment.getTime();
            String haircut = appointment.getHaircut();
            appointmentDate.setText(date);
            appointmentTime.setText(time);
            appointmentHaircut.setText(haircut);
        }

        public void remove(int position)
        {
            String appointmentID = appointmentsList.get(position).toString();
            mesName = appointmentsList.get(position).getName();
            mesDate = appointmentsList.get(position).getDate();
            mesHour = appointmentsList.get(position).getTime();
            AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(info);
            alertDialogBuilder.setMessage("Are you sure you want to cancel this appointment "+ appointmentID+ "?");
            alertDialogBuilder.setPositiveButton("YES",
                    new DialogInterface.OnClickListener() {
                        public void onClick(DialogInterface dialog, int which) {
                            messageFB mes = new messageFB();
                            mes.addMessage(mesName, mesDate , mesHour, "canceled an appointment");
                            appointmentFB app = new appointmentFB();
                            app.getAppointmendByID(appointmentID).child("name").setValue("Available");
                            app.getAppointmendByID(appointmentID).child("haircut").setValue("-");
                            app.getAppointmendByID(appointmentID).child("phone").setValue("-");
                            appointmentsList.remove(position);
                            notifyItemRemoved(position);
                            //send notification manager
                        }
                    });
            alertDialogBuilder.setNegativeButton("NO",
                    new DialogInterface.OnClickListener() {
                        public void onClick(DialogInterface dialog, int which) {
                        }
                    });
            AlertDialog alertDialog = alertDialogBuilder.create();
            alertDialog.show();
        }
    }
}