package com.example.mybarber.Adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.mybarber.Objects.hairCut;
import com.example.mybarber.Objects.message;
import com.example.mybarber.R;
import com.google.firebase.database.FirebaseDatabase;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import static android.view.View.*;

public class messageAdapter extends RecyclerView.Adapter<messageAdapter.ViewHolder> {
    private List<message> messagesList=new ArrayList();
    private Context info;

    //constructor
    public messageAdapter(Context info) {
        this.info = info;
    }
    public void setMessagesList(List<message> messages){
        this.messagesList=messages;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        int layout = R.layout.messages_list;
        View v = LayoutInflater.from(parent.getContext()).inflate(layout, parent, false);
        return new ViewHolder(v);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        message messages = messagesList.get(position);
        holder.showCallDetails(messages);
    }

    @Override
    public int getItemCount() {
        return messagesList.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        private TextView messageView;
        private ImageButton removeButtom;
        public ViewHolder(View itemView) {
            super(itemView);
            // Initiate view
            messageView =(TextView)itemView.findViewById(R.id.massageView);
            removeButtom = itemView.findViewById(R.id.removeButton);
            removeButtom.setOnClickListener(new View.OnClickListener(){
                @Override
                public void onClick(View v){
                    remove(getAdapterPosition());
                }
            });
        }

        public void showCallDetails(message message){
            //Attach values for each item
            String strMassage = message.toString();
            messageView.setText(strMassage);
        }

        public void remove(int position)
        {
            int messageID = messagesList.get(position).getId();
            FirebaseDatabase.getInstance().getReference().child("messages").child(""+messageID).removeValue();
            messagesList.remove(position);
            notifyItemRemoved(position);
        }
    }
}
