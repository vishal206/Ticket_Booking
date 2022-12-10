package com.example.plotlineticketbooking.Adapters;

import android.content.Context;
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

public class SelectedShowRecyclerAdapter extends RecyclerView.Adapter<SelectedShowRecyclerAdapter.Viewholder>{

    Context context;
    ArrayList<Events> showsList;

    public SelectedShowRecyclerAdapter(Context context, ArrayList<Events> showsList) {
        this.context = context;
        this.showsList = showsList;
    }

    @NonNull
    @Override
    public SelectedShowRecyclerAdapter.Viewholder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view= LayoutInflater.from(parent.getContext()).inflate(R.layout.selected_shows_list, parent, false);
        return new SelectedShowRecyclerAdapter.Viewholder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull SelectedShowRecyclerAdapter.Viewholder holder, int position) {
        Events events=showsList.get(position);
        holder.showName.setText(events.getName());
    }

    @Override
    public int getItemCount() {
        return showsList.size();
    }

    public static class Viewholder extends RecyclerView.ViewHolder {
        TextView showName;
        public Viewholder(@NonNull View itemView) {
            super(itemView);
            showName=itemView.findViewById(R.id.showName);
        }
    }
}
