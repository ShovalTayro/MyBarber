package com.example.mybarber.Adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.mybarber.Objects.hairCut;
import com.example.mybarber.R;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.List;

public class haircutClientAdapter extends RecyclerView.Adapter<haircutClientAdapter.ViewHolder> {
    private List<hairCut> haircutsList = new ArrayList();
    private Context info;

    //constructor
    public haircutClientAdapter(Context info) {
        this.info = info;
    }
    public void setHaircutsList(List<hairCut> haircuts){
        this.haircutsList=haircuts;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        int layout = R.layout.gallery_cell;
        View v = LayoutInflater.from(parent.getContext()).inflate(layout, parent, false);
        return new ViewHolder(v);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        hairCut haircuts = haircutsList.get(position);
        holder.showCallDetails(haircuts);
        Picasso.with(info).load(haircuts.getImg()).into(holder.image);
    }

    @Override
    public int getItemCount() { return haircutsList.size();}

    public class ViewHolder extends RecyclerView.ViewHolder {
        private TextView haircurName;
        private ImageView image;

        public ViewHolder(View itemView) {
            super(itemView);
            // Initiate view
            haircurName =(TextView)itemView.findViewById(R.id.title);
            image = itemView.findViewById(R.id.img);
        }

        public void showCallDetails(hairCut haircut){
            //Attach values for each item
            haircurName.setText(haircut.toString());
        }
    }
}
