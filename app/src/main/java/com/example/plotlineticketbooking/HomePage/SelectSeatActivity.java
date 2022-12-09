package com.example.plotlineticketbooking.HomePage;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;

import com.example.plotlineticketbooking.Models.Events;
import com.example.plotlineticketbooking.R;

import java.util.ArrayList;

public class SelectSeatActivity extends AppCompatActivity {

    String TAG="selSeat";
    ArrayList<Events> selectedEvents;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_select_seat);

        selectedEvents=new ArrayList<>();
        Intent intent = getIntent();
        Bundle args = intent.getBundleExtra("BUNDLE");
        selectedEvents = (ArrayList<Events>) args.getSerializable("ARRAYLIST");
        Log.d(TAG, ": "+selectedEvents.size());
    }
}