package com.example.mybarber.Adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.mybarber.Objects.hairCut;
import com.example.mybarber.R;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class haircutAdapter extends RecyclerView.Adapter<haircutAdapter.ViewHolder> {
    private List<hairCut> haircutsList=new ArrayList();
    private Context info;

    public haircutAdapter(Context info)
    {
        this.info = info;
    }

    public void setHaircutsList(List<hairCut> haircuts){

        this.haircutsList=haircuts;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        int layout = R.layout.haircuts_list;
        View v = LayoutInflater.from(parent.getContext()).inflate(layout, parent, false);
        return new ViewHolder(v);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        hairCut haircuts = haircutsList.get(position);
        holder.showCallDetails(haircuts);
    }

    @Override
    public int getItemCount() {
        return haircutsList.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        private TextView haircurName;
        private TextView hairCutprice;

        public ViewHolder(View itemView) {
            super(itemView);
            // Initiate view
            haircurName =(TextView)itemView.findViewById(R.id.haircutName);
            hairCutprice =(TextView)itemView.findViewById(R.id.price);
        }

        public void showCallDetails(hairCut haircut){
            //Attach values for each item
            String name = haircut.getHairCutName();
            String price = haircut.getPrice();
            haircurName.setText(name);
            hairCutprice.setText(price);
        }
    }
}
