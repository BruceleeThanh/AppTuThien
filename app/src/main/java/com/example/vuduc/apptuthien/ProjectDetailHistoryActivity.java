package com.example.vuduc.apptuthien;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

public class ProjectDetailHistoryActivity extends AppCompatActivity {

    //Link trang ca nhan
    TextView txt_link_user_profile;

    TextView txt_project_title, txt_project_content, txt_project_time, txt_project_location, txt_project_money_duKien,
            txt_project_money_thucTe, txt_project_status;

    ImageView img_project_title;
    ImageView img_project_content;

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
}
