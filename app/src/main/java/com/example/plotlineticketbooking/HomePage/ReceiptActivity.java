package com.example.plotlineticketbooking.HomePage;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;

import com.example.plotlineticketbooking.Adapters.ReceiptAdapter;
import com.example.plotlineticketbooking.Adapters.SeatConfirmationAdapter;
import com.example.plotlineticketbooking.Models.Events;
import com.example.plotlineticketbooking.R;

import java.util.ArrayList;

public class ReceiptActivity extends AppCompatActivity {

    ArrayList<Events> selectedEvents;
    RecyclerView showsRecyclerView;
    ReceiptAdapter receiptAdapter;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_receipt);

        initializeViews();
        Intent intent = getIntent();
        Bundle args = intent.getBundleExtra("BUNDLE");
        selectedEvents = (ArrayList<Events>) args.getSerializable("ARRAYLIST");

        setRecyclerView();
    }

    private void setRecyclerView() {
        receiptAdapter = new ReceiptAdapter(ReceiptActivity.this, selectedEvents);
        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(ReceiptActivity.this);
        showsRecyclerView.setLayoutManager(layoutManager);
        showsRecyclerView.setItemAnimator(new DefaultItemAnimator());
        showsRecyclerView.setAdapter(receiptAdapter);
    }

    private void initializeViews() {
        selectedEvents=new ArrayList<>();
        showsRecyclerView=findViewById(R.id.showsRecyclerView);
    }
}