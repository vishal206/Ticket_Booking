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

import org.w3c.dom.Text;

import java.util.ArrayList;

public class SeatConfirmationAdapter extends RecyclerView.Adapter<SeatConfirmationAdapter.Viewholder> {

    Context context;
    ArrayList<Events> selectedEvents;


    public SeatConfirmationAdapter(Context context, ArrayList<Events> selectedEvents) {
        this.context = context;
        this.selectedEvents = selectedEvents;
    }

    @NonNull
    @Override
    public SeatConfirmationAdapter.Viewholder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.confirmation_selected_shows_list, parent, false);
        return new SeatConfirmationAdapter.Viewholder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull SeatConfirmationAdapter.Viewholder holder, int position) {
        Events events = selectedEvents.get(position);
        ArrayList<String> selectedSeats = events.getSelectedSeats();
        holder.showName.setText(events.getName());
        String seatName = "";
        for (int i = 0; i < selectedSeats.size(); i++) {
            seatName += selectedSeats.get(i) + " ";
        }
        holder.showTickets.setText(selectedSeats.size() + " tickets:" + seatName);
        holder.ticketPrice.setText("Rs " + selectedSeats.size() * 100);//Assuming one ticket is 100Rs for all shows and seats
        holder.showDate.setText(events.getShowDate());
    }

    @Override
    public int getItemCount() {
        return selectedEvents.size();
    }

    public static class Viewholder extends RecyclerView.ViewHolder {
        TextView showName, showTickets, ticketPrice, showDate;

        public Viewholder(@NonNull View itemView) {
            super(itemView);
            showName = itemView.findViewById(R.id.showName);
            showTickets = itemView.findViewById(R.id.showTickets);
            ticketPrice = itemView.findViewById(R.id.ticketPrice);
            showDate = itemView.findViewById(R.id.showDate);
        }
    }
}
