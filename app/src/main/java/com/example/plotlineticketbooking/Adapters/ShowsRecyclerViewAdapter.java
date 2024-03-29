package com.example.plotlineticketbooking.Adapters;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.plotlineticketbooking.HomePage.LearnMoreActivity;
import com.example.plotlineticketbooking.Models.Events;
import com.example.plotlineticketbooking.R;

import java.io.Serializable;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

public class ShowsRecyclerViewAdapter extends RecyclerView.Adapter<ShowsRecyclerViewAdapter.Viewholder> {

    Context context;
    ArrayList<Events> showsList;

    public ShowsRecyclerViewAdapter(Context context, ArrayList<Events> showsList) {
        this.context = context;
        this.showsList = showsList;
    }

    @NonNull
    @Override
    public ShowsRecyclerViewAdapter.Viewholder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.shows_list, parent, false);
        return new ShowsRecyclerViewAdapter.Viewholder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ShowsRecyclerViewAdapter.Viewholder holder, int position) {
        SimpleDateFormat formatter = new SimpleDateFormat("dd-MM-yyyy");
        Date date = new Date();
        String todayDate = formatter.format(date);
        Events events = showsList.get(position);
        if (events.getSelected()) {
            holder.btnSelect.setVisibility(View.GONE);
            holder.btnRemove.setVisibility(View.VISIBLE);
        }
        holder.showName.setText(events.getName());
        Glide.with(context).load(events.getShowPic()).into(holder.showImage);
        Log.d("showDate", ": "+todayDate+"//"+events.getShowDate());
        if(todayDate.compareTo(events.getShowDate())>0){
            holder.btnSelect.setVisibility(View.GONE);
            holder.btnRemove.setVisibility(View.GONE);
            holder.tvLate.setVisibility(View.VISIBLE);
        }
        holder.btnSelect.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                holder.btnSelect.setVisibility(View.GONE);
                holder.btnRemove.setVisibility(View.VISIBLE);
                events.setSelected(true);
            }
        });
        holder.btnRemove.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                holder.btnRemove.setVisibility(View.GONE);
                holder.btnSelect.setVisibility(View.VISIBLE);
                events.setSelected(false);
            }
        });
        holder.learnMore.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent=new Intent(context, LearnMoreActivity.class);
                Bundle args = new Bundle();
                args.putSerializable("ARRAYLIST", (Serializable) showsList);
                intent.putExtra("BUNDLE", args);
                intent.putExtra("position",position+"");
                context.startActivity(intent);
            }
        });
        Log.d("booked", ":" + events.getBookedSeats().size());
    }

    @Override
    public int getItemCount() {
        return showsList.size();
    }

    public static class Viewholder extends RecyclerView.ViewHolder {

        Button btnSelect, btnRemove;
        TextView showName, learnMore,tvLate;
        ImageView showImage;
        public Viewholder(@NonNull View itemView) {
            super(itemView);
            btnSelect = itemView.findViewById(R.id.btnSelect);
            btnRemove = itemView.findViewById(R.id.btnRemove);
            showName = itemView.findViewById(R.id.showName);
            learnMore = itemView.findViewById(R.id.learnMore);
            showImage=itemView.findViewById(R.id.showImage);
            tvLate=itemView.findViewById(R.id.tvLate);
        }
    }
}
