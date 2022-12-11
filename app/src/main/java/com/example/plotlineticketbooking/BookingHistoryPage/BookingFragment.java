package com.example.plotlineticketbooking.BookingHistoryPage;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.plotlineticketbooking.Adapters.ReceiptAdapter;
import com.example.plotlineticketbooking.Models.Events;
import com.example.plotlineticketbooking.R;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;
import java.util.List;


public class BookingFragment extends Fragment {

    private FirebaseFirestore firestore;
    private FirebaseAuth mAuth;
    private FirebaseUser mUser;
    ArrayList<Events> bookedEvents;
    RecyclerView showsRecyclerView;
    ReceiptAdapter receiptAdapter;
    View view;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        view = inflater.inflate(R.layout.fragment_booking, container, false);

        initializeViews();
        getBookedEvents();
        return view;
    }

    private void getBookedEvents() {
        firestore.collection("users").document(mUser.getUid()).collection("bookedEvents")
                .orderBy("timestamp")
                .get().addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<QuerySnapshot> task) {
                        List<DocumentSnapshot> docList = task.getResult().getDocuments();
                        for (DocumentSnapshot doc : docList) {
                            ArrayList<String> booked = (ArrayList<String>) doc.get("booked");
                            ArrayList<String> selected = (ArrayList<String>) doc.get("selectedSeats");
                            Events events = new Events(doc.get("name").toString(), doc.get("description").toString(),
                                    doc.get("category").toString(), doc.get("duration").toString(),
                                    false, booked, selected, doc.get("showDate").toString(), doc.getId());
                            bookedEvents.add(events);
                        }
                        setRecyclerView();
                    }
                });
    }

    private void setRecyclerView() {
        receiptAdapter = new ReceiptAdapter(getContext(), bookedEvents);
        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(getContext());
        showsRecyclerView.setLayoutManager(layoutManager);
        showsRecyclerView.setItemAnimator(new DefaultItemAnimator());
        showsRecyclerView.setAdapter(receiptAdapter);
    }

    private void initializeViews() {
        firestore = FirebaseFirestore.getInstance();
        mAuth = FirebaseAuth.getInstance();
        mUser = mAuth.getCurrentUser();
        showsRecyclerView = view.findViewById(R.id.showsRecyclerView);
        bookedEvents = new ArrayList<>();
    }
}