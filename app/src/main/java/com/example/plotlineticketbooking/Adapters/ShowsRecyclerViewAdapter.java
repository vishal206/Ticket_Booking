package com.example.plotlineticketbooking.Adapters;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.plotlineticketbooking.Models.Events;
import com.example.plotlineticketbooking.R;

import java.util.ArrayList;

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
        Events events = showsList.get(position);
        if (events.getSelected()) {
            holder.btnSelect.setVisibility(View.GONE);
            holder.btnRemove.setVisibility(View.VISIBLE);
        }
        holder.showName.setText(events.getName());
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
        Log.d("booked", ":" + events.getBookedSeats().size());
    }

    @Override
    public int getItemCount() {
        return showsList.size();
    }

    public static class Viewholder extends RecyclerView.ViewHolder {

        Button btnSelect, btnRemove;
        TextView showName, learnMore;

        public Viewholder(@NonNull View itemView) {
            super(itemView);
            btnSelect = itemView.findViewById(R.id.btnSelect);
            btnRemove = itemView.findViewById(R.id.btnRemove);
            showName = itemView.findViewById(R.id.showName);
            learnMore = itemView.findViewById(R.id.learnMore);
        }
    }
}
