package com.example.vuduc.apptuthien;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.android.volley.Request;
import com.example.vuduc.adapters.ListLinkProjectAdapter;
import com.example.vuduc.adapters.ListLocationAdapter;
import com.example.vuduc.helpers.ConvertTimeHelper;
import com.example.vuduc.helpers.PicassoHelper;
import com.example.vuduc.libs.ApiConstants;
import com.example.vuduc.model.Location;
import com.example.vuduc.model.RedPoint;
import com.example.vuduc.model.User;
import com.example.vuduc.model.VoluntaryProject;
import com.example.vuduc.services.AppRequest;
import com.example.vuduc.utils.JsonUtil;
import com.example.vuduc.utils.SharedPref;
import com.example.vuduc.utils.StringUtil;
import com.example.vuduc.utils.ToastUtil;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class RedpointDetailActivity extends AppCompatActivity {

    private ImageView ivImageRedPoint;
    private TextView tvTitleRedPoint;
    private TextView tvContentRedPoint;
    private TextView tvCreatorRedPoint;
    private TextView tvCreatedAtRedPoint;
    private TextView tvLocationRedPoint;
    private TextView tvStatusRedPoint;
    private RecyclerView rvImagesRedPoint;
    private AppRequest appRequest;
    private String token;
    private String idRedPoint;
    private RedPoint redPoint;


    ListLocationAdapter listLocationAdapter;
    List<Location> locationLists;

    //Danh sach du an tai tro
    RecyclerView rv_list_link_project;
    ListLinkProjectAdapter listLinkProjectAdapter;
    List<VoluntaryProject> voluntaryProjectList;

    Button btn_edit_thongtin;
    Button btn_dangky_cuutro;

    //Link trang ca nhan
    TextView txt_link_user_profile;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_redpoint_detail);
        if (getIntent().hasExtra(ApiConstants.KEY_ID)) {
            idRedPoint = getIntent().getStringExtra(ApiConstants.KEY_ID);
        }
        getSupportActionBar().setTitle("Thông tin điểm nóng");
        addControls();
        addEvents();

    }

    private void addControls() {
        ivImageRedPoint = (ImageView) findViewById(R.id.ivImageRedPoint);
        tvTitleRedPoint = (TextView) findViewById(R.id.tvTitleRedPoint);
        tvContentRedPoint = (TextView) findViewById(R.id.tvContentRedPoint);
        tvCreatorRedPoint = (TextView) findViewById(R.id.tvCreatorRedPoint);
        tvCreatedAtRedPoint = (TextView) findViewById(R.id.tvCreatedAtRedPoint);
        tvLocationRedPoint = (TextView) findViewById(R.id.tvLocationRedPoint);
        tvStatusRedPoint = (TextView) findViewById(R.id.tvStatusRedPoint);
        rvImagesRedPoint = (RecyclerView) findViewById(R.id.rvImagesRedPoint);

        btn_edit_thongtin = (Button) findViewById(R.id.btn_edit_thongtin);
        rv_list_link_project = (RecyclerView) findViewById(R.id.rv_list_link_project);

        //Dang ky cuu tro
        btn_dangky_cuutro = (Button) findViewById(R.id.btn_dangky_cuutro);

        // get token
        token = SharedPref.getInstance(this).getString(ApiConstants.KEY_TOKEN, "");

        loadRedPointDetail();
    }

    private void addEvents() {
        //Chuyen sang man hinh edit redpoint
        btn_edit_thongtin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(RedpointDetailActivity.this, EditRedPointActivity.class));
            }
        });

        //Chuyen sang du an lien ket
        LinearLayoutManager linearLayoutManager1 = new LinearLayoutManager(this);
        rv_list_link_project.setLayoutManager(linearLayoutManager1);
        voluntaryProjectList = new ArrayList<>();
        listLinkProjectAdapter = new ListLinkProjectAdapter(this, voluntaryProjectList);
        rv_list_link_project.setAdapter(listLinkProjectAdapter);
        LinkProjectData();

        //Dang ky cuu tro
        btn_dangky_cuutro.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(RedpointDetailActivity.this, ListProjectSignupActivity.class));
            }
        });

        //Link den trang ca nhan
        tvCreatorRedPoint.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(RedpointDetailActivity.this, UserProfileActivity.class));
            }
        });
    }

    private void setRedPointDetail() {
        PicassoHelper.execPicasso(RedpointDetailActivity.this, redPoint.getImages().get(0), ivImageRedPoint);
        StringUtil.setText(tvTitleRedPoint, redPoint.getTitle());
        StringUtil.setText(tvContentRedPoint, redPoint.getContent());
        StringUtil.setText(tvCreatorRedPoint, redPoint.getCreator().getFirstName() + " " + redPoint.getCreator().getLastName());
        StringUtil.setText(tvCreatedAtRedPoint,
                ConvertTimeHelper.convertDateToString(redPoint.getHappened_at(), ConvertTimeHelper.DATE_FORMAT_2));
        StringUtil.setText(tvLocationRedPoint, redPoint.getLocation());
        StringUtil.setText(tvStatusRedPoint, redPoint.getId_status());
    }

    private void loadRedPointDetail() {
        appRequest = new AppRequest(RedpointDetailActivity.this, Request.Method.GET,
                ApiConstants.getUrl(ApiConstants.API_VOLUNTARY_SPOT_GET), getRedPointDetailParams(), false);
        getRedPointResponse();
    }

    private HashMap getRedPointDetailParams() {
        HashMap<String, String> params = new HashMap<>();
        params.put(ApiConstants.KEY_TOKEN, token);
        params.put(ApiConstants.KEY_ID, idRedPoint);
        return params;
    }

    private void getRedPointResponse() {
        appRequest.setOnAppRequestListener(new AppRequest.OnAppRequestListener() {
            @Override
            public void onAppResponse(JSONObject response) {
                JSONObject data = JsonUtil.getJSONObject(response, ApiConstants.DEF_DATA);

                // Creator
                JSONObject creator = JsonUtil.getJSONObject(data, ApiConstants.KEY_CREATOR);

                // Category
                JSONArray categories = JsonUtil.getJSONArray(data, ApiConstants.KEY_CATEGORY);
                JSONObject category = JsonUtil.getJSONObject(categories, 0);
                String strCategory = JsonUtil.getString(category, ApiConstants.KEY_TITLE, "");

                // Status
                JSONObject status = JsonUtil.getJSONObject(data, ApiConstants.KEY_STATUS);
                String strStatus = JsonUtil.getString(status, ApiConstants.KEY_TITLE, "");

                // Location
                JSONArray location = JsonUtil.getJSONArray(data, ApiConstants.KEY_LOCATION);
                String strLocation = "";
                int locationLength = location.length();
                for (int i = 0; i < locationLength; i++) {
                    JSONObject place = JsonUtil.getJSONObject(location, i);
                    strLocation = JsonUtil.getString(place, ApiConstants.KEY_NAME, "") + " " + strLocation;
                }

                // Images
                JSONArray image = JsonUtil.getJSONArray(data, ApiConstants.KEY_IMAGES);
                List<String> lstImage = new ArrayList<String>();
                int imageLength = image.length();
                for (int i = 0; i < imageLength; i++) {
                    lstImage.add(JsonUtil.getString(image, i, ""));
                }
                redPoint = new RedPoint(
                        JsonUtil.getString(data, ApiConstants.KEY_ID, ""),
                        JsonUtil.getString(data, ApiConstants.KEY_TITLE, ""),
                        JsonUtil.getString(data, ApiConstants.KEY_CONTENT, ""),
                        new User(
                                JsonUtil.getString(creator, ApiConstants.KEY_ID, ""),
                                JsonUtil.getString(creator, ApiConstants.KEY_FIRST_NAME, ""),
                                JsonUtil.getString(creator, ApiConstants.KEY_LAST_NAME, ""),
                                JsonUtil.getString(creator, ApiConstants.KEY_AVATAR, "")
                        ),
                        strLocation,
                        0,
                        strCategory,
                        strStatus,
                        lstImage,
                        "",
                        ConvertTimeHelper.convertISODateToDate(JsonUtil.getString(data, ApiConstants.KEY_HAPPENED_AT, "")),
                        ConvertTimeHelper.convertISODateToDate(JsonUtil.getString(data, ApiConstants.KEY_CREATED_AT, "")),
                        ConvertTimeHelper.convertISODateToDate(JsonUtil.getString(data, ApiConstants.KEY_CREATED_AT, "")),
                        JsonUtil.getBoolean(data, ApiConstants.KEY_VERIFICATION_STATUS, false)
                );
                setRedPointDetail();
            }

            @Override
            public void onAppError(int errorCode, String errorMsg) {
                ToastUtil.showToast(RedpointDetailActivity.this, errorMsg);
            }
        });
    }

    private void LinkProjectData() {
        VoluntaryProject b = new VoluntaryProject("Dự án từ thiện 1");
        voluntaryProjectList.add(b);

        b = new VoluntaryProject("Dự án từ thiện 2");
        voluntaryProjectList.add(b);
        listLinkProjectAdapter.notifyDataSetChanged();
    }
}
