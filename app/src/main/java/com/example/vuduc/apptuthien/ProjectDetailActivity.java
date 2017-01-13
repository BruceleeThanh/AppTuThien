package com.example.vuduc.apptuthien;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

public class ProjectDetailActivity extends AppCompatActivity {

    Button btn_moi_taitro, btn_xemtt_taitro, btn_edit_thongtin;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_project_detail);
        getSupportActionBar().setTitle("Thông tin dự án");
        addControls();
        addEvents();
    }

    private void addEvents() {
        btn_moi_taitro.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(ProjectDetailActivity.this, MoiTaiTroActivity.class));
            }
        });

        btn_xemtt_taitro.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(ProjectDetailActivity.this, XemTaiTroActivity.class));
            }
        });

        btn_edit_thongtin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(ProjectDetailActivity.this, EditProjectActivity.class));
            }
        });
    }

    private void addControls() {
        btn_moi_taitro= (Button) findViewById(R.id.btn_moi_taitro);
        btn_xemtt_taitro= (Button) findViewById(R.id.btn_xemtt_taitro);
        btn_edit_thongtin= (Button) findViewById(R.id.btn_edit_thongtin);
    }
}
