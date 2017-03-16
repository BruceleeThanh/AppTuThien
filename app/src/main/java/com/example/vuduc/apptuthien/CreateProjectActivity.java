package com.example.vuduc.apptuthien;

import android.app.DatePickerDialog;
import android.content.Intent;
import android.graphics.Bitmap;
import android.opengl.ETC1;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.Spinner;
import android.widget.TextView;

import com.android.volley.DefaultRetryPolicy;
import com.android.volley.NetworkResponse;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.error.VolleyError;
import com.example.vuduc.adapters.ListLocationAdapter;
import com.example.vuduc.adapters.ListPickImageAdapter;
import com.example.vuduc.helpers.ConvertTimeHelper;
import com.example.vuduc.libs.ApiConstants;
import com.example.vuduc.model.Category;
import com.example.vuduc.model.Image;
import com.example.vuduc.model.Location;
import com.example.vuduc.model.Place;
import com.example.vuduc.model.Status;
import com.example.vuduc.services.AppRequest;
import com.example.vuduc.services.MultipartRequest;
import com.example.vuduc.services.MySingleton;
import com.example.vuduc.utils.ImagePickerUtils;
import com.example.vuduc.utils.JsonUtil;
import com.example.vuduc.utils.RLog;
import com.example.vuduc.utils.SharedPref;
import com.example.vuduc.utils.StringUtil;
import com.example.vuduc.utils.ToastUtil;
import com.nguyenhoanglam.imagepicker.activity.ImagePicker;
import com.nguyenhoanglam.imagepicker.activity.ImagePickerActivity;

import org.json.JSONArray;
import org.json.JSONObject;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import id.zelory.compressor.Compressor;

public class CreateProjectActivity extends AppCompatActivity {

    //spinner location
    Spinner spinner_project_City, spinner_project_District, spinner_project_Wards, spinner_project_status, spinner_project_category;
    ArrayAdapter<String> citySpinAdapter;
    ArrayAdapter<String> districtSpinAdapter;
    ArrayAdapter<String> wardsSpinAdapter;
    ArrayAdapter<String> statusSpinAdapter;
    ArrayAdapter<String> categorySpinAdapter;

    //danh sach location
    String city = "", district = "", ward = "", status = "", category="";
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
    TextView txtDayStartProject, txtDayEndProject;
    ImageButton btnStartDate, btnEndDate;
    Calendar calendarStart = Calendar.getInstance();
    Calendar calendarEnd = Calendar.getInstance();
    SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");

    private EditText editProjectName;
    private EditText editProjectContent;
    private EditText editKinhPhiProject;
    private EditText editTienTaiTro;
    private Button btnLuu;
    private Button btnHuy;
    private AppRequest appRequest;
    private String token;
    private List<String> lstUrlImageUploaded = null;

    // Resource for Spinner Location
    private List<Place> lstCity = null;
    private List<Place> lstDistrict = null;
    private List<Place> lstWard = null;

    // Resource for Spinner Status
    private String spinArr[] = {"Chưa thực hiện", "Đang thực hiện", "Đã kết thúc"};

