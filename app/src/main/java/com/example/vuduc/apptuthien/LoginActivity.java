package com.example.vuduc.apptuthien;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

public class LoginActivity extends AppCompatActivity {
    private static final int REQUEST_SIGNUP = 0;
    EditText editEmail, editPassword;
    Button btnLogin;
    TextView txtLink_Signup;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
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
        startActivity(new Intent(LoginActivity.this, MainActivity.class));
        finish();
    }

}
