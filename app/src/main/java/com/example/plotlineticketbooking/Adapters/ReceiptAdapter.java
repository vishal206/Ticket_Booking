package com.example.plotlineticketbooking.Adapters;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.plotlineticketbooking.Models.Events;
import com.example.plotlineticketbooking.R;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FieldValue;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QuerySnapshot;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class ReceiptAdapter extends RecyclerView.Adapter<ReceiptAdapter.Viewholder> {
    Context context;
    ArrayList<Events> selectedEvents;
    String TAG="recAd";

    public ReceiptAdapter(Context context, ArrayList<Events> selectedEvents) {
        this.context = context;
        this.selectedEvents = selectedEvents;
    }

    @NonNull
    @Override
    public ReceiptAdapter.Viewholder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.booked_shows_list, parent, false);
        return new ReceiptAdapter.Viewholder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ReceiptAdapter.Viewholder holder, int position) {
        SimpleDateFormat formatter = new SimpleDateFormat("dd-MM-yyyy");
        Date date = new Date();
        String todayDate = formatter.format(date);
        Events events = selectedEvents.get(position);
        if(todayDate.compareTo(events.getShowDate())>0){
            holder.btnCancelBooking.setVisibility(View.GONE);
            holder.bookingStatus.setText("Watched");
            holder.tvLate.setVisibility(View.VISIBLE);
        }else{
            holder.bookingStatus.setText("Booked");
        }
        ArrayList<String> selectedSeats = events.getSelectedSeats();
        holder.showName.setText(events.getName());
        String seatName = "";
        for (int i = 0; i < selectedSeats.size(); i++) {
            seatName += selectedSeats.get(i) + " ";
        }
        holder.showTickets.setText(selectedSeats.size() + " tickets:" + seatName);

        holder.showDate.setText(events.getShowDate());
        Glide.with(context).load(events.getShowPic()).into(holder.showImage);

        holder.btnCancelBooking.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                FirebaseFirestore firestore= FirebaseFirestore.getInstance();

                for (int j = 0; j < selectedSeats.size(); j++) {
                    Log.d(TAG, "eve:"+events.getName()+"-"+events.getShowDate()+"/"+events.getCategory()+"/"+events.getFirebaseDocName()+"/"+selectedSeats.get(j));
                    firestore.collection("events").document(events.getShowDate()).collection(events.getCategory()).document(events.getFirebaseDocName())
                            .update("booked", FieldValue.arrayRemove(selectedSeats.get(j))).addOnSuccessListener(new OnSuccessListener<Void>() {
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
                FirebaseAuth mAuth;
                FirebaseUser mUser;
                mAuth = FirebaseAuth.getInstance();
                mUser = mAuth.getCurrentUser();

                firestore.collection("users").document(mUser.getUid()).collection("bookedEvents")
                                .get().addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                            @Override
                            public void onComplete(@NonNull Task<QuerySnapshot> task) {
                                List<DocumentSnapshot> docs=task.getResult().getDocuments();
                                String documentId="";
                                for (int i = 0; i < docs.size(); i++) {
                                    if(docs.get(i).get("firebaseDocName").equals(events.getFirebaseDocName())){
                                        documentId=docs.get(i).getId().toString();
                                        break;
                                    }
                                }
                                Log.d(TAG, "refId: "+documentId);
                                firestore.collection("users").document(mUser.getUid()).collection("bookedEvents")
                                        .document(documentId).delete().addOnSuccessListener(new OnSuccessListener<Void>() {
                                            @Override
                                            public void onSuccess(Void unused) {
                                                Log.d(TAG, "onSuccess2: Added");
                                            }
                                        });
                            }
                        });
                selectedEvents.remove(position);
                notifyDataSetChanged();
            }
        });
    }

    @Override
    public int getItemCount() {
        return selectedEvents.size();
    }

    public static class Viewholder extends RecyclerView.ViewHolder {
        TextView showName, showTickets, bookingStatus, showDate,tvLate;
        Button btnCancelBooking;
        ImageView showImage;

        public Viewholder(@NonNull View itemView) {
            super(itemView);
            bookingStatus = itemView.findViewById(R.id.bookingStatus);
            showName = itemView.findViewById(R.id.showName);
            showDate = itemView.findViewById(R.id.showDate);
            showTickets = itemView.findViewById(R.id.showTickets);
            btnCancelBooking=itemView.findViewById(R.id.btnCancelBooking);
            showImage=itemView.findViewById(R.id.showImage);
            tvLate=itemView.findViewById(R.id.tvLate);
        }
    }
}
