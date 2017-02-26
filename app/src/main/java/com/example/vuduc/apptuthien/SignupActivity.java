package com.example.vuduc.apptuthien;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;

import com.android.volley.Request;
import com.example.vuduc.libs.ApiConstants;
import com.example.vuduc.services.AppRequest;
import com.example.vuduc.utils.ToastUtil;

import org.json.JSONObject;

import java.util.HashMap;

public class SignupActivity extends AppCompatActivity {

    EditText editPhoneEmail, editPassword1, editPassword2, editName, edit_Signup_Address;
    Button btnSignUp;
    TextView txt_Link_Login;

    Spinner spinner_signup_LoaiAcc;
    String spinArr[]={"Tài khoản thường", "Chính quyền địa phương", "Hội chữ thập đỏ", "Hội nhóm từ thiện"};
    String typeAccount="";
    ArrayAdapter<String> spinAdapter;

    private AppRequest appRequest;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_signup);
        getSupportActionBar().hide();
        addControls();
        addEvents();
    }

    private void addEvents() {
        txt_Link_Login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent=new Intent(getApplicationContext(),LoginActivity.class);
                startActivity(intent);
                finish();
            }
        });

        spinner_signup_LoaiAcc.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                typeAccount= spinArr[i];
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {
                typeAccount="";
            }
        });

        btnSignUp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                signUp();
            }
        });
    }

    private void addControls() {
        editPhoneEmail= (EditText) findViewById(R.id.edit_Signup_Phone_Email);
        editPassword1= (EditText) findViewById(R.id.edit_Signup_Password1);
        editPassword2= (EditText) findViewById(R.id.edit_Signup_Password2);
        editName= (EditText) findViewById(R.id.edit_Signup_Name);
        btnSignUp= (Button) findViewById(R.id.btn_Signup);
        txt_Link_Login= (TextView) findViewById(R.id.txt_Link_Login);

        spinner_signup_LoaiAcc = (Spinner) findViewById(R.id.spinner_signup_LoaiAcc);
        spinAdapter=new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item, spinArr);
        spinAdapter.setDropDownViewResource(android.R.layout.simple_list_item_single_choice);
        spinner_signup_LoaiAcc.setAdapter(spinAdapter);
    }

    private void signUp(){
        appRequest = new AppRequest(SignupActivity.this, Request.Method.POST, ApiConstants.getUrl(ApiConstants.API_SIGN_UP), getParams(), false);
        getResponse();
    }

    private HashMap getParams(){
        HashMap<String, String> params = new HashMap<>();
        params.put(ApiConstants.KEY_PHONE_NUMBER_EMAIL, editPhoneEmail.getText().toString());
        params.put(ApiConstants.KEY_PASSWORD, editPassword1.getText().toString());
        params.put(ApiConstants.KEY_FIRST_NAME, editName.getText().toString());
        params.put(ApiConstants.KEY_LAST_NAME, "");
        params.put(ApiConstants.KEY_ROLE, String.valueOf(spinner_signup_LoaiAcc.getSelectedItemPosition() + 1));
        return params;
    }

    private void getResponse(){
        appRequest.setOnAppRequestListener(new AppRequest.OnAppRequestListener() {
            @Override
            public void onAppResponse(JSONObject response) {
                ToastUtil.showToast(SignupActivity.this, "Đăng ký thành công.");
                Intent intent=new Intent(getApplicationContext(),LoginActivity.class);
                startActivity(intent);
                finish();
            }

            @Override
            public void onAppError(int errorCode, String errorMsg) {
                ToastUtil.showToast(SignupActivity.this, errorMsg);
            }
        });
    }
}
