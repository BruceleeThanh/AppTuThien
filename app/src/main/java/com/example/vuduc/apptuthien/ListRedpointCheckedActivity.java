package com.example.vuduc.apptuthien;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;

import com.example.vuduc.adapters.ListCheckRedpointAdapter;
import com.example.vuduc.adapters.ListRedPointAdapter;

public class ListRedpointCheckedActivity extends AppCompatActivity {

    RecyclerView rvListRedPoint;
    ListCheckRedpointAdapter listCheckRedpointAdapter;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_list_redpoint_checked);
        getSupportActionBar().setTitle("Xác minh điểm nóng");
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
        listCheckRedpointAdapter=new ListCheckRedpointAdapter(this);
        rvListRedPoint.setAdapter(listCheckRedpointAdapter);
    }
}
