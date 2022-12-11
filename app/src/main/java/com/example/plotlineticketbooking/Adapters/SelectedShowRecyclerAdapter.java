package com.example.plotlineticketbooking.Adapters;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.plotlineticketbooking.ItemClickListener;
import com.example.plotlineticketbooking.Models.Events;
import com.example.plotlineticketbooking.R;

import java.util.ArrayList;

public class SelectedShowRecyclerAdapter extends RecyclerView.Adapter<SelectedShowRecyclerAdapter.Viewholder> {

    Context context;
    ArrayList<Events> showsList;
    ItemClickListener itemClickListener;
    int selectedPosition = 0;

    public SelectedShowRecyclerAdapter(Context context, ArrayList<Events> showsList, ItemClickListener itemClickListener) {
        this.context = context;
        this.showsList = showsList;
        this.itemClickListener = itemClickListener;
    }

    @NonNull
    @Override
    public SelectedShowRecyclerAdapter.Viewholder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.selected_shows_list, parent, false);
        return new SelectedShowRecyclerAdapter.Viewholder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull SelectedShowRecyclerAdapter.Viewholder holder, int position) {
        Events events = showsList.get(position);
        holder.showName.setText(events.getName());
        Glide.with(context).load(events.getShowPic()).into(holder.showImage);
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                itemClickListener.onClick(position);
                selectedPosition = position;
                notifyDataSetChanged();
            }
        });

        if (selectedPosition == position) {
            holder.itemView.setBackground(ContextCompat.getDrawable(context, R.drawable.primary_custom_button));
            holder.showName.setTextColor(ContextCompat.getColor(context, R.color.primaryButtonTextColor));
        } else {
            holder.itemView.setBackground(ContextCompat.getDrawable(context, R.drawable.secondary_custom_button));
            holder.showName.setTextColor(ContextCompat.getColor(context, R.color.secondaryButtonTextColor));
        }
    }

    @Override
    public int getItemCount() {
        return showsList.size();
    }

    public class Viewholder extends RecyclerView.ViewHolder {
        TextView showName;
        ImageView showImage;

        public Viewholder(@NonNull View itemView) {
            super(itemView);
            showName = itemView.findViewById(R.id.showName);
            showImage=itemView.findViewById(R.id.showImage);
        }
    }
}
