package com.example.vuduc.apptuthien;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutCompat;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;

import com.example.vuduc.adapters.ListRedPointAdapter;

public class ListRedPointActivity extends AppCompatActivity {

    RecyclerView rvListRedPoint;
    ListRedPointAdapter listRedPointAdapter;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_list_red_point);
        addControls();
        addEvents();
    }

    private void addControls() {
        rvListRedPoint= (RecyclerView) findViewById(R.id.rvListRedPoint);
    }

    private void addEvents() {
        LinearLayoutManager linearLayoutManager=new LinearLayoutManager(this);
        rvListRedPoint.setLayoutManager(linearLayoutManager);
        rvListRedPoint.setItemAnimator(new DefaultItemAnimator());
        listRedPointAdapter=new ListRedPointAdapter(this);
        rvListRedPoint.setAdapter(listRedPointAdapter);
    }
}
