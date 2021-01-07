package com.example.mybarber.Adapter;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
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

import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import static android.view.View.*;
import static com.google.common.io.Files.getFileExtension;

public class haircutAdapter extends RecyclerView.Adapter<haircutAdapter.ViewHolder> {
    private List<hairCut> haircutsList = new ArrayList();
    private Context info;

    //constructor
    public haircutAdapter(Context info) { this.info = info; }
    public void setHaircutsList(List<hairCut> haircuts){
        this.haircutsList=haircuts;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        int layout = R.layout.haircut_list;
        View v = LayoutInflater.from(parent.getContext()).inflate(layout, parent, false);
        return new ViewHolder(v);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        hairCut haircuts = haircutsList.get(position);
        holder.showCallDetails(haircuts);
        Picasso.with(info).load(haircuts.getImg()).into(holder.photo);
    }

    @Override
    public int getItemCount() { return haircutsList.size();}

    public class ViewHolder extends RecyclerView.ViewHolder {
        private TextView title;
       // private ImageButton deleteButtom;
        private ImageView photo;
        private ImageView delete;

        public ViewHolder(View itemView) {
            super(itemView);
            // Initiate view
            title =(TextView)itemView.findViewById(R.id.manager_title);
            delete = itemView.findViewById(R.id.manager_imageView);
            photo = itemView.findViewById(R.id.manager_image);
            delete.setOnClickListener(new View.OnClickListener(){
                @Override
                public void onClick(View v){
                    remove(getAdapterPosition());
                }
            });
        }

        public void showCallDetails(hairCut haircut){
            title.setText(haircut.toString());
        }

        public void remove(int position)
        {
            String mImageUrl = haircutsList.get(position).getImg();
            FirebaseStorage firebaseStorage = FirebaseStorage.getInstance();
            StorageReference storageReference = firebaseStorage.getReferenceFromUrl(mImageUrl);
            storageReference.delete().addOnSuccessListener(new OnSuccessListener<Void>() {
                @Override
                public void onSuccess(Void aVoid) {
                }
            });
            String haircutID = haircutsList.get(position).getHairCutName();
            FirebaseDatabase.getInstance().getReference().child("haircuts").child(haircutID).removeValue();
            haircutsList.remove(position);
            notifyItemRemoved(position);
        }
    }
}