    // Resource for Spinner Category
    private List<Category> lstCategory = null;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create_project);
        getSupportActionBar().setTitle("Tạo mới dự án từ thiện");
        addControls();
        addEvents();
    }

    private void addControls() {
        editProjectName = (EditText) findViewById(R.id.editProjectName);
        editProjectContent = (EditText) findViewById(R.id.editProjectContent);
        editKinhPhiProject = (EditText) findViewById(R.id.editKinhPhiProject);
        editTienTaiTro = (EditText) findViewById(R.id.editTienTaiTro);

        btnLuu = (Button) findViewById(R.id.btnLuu);
        btnHuy = (Button) findViewById(R.id.btnHuy);

        spinner_project_City = (Spinner) findViewById(R.id.spinner_project_City);
        spinner_project_District = (Spinner) findViewById(R.id.spinner_project_District);
        spinner_project_Wards = (Spinner) findViewById(R.id.spinner_project_Wards);
        spinner_project_status = (Spinner) findViewById(R.id.spinner_project_status);
        spinner_project_category = (Spinner) findViewById(R.id.spinner_project_category);

        // Khởi tạo rv_chon_anh nhé
        btn_chon_anh = (Button) findViewById(R.id.btn_chon_anh);
        rv_chon_anh = (RecyclerView) findViewById(R.id.rv_chon_anh);
        rv_chon_anh.setLayoutManager(new LinearLayoutManager(this));
        lstImage = new ArrayList<>();
        lpiaAdapter = new ListPickImageAdapter(this, lstImage);
        rv_chon_anh.setAdapter(lpiaAdapter);

        //date picker
        txtDayStartProject = (TextView) findViewById(R.id.txtDayStartProject);
        txtDayEndProject = (TextView) findViewById(R.id.txtDayEndProject);
        btnStartDate = (ImageButton) findViewById(R.id.btnStartDate);
        btnEndDate = (ImageButton) findViewById(R.id.btnEndDate);
        txtDayStartProject.setText(sdf.format(calendarStart.getTime()));
        txtDayEndProject.setText(sdf.format(calendarEnd.getTime()));

        // get token
        token = SharedPref.getInstance(this).getString(ApiConstants.KEY_TOKEN, "");

        loadPlace(null, 1);
        loadStatus();
        loadCategory();
    }

    private void addEvents() {
        spinner_project_status.setOnItemSelectedListener(new StatusProcessEvent());
        spinner_project_City.setOnItemSelectedListener(new CityProcessEvent());
        spinner_project_District.setOnItemSelectedListener(new DistrictProcessEvent());
        spinner_project_Wards.setOnItemSelectedListener(new WardProcessEvent());
        spinner_project_category.setOnItemSelectedListener(new CategoryProcessEvent());

        // Nút chọn ảnh
        btn_chon_anh.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // chỉ cần dòng bên dưới là sẽ gọi được Activity chọn ảnh
                ImagePickerUtils.showFileChooser(CreateProjectActivity.this, chooseImages, PICK_IMAGE_REQUEST);
            }
        });

        btnStartDate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                xuLyStartDay();
            }
        });
        btnEndDate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                xyLyEndDay();
            }
        });
        btnLuu.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                uploadProject();
            }
        });
        btnHuy.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });
    }

    private void xyLyEndDay() {
        DatePickerDialog.OnDateSetListener callBack = new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker datePicker, int i, int i1, int i2) {
                calendarEnd.set(Calendar.YEAR, i);
                calendarEnd.set(Calendar.MONTH, i1);
                calendarEnd.set(Calendar.DAY_OF_MONTH, i2);
                txtDayEndProject.setText(sdf.format(calendarEnd.getTime()));
            }
        };
        DatePickerDialog dialog = new DatePickerDialog(CreateProjectActivity.this, callBack,
                calendarEnd.get(Calendar.YEAR),
                calendarEnd.get(Calendar.MONTH),
                calendarEnd.get(Calendar.DAY_OF_MONTH));
        dialog.show();

    }

    private void xuLyStartDay() {
        DatePickerDialog.OnDateSetListener callBack = new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker datePicker, int i, int i1, int i2) {
                calendarStart.set(Calendar.YEAR, i);
                calendarStart.set(Calendar.MONTH, i1);
                calendarStart.set(Calendar.DAY_OF_MONTH, i2);
                txtDayStartProject.setText(sdf.format(calendarStart.getTime()));
            }
        };
        DatePickerDialog dialog = new DatePickerDialog(CreateProjectActivity.this, callBack,
                calendarStart.get(Calendar.YEAR),
                calendarStart.get(Calendar.MONTH),
                calendarStart.get(Calendar.DAY_OF_MONTH));
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

    public void uploadProject() {
        if (lstImage.isEmpty()) {
            this.uploadSingleProject();
        } else {
            lstUrlImageUploaded = new ArrayList<>();

            for (int i = 0; i < lstImage.size(); i++) {
                final int temp = i;
                MultipartRequest multipartRequest = new MultipartRequest(Request.Method.POST, ApiConstants.getUrl(ApiConstants.API_UPLOAD_IMAGE),
                        new Response.Listener<NetworkResponse>() {
                            @Override
                            public void onResponse(NetworkResponse response) {
                                RLog.i(response);
                                String resultResponse = new String(response.data);
                                JSONObject jsonObject = JsonUtil.createJSONObject(resultResponse);
                                if (JsonUtil.getInt(jsonObject, ApiConstants.DEF_CODE, 0) == 1) {
                                    jsonObject = JsonUtil.getJSONObject(jsonObject, ApiConstants.DEF_DATA);
                                    lstUrlImageUploaded.add(JsonUtil.getString(jsonObject, ApiConstants.KEY_LINK, ""));
                                }
                                if (temp == lstImage.size() - 1) {
                                    uploadSingleProject();
                                }
                                RLog.i("fucking res " + resultResponse);
                            }
                        }, new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        if (temp == lstImage.size() - 1) {
                            uploadSingleProject();
                        }
                        RLog.e("fucking err " + error.getMessage());
                    }
                }) {
                    @Override
                    protected Map<String, String> getParams() {
                        Map<String, String> params = new HashMap<>();
                        params.put(ApiConstants.KEY_TOKEN, token);
                        return params;
                    }

                    @Override
                    protected Map<String, DataPart> getByteData() {
                        Map<String, DataPart> params = new HashMap<>();
                        // file name could found file base or direct access from real path
                        // for now just get bitmap data from ImageView
                        ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
                        lstImage.get(temp).getImg().compress(Bitmap.CompressFormat.PNG, 0, byteArrayOutputStream);
                        params.put(ApiConstants.KEY_FILE, new DataPart("cover.jpg", byteArrayOutputStream.toByteArray(), "image/jpeg"));
                        return params;
                    }
                };
                multipartRequest.setRetryPolicy(new DefaultRetryPolicy(
                        DefaultRetryPolicy.DEFAULT_TIMEOUT_MS,
                        0,
                        DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));
                MySingleton.getInstance(CreateProjectActivity.this).addToRequestQueue(multipartRequest, false);
            }
        }
    }

    private void uploadSingleProject() {
        appRequest = new AppRequest(CreateProjectActivity.this, Request.Method.POST,
                ApiConstants.getUrl(ApiConstants.API_VOLUNTARY_PROJECT_CREATE), getUploadSingleProjectParams(), false);
        getUploadProjectResponse();
    }

    private HashMap getUploadSingleProjectParams() {
        HashMap<String, String> params = new HashMap<>();
        params.put(ApiConstants.KEY_TOKEN, token);
        params.put(ApiConstants.KEY_TITLE, StringUtil.getText(editProjectName));
        params.put(ApiConstants.KEY_CONTENT, StringUtil.getText(editProjectContent));
        params.put(ApiConstants.KEY_IMAGES, getValueImages());
        params.put(ApiConstants.KEY_VIDEOS, null);
        params.put(ApiConstants.KEY_LOCATION, getValueLocation());
        params.put(ApiConstants.KEY_CATEGORY, getValueCategory());
        params.put(ApiConstants.KEY_STATUS, status);
        params.put(ApiConstants.KEY_DONATE, StringUtil.getText(editTienTaiTro));
        params.put(ApiConstants.KEY_STARTING_TIME,
                calendarStart.getTime() != null ? ConvertTimeHelper.convertDateToISOFormat(calendarStart.getTime()) : "");
        params.put(ApiConstants.KEY_FINISH_TIME,
                calendarEnd.getTime() != null ? ConvertTimeHelper.convertDateToISOFormat(calendarEnd.getTime()) : "");
        params.put(ApiConstants.KEY_MONEY, StringUtil.getText(editKinhPhiProject));
        return params;
    }

    private void getUploadProjectResponse() {
        appRequest.setOnAppRequestListener(new AppRequest.OnAppRequestListener() {
            @Override
            public void onAppResponse(JSONObject response) {
                ToastUtil.showToast(CreateProjectActivity.this, "Tạo dự án thành công");
                finish();
            }

            @Override
            public void onAppError(int errorCode, String errorMsg) {
                ToastUtil.showToast(CreateProjectActivity.this, errorMsg);
            }
        });
    }


    @Nullable
    private String getJsonArrayFromString(List<String> lstString) {
        JSONArray jsonArray = new JSONArray();
        for (String temp : lstString) {
            jsonArray.put(temp);
        }
        return jsonArray.toString() == null ? null : jsonArray.toString();
    }

    @Nullable
    private String getValueLocation() {
        List<String> lstLocation = new ArrayList<>();
        lstLocation.add(city);
        lstLocation.add(district);
        lstLocation.add(ward);
        return getJsonArrayFromString(lstLocation);
    }

    @Nullable
    private String getValueCategory(){
        List<String > lstCategories = new ArrayList<>();
        lstCategories.add(category);
        return getJsonArrayFromString(lstCategories);
    }

    @Nullable
    private String getValueImages() {
        return lstUrlImageUploaded == null ? null : getJsonArrayFromString(lstUrlImageUploaded);
    }

    //region Choose Location
    private void loadPlace(@Nullable String parent, int level) {
        appRequest = new AppRequest(this, Request.Method.GET, ApiConstants.getUrl(ApiConstants.API_LOCATION_BROWSE),
                getPlaceParams(parent), false);
        getPlaceResponse(level);
    }

    private HashMap getPlaceParams(@Nullable String parent) {
        HashMap<String, String> params = new HashMap<>();
        params.put(ApiConstants.KEY_TOKEN, token);
        if (parent != null) {
            params.put(ApiConstants.KEY_PARENT, parent);
        }
        return params;
    }

    private void getPlaceResponse(final int level) {
        appRequest.setOnAppRequestListener(new AppRequest.OnAppRequestListener() {
            @Override
            public void onAppResponse(JSONObject response) {
                List<String> lstPlaceName = new ArrayList<>();
                List<Place> lstPlace = new ArrayList<>();
                JSONArray data = JsonUtil.getJSONArray(response, ApiConstants.DEF_DATA);
                JSONObject item = null;
                int length = data.length();
                for (int i = 0; i < length; i++) {
                    item = JsonUtil.getJSONObject(data, i);
                    lstPlace.add(new Place(
                            JsonUtil.getString(item, ApiConstants.KEY_ID, ""),
                            JsonUtil.getString(item, ApiConstants.KEY_NAME, ""),
                            JsonUtil.getString(item, ApiConstants.KEY_TYPE, ""),
                            JsonUtil.getString(item, ApiConstants.KEY_PARENT, "")
                    ));
                    lstPlaceName.add(JsonUtil.getString(item, ApiConstants.KEY_NAME, ""));
                }

                if (level == 1) {
                    loadCitySpinner(lstPlace, lstPlaceName);
                } else if (level == 2) {
                    loadDistrictSpinner(lstPlace, lstPlaceName);
                } else if (level == 3) {
                    loadWardSpinner(lstPlace, lstPlaceName);
                }
            }

            @Override
            public void onAppError(int errorCode, String errorMsg) {
                RLog.e(errorMsg);
            }
        });
    }

    private void loadCitySpinner(List<Place> lstPlace, List<String> lstPlaceName) {
        lstCity = lstPlace;
        citySpinAdapter = new ArrayAdapter<>(CreateProjectActivity.this,
                android.R.layout.simple_spinner_item, lstPlaceName);
        citySpinAdapter.setDropDownViewResource(android.R.layout.simple_list_item_single_choice);
        spinner_project_City.setPrompt("Chọn Tỉnh, Thành phố");
        spinner_project_City.setAdapter(citySpinAdapter);
    }

    private void loadDistrictSpinner(List<Place> lstPlace, List<String> lstPlaceName) {
        lstDistrict = lstPlace;
        districtSpinAdapter = new ArrayAdapter<>(CreateProjectActivity.this,
                android.R.layout.simple_spinner_item, lstPlaceName);
        districtSpinAdapter.setDropDownViewResource(android.R.layout.simple_list_item_single_choice);
        spinner_project_District.setPrompt("Chọn Huyện, Quận");
        spinner_project_District.setAdapter(districtSpinAdapter);
    }

    private void loadWardSpinner(List<Place> lstPlace, List<String> lstPlaceName) {
        lstWard = lstPlace;
        wardsSpinAdapter = new ArrayAdapter<>(CreateProjectActivity.this,
                android.R.layout.simple_spinner_item, lstPlaceName);
        wardsSpinAdapter.setDropDownViewResource(android.R.layout.simple_list_item_single_choice);
        spinner_project_Wards.setPrompt("Chọn Xã, Phường");
        spinner_project_Wards.setAdapter(wardsSpinAdapter);
    }

    private class CityProcessEvent implements android.widget.AdapterView.OnItemSelectedListener {
        @Override
        public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
            city = lstCity.get(i).getId();
            loadPlace(city, 2);
        }

        @Override
        public void onNothingSelected(AdapterView<?> adapterView) {
            city = "";
        }
    }

    private class DistrictProcessEvent implements android.widget.AdapterView.OnItemSelectedListener {
        @Override
        public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
            district = lstDistrict.get(i).getId();
            loadPlace(district, 3);
        }

        @Override
        public void onNothingSelected(AdapterView<?> adapterView) {
            district = "";
        }
    }

    private class WardProcessEvent implements android.widget.AdapterView.OnItemSelectedListener {
        @Override
        public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
            ward = lstWard.get(i).getId();
        }

        @Override
        public void onNothingSelected(AdapterView<?> adapterView) {
            ward = "";
        }
    }
    //endregion

    //region Choose Status
    private void loadStatus() {
        statusSpinAdapter = new ArrayAdapter<>(CreateProjectActivity.this,
                android.R.layout.simple_spinner_item, spinArr);
        statusSpinAdapter.setDropDownViewResource(android.R.layout.simple_list_item_single_choice);
        spinner_project_status.setPrompt("Chọn trạng thái");
        spinner_project_status.setAdapter(statusSpinAdapter);
    }

    private class StatusProcessEvent implements AdapterView.OnItemSelectedListener {
        @Override
        public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
            status = spinArr[i];
        }

        @Override
        public void onNothingSelected(AdapterView<?> adapterView) {
            status = "";
        }
    }
    //endregion

    //region Choose Category
    private void loadCategory() {
        appRequest = new AppRequest(CreateProjectActivity.this, Request.Method.GET, ApiConstants.getUrl(ApiConstants.API_CATEGORY_BROWSE),
                getCategoryParams(), false);
        getCategoryResponse();
    }

    private HashMap getCategoryParams() {
        HashMap<String, String> params = new HashMap<>();
        params.put(ApiConstants.KEY_TOKEN, token);
        return params;
    }

    private void getCategoryResponse() {
        appRequest.setOnAppRequestListener(new AppRequest.OnAppRequestListener() {
            @Override
            public void onAppResponse(JSONObject response) {
                List<String> lstCategoryTitle = new ArrayList<>();
                lstCategory = new ArrayList<>();
                JSONArray data = JsonUtil.getJSONArray(response, ApiConstants.DEF_DATA);
                int length = data.length();
                for (int i = 0; i < length; i++) {
                    JSONObject item = JsonUtil.getJSONObject(data, i);
                    lstCategory.add(new Category(
                            JsonUtil.getString(item, ApiConstants.KEY_ID, ""),
                            JsonUtil.getString(item, ApiConstants.KEY_TITLE, ""),
                            JsonUtil.getString(item, ApiConstants.KEY_CONTENT, ""),
                            JsonUtil.getString(item, ApiConstants.KEY_PARENT, "")
                    ));
                    lstCategoryTitle.add(JsonUtil.getString(item, ApiConstants.KEY_TITLE, ""));
                }
                categorySpinAdapter = new ArrayAdapter<>(CreateProjectActivity.this,
                        android.R.layout.simple_spinner_item, lstCategoryTitle);
                categorySpinAdapter.setDropDownViewResource(android.R.layout.simple_list_item_single_choice);
                spinner_project_category.setPrompt("Chọn danh mục");
                spinner_project_category.setAdapter(categorySpinAdapter);
            }

            @Override
            public void onAppError(int errorCode, String errorMsg) {

            }
        });
    }

    private class CategoryProcessEvent implements AdapterView.OnItemSelectedListener {
        @Override
        public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
            category = lstCategory.get(i).getId();
        }

        @Override
        public void onNothingSelected(AdapterView<?> adapterView) {
            category = "";
        }
    }
    //endregion

}
