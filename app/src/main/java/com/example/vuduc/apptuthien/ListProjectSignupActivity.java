package com.example.vuduc.apptuthien;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;

import com.android.volley.Request;
import com.example.vuduc.adapters.ListProjectAdapter;
import com.example.vuduc.adapters.ListProjectSignupAdapter;
import com.example.vuduc.libs.ApiConstants;
import com.example.vuduc.model.VoluntaryProject;
import com.example.vuduc.services.AppRequest;
import com.example.vuduc.utils.JsonUtil;
import com.example.vuduc.utils.RLog;
import com.example.vuduc.utils.SharedPref;
import com.example.vuduc.utils.ToastUtil;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class ListProjectSignupActivity extends AppCompatActivity {

    RecyclerView rvListProject;
    ListProjectSignupAdapter lpaAdapter;
    private List<VoluntaryProject> lstVoluntaryProject;
    private AppRequest appRequest;
    private String token;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_list_project_signup);
        getSupportActionBar().setTitle("Danh sách dự án khả thi");
        addControls();
        addevents();
    }

    private void addevents() {
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this);
        rvListProject.setLayoutManager(linearLayoutManager);
        lpaAdapter = new ListProjectSignupAdapter(this, lstVoluntaryProject);
        rvListProject.setAdapter(lpaAdapter);
        loadProject();
    }

    private void addControls() {
        rvListProject=(RecyclerView) findViewById(R.id.rvListProject);
        lstVoluntaryProject = new ArrayList<>();
        // get token
        token = SharedPref.getInstance(this).getString(ApiConstants.KEY_TOKEN, "");
    }

    private void loadProject() {
        appRequest = new AppRequest(ListProjectSignupActivity.this, Request.Method.GET,
                ApiConstants.getUrl(ApiConstants.API_VOLUNTARY_PROJECT_BROWSE), getProjectParams(), false);
        getProjectResponse();
    }

    private HashMap getProjectParams() {
        HashMap<String, String> params = new HashMap<>();
        params.put(ApiConstants.KEY_TOKEN, token);
        return params;
    }

    private void getProjectResponse() {
        appRequest.setOnAppRequestListener(new AppRequest.OnAppRequestListener() {
            @Override
            public void onAppResponse(JSONObject response) {
                RLog.i(response.toString());
                JSONArray data = JsonUtil.getJSONArray(response, ApiConstants.DEF_DATA);
                int length = data.length();
                for (int i = 0; i < length; i++) {
                    JSONObject item = JsonUtil.getJSONObject(data, i);
                    JSONArray images = JsonUtil.getJSONArray(item, ApiConstants.KEY_IMAGES);
                    List<String> lstImage = new ArrayList<String>();
                    if (images != null) {
                        int lengthImages = images.length();
                        for (int j = 0; j < lengthImages; j++) {
                            lstImage.add(JsonUtil.getString(images, j, ""));
                        }
                    }
                    lstVoluntaryProject.add(new VoluntaryProject(
                            JsonUtil.getString(item, ApiConstants.KEY_ID, ""),
                            JsonUtil.getString(item, ApiConstants.KEY_TITLE, ""),
                            JsonUtil.getString(item, ApiConstants.KEY_CONTENT, ""),
                            lstImage
                    ));
                }
                lpaAdapter.notifyDataSetChanged();
            }

            @Override
            public void onAppError(int errorCode, String errorMsg) {
                ToastUtil.showToast(ListProjectSignupActivity.this, errorMsg);
            }
        });
    }
}
