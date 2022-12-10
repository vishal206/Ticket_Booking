package com.example.plotlineticketbooking.HomePage;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AbsListView;
import android.widget.Button;

import com.example.plotlineticketbooking.Adapters.SeatConfirmationAdapter;
import com.example.plotlineticketbooking.Adapters.SelectedShowRecyclerAdapter;
import com.example.plotlineticketbooking.Models.Events;
import com.example.plotlineticketbooking.Models.Seats;
import com.example.plotlineticketbooking.R;

import java.io.Serializable;
import java.util.ArrayList;

public class SeatConfirmationActivity extends AppCompatActivity {

    ArrayList<Events> selectedEvents;
    RecyclerView showsRecyclerView;
    SeatConfirmationAdapter seatConfirmationAdapter;
    int totalPrice=0;
    Button btnPay;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_seat_confirmation);

        initializeViews();
        Intent intent = getIntent();
        Bundle args = intent.getBundleExtra("BUNDLE");
        selectedEvents = (ArrayList<Events>) args.getSerializable("ARRAYLIST");

        for(int i=0;i<selectedEvents.size();i++){
            totalPrice+=selectedEvents.get(i).getSelectedSeats().size();
        }
        totalPrice*=100; //Assuming one ticket is 100Rs for all shows and seats
        btnPay.setText("Pay-Rs "+totalPrice);
        setRecyclerView();

        btnPay.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent2=new Intent(SeatConfirmationActivity.this,ReceiptActivity.class);
                Bundle args = new Bundle();
                args.putSerializable("ARRAYLIST",(Serializable)selectedEvents);
                intent2.putExtra("BUNDLE",args);
                startActivity(intent2);
            }
        });


    }

    private void setRecyclerView() {
        seatConfirmationAdapter = new SeatConfirmationAdapter(SeatConfirmationActivity.this, selectedEvents);
        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(SeatConfirmationActivity.this);
        showsRecyclerView.setLayoutManager(layoutManager);
        showsRecyclerView.setItemAnimator(new DefaultItemAnimator());
        showsRecyclerView.setAdapter(seatConfirmationAdapter);
    }

    private void initializeViews() {
        selectedEvents=new ArrayList<>();
        showsRecyclerView=findViewById(R.id.showsRecyclerView);
        btnPay=findViewById(R.id.btnPay);
    }
}