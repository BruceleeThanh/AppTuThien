package com.example.vuduc.apptuthien;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutCompat;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.View;

import com.android.volley.Request;
import com.example.vuduc.adapters.ListRedPointAdapter;
import com.example.vuduc.libs.ApiConstants;
import com.example.vuduc.model.RedPoint;
import com.example.vuduc.services.AppRequest;
import com.example.vuduc.utils.JsonUtil;
import com.example.vuduc.utils.RLog;
import com.example.vuduc.utils.SharedPref;
import com.example.vuduc.utils.ToastUtil;
import com.github.clans.fab.FloatingActionButton;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class ListRedPointActivity extends AppCompatActivity {

    private List<RedPoint> lstRedPoint = null;
    private AppRequest appRequest = null;
    private String token = null;

    RecyclerView rvListRedPoint;
    ListRedPointAdapter listRedPointAdapter;
    com.github.clans.fab.FloatingActionButton fabCreateRedpoint;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_list_red_point);
        getSupportActionBar().setTitle("Danh sách điểm nóng");
        addControls();
        addEvents();
    }

    private void addControls() {
        rvListRedPoint = (RecyclerView) findViewById(R.id.rvListRedPoint);
        //Tao moi diem nong
        fabCreateRedpoint = (com.github.clans.fab.FloatingActionButton) findViewById(R.id.fabCreateRedPoint);

        // get token
        token = SharedPref.getInstance(this).getString(ApiConstants.KEY_TOKEN, "");
        lstRedPoint = new ArrayList<RedPoint>();
    }

    private void addEvents() {
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this);
        rvListRedPoint.setLayoutManager(linearLayoutManager);
        rvListRedPoint.setItemAnimator(new DefaultItemAnimator());
        listRedPointAdapter = new ListRedPointAdapter(this, lstRedPoint);
        rvListRedPoint.setAdapter(listRedPointAdapter);

        //Tao moi diem nong
        fabCreateRedpoint.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(ListRedPointActivity.this, CreateRedPontActivity.class));
            }
        });
        loadRedPoints();
    }

    private void loadRedPoints() {
        appRequest = new AppRequest(ListRedPointActivity.this, Request.Method.GET,
                ApiConstants.getUrl(ApiConstants.API_VOLUNTARY_SPOT_BROWSE), getRedPointsParams(), false);
        getRedPointsResponse();
    }

    private HashMap getRedPointsParams() {
        HashMap<String, String> params = new HashMap<>();
        params.put(ApiConstants.KEY_TOKEN, token);
        return params;
    }

    private void getRedPointsResponse() {
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
                    lstRedPoint.add(new RedPoint(
                            JsonUtil.getString(item, ApiConstants.KEY_ID, ""),
                            JsonUtil.getString(item, ApiConstants.KEY_TITLE, ""),
                            JsonUtil.getString(item, ApiConstants.KEY_CONTENT, ""),
                            lstImage
                    ));
                }
                listRedPointAdapter.notifyDataSetChanged();
            }

            @Override
            public void onAppError(int errorCode, String errorMsg) {
                ToastUtil.showToast(ListRedPointActivity.this, errorMsg);
            }
        });
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.toolbar_search, menu);
        return true;
    }
}
