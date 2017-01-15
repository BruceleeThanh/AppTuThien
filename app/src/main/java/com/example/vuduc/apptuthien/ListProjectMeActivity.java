package com.example.vuduc.apptuthien;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.View;

import com.example.vuduc.adapters.ListProjectAdapter;

public class ListProjectMeActivity extends AppCompatActivity {
    RecyclerView rvListProject;
    ListProjectAdapter lpaAdapter;
    com.github.clans.fab.FloatingActionButton fabCreateProject;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_list_project_me);
        getSupportActionBar().setTitle("Danh sách dự án của tôi");
        addControls();
        addEvents();
    }

    private void addControls(){
        rvListProject=(RecyclerView) findViewById(R.id.rvListProject);
        fabCreateProject = (com.github.clans.fab.FloatingActionButton) findViewById(R.id.fabCreateProject);
    }

    private void addEvents(){
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this);
        rvListProject.setLayoutManager(linearLayoutManager);
        lpaAdapter = new ListProjectAdapter(this);
        rvListProject.setAdapter(lpaAdapter);

        //Tao moi project
        fabCreateProject.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(ListProjectMeActivity.this, CreateProjectActivity.class));
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
