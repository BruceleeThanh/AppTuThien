package com.example.vuduc.apptuthien;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.Spinner;

import com.example.vuduc.adapters.ListLocationAdapter;
import com.example.vuduc.model.Location;

import java.util.ArrayList;

public class EditRedPointActivity extends AppCompatActivity {

    Spinner spinner_redpoint_City, spinner_redpoint_District, spinner_redpoint_Wards, spinner_redpoint_status;
    ArrayAdapter<CharSequence> citySpinAdapter;
    ArrayAdapter<CharSequence> districtSpinAdapter;
    ArrayAdapter<CharSequence> wardsSpinAdapter;
    ArrayAdapter<String> statusSpinAdapter;
    String spinStatus[]={"Chưa khẩn cấp", "Khẩn cấp", "Cựu kỳ khẩn cấp"};
    String redpoint_statuts;

    Button btn_redpoint_them;
    RecyclerView rv_redpoint_listLocation;
    ArrayList<Location> locationList;
    ListLocationAdapter locationAdapter;
    Location a;
    String city = "", district = "", ward = "";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_red_point);
        getSupportActionBar().setTitle("Sửa thông tin điểm nóng");
        addControls();
        addEvents();
    }
    private void addEvents() {
        spinner_redpoint_City.setOnItemSelectedListener(new CityProcessEvent());
        spinner_redpoint_District.setOnItemSelectedListener(new DistrictProcessEvent());
        spinner_redpoint_Wards.setOnItemSelectedListener(new WardProcessEvent());
        spinner_redpoint_status.setOnItemSelectedListener(new StatusProcessEvent());

        //Danh sach dia diem
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this);
        rv_redpoint_listLocation.setLayoutManager(linearLayoutManager);
        locationAdapter=new ListLocationAdapter(this, locationList);
        rv_redpoint_listLocation.setAdapter(locationAdapter);

        //gia tri ban dau cua dia diem
        a=new Location("Xã phường", "Quận huyện", "Thành phố");
        locationList.add(a);
        locationAdapter.notifyDataSetChanged();

        btn_redpoint_them.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                xuLyThemLocation();
            }
        });
    }

    private void xuLyThemLocation() {
        a =new Location(city, district, ward);
        locationList.add(a);
        locationAdapter.notifyDataSetChanged();
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

        statusSpinAdapter=new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item,spinStatus);
        statusSpinAdapter.setDropDownViewResource(android.R.layout.simple_list_item_single_choice);
        spinner_redpoint_status.setAdapter(statusSpinAdapter);

        //Danh sach dia diem
        rv_redpoint_listLocation= (RecyclerView) findViewById(R.id.rv_redpoint_listLocation);
        btn_redpoint_them= (Button) findViewById(R.id.btn_redpoint_them);
        locationList=new ArrayList<>();
    }

    private class CityProcessEvent implements android.widget.AdapterView.OnItemSelectedListener {
        @Override
        public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
            city=getResources().getStringArray(R.array.arrTinhThanh)[i];
        }

        @Override
        public void onNothingSelected(AdapterView<?> adapterView) {
            city="";
        }
    }

    private class DistrictProcessEvent implements android.widget.AdapterView.OnItemSelectedListener {
        @Override
        public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
            district=getResources().getStringArray(R.array.HaNoiDistrict)[i];
        }

        @Override
        public void onNothingSelected(AdapterView<?> adapterView) {
            district="";
        }
    }

    private class WardProcessEvent implements android.widget.AdapterView.OnItemSelectedListener {
        @Override
        public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
            ward=getResources().getStringArray(R.array.HoangMaiWards)[i];
        }

        @Override
        public void onNothingSelected(AdapterView<?> adapterView) {
            ward="";
        }
    }

    private class StatusProcessEvent implements AdapterView.OnItemSelectedListener {
        @Override
        public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
            redpoint_statuts=spinStatus[i];
        }

        @Override
        public void onNothingSelected(AdapterView<?> adapterView) {
            redpoint_statuts=spinStatus[0];
        }
    }
}
