package com.example.vuduc.apptuthien;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Spinner;

public class EditProjectActivity extends AppCompatActivity {

    String spinArr[]={"Chưa thực hiện", "Đang thực hiện", "Đã kết thúc"};
    String project_startus;
    Spinner spin;
    ArrayAdapter<String> spinAdapter;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_project);
        getSupportActionBar().setTitle("Sửa thông tin dự án từ thiện");
        addControls();
        addEvents();
    }

    private void addEvents() {
        spin.setOnItemSelectedListener(new MyProcessSpiner());
    }

    private void addControls() {
        spin= (Spinner) findViewById(R.id.spinner_project_startus);
        spinAdapter = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item,spinArr);
        spinAdapter.setDropDownViewResource(android.R.layout.simple_list_item_single_choice);
        spin.setAdapter(spinAdapter);
    }

    private class MyProcessSpiner implements AdapterView.OnItemSelectedListener {
        @Override
        public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
            project_startus = spinArr[i];
        }

        @Override
        public void onNothingSelected(AdapterView<?> adapterView) {
            project_startus = spinArr[0];
        }
    }
}
