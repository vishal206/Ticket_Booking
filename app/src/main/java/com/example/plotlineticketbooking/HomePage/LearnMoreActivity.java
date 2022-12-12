package com.example.plotlineticketbooking.HomePage;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.example.plotlineticketbooking.Models.Events;
import com.example.plotlineticketbooking.R;

import org.checkerframework.checker.units.qual.A;

import java.util.ArrayList;

public class LearnMoreActivity extends AppCompatActivity {

    TextView showName,showCategory,showDuration,showDescription;
    ImageView showPic;
    ArrayList<Events> showList;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_learn_more);

        showName=findViewById(R.id.showName);
        showCategory=findViewById(R.id.showCategory);
        showDuration=findViewById(R.id.showDuration);
        showDescription=findViewById(R.id.showDescription);
        showPic=findViewById(R.id.showPic);
        showList=new ArrayList<>();

        Intent intent = getIntent();
        Bundle args = intent.getBundleExtra("BUNDLE");
        showList = (ArrayList<Events>) args.getSerializable("ARRAYLIST");
        String position=intent.getStringExtra("position");
        int pos=Integer.parseInt(position);

        showName.setText(showList.get(pos).getName());
        showCategory.setText(showList.get(pos).getCategory());
        showDuration.setText(showList.get(pos).getDuration());
        showDescription.setText(showList.get(pos).getDescription());
        Glide.with(this).load(showList.get(pos).getShowPic()).into(showPic);

    }
}