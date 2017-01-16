package com.example.vuduc.apptuthien;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.Button;

import com.example.vuduc.adapters.ListLinkProjectAdapter;
import com.example.vuduc.adapters.ListLocationAdapter;
import com.example.vuduc.model.Location;
import com.example.vuduc.model.VoluntaryProject;

import java.util.ArrayList;
import java.util.List;

public class RedpointDetailActivity extends AppCompatActivity {

    RecyclerView rv_redpoint__detail_listLocation;
    ListLocationAdapter listLocationAdapter;
    List<Location> locationLists;

    //Danh sach du an tai tro
    RecyclerView rv_list_link_project;
    ListLinkProjectAdapter listLinkProjectAdapter;
    List<VoluntaryProject> voluntaryProjectList;

    Button btn_edit_thongtin;
    Button btn_dangky_cuutro;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_redpoint_detail);
        getSupportActionBar().setTitle("Thông tin điểm nóng");
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
                startActivity(new Intent(RedpointDetailActivity.this, EditRedPointActivity.class));
            }
        });

        //Chuyen sang du an lien ket
        LinearLayoutManager linearLayoutManager1 = new LinearLayoutManager(this);
        rv_list_link_project.setLayoutManager(linearLayoutManager1);
        voluntaryProjectList =new ArrayList<>();
        listLinkProjectAdapter=new ListLinkProjectAdapter(this, voluntaryProjectList);
        rv_list_link_project.setAdapter(listLinkProjectAdapter);
        LinkProjectData();

        //Dang ky cuu tro
        btn_dangky_cuutro.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(RedpointDetailActivity.this, ListProjectSignupActivity.class));
            }
        });
    }

    private void LinkProjectData() {
        VoluntaryProject b=new VoluntaryProject("Dự án từ thiện 1");
        voluntaryProjectList.add(b);

        b=new VoluntaryProject("Dự án từ thiện 2");
        voluntaryProjectList.add(b);
        listLinkProjectAdapter.notifyDataSetChanged();
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
        rv_list_link_project= (RecyclerView) findViewById(R.id.rv_list_link_project);

        //Dang ky cuu tro
        btn_dangky_cuutro = (Button) findViewById(R.id.btn_dangky_cuutro);
    }
}
