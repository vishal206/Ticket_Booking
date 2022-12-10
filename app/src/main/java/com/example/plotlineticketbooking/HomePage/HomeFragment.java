package com.example.plotlineticketbooking.HomePage;

import android.app.DatePickerDialog;
import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Parcelable;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;

import com.example.plotlineticketbooking.Adapters.ShowsRecyclerViewAdapter;
import com.example.plotlineticketbooking.Models.Events;
import com.example.plotlineticketbooking.R;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QuerySnapshot;

import org.checkerframework.checker.units.qual.A;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collection;
import java.util.Collections;
import java.util.List;


public class HomeFragment extends Fragment implements DatePickerDialog.OnDateSetListener {

    View view;
    EditText date;
    String selectedDate, TAG = "Hfrag";
    private FirebaseAuth mAuth;
    private FirebaseUser mUser;
    private FirebaseFirestore firestore;
    ArrayList<Events> comedyShowsList, playsList, moviesList;
    RecyclerView showsRecyclerView;
    ShowsRecyclerViewAdapter showsRecyclerViewAdapter;
    Button btnComedyShows, btnMovies, btnPlays, btnBookTicket;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_home, container, false);
        initializeViews();
        date.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showDatePickerDailog();
            }
        });
        btnComedyShows.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                btnComedyShows.setBackground(ContextCompat.getDrawable(getContext(), R.drawable.primary_custom_button));
                btnMovies.setBackground(ContextCompat.getDrawable(getContext(), R.drawable.secondary_custom_button));
                btnPlays.setBackground(ContextCompat.getDrawable(getContext(), R.drawable.secondary_custom_button));
                btnMovies.setTextColor(ContextCompat.getColor(getContext(), R.color.secondaryButtonTextColor));
                btnPlays.setTextColor(ContextCompat.getColor(getContext(), R.color.secondaryButtonTextColor));
                btnComedyShows.setTextColor(ContextCompat.getColor(getContext(), R.color.primaryButtonTextColor));
                setRecyclerView("comedy_shows");
            }
        });
        btnMovies.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                btnMovies.setBackground(ContextCompat.getDrawable(getContext(), R.drawable.primary_custom_button));
                btnComedyShows.setBackground(ContextCompat.getDrawable(getContext(), R.drawable.secondary_custom_button));
                btnPlays.setBackground(ContextCompat.getDrawable(getContext(), R.drawable.secondary_custom_button));
                btnComedyShows.setTextColor(ContextCompat.getColor(getContext(), R.color.secondaryButtonTextColor));
                btnPlays.setTextColor(ContextCompat.getColor(getContext(), R.color.secondaryButtonTextColor));
                btnMovies.setTextColor(ContextCompat.getColor(getContext(), R.color.primaryButtonTextColor));
                setRecyclerView("movies");
            }
        });
        btnPlays.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                btnPlays.setBackground(ContextCompat.getDrawable(getContext(), R.drawable.primary_custom_button));
                btnComedyShows.setBackground(ContextCompat.getDrawable(getContext(), R.drawable.secondary_custom_button));
                btnMovies.setBackground(ContextCompat.getDrawable(getContext(), R.drawable.secondary_custom_button));
                btnComedyShows.setTextColor(ContextCompat.getColor(getContext(), R.color.secondaryButtonTextColor));
                btnMovies.setTextColor(ContextCompat.getColor(getContext(), R.color.secondaryButtonTextColor));
                btnPlays.setTextColor(ContextCompat.getColor(getContext(), R.color.primaryButtonTextColor));
                setRecyclerView("plays");
            }
        });
        btnBookTicket.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                ArrayList<Events> selectedEvents=new ArrayList<>();
                for(int i=0;i<comedyShowsList.size();i++){
                    if(comedyShowsList.get(i).getSelected()){
                        selectedEvents.add(comedyShowsList.get(i));
                    }
                }
                for(int i=0;i<moviesList.size();i++){
                    if(moviesList.get(i).getSelected()){
                        selectedEvents.add(moviesList.get(i));
                    }
                }
                for(int i=0;i<playsList.size();i++){
                    if(playsList.get(i).getSelected()){
                        selectedEvents.add(playsList.get(i));
                    }
                }
                Intent intent=new Intent(getContext(),SelectSeatActivity.class);
                Bundle args = new Bundle();
                args.putSerializable("ARRAYLIST",(Serializable)selectedEvents);
                intent.putExtra("BUNDLE",args);
                startActivity(intent);
            }
        });
        return view;
    }


    private void initializeViews() {
        date = view.findViewById(R.id.et_date);
        mAuth = FirebaseAuth.getInstance();
        firestore = FirebaseFirestore.getInstance();
        comedyShowsList = new ArrayList<>();
        playsList = new ArrayList<>();
        moviesList = new ArrayList<>();
        showsRecyclerView = view.findViewById(R.id.showsRecyclerView);
        btnComedyShows = view.findViewById(R.id.btnComedyShows);
        btnMovies = view.findViewById(R.id.btnMovies);
        btnPlays = view.findViewById(R.id.btnPlays);
        btnBookTicket = view.findViewById(R.id.btnBookTicket);
    }

    private void showDatePickerDailog() {
        DatePickerDialog datePickerDialog = new DatePickerDialog(
                getContext(),
                this,
                Calendar.getInstance().get(Calendar.YEAR),
                Calendar.getInstance().get(Calendar.MONTH),
                Calendar.getInstance().get(Calendar.DAY_OF_MONTH));
        datePickerDialog.show();
    }

    @Override
    public void onDateSet(DatePicker datePicker, int year, int month, int dayOfMonth) {
        month++;
        selectedDate = dayOfMonth + "-" + month + "-" + year;
        date.setText(selectedDate);
        getEvents();
    }

    private void getEvents() {
        firestore.collection("events").document(selectedDate).collection("comedy_shows").get().addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
            @Override
            public void onComplete(@NonNull Task<QuerySnapshot> task) {
                List<DocumentSnapshot> docList = task.getResult().getDocuments();
                for (DocumentSnapshot doc : docList) {
                    ArrayList<String> booked=(ArrayList<String>)doc.get("booked");
                    ArrayList<String> selected=new ArrayList<>();
                    Events event = new Events(doc.get("name").toString(), doc.get("description").toString(), doc.get("category").toString(), doc.get("duration").toString(), false,booked,selected,selectedDate);
                    comedyShowsList.add(event);
                    Log.d(TAG, "co:" + doc.get("name"));
                }
                setRecyclerView("comedy_shows");
            }
        });
        firestore.collection("events").document(selectedDate).collection("movies").get().addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
            @Override
            public void onComplete(@NonNull Task<QuerySnapshot> task) {
                List<DocumentSnapshot> docList = task.getResult().getDocuments();
                for (DocumentSnapshot doc : docList) {
                    ArrayList<String> booked=(ArrayList<String>)doc.get("booked");
                    ArrayList<String> selected=new ArrayList<>();
                    Events event = new Events(doc.get("name").toString(), doc.get("description").toString(), doc.get("category").toString(), doc.get("duration").toString(), false,booked,selected,selectedDate);
                    moviesList.add(event);
                }
            }
        });
        firestore.collection("events").document(selectedDate).collection("plays").get().addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
            @Override
            public void onComplete(@NonNull Task<QuerySnapshot> task) {
                List<DocumentSnapshot> docList = task.getResult().getDocuments();
                for (DocumentSnapshot doc : docList) {
                    ArrayList<String> booked=(ArrayList<String>)doc.get("booked");
                    ArrayList<String> selected=new ArrayList<>();
                    Events event = new Events(doc.get("name").toString(), doc.get("description").toString(), doc.get("category").toString(), doc.get("duration").toString(), false,booked,selected,selectedDate);
                    playsList.add(event);
                }
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
        }
        showsRecyclerViewAdapter = new ShowsRecyclerViewAdapter(getContext(), showList);
        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(getContext());
        showsRecyclerView.setLayoutManager(layoutManager);
        showsRecyclerView.setItemAnimator(new DefaultItemAnimator());
        showsRecyclerView.setAdapter(showsRecyclerViewAdapter);
    }
}