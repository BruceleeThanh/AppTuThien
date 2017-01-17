package com.example.vuduc.apptuthien;

import android.app.DatePickerDialog;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.ImageButton;
import android.widget.Spinner;
import android.widget.TextView;

import com.example.vuduc.adapters.ListLocationAdapter;
import com.example.vuduc.adapters.ListPickImageAdapter;
import com.example.vuduc.model.Image;
import com.example.vuduc.model.Location;
import com.example.vuduc.utils.ImagePickerUtils;
import com.nguyenhoanglam.imagepicker.activity.ImagePickerActivity;

import java.io.File;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

import id.zelory.compressor.Compressor;

public class CreateRedPontActivity extends AppCompatActivity {

    Spinner spinner_redpoint_City, spinner_redpoint_District, spinner_redpoint_Wards, spinner_redpoint_status;
    ArrayAdapter<CharSequence> citySpinAdapter;
    ArrayAdapter<CharSequence> districtSpinAdapter;
    ArrayAdapter<CharSequence> wardsSpinAdapter;
    ArrayAdapter<String> statusSpinAdapter;
    String spinStatus[]={"Chưa khẩn cấp", "Khẩn cấp", "Cực kỳ khẩn cấp"};
    String redpoint_statuts;
    // danh sach location
    Button btn_redpoint_them;
    RecyclerView rv_redpoint_listLocation;
    ArrayList<Location> locationList;
    ListLocationAdapter locationAdapter;
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

    //Date picker
    TextView txtDayStartProject;
    ImageButton btnStartDate;
    Calendar calendar = Calendar.getInstance();
    SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create_red_pont);
        getSupportActionBar().setTitle("Tạo mới điểm nóng");
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

        btn_redpoint_them.setOnClickListener(new View.OnClickListener() {
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
                ImagePickerUtils.showFileChooser(CreateRedPontActivity.this, chooseImages, PICK_IMAGE_REQUEST);
            }
        });

        btnStartDate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                xuLyStartDay();
            }
        });
    }

    private void xuLyStartDay() {
        DatePickerDialog.OnDateSetListener callBack=new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker datePicker, int i, int i1, int i2) {
                calendar.set(Calendar.YEAR, i);
                calendar.set(Calendar.MONTH,i1);
                calendar.set(Calendar.DAY_OF_MONTH,i2);
                txtDayStartProject.setText(sdf.format(calendar.getTime()));
            }
        };
        DatePickerDialog dialog=new DatePickerDialog(CreateRedPontActivity.this, callBack,
                calendar.get(Calendar.YEAR),
                calendar.get(Calendar.MONTH),
                calendar.get(Calendar.DAY_OF_MONTH));
        dialog.show();
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
        Location a =new Location(city, district, ward);
        locationList.add(a);
        locationAdapter.notifyDataSetChanged();
    }

    private void addControls() {
        //List location
        btn_redpoint_them= (Button) findViewById(R.id.btn_redpoint_them);
        rv_redpoint_listLocation= (RecyclerView) findViewById(R.id.rv_redpoint_listLocation);
        locationList=new ArrayList<>();

        //Spinner location
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

        // Khởi tạo rv_chon_anh nhé
        btn_chon_anh = (Button) findViewById(R.id.btn_chon_anh);
        rv_chon_anh = (RecyclerView) findViewById(R.id.rv_chon_anh);
        rv_chon_anh.setLayoutManager(new LinearLayoutManager(this));
        lstImage = new ArrayList<>();
        lpiaAdapter = new ListPickImageAdapter(this, lstImage);
        rv_chon_anh.setAdapter(lpiaAdapter);

        //date picker
        txtDayStartProject = (TextView) findViewById(R.id.txtDayStartProject);
        btnStartDate = (ImageButton) findViewById(R.id.btnStartDate);
        txtDayStartProject.setText(sdf.format(calendar.getTime()));
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
