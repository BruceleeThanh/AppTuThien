package com.example.vuduc.apptuthien;

import android.app.DatePickerDialog;
import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
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

public class CreateRedPontActivity extends AppCompatActivity {

    Spinner spinner_redpoint_City, spinner_redpoint_District, spinner_redpoint_Wards, spinner_redpoint_status, spinner_redpoint_category;
    ArrayAdapter<String> citySpinAdapter;
    ArrayAdapter<String> districtSpinAdapter;
    ArrayAdapter<String> wardsSpinAdapter;
    ArrayAdapter<String> statusSpinAdapter;
    ArrayAdapter<String> categorySpinAdapter;

    String city = "", district = "", ward = "", status = "", category = "";
    private final int PICK_IMAGE_REQUEST = 200;

    // Những biến và list bên dưới đây phục vụ cho việc chọn ảnh
    Button btn_chon_anh;
    RecyclerView rv_chon_anh;
    ListPickImageAdapter lpiaAdapter;
    // Mình phải dùng 1 cái list Image của nó để chứa dữ liệu trả về.
    // Vì 2 class trùng tên, nên một thằng phải viết dài như thế này
    private ArrayList<com.nguyenhoanglam.imagepicker.model.Image> chooseImages;
    private List<Image> lstImage;

    EditText editProjectName, editProjectContent;
    Button btnLuu, btnHuy;

    //Date picker
    TextView txtDayStartProject;
    ImageButton btnStartDate;
    Calendar calendar = Calendar.getInstance();
    SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");

    // Resource for Spinner Location
    private List<Place> lstCity = null;
    private List<Place> lstDistrict = null;
    private List<Place> lstWard = null;

    // Resource for Spinner Status
    private List<Status> lstStatus = null;

    // Resource for Spinner Category
    private List<Category> lstCategory = null;

    // Resource for Request APIs
    private AppRequest appRequest;
    private String token = null;

