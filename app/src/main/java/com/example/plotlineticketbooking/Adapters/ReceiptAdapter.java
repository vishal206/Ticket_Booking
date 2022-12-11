package com.example.plotlineticketbooking.Adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.plotlineticketbooking.Models.Events;
import com.example.plotlineticketbooking.R;

import java.util.ArrayList;

public class ReceiptAdapter extends RecyclerView.Adapter<ReceiptAdapter.Viewholder> {
    Context context;
    ArrayList<Events> selectedEvents;

    public ReceiptAdapter(Context context, ArrayList<Events> selectedEvents) {
        this.context = context;
        this.selectedEvents = selectedEvents;
    }

    @NonNull
    @Override
    public ReceiptAdapter.Viewholder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.booked_shows_list, parent, false);
        return new ReceiptAdapter.Viewholder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ReceiptAdapter.Viewholder holder, int position) {
        Events events = selectedEvents.get(position);
        ArrayList<String> selectedSeats = events.getSelectedSeats();
        holder.showName.setText(events.getName());
        String seatName = "";
        for (int i = 0; i < selectedSeats.size(); i++) {
            seatName += selectedSeats.get(i) + " ";
        }
        holder.showTickets.setText(selectedSeats.size() + " tickets:" + seatName);
        holder.bookingStatus.setText("Booked");
        holder.showDate.setText(events.getShowDate());
    }

    @Override
    public int getItemCount() {
        return selectedEvents.size();
    }

    public static class Viewholder extends RecyclerView.ViewHolder {
        TextView showName, showTickets, bookingStatus, showDate;

        public Viewholder(@NonNull View itemView) {
            super(itemView);
            bookingStatus = itemView.findViewById(R.id.bookingStatus);
            showName = itemView.findViewById(R.id.showName);
            showDate = itemView.findViewById(R.id.showDate);
            showTickets = itemView.findViewById(R.id.showTickets);
        }
    }
}
