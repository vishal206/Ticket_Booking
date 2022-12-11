package com.example.plotlineticketbooking.HomePage;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;

import com.example.plotlineticketbooking.Adapters.ReceiptAdapter;
import com.example.plotlineticketbooking.Adapters.SeatConfirmationAdapter;
import com.example.plotlineticketbooking.MainActivity;
import com.example.plotlineticketbooking.Models.Events;
import com.example.plotlineticketbooking.R;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FieldValue;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;
import java.util.List;

public class ReceiptActivity extends AppCompatActivity {

    ArrayList<Events> selectedEvents;
    RecyclerView showsRecyclerView;
    ReceiptAdapter receiptAdapter;
    private FirebaseFirestore firestore;
    private FirebaseAuth mAuth;
    private FirebaseUser mUser;
    String selectedDate;
    String TAG = "RecAc";
    Button btnDone;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_receipt);

        initializeViews();
        Intent intent = getIntent();
        Bundle args = intent.getBundleExtra("BUNDLE");
        selectedEvents = (ArrayList<Events>) args.getSerializable("ARRAYLIST");
        selectedDate = selectedEvents.get(0).getShowDate();
        setRecyclerView();
        updateFirestore();

        btnDone.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent in = new Intent(ReceiptActivity.this, MainActivity.class);
                startActivity(in);
                finish();
            }
        });
    }

    private void updateFirestore() {
        for (int i = 0; i < selectedEvents.size(); i++) {
            for (int j = 0; j < selectedEvents.get(i).getSelectedSeats().size(); j++) {
                firestore.collection("events").document(selectedDate).collection(selectedEvents.get(i).getCategory()).document(selectedEvents.get(i).getFirebaseDocName())
                        .update("booked", FieldValue.arrayUnion(selectedEvents.get(i).getSelectedSeats().get(j))).addOnSuccessListener(new OnSuccessListener<Void>() {
                            @Override
                            public void onSuccess(Void unused) {
                                Log.d(TAG, "onSuccess: Added");
                            }
                        }).addOnFailureListener(new OnFailureListener() {
                            @Override
                            public void onFailure(@NonNull Exception e) {
                                Log.d(TAG, "onFailure: " + e.getMessage());
                            }
                        });
            }
            if (selectedEvents.get(i).getSelectedSeats().size() != 0) {
                firestore.collection("users").document(mUser.getUid()).collection("bookedEvents")
                        .add(selectedEvents.get(i)).addOnSuccessListener(new OnSuccessListener<DocumentReference>() {
                            @Override
                            public void onSuccess(DocumentReference documentReference) {
                                Log.d(TAG, "onSuccess:userData ");
                            }
                        }).addOnFailureListener(new OnFailureListener() {
                            @Override
                            public void onFailure(@NonNull Exception e) {
                                Log.d(TAG, "onFailure:userData ");
                            }
                        });
            }

        }


    }

    private void setRecyclerView() {
        receiptAdapter = new ReceiptAdapter(ReceiptActivity.this, selectedEvents);
        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(ReceiptActivity.this);
        showsRecyclerView.setLayoutManager(layoutManager);
        showsRecyclerView.setItemAnimator(new DefaultItemAnimator());
        showsRecyclerView.setAdapter(receiptAdapter);
    }

    private void initializeViews() {
        selectedEvents = new ArrayList<>();
        showsRecyclerView = findViewById(R.id.showsRecyclerView);
        firestore = FirebaseFirestore.getInstance();
        btnDone = findViewById(R.id.btnDone);
        mAuth = FirebaseAuth.getInstance();
        mUser = mAuth.getCurrentUser();
    }
}