    // Resource for Images upload
    private List<String> lstUrlImageUploaded;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create_red_pont);
        getSupportActionBar().setTitle("Tạo mới điểm nóng");
        addControls();
        addEvents();
    }

    private void addControls() {

        //Spinner location
        spinner_redpoint_City = (Spinner) findViewById(R.id.spinner_redpoint_City);
        spinner_redpoint_District = (Spinner) findViewById(R.id.spinner_redpoint_District);
        spinner_redpoint_Wards = (Spinner) findViewById(R.id.spinner_redpoint_Wards);
        spinner_redpoint_status = (Spinner) findViewById(R.id.spinner_redpoint_status);
        spinner_redpoint_category = (Spinner) findViewById(R.id.spinner_redpoint_category);

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

        editProjectName = (EditText) findViewById(R.id.editProjectName);
        editProjectContent = (EditText) findViewById(R.id.editProjectContent);

        // Lưu vs Huỷ
        btnLuu = (Button) findViewById(R.id.btnLuu);
        btnHuy = (Button) findViewById(R.id.btnHuy);

        // get token
        token = SharedPref.getInstance(this).getString(ApiConstants.KEY_TOKEN, "");

        loadPlace(null, 1);
        loadStatus();
        loadCategory();
    }

    private void addEvents() {
        spinner_redpoint_City.setOnItemSelectedListener(new CityProcessEvent());
        spinner_redpoint_District.setOnItemSelectedListener(new DistrictProcessEvent());
        spinner_redpoint_Wards.setOnItemSelectedListener(new WardProcessEvent());
        spinner_redpoint_status.setOnItemSelectedListener(new StatusProcessEvent());
        spinner_redpoint_category.setOnItemSelectedListener(new CategoryProcessEvent());

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

        btnLuu.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                uploadRedPoint();
            }
        });

        btnHuy.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });
    }

    private void xuLyStartDay() {
        DatePickerDialog.OnDateSetListener callBack = new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker datePicker, int i, int i1, int i2) {
                calendar.set(Calendar.YEAR, i);
                calendar.set(Calendar.MONTH, i1);
                calendar.set(Calendar.DAY_OF_MONTH, i2);
                txtDayStartProject.setText(sdf.format(calendar.getTime()));
            }
        };
        DatePickerDialog dialog = new DatePickerDialog(CreateRedPontActivity.this, callBack,
                calendar.get(Calendar.YEAR),
                calendar.get(Calendar.MONTH),
                calendar.get(Calendar.DAY_OF_MONTH));
        dialog.show();
    }

    // Sau khi chọn ảnh thì nó sẽ trả về ActivityResult nhé
    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode == PICK_IMAGE_REQUEST && resultCode == RESULT_OK && data != null) {
            lstImage.clear();
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

    //region Upload Red Point (Voluntary spot)
    public void uploadRedPoint() {
        if (lstImage.isEmpty()) {
            this.uploadSingleRedPoint();
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
                                    uploadSingleRedPoint();
                                }
                                RLog.i("fucking res " + resultResponse);
                            }
                        }, new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        if (temp == lstImage.size() - 1) {
                            uploadSingleRedPoint();
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
                MySingleton.getInstance(CreateRedPontActivity.this).addToRequestQueue(multipartRequest, false);
            }
        }
    }

    private void uploadSingleRedPoint() {
        appRequest = new AppRequest(CreateRedPontActivity.this, Request.Method.POST,
                ApiConstants.getUrl(ApiConstants.API_VOLUNTARY_SPOT_CREATE), getSingleRedPointParams(), false);
        getSingleRedPointResponse();
    }

    private HashMap getSingleRedPointParams() {
        HashMap<String, String> params = new HashMap<>();
        params.put(ApiConstants.KEY_TOKEN, token);
        params.put(ApiConstants.KEY_TITLE, StringUtil.getText(editProjectName));
        params.put(ApiConstants.KEY_CONTENT, StringUtil.getText(editProjectContent));
        params.put(ApiConstants.KEY_LOCATION, getValueLocation());
        params.put(ApiConstants.KEY_CATEGORY, getValueCategory());
        params.put(ApiConstants.KEY_STATUS, status);
        params.put(ApiConstants.KEY_HAPPENED_AT, calendar.getTime() != null ? ConvertTimeHelper.convertDateToISOFormat(calendar.getTime()) : "");
        params.put(ApiConstants.KEY_VIDEOS, null);
        params.put(ApiConstants.KEY_IMAGES, getValueImages());
        return params;
    }

    private void getSingleRedPointResponse(){
        appRequest.setOnAppRequestListener(new AppRequest.OnAppRequestListener() {
            @Override
            public void onAppResponse(JSONObject response) {
                ToastUtil.showToast(CreateRedPontActivity.this, "Tạo điểm nóng thành công");
                finish();
            }

            @Override
            public void onAppError(int errorCode, String errorMsg) {
                ToastUtil.showToast(CreateRedPontActivity.this, errorMsg);
            }
        });
    }

    @Nullable
    private String getJsonArrayFromString(List<String> lstString) {
        JSONArray jsonArray = new JSONArray();
        for (String temp:lstString) {
            jsonArray.put(temp);
        }
        return jsonArray.toString() == null ? null : jsonArray.toString();
    }

    @Nullable
    private String getValueLocation(){
        List<String > lstLocation = new ArrayList<>();
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
    private String getValueImages(){
        return lstUrlImageUploaded == null ? null : getJsonArrayFromString(lstUrlImageUploaded);
    }
    //endregion

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
        citySpinAdapter = new ArrayAdapter<>(CreateRedPontActivity.this,
                android.R.layout.simple_spinner_item, lstPlaceName);
        citySpinAdapter.setDropDownViewResource(android.R.layout.simple_list_item_single_choice);
        spinner_redpoint_City.setPrompt("Chọn Tỉnh, Thành phố");
        spinner_redpoint_City.setAdapter(citySpinAdapter);
    }

    private void loadDistrictSpinner(List<Place> lstPlace, List<String> lstPlaceName) {
        lstDistrict = lstPlace;
        districtSpinAdapter = new ArrayAdapter<>(CreateRedPontActivity.this,
                android.R.layout.simple_spinner_item, lstPlaceName);
        districtSpinAdapter.setDropDownViewResource(android.R.layout.simple_list_item_single_choice);
        spinner_redpoint_District.setPrompt("Chọn Huyện, Quận");
        spinner_redpoint_District.setAdapter(districtSpinAdapter);
    }

    private void loadWardSpinner(List<Place> lstPlace, List<String> lstPlaceName) {
        lstWard = lstPlace;
        wardsSpinAdapter = new ArrayAdapter<>(CreateRedPontActivity.this,
                android.R.layout.simple_spinner_item, lstPlaceName);
        wardsSpinAdapter.setDropDownViewResource(android.R.layout.simple_list_item_single_choice);
        spinner_redpoint_Wards.setPrompt("Chọn Xã, Phường");
        spinner_redpoint_Wards.setAdapter(wardsSpinAdapter);
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
        appRequest = new AppRequest(CreateRedPontActivity.this, Request.Method.GET, ApiConstants.getUrl(ApiConstants.API_STATUS_BROWSE),
                getStatusParams(), false);
        getStatusResponse();
    }

    private HashMap getStatusParams() {
        HashMap<String, String> params = new HashMap<>();
        params.put(ApiConstants.KEY_TOKEN, token);
        return params;
    }

    private void getStatusResponse() {
        appRequest.setOnAppRequestListener(new AppRequest.OnAppRequestListener() {
            @Override
            public void onAppResponse(JSONObject response) {
                List<String> lstStatusTitle = new ArrayList<String>();
                lstStatus = new ArrayList<Status>();
                JSONArray data = JsonUtil.getJSONArray(response, ApiConstants.DEF_DATA);
                int length = data.length();
                for (int i = 0; i < length; i++) {
                    JSONObject item = JsonUtil.getJSONObject(data, i);
                    lstStatus.add(new Status(
                            JsonUtil.getString(item, ApiConstants.KEY_ID, ""),
                            JsonUtil.getString(item, ApiConstants.KEY_TITLE, ""),
                            JsonUtil.getString(item, ApiConstants.KEY_CONTENT, ""),
                            JsonUtil.getString(item, ApiConstants.KEY_LABEL, "")
                    ));
                    lstStatusTitle.add(JsonUtil.getString(item, ApiConstants.KEY_TITLE, ""));
                }
                statusSpinAdapter = new ArrayAdapter<>(CreateRedPontActivity.this,
                        android.R.layout.simple_spinner_item, lstStatusTitle);
                statusSpinAdapter.setDropDownViewResource(android.R.layout.simple_list_item_single_choice);
                spinner_redpoint_status.setPrompt("Chọn trạng thái");
                spinner_redpoint_status.setAdapter(statusSpinAdapter);
            }

            @Override
            public void onAppError(int errorCode, String errorMsg) {

            }
        });
    }

    private class StatusProcessEvent implements AdapterView.OnItemSelectedListener {
        @Override
        public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
            status = lstStatus.get(i).getId();
        }

        @Override
        public void onNothingSelected(AdapterView<?> adapterView) {
            status = "";
        }
    }
    //endregion

    //region Choose Category
    private void loadCategory() {
        appRequest = new AppRequest(CreateRedPontActivity.this, Request.Method.GET, ApiConstants.getUrl(ApiConstants.API_CATEGORY_BROWSE),
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
                categorySpinAdapter = new ArrayAdapter<>(CreateRedPontActivity.this,
                        android.R.layout.simple_spinner_item, lstCategoryTitle);
                categorySpinAdapter.setDropDownViewResource(android.R.layout.simple_list_item_single_choice);
                spinner_redpoint_category.setPrompt("Chọn danh mục");
                spinner_redpoint_category.setAdapter(categorySpinAdapter);
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
