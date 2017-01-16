package com.example.vuduc.apptuthien;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.Button;

import com.example.vuduc.adapters.ListLocationAdapter;
import com.example.vuduc.model.Location;

import java.util.ArrayList;
import java.util.List;

public class RedpointCheckDetailActivity extends AppCompatActivity {

    RecyclerView rv_redpoint__detail_listLocation;
    ListLocationAdapter listLocationAdapter;
    List<Location> locationLists;

    Button btn_edit_thongtin;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_redpoint_check_detail);
        getSupportActionBar().setTitle("Điểm nóng chưa được duyệt");
        addControls();
        addEvents();
    }
    private void addEvents() {
        //Danh sach dia diem
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this);
        rv_redpoint__detail_listLocation.setLayoutManager(linearLayoutManager);
        locationLists=new ArrayList<>();
        listLocationAdapter=new ListLocationAdapter(this, locationLists);
        rv_redpoint__detail_listLocation.setAdapter(listLocationAdapter);
        LocationData();

        //Chuyen sang man hinh edit redpoint
        btn_edit_thongtin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(RedpointCheckDetailActivity.this, EditRedPointActivity.class));
            }
        });

    }

    private void LocationData() {
        Location a=new Location("Hà Tĩnh", "Huyện A", "Xã B");
        locationLists.add(a);

        a=new Location("Hà Tĩnh", "Huyện C", "Xã D");
        locationLists.add(a);

        listLocationAdapter.notifyDataSetChanged();
    }

    private void addControls() {
        rv_redpoint__detail_listLocation= (RecyclerView) findViewById(R.id.rv_redpoint__detail_listLocation);
        btn_edit_thongtin= (Button) findViewById(R.id.btn_edit_thongtin);

    }
}
