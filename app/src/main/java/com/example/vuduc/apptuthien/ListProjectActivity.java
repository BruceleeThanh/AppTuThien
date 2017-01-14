package com.example.vuduc.apptuthien;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.Menu;
import android.view.MenuInflater;

import com.example.vuduc.adapters.ListProjectAdapter;

public class ListProjectActivity extends AppCompatActivity {

    RecyclerView rvListProject;
    ListProjectAdapter lpaAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_list_project);
        getSupportActionBar().setTitle("Danh sách dự án từ thiện");
        this.addControls();
        this.initListProject();
    }

    private void addControls(){
        rvListProject=(RecyclerView) findViewById(R.id.rvListProject);
    }

    private void initListProject(){
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this);
        rvListProject.setLayoutManager(linearLayoutManager);
        lpaAdapter = new ListProjectAdapter(this);
        rvListProject.setAdapter(lpaAdapter);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.toolbar_search, menu);
        return true;
    }
}
