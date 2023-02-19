package com.sanioluke00.plantrium_beaplanter;

import android.annotation.SuppressLint;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.content.res.AppCompatResources;
import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

public class FlowerDisplayAdapter extends RecyclerView.Adapter<FlowerDisplayAdapter.ListViewHolder> {

    Context context;
    ArrayList<FlowerDataModel> flowers_arr;

    public FlowerDisplayAdapter(Context context, ArrayList<FlowerDataModel> flowers_arr) {
        this.context = context;
        this.flowers_arr = flowers_arr;
    }

    @NonNull
    @Override
    public ListViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        @SuppressLint("InflateParams") View inflate = LayoutInflater.from(parent.getContext()).inflate(R.layout.foreign_plant_items, null);
        return new ListViewHolder(inflate);
    }

    @Override
    public void onBindViewHolder(@NonNull ListViewHolder holder, int position) {
        holder.frn_plants_img.setImageDrawable(AppCompatResources.getDrawable(context,flowers_arr.get(position).getImage_res()));
        holder.frn_plants_commonname.setText(flowers_arr.get(position).getFlower_name());
        holder.frn_plants_bname.setText(flowers_arr.get(position).getBotanical_name());
    }

    @Override
    public int getItemCount() {
        return flowers_arr.size();
    }

    public class ListViewHolder extends RecyclerView.ViewHolder {

        ImageView frn_plants_img;
        TextView frn_plants_commonname, frn_plants_bname;

        public ListViewHolder(@NonNull View itemView) {
            super(itemView);
            frn_plants_img= itemView.findViewById(R.id.frn_plants_img);
            frn_plants_commonname= itemView.findViewById(R.id.frn_plants_commonname);
            frn_plants_bname= itemView.findViewById(R.id.frn_plants_bname);
        }
    }
}
