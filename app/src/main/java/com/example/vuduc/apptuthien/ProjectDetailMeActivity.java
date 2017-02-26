package com.example.vuduc.apptuthien;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.vuduc.adapters.ListEditHistoryAdapter;
import com.example.vuduc.adapters.ListLinkRedpointAdapter;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;

public class ProjectDetailMeActivity extends AppCompatActivity {

    @BindView(R.id.rv_edit_history)
    RecyclerView rv_edit_history;

    @BindView(R.id.btn_moi_taitro)
    Button btn_moi_taitro;
    @BindView(R.id.btn_xemtt_taitro)
    Button btn_xemtt_taitro;
    @BindView(R.id.btn_edit_thongtin)
    Button btn_edit_thongtin;

    @BindView(R.id.txt_link_user_profile)
    TextView txt_link_user_profile;

    TextView txt_project_title, txt_project_content, txt_project_time, txt_project_location, txt_project_money_duKien,
            txt_project_money_thucTe, txt_project_status;

    ImageView img_project_title;
    ImageView img_project_content;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_project_detail_me);
        getSupportActionBar().setTitle("Chi tiết dự án của tôi");
        this.addControl();
        addEvents();
        this.addEditHistoryList();
    }

    private void addControl(){
        ButterKnife.bind(this);

        txt_project_title= (TextView) findViewById(R.id.txt_project_title);
        txt_project_content= (TextView) findViewById(R.id.txt_project_content);
        txt_project_time= (TextView) findViewById(R.id.txt_project_time);
        txt_project_location= (TextView) findViewById(R.id.txt_project_location);
        txt_project_money_duKien= (TextView) findViewById(R.id.txt_project_money_duKien);
        txt_project_money_thucTe = (TextView) findViewById(R.id.txt_project_money_thucTe);
        txt_project_status= (TextView) findViewById(R.id.txt_project_status);

        img_project_title= (ImageView) findViewById(R.id.img_project_title);
        img_project_content= (ImageView) findViewById(R.id.img_project_content);
    }

    private void addEvents() {
        btn_moi_taitro.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(ProjectDetailMeActivity.this, MoiTaiTroActivity.class));
            }
        });

        btn_xemtt_taitro.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(ProjectDetailMeActivity.this, XemTaiTroActivity.class));
            }
        });

        btn_edit_thongtin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(ProjectDetailMeActivity.this, EditProjectActivity.class));
            }
        });

        //Link den trang ca nhan
        txt_link_user_profile.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(ProjectDetailMeActivity.this, UserProfileActivity.class));
            }
        });
    }

    private void addEditHistoryList(){
        rv_edit_history.setLayoutManager(new LinearLayoutManager(this));
        rv_edit_history.setAdapter(new ListEditHistoryAdapter(this));
    }
}
