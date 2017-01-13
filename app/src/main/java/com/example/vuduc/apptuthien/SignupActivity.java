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

public class SignupActivity extends AppCompatActivity {

    EditText editEmail, editPhone, editPassword1, editPassword2, editFirstName, editLastName;
    Button btnSignUp;
    TextView txt_Link_Login;

    Spinner spinner_signup_LoaiAcc;
    String spinArr[]={"Hội chữ thập đỏ", "Hội nhóm từ thiện", "Chính quyền địa phương", "Khách"};
    String typeAccount="";
    ArrayAdapter<String> spinAdapter;
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
    }

    private void addControls() {
        editEmail= (EditText) findViewById(R.id.edit_Signup_Email);
        editPhone= (EditText) findViewById(R.id.edit_Signup_Phone);
        editPassword1= (EditText) findViewById(R.id.edit_Signup_Password1);
        editPassword2= (EditText) findViewById(R.id.edit_Signup_Password2);
        editFirstName= (EditText) findViewById(R.id.edit_Signup_FirstName);
        editLastName= (EditText) findViewById(R.id.edit_Signup_LastName);
        btnSignUp= (Button) findViewById(R.id.btn_Signup);
        txt_Link_Login= (TextView) findViewById(R.id.txt_Link_Login);

        spinner_signup_LoaiAcc = (Spinner) findViewById(R.id.spinner_signup_LoaiAcc);
        spinAdapter=new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item, spinArr);
        spinAdapter.setDropDownViewResource(android.R.layout.simple_list_item_single_choice);
        spinner_signup_LoaiAcc.setAdapter(spinAdapter);
    }
}
