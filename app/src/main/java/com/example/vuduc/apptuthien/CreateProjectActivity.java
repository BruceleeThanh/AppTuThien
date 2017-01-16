package com.example.vuduc.apptuthien;

import android.content.Intent;
import android.graphics.Bitmap;
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
import com.example.vuduc.adapters.ListPickImageAdapter;
import com.example.vuduc.model.Image;
import com.example.vuduc.model.Location;
import com.example.vuduc.utils.ImagePickerUtils;
import com.nguyenhoanglam.imagepicker.activity.ImagePicker;
import com.nguyenhoanglam.imagepicker.activity.ImagePickerActivity;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

import id.zelory.compressor.Compressor;

public class CreateProjectActivity extends AppCompatActivity {

    Spinner spinner_project_startus;
    String spinArr[]={"Chưa thực hiện", "Đang thực hiện", "Đã kết thúc"};
    String project_status;
    ArrayAdapter<String> spinAdapter;

    //spinner location
    Spinner spinner_project_City, spinner_project_District, spinner_project_Wards, spinner_project_status;
    ArrayAdapter<CharSequence> citySpinAdapter;
    ArrayAdapter<CharSequence> districtSpinAdapter;
    ArrayAdapter<CharSequence> wardsSpinAdapter;

    //danh sach location
    Button btn_project_them;
    RecyclerView rv_project_listLocation;
    ArrayList<Location> locationList;
    ListLocationAdapter locationAdapter;
    Location a;
    String city = "", district = "", ward = "";
    private final int PICK_IMAGE_REQUEST = 200;




    // Những biến và list bên dưới đây phục vụ cho việc chọn ảnh
    Button btn_chon_anh;
    RecyclerView rv_chon_anh;
    ListPickImageAdapter lpiaAdapter;
    // Mình phải dùng 1 cái list Image của nó để chứa dữ liệu trả về.
    // Vì 2 class trùng tên, nên một thằng phải viết dài như thế này
    private ArrayList<com.nguyenhoanglam.imagepicker.model.Image> chooseImages;
    private List<Image> lstImage;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create_project);
        getSupportActionBar().setTitle("Tạo mới dự án từ thiện");
        addControls();
        addEvents();
    }

    private void addControls() {
        spinner_project_City= (Spinner) findViewById(R.id.spinner_project_City);
        spinner_project_District= (Spinner) findViewById(R.id.spinner_project_District);
        spinner_project_Wards= (Spinner) findViewById(R.id.spinner_project_Wards);

        citySpinAdapter=ArrayAdapter.createFromResource(this, R.array.arrTinhThanh,android.R.layout.simple_spinner_item);
        citySpinAdapter.setDropDownViewResource(android.R.layout.simple_list_item_single_choice);
        spinner_project_City.setAdapter(citySpinAdapter);

        districtSpinAdapter=ArrayAdapter.createFromResource(this, R.array.HaNoiDistrict, android.R.layout.simple_spinner_item);
        districtSpinAdapter.setDropDownViewResource(android.R.layout.simple_list_item_single_choice);
        spinner_project_District.setAdapter(districtSpinAdapter);

        wardsSpinAdapter=ArrayAdapter.createFromResource(this, R.array.HoangMaiWards, android.R.layout.simple_spinner_item);
        wardsSpinAdapter.setDropDownViewResource(android.R.layout.simple_list_item_single_choice);
        spinner_project_Wards.setAdapter(wardsSpinAdapter);

        btn_project_them= (Button) findViewById(R.id.btn_project_them);
        rv_project_listLocation= (RecyclerView) findViewById(R.id.rv_project_listLocation);
        locationList=new ArrayList<>();


        spinner_project_startus = (Spinner) findViewById(R.id.spinner_project_startus);
        spinAdapter = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item, spinArr);
        spinAdapter.setDropDownViewResource(android.R.layout.simple_list_item_single_choice);
        spinner_project_startus.setAdapter(spinAdapter);

        // Khởi tạo rv_chon_anh nhé
        btn_chon_anh = (Button) findViewById(R.id.btn_chon_anh);
        rv_chon_anh = (RecyclerView) findViewById(R.id.rv_chon_anh);
        rv_chon_anh.setLayoutManager(new LinearLayoutManager(this));
        lstImage = new ArrayList<>();
        lpiaAdapter = new ListPickImageAdapter(this, lstImage);
        rv_chon_anh.setAdapter(lpiaAdapter);
    }

    private void addEvents() {
        spinner_project_startus.setOnItemSelectedListener(new StatusProjectProcess());

        spinner_project_City.setOnItemSelectedListener(new CityProcessEvent());
        spinner_project_District.setOnItemSelectedListener(new DistrictProcessEvent());
        spinner_project_Wards.setOnItemSelectedListener(new WardProcessEvent());

        //Danh sach dia diem
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this);
        rv_project_listLocation.setLayoutManager(linearLayoutManager);
        locationAdapter=new ListLocationAdapter(this, locationList);
        rv_project_listLocation.setAdapter(locationAdapter);

        btn_project_them.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                xuLyThemLocation();
            }
        });

        // Nút chọn ảnh
        btn_chon_anh.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // chỉ cần dòng bên dưới là sẽ gọi được Activity chọn ảnh
                ImagePickerUtils.showFileChooser(CreateProjectActivity.this, chooseImages, PICK_IMAGE_REQUEST);
            }
        });
    }

    // Sau khi chọn ảnh thì nó sẽ trả về ActivityResult nhé
    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode == PICK_IMAGE_REQUEST && resultCode == RESULT_OK && data != null) {
            // dùng cái list Image (Image này của nó) để load data trả về
            chooseImages = data.getParcelableArrayListExtra(ImagePickerActivity.INTENT_EXTRA_SELECTED_IMAGES);
            for (int i = 0; i < chooseImages.size(); i++) {
                // Dùng thêm một thư viện nén ảnh để khi đổ ra Recycle View ko bị giật, và tránh bị việc ko hiển thị nếu độ phân
                // giải ảnh quá lớn. Nói chung là muốn làm gì thì cũng phải nén bớt nó lại =))
                lstImage.add(new Image(Compressor.getDefault(this).compressToBitmap(new File(chooseImages.get(i).getPath()))));
            }
            lpiaAdapter.notifyDataSetChanged();
        }
    }

    private void xuLyThemLocation() {
        a =new Location(city, district, ward);
        locationList.add(a);
        locationAdapter.notifyDataSetChanged();
    }

    private class StatusProjectProcess implements AdapterView.OnItemSelectedListener{
        @Override
        public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
            project_status = spinArr[i];
        }

        @Override
        public void onNothingSelected(AdapterView<?> adapterView) {
            project_status = spinArr[0];
        }
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
}
