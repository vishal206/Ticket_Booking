package com.example.plotlineticketbooking.BookingHistoryPage;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

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
    ArrayList<Events> comedyShowsList, playsList, moviesList;
    RecyclerView showsRecyclerView;
    ReceiptAdapter receiptAdapter;
    View view;
    Button btnComedyShows, btnMovies, btnPlays, btnAll;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        view = inflater.inflate(R.layout.fragment_booking, container, false);

        initializeViews();
        getBookedEvents("normal");

        btnAll.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                btnAll.setBackground(ContextCompat.getDrawable(getContext(), R.drawable.primary_custom_button));
                btnAll.setTextColor(ContextCompat.getColor(getContext(), R.color.primaryButtonTextColor));
                btnComedyShows.setBackground(ContextCompat.getDrawable(getContext(), R.drawable.secondary_custom_button));
                btnMovies.setBackground(ContextCompat.getDrawable(getContext(), R.drawable.secondary_custom_button));
                btnPlays.setBackground(ContextCompat.getDrawable(getContext(), R.drawable.secondary_custom_button));
                btnMovies.setTextColor(ContextCompat.getColor(getContext(), R.color.secondaryButtonTextColor));
                btnPlays.setTextColor(ContextCompat.getColor(getContext(), R.color.secondaryButtonTextColor));
                btnComedyShows.setTextColor(ContextCompat.getColor(getContext(), R.color.secondaryButtonTextColor));
                getBookedEvents("normal");
            }
        });

        btnComedyShows.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                btnAll.setBackground(ContextCompat.getDrawable(getContext(), R.drawable.secondary_custom_button));
                btnAll.setTextColor(ContextCompat.getColor(getContext(), R.color.secondaryButtonTextColor));
                btnComedyShows.setBackground(ContextCompat.getDrawable(getContext(), R.drawable.primary_custom_button));
                btnMovies.setBackground(ContextCompat.getDrawable(getContext(), R.drawable.secondary_custom_button));
                btnPlays.setBackground(ContextCompat.getDrawable(getContext(), R.drawable.secondary_custom_button));
                btnMovies.setTextColor(ContextCompat.getColor(getContext(), R.color.secondaryButtonTextColor));
                btnPlays.setTextColor(ContextCompat.getColor(getContext(), R.color.secondaryButtonTextColor));
                btnComedyShows.setTextColor(ContextCompat.getColor(getContext(), R.color.primaryButtonTextColor));
                getBookedEvents("comedy_shows");
            }
        });
        btnMovies.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                btnAll.setBackground(ContextCompat.getDrawable(getContext(), R.drawable.secondary_custom_button));
                btnAll.setTextColor(ContextCompat.getColor(getContext(), R.color.secondaryButtonTextColor));
                btnMovies.setBackground(ContextCompat.getDrawable(getContext(), R.drawable.primary_custom_button));
                btnComedyShows.setBackground(ContextCompat.getDrawable(getContext(), R.drawable.secondary_custom_button));
                btnPlays.setBackground(ContextCompat.getDrawable(getContext(), R.drawable.secondary_custom_button));
                btnComedyShows.setTextColor(ContextCompat.getColor(getContext(), R.color.secondaryButtonTextColor));
                btnPlays.setTextColor(ContextCompat.getColor(getContext(), R.color.secondaryButtonTextColor));
                btnMovies.setTextColor(ContextCompat.getColor(getContext(), R.color.primaryButtonTextColor));
                getBookedEvents("movies");
            }
        });
        btnPlays.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                btnAll.setBackground(ContextCompat.getDrawable(getContext(), R.drawable.secondary_custom_button));
                btnAll.setTextColor(ContextCompat.getColor(getContext(), R.color.secondaryButtonTextColor));
                btnPlays.setBackground(ContextCompat.getDrawable(getContext(), R.drawable.primary_custom_button));
                btnComedyShows.setBackground(ContextCompat.getDrawable(getContext(), R.drawable.secondary_custom_button));
                btnMovies.setBackground(ContextCompat.getDrawable(getContext(), R.drawable.secondary_custom_button));
                btnComedyShows.setTextColor(ContextCompat.getColor(getContext(), R.color.secondaryButtonTextColor));
                btnMovies.setTextColor(ContextCompat.getColor(getContext(), R.color.secondaryButtonTextColor));
                btnPlays.setTextColor(ContextCompat.getColor(getContext(), R.color.primaryButtonTextColor));
                getBookedEvents("plays");
            }
        });

        return view;
    }

    private void setFilterEvents() {
        comedyShowsList.clear();
        moviesList.clear();
        playsList.clear();
        for(int i=0;i<bookedEvents.size();i++){
            if(bookedEvents.get(i).getCategory().equals("comedy_shows")){
                comedyShowsList.add(bookedEvents.get(i));
            }else if(bookedEvents.get(i).getCategory().equals("movies")){
                moviesList.add(bookedEvents.get(i));
            }else if(bookedEvents.get(i).getCategory().equals("plays")){
                playsList.add(bookedEvents.get(i));
            }
        }
    }

    private void getBookedEvents(String selectedShow) {
        bookedEvents.clear();
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
                                    false, booked, selected, doc.get("showDate").toString(), doc.get("firebaseDocName").toString(),doc.getId());
                            bookedEvents.add(events);
                        }
                        setFilterEvents();
                        setRecyclerView(selectedShow);
                    }
                });
    }

    private void setRecyclerView(String selectedShow) {
        ArrayList<Events> showList = new ArrayList<>();
        if (selectedShow.equals("comedy_shows")) {
            showList = comedyShowsList;
        } else if (selectedShow.equals("movies")) {
            showList = moviesList;
        } else if (selectedShow.equals("plays")) {
            showList = playsList;
        }else{
            showList=bookedEvents;
        }
        receiptAdapter = new ReceiptAdapter(getContext(), showList);
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
        btnComedyShows = view.findViewById(R.id.btnComedyShows);
        btnMovies = view.findViewById(R.id.btnMovies);
        btnPlays = view.findViewById(R.id.btnPlays);
        comedyShowsList = new ArrayList<>();
        playsList = new ArrayList<>();
        moviesList = new ArrayList<>();
        btnAll=view.findViewById(R.id.btnAll);
    }
}