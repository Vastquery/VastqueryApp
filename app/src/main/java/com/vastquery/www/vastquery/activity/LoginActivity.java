package com.vastquery.www.vastquery.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.view.WindowManager;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.TextView;

import com.vastquery.www.vastquery.R;


public class LoginActivity extends AppCompatActivity implements View.OnClickListener{

    private Button login_Button;
    private TextView goto_Register;
    private AutoCompleteTextView phone_number,user_password;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        //getWindow().setBackgroundDrawableResource(R.drawable.background);
        this.getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_HIDDEN);

        //initialization
        login_Button =  findViewById(R.id.login_button);
        goto_Register = findViewById(R.id.Goto_Register);
        phone_number = findViewById(R.id.Phone_number);
        user_password = findViewById(R.id.userPassword);
        login_Button.setOnClickListener(this);
        goto_Register.setOnClickListener(this);

    }

    @Override
    public void onClick(View view) {
        switch (view.getId()){
            case R.id.login_button:
                startActivity(new Intent(LoginActivity.this,BottomNavi.class));
                finish();
                break;
            case R.id.Goto_Register:
                startActivity(new Intent(LoginActivity.this,RegisterActivity.class));
                break;
            default:

        }
    }
}
