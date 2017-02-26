package com.example.vuduc.apptuthien;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.android.volley.Request;
import com.example.vuduc.libs.ApiConstants;
import com.example.vuduc.services.AppRequest;
import com.example.vuduc.utils.JsonUtil;
import com.example.vuduc.utils.SharedPref;
import com.example.vuduc.utils.ToastUtil;

import org.json.JSONObject;

import java.util.HashMap;

public class LoginActivity extends AppCompatActivity {
    private static final int REQUEST_SIGNUP = 0;
    EditText editEmail, editPassword;
    Button btnLogin;
    TextView txtLink_Signup;
    private AppRequest appRequest;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        String token = SharedPref.getInstance(this).getString(ApiConstants.KEY_TOKEN, "");
        if(!token.equals("")){
            startActivity(new Intent(LoginActivity.this, MainActivity.class));
            finish();
        }
        getSupportActionBar().hide();
        addControls();
        addEvents();
    }

    private void addControls() {
        editEmail= (EditText) findViewById(R.id.edit_login_Email);
        editPassword= (EditText) findViewById(R.id.edit_login_Password);
        btnLogin= (Button) findViewById(R.id.btn_login);
        txtLink_Signup= (TextView) findViewById(R.id.txtLink_signup);
    }

    private void addEvents() {
        txtLink_Signup.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent=new Intent(getApplicationContext(),SignupActivity.class);
                startActivityForResult(intent,REQUEST_SIGNUP);
                finish();
            }
        });
        btnLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                login();
            }
        });
    }

    private void login() {
        appRequest = new AppRequest(LoginActivity.this, Request.Method.POST, ApiConstants.getUrl(ApiConstants.API_LOGIN), getParams(), false);
        getResponse();
    }

    private HashMap getParams(){
        HashMap<String, String> params = new HashMap<>();
        params.put(ApiConstants.KEY_PHONE_NUMBER_EMAIL, editEmail.getText().toString());
        params.put(ApiConstants.KEY_PASSWORD, editPassword.getText().toString());
        return params;
    }

    private void getResponse(){
        appRequest.setOnAppRequestListener(new AppRequest.OnAppRequestListener() {
            @Override
            public void onAppResponse(JSONObject response) {
                JSONObject data = JsonUtil.getJSONObject(response, ApiConstants.DEF_DATA);
                String token = JsonUtil.getString(data, ApiConstants.KEY_TOKEN, "");
                String name = JsonUtil.getString(data, ApiConstants.KEY_FIRST_NAME, "");
                SharedPref.getInstance(LoginActivity.this).putString(ApiConstants.KEY_TOKEN, token);
                SharedPref.getInstance(LoginActivity.this).putString(ApiConstants.KEY_FIRST_NAME, name);
                ToastUtil.showToast(LoginActivity.this, "Đăng nhập thành công");
                startActivity(new Intent(LoginActivity.this, MainActivity.class));
                finish();
            }

            @Override
            public void onAppError(int errorCode, String errorMsg) {

            }
        });
    }

}
