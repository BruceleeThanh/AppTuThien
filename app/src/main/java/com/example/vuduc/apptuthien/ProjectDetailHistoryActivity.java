package com.example.vuduc.apptuthien;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

public class ProjectDetailHistoryActivity extends AppCompatActivity {

    //Link trang ca nhan
    TextView txt_link_user_profile;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_project_detail_history);
        getSupportActionBar().setTitle("Chi tiết lịch sử");
        addControls();
        addEvents();
    }

    private void addEvents() {
        //Link den trang ca nhan
        txt_link_user_profile.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(ProjectDetailHistoryActivity.this, UserProfileActivity.class));
            }
        });
    }

    private void addControls() {
        //Link den trang ca nhan
        txt_link_user_profile = (TextView) findViewById(R.id.txt_link_user_profile);
    }
}
