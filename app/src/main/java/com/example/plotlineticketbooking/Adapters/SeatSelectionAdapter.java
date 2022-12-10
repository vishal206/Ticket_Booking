package com.example.plotlineticketbooking.Adapters;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;

import com.example.plotlineticketbooking.HomePage.SelectSeatActivity;
import com.example.plotlineticketbooking.Models.Events;
import com.example.plotlineticketbooking.Models.Seats;
import com.example.plotlineticketbooking.R;

import android.content.Context;
import android.widget.Button;

import androidx.core.content.ContextCompat;

import java.util.ArrayList;

public class SeatSelectionAdapter extends BaseAdapter {
    ArrayList<Seats> seatsArrayList;
    Context context;
    LayoutInflater inflater;
    ArrayList<String> bookedSeats;
    Events events;

    public SeatSelectionAdapter(ArrayList<Seats> seatsArrayList, Context context,ArrayList<String> bookedSeats,Events events) {
        this.seatsArrayList = seatsArrayList;
        this.context = context;
        this.bookedSeats=bookedSeats;
        this.events= events;
    }


    @Override
    public int getCount() {
        return seatsArrayList.size();
    }

    @Override
    public Object getItem(int i) {
        return null;
    }

    @Override
    public long getItemId(int i) {
        return 0;
    }

    @Override
    public View getView(int position, View view, ViewGroup viewGroup) {

        if (inflater == null) {
            inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        }
        if (view == null) {
            view = inflater.inflate(R.layout.seat_button_list, null);
        }
        Button btnSeat = view.findViewById(R.id.btnSeat);
        Seats seats=seatsArrayList.get(position);
        if(bookedSeats.contains(seats.getName())){
            seats.setBooked(true);
            btnSeat.setBackground(ContextCompat.getDrawable(context, R.drawable.booked_seat_button));
        }
        if(seats.getSelected()){
            btnSeat.setBackground(ContextCompat.getDrawable(context, R.drawable.selected_seat_button));
        }
        btnSeat.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(!seats.getBooked()){
                    if(seats.getSelected()){
                        ArrayList<String> selected=events.getSelectedSeats();
                        selected.remove(seats.getName());
                        events.setSelectedSeats(selected);
                        seats.setSelected(false);
                        btnSeat.setBackground(ContextCompat.getDrawable(context, R.drawable.unselected_seat_button));
                    }else{
                        ArrayList<String> selected=events.getSelectedSeats();
                        selected.add(seats.getName());
                        events.setSelectedSeats(selected);
                        seats.setSelected(true);
                        btnSeat.setBackground(ContextCompat.getDrawable(context, R.drawable.selected_seat_button));
                    }
                }
            }
        });
        return view;
    }
}
