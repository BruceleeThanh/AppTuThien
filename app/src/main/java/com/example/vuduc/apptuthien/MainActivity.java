package com.example.vuduc.apptuthien;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;

import com.example.vuduc.adapters.MainMenuAdapter;

public class MainActivity extends AppCompatActivity {

    RecyclerView rvMainMenu;
    MainMenuAdapter mmaAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        this.addControls();
        this.initMainMenu();
    }

    private void addControls(){
        rvMainMenu = (RecyclerView) findViewById(R.id.rvMainMenu);
    }

    private void initMainMenu(){
        GridLayoutManager gridLayoutManager = new GridLayoutManager(this, 2);
        rvMainMenu.setLayoutManager(gridLayoutManager);
        mmaAdapter = new MainMenuAdapter(this);
        rvMainMenu.setAdapter(mmaAdapter);
    }
}
