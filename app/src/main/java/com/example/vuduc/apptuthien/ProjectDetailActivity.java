package com.example.vuduc.apptuthien;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.example.vuduc.adapters.ListEditHistoryAdapter;
import com.example.vuduc.adapters.ListLinkRedpointAdapter;
import com.example.vuduc.model.RedPoint;

import java.util.ArrayList;
import java.util.List;

public class ProjectDetailActivity extends AppCompatActivity {

    Button btn_xemtt_taitro;

    //Danh sach cac diem nong da tai tro
    RecyclerView rv_list_link_redpoint;
    RecyclerView rv_edit_history;
    ListLinkRedpointAdapter listLinkRedpointAdapter;
    List<RedPoint> redPointList;

    //Link trang ca nhan
    TextView txt_link_user_profile;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_project_detail);
        getSupportActionBar().setTitle("Thông tin dự án");
        addControls();
        addEvents();
    }

    private void addEvents() {
        btn_xemtt_taitro.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(ProjectDetailActivity.this, XemTaiTroActivity.class));
            }
        });

        //Chuyen sang diem nong tai tro
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this);
        rv_list_link_redpoint.setLayoutManager(linearLayoutManager);
        redPointList = new ArrayList<>();
        listLinkRedpointAdapter = new ListLinkRedpointAdapter(this, redPointList);
        rv_list_link_redpoint.setAdapter(listLinkRedpointAdapter);
        ListRedpointData();

        rv_edit_history.setLayoutManager(new LinearLayoutManager(this));
        rv_edit_history.setAdapter(new ListEditHistoryAdapter(this));

        //Link den trang ca nhan
        txt_link_user_profile.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(ProjectDetailActivity.this, UserProfileActivity.class));
            }
        });
    }

    private void ListRedpointData() {
        RedPoint a = new RedPoint("Lũ lụt ở Hà Tĩnh");
        redPointList.add(a);
        listLinkRedpointAdapter.notifyDataSetChanged();
    }

    private void addControls() {
        btn_xemtt_taitro= (Button) findViewById(R.id.btn_xemtt_taitro);

        //Danh sach diem nong tai tro
        rv_list_link_redpoint= (RecyclerView) findViewById(R.id.rv_list_link_redpoint);
        rv_edit_history = (RecyclerView) findViewById(R.id.rv_edit_history);

        //Link den trang ca nhan
        txt_link_user_profile = (TextView) findViewById(R.id.txt_link_user_profile);
    }
}
