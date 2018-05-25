package com.vastquery.www.vastquery.activity;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.view.WindowManager;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.vastquery.www.vastquery.DatabaseConnection.ConnectionHelper;
import com.vastquery.www.vastquery.R;
import com.vastquery.www.vastquery.helper.PrefManager;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.Statement;

import static com.vastquery.www.vastquery.activity.RegisterActivity.isValidPhoneNumber;


public class LoginActivity extends AppCompatActivity implements View.OnClickListener{

    private Button login_Button;
    private TextView register,forgetPassword;
    private AutoCompleteTextView phone_number,user_password;
    String number,password;
    PrefManager pref;
    ProgressDialog dialog;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        //getWindow().setBackgroundDrawableResource(R.drawable.background);
        this.getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_HIDDEN);

        //initialization
        login_Button =  findViewById(R.id.login_button);
        forgetPassword = findViewById(R.id.forgetPassword);
        register = findViewById(R.id.register);
        phone_number = findViewById(R.id.Phone_number);
        user_password = findViewById(R.id.userPassword);

        pref = new PrefManager(this);

        // Checking for user session
        // if user is already logged in, take him to main activity
        if (pref.isLoggedIn()) {
            Intent intent = new Intent(LoginActivity.this, BottomNavi.class);
            intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TOP);
            startActivity(intent);
            finish();
        }


        forgetPassword.setOnClickListener(this);
        login_Button.setOnClickListener(this);
        register.setOnClickListener(this);

    }

    @Override
    public void onClick(View view) {
        switch (view.getId()){
            case R.id.login_button:
                number = phone_number.getText().toString();
                password = user_password.getText().toString();
                pref = new PrefManager(LoginActivity.this);
                if( password.length() == 0 || !isValidPhoneNumber(number)){
                    Toast.makeText(LoginActivity.this,"Enter correct Details",Toast.LENGTH_LONG).show();
                }else {
                    loginAuthentication login = new loginAuthentication();
                    login.execute();
                }
                break;
            case R.id.forgetPassword:
                startActivity(new Intent(LoginActivity.this,ForgetPassword.class));
                break;
            case R.id.register:
                startActivity(new Intent(LoginActivity.this,RegisterActivity.class));
                break;
            default:

        }
    }


    public class loginAuthentication extends AsyncTask<String,String,String> {

        String ConnectionResult;
        boolean isSuccess;

        @Override
        protected void onPreExecute() {
            dialog =  ProgressDialog.show(LoginActivity.this,"","Verifying...",true);

        }

        @Override
        protected String doInBackground(String... strings) {

            try {
                ConnectionHelper con = new ConnectionHelper();
                Connection connect = con.connectionclass();// Connect to database
                if (connect == null) {
                    ConnectionResult = "Check Your Internet Access!";
                } else {
                    ConnectionResult = "user does not exist";
                    String query = "select Usr_ID,U_Name,Email,U_Mobile from tblUser where U_Mobile='"+number+"' " +
                            "and Pswd='"+password+"'";
                    Statement stmt = connect.createStatement();
                    ResultSet rs = stmt.executeQuery(query);
                    if(rs.next()) {
                        isSuccess = true;
                        pref.createLogin(rs.getInt("Usr_ID")+"",rs.getString("U_Name"),rs.getString("Email")
                                ,rs.getString("U_Mobile"));
                    }

                    connect.close();
                }
            } catch (Exception ex) {
                ConnectionResult = ex.getMessage();
            }
            return ConnectionResult;
        }

        @Override
        protected void onPostExecute(String s) {
            dialog.dismiss();
            if(isSuccess){
                startActivity(new Intent(LoginActivity.this,BottomNavi.class));
                finish();
            }
            else {
                Toast.makeText(LoginActivity.this,s,Toast.LENGTH_LONG).show();
            }
        }

    }

}
