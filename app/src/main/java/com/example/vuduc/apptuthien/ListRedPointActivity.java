package com.example.vuduc.apptuthien;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutCompat;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.View;

import com.example.vuduc.adapters.ListRedPointAdapter;
import com.github.clans.fab.FloatingActionButton;

public class ListRedPointActivity extends AppCompatActivity {

    RecyclerView rvListRedPoint;
    ListRedPointAdapter listRedPointAdapter;
    com.github.clans.fab.FloatingActionButton fabCreateRedpoint;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_list_red_point);
        getSupportActionBar().setTitle("Danh sách điểm nóng");
        addControls();
        addEvents();
    }

    private void addControls() {
        rvListRedPoint= (RecyclerView) findViewById(R.id.rvListRedPoint);
        //Tao moi diem nong
        fabCreateRedpoint = (com.github.clans.fab.FloatingActionButton) findViewById(R.id.fabCreateRedPoint);

    }

    private void addEvents() {
        LinearLayoutManager linearLayoutManager=new LinearLayoutManager(this);
        rvListRedPoint.setLayoutManager(linearLayoutManager);
        rvListRedPoint.setItemAnimator(new DefaultItemAnimator());
        listRedPointAdapter=new ListRedPointAdapter(this);
        rvListRedPoint.setAdapter(listRedPointAdapter);

        //Tao moi diem nong
        fabCreateRedpoint.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(ListRedPointActivity.this, CreateRedPontActivity.class));
            }
        });
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.toolbar_search, menu);
        return true;
    }
}
