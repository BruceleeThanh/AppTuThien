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
import com.example.vuduc.adapters.ListEditHistoryAdapter;
import com.example.vuduc.adapters.ListLinkRedpointAdapter;
import com.example.vuduc.helpers.ConvertTimeHelper;
import com.example.vuduc.helpers.PicassoHelper;
import com.example.vuduc.libs.ApiConstants;
import com.example.vuduc.model.Image;
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

public class ProjectDetailActivity extends AppCompatActivity {

    Button btn_xemtt_taitro;

    //Danh sach cac diem nong da tai tro
    RecyclerView rv_list_link_redpoint;
    RecyclerView rv_edit_history;
    ListLinkRedpointAdapter listLinkRedpointAdapter;
    List<RedPoint> redPointList;

    //Link trang ca nhan
    TextView txt_link_user_profile;

    TextView txt_project_title, txt_project_content, txt_project_time, txt_project_location, txt_project_money_duKien,
            txt_project_money_thucTe, txt_project_status;

    ImageView img_project_title;
    ImageView img_project_content;


    private String token;
    private String idVoluntaryProject;
    private AppRequest appRequest;
    private VoluntaryProject voluntaryProject;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_project_detail);
        if (getIntent().hasExtra(ApiConstants.KEY_ID)) {
            idVoluntaryProject = getIntent().getStringExtra(ApiConstants.KEY_ID);
        }
        getSupportActionBar().setTitle("Thông tin dự án");
        addControls();
        addEvents();
    }

    private void addEvents() {
        btn_xemtt_taitro.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(ProjectDetailActivity.this, XemTaiTroActivity.class));
            }
        });

        //Chuyen sang diem nong tai tro
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this);
        rv_list_link_redpoint.setLayoutManager(linearLayoutManager);
        redPointList = new ArrayList<>();
        listLinkRedpointAdapter = new ListLinkRedpointAdapter(this, redPointList);
        rv_list_link_redpoint.setAdapter(listLinkRedpointAdapter);
        ListRedpointData();

        rv_edit_history.setLayoutManager(new LinearLayoutManager(this));
        rv_edit_history.setAdapter(new ListEditHistoryAdapter(this));

        //Link den trang ca nhan
        txt_link_user_profile.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(ProjectDetailActivity.this, UserProfileActivity.class));
            }
        });
    }

    private void ListRedpointData() {
        RedPoint a = new RedPoint("Lũ lụt ở Hà Tĩnh");
        redPointList.add(a);
        listLinkRedpointAdapter.notifyDataSetChanged();
    }

    private void addControls() {
        btn_xemtt_taitro = (Button) findViewById(R.id.btn_xemtt_taitro);

        //Danh sach diem nong tai tro
        rv_list_link_redpoint = (RecyclerView) findViewById(R.id.rv_list_link_redpoint);
        rv_edit_history = (RecyclerView) findViewById(R.id.rv_edit_history);

        //Link den trang ca nhan
        txt_link_user_profile = (TextView) findViewById(R.id.txt_link_user_profile);

        txt_project_title = (TextView) findViewById(R.id.txt_project_title);
        txt_project_content = (TextView) findViewById(R.id.txt_project_content);
        txt_project_time = (TextView) findViewById(R.id.txt_project_time);
        txt_project_location = (TextView) findViewById(R.id.txt_project_location);
        txt_project_money_duKien = (TextView) findViewById(R.id.txt_project_money_duKien);
        txt_project_money_thucTe = (TextView) findViewById(R.id.txt_project_money_thucTe);
        txt_project_status = (TextView) findViewById(R.id.txt_project_status);

        img_project_title = (ImageView) findViewById(R.id.img_project_title);
        img_project_content = (ImageView) findViewById(R.id.img_project_content);

        // get token
        token = SharedPref.getInstance(this).getString(ApiConstants.KEY_TOKEN, "");

        loadProjectDetail();
    }

    private void loadProjectDetail() {
        appRequest = new AppRequest(ProjectDetailActivity.this, Request.Method.GET,
                ApiConstants.getUrl(ApiConstants.API_VOLUNTARY_PROJECT_GET), getProjectDetailParams(), false);
        getProjectDetailResponse();
    }

    private HashMap getProjectDetailParams() {
        HashMap<String, String> params = new HashMap<>();
        params.put(ApiConstants.KEY_TOKEN, token);
        params.put(ApiConstants.KEY_ID, idVoluntaryProject);
        return params;
    }

    private void getProjectDetailResponse() {
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
                String strStatus = JsonUtil.getString(data, ApiConstants.KEY_STATUS, "");

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
                if(image != null) {
                    int imageLength = image.length();
                    for (int i = 0; i < imageLength; i++) {
                        lstImage.add(JsonUtil.getString(image, i, ""));
                    }
                }

                voluntaryProject = new VoluntaryProject(
                        JsonUtil.getString(data, ApiConstants.KEY_ID, ""),
                        ConvertTimeHelper.convertISODateToDate(JsonUtil.getString(data, ApiConstants.KEY_CREATED_AT, "")),
                        JsonUtil.getString(data, ApiConstants.KEY_TITLE, ""),
                        JsonUtil.getString(data, ApiConstants.KEY_CONTENT, ""),
                        lstImage,
                        strLocation,
                        strCategory,
                        strStatus,
                        JsonUtil.getString(data, ApiConstants.KEY_MONEY, ""),
                        new User(
                                JsonUtil.getString(creator, ApiConstants.KEY_ID, ""),
                                JsonUtil.getString(creator, ApiConstants.KEY_FIRST_NAME, ""),
                                JsonUtil.getString(creator, ApiConstants.KEY_LAST_NAME, ""),
                                JsonUtil.getString(creator, ApiConstants.KEY_AVATAR, "")
                        )
                );
                setProjectDetail();
            }



            @Override
            public void onAppError(int errorCode, String errorMsg) {
                ToastUtil.showToast(ProjectDetailActivity.this, errorMsg);
            }
        });
    }

    private void setProjectDetail() {
        if (voluntaryProject.getImages() != null && !voluntaryProject.getImages().isEmpty())
            PicassoHelper.execPicasso(ProjectDetailActivity.this, voluntaryProject.getImages().get(0), img_project_title);
        StringUtil.setText(txt_project_title, voluntaryProject.getTitle());
        StringUtil.setText(txt_project_content, voluntaryProject.getContent());
        StringUtil.setText(txt_link_user_profile, voluntaryProject.getCreator().getFirstName() + " " + voluntaryProject.getCreator().getLastName());
        StringUtil.setText(txt_project_time,
                ConvertTimeHelper.convertDateToString(voluntaryProject.getCreated_at(), ConvertTimeHelper.DATE_FORMAT_2));
        StringUtil.setText(txt_project_location, voluntaryProject.getLocation());
        StringUtil.setText(txt_project_status, voluntaryProject.getId_status());
        StringUtil.setText(txt_project_money_thucTe, voluntaryProject.getMoney());
        StringUtil.setText(txt_project_money_duKien, voluntaryProject.getMoney());
    }
}
