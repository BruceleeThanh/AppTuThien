package com.example.vuduc.apptuthien;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;

import com.example.vuduc.adapters.ListProjectAdapter;
import com.example.vuduc.adapters.ListProjectSignupAdapter;

public class ListProjectSignupActivity extends AppCompatActivity {

    RecyclerView rvListProject;
    ListProjectSignupAdapter lpaAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_list_project_signup);
        getSupportActionBar().setTitle("Danh sách dự án khả thi");
        addControls();
        addevents();
    }

    private void addevents() {
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this);
        rvListProject.setLayoutManager(linearLayoutManager);
        lpaAdapter = new ListProjectSignupAdapter(this);
        rvListProject.setAdapter(lpaAdapter);
    }

    private void addControls() {
        rvListProject=(RecyclerView) findViewById(R.id.rvListProject);
    }
}
