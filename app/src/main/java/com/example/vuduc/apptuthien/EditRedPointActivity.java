package com.example.vuduc.apptuthien;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.ArrayAdapter;
import android.widget.Spinner;

public class EditRedPointActivity extends AppCompatActivity {

    Spinner spinner_redpoint_City, spinner_redpoint_District, spinner_redpoint_Wards, spinner_redpoint_status;
    ArrayAdapter<CharSequence> citySpinAdapter;
    ArrayAdapter<CharSequence> districtSpinAdapter;
    ArrayAdapter<CharSequence> wardsSpinAdapter;
    ArrayAdapter<String> statusSpinAdapter;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_red_point);
        addControls();
        addEvents();
    }
    private void addEvents() {

    }

    private void addControls() {
        spinner_redpoint_City= (Spinner) findViewById(R.id.spinner_redpoint_City);
        spinner_redpoint_District= (Spinner) findViewById(R.id.spinner_redpoint_District);
        spinner_redpoint_Wards= (Spinner) findViewById(R.id.spinner_redpoint_Wards);
        spinner_redpoint_status= (Spinner) findViewById(R.id.spinner_redpoint_status);

        citySpinAdapter=ArrayAdapter.createFromResource(this, R.array.arrTinhThanh,android.R.layout.simple_spinner_item);
        citySpinAdapter.setDropDownViewResource(android.R.layout.simple_list_item_single_choice);
        spinner_redpoint_City.setAdapter(citySpinAdapter);

        districtSpinAdapter=ArrayAdapter.createFromResource(this, R.array.HaNoiDistrict, android.R.layout.simple_spinner_item);
        districtSpinAdapter.setDropDownViewResource(android.R.layout.simple_list_item_single_choice);
        spinner_redpoint_District.setAdapter(districtSpinAdapter);

        wardsSpinAdapter=ArrayAdapter.createFromResource(this, R.array.HoangMaiWards, android.R.layout.simple_spinner_item);
        wardsSpinAdapter.setDropDownViewResource(android.R.layout.simple_list_item_single_choice);
        spinner_redpoint_Wards.setAdapter(wardsSpinAdapter);


    }
}
