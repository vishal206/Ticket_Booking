package com.example.plotlineticketbooking.HomePage;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.GridView;
import android.widget.Toast;

import com.example.plotlineticketbooking.Adapters.SeatSelectionAdapter;
import com.example.plotlineticketbooking.Adapters.SelectedShowRecyclerAdapter;
import com.example.plotlineticketbooking.Adapters.ShowsRecyclerViewAdapter;
import com.example.plotlineticketbooking.ItemClickListener;
import com.example.plotlineticketbooking.Models.Events;
import com.example.plotlineticketbooking.Models.Seats;
import com.example.plotlineticketbooking.R;

import org.checkerframework.checker.units.qual.A;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashMap;

public class SelectSeatActivity extends AppCompatActivity {

    String TAG = "selSeat";
    public ArrayList<Events> selectedEvents;
    ArrayList<Seats> seatsArrayList;
    ArrayList<String> bookedSeats, selectedSeats;
    HashMap<String, Integer> seatNameMap;
    RecyclerView selectedShowsRecyclerView;
    SelectedShowRecyclerAdapter selectedShowRecyclerAdapter;
    SeatSelectionAdapter seatSelectionAdapter;
    GridView seatsGridView;
    ArrayList<Seats> selectedSeatsList;
    Button btnBookTicket;
    ItemClickListener itemClickListener;
    int selectedPosition = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_select_seat);

        initializeViews();
        Intent intent = getIntent();
        Bundle args = intent.getBundleExtra("BUNDLE");
        selectedEvents = (ArrayList<Events>) args.getSerializable("ARRAYLIST");
        selectedSeats = selectedEvents.get(0).getSelectedSeats();
        bookedSeats = selectedEvents.get(0).getBookedSeats();
        itemClickListener = new ItemClickListener() {
            @Override
            public void onClick(int position) {
                bookedSeats = selectedEvents.get(position).getBookedSeats();
                selectedSeats = selectedEvents.get(position).getSelectedSeats();
                selectedPosition = position;
                setSeats();
            }
        };
        setRecyclerView();
        setSeats();


        btnBookTicket.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(SelectSeatActivity.this, SeatConfirmationActivity.class);
                Bundle args = new Bundle();
                args.putSerializable("ARRAYLIST", (Serializable) selectedEvents);
                intent.putExtra("BUNDLE", args);
                startActivity(intent);
            }
        });
    }

    private void setSeats() {
        seatsArrayList.clear();
        int t = 0;
        for (int i = 1; i <= 10; i++) {
            for (int j = 1; j <= 15; j++) {
                String name = (char) (64 + i) + "";
                name = name + (j);
                seatNameMap.put(name, t);
                Seats seats = new Seats(name, false, false);
                seatsArrayList.add(seats);
                t++;
            }
        }
//        Log.d(TAG, "setSeats: "+t+"-"+seatsArrayList.size());
        setGridView();
    }

    private void setGridView() {
        seatSelectionAdapter = new SeatSelectionAdapter(seatsArrayList, this, bookedSeats, selectedEvents.get(selectedPosition), selectedSeats);
        seatsGridView.setAdapter(seatSelectionAdapter);

    }

    private void setRecyclerView() {
        selectedShowRecyclerAdapter = new SelectedShowRecyclerAdapter(SelectSeatActivity.this, selectedEvents, itemClickListener);
        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(SelectSeatActivity.this, RecyclerView.HORIZONTAL, false);
        selectedShowsRecyclerView.setLayoutManager(layoutManager);
        selectedShowsRecyclerView.setItemAnimator(new DefaultItemAnimator());
        selectedShowsRecyclerView.setAdapter(selectedShowRecyclerAdapter);
    }

    private void initializeViews() {
        selectedShowsRecyclerView = findViewById(R.id.selectedShowsRecyclerView);
        seatsGridView = findViewById(R.id.seatsGridView);
        selectedEvents = new ArrayList<>();
        seatsArrayList = new ArrayList<>();
        seatNameMap = new HashMap<>();
        bookedSeats = new ArrayList<>();
        selectedSeatsList = new ArrayList<>();
        btnBookTicket = findViewById(R.id.btnBookTicket);
    }
}