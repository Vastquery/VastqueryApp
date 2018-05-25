package com.vastquery.www.vastquery.activity;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.view.WindowManager;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.vastquery.www.vastquery.DatabaseConnection.ConnectionHelper;
import com.vastquery.www.vastquery.DatabaseConnection.GetId;
import com.vastquery.www.vastquery.R;
import com.vastquery.www.vastquery.helper.PrefManager;

import java.sql.Connection;
import java.sql.PreparedStatement;

import static com.vastquery.www.vastquery.activity.postShopForm.setDistrictState;

public class RegisterActivity extends AppCompatActivity implements View.OnClickListener {

    private static String TAG = RegisterActivity.class.getSimpleName();
    private Button signup;
    private AutoCompleteTextView inputName, inputEmail, inputMobile, password, confirmpassword,town;
    private PrefManager pref;
    private ProgressDialog dialog;
    public String name,email,mobile,pwd,cpwd,userdistrict,userstate,usertown;
    public Spinner user_district,user_state;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);


        inputName =  findViewById(R.id.inputName);
        inputEmail =  findViewById(R.id.inputEmail);
        inputMobile =  findViewById(R.id.inputMobile);
        signup =  findViewById(R.id.signup);
        password = findViewById(R.id.userPassword);
        confirmpassword = findViewById(R.id.userCPassword);
        user_district = findViewById(R.id.user_district);
        user_state = findViewById(R.id.user_state);
        town = findViewById(R.id.town);

        this.getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_HIDDEN);

        pref = new PrefManager(this);

        setDistrictState(RegisterActivity.this,user_state,user_district);


        //view click listener
        signup.setOnClickListener(this);

    }

    @Override
    public void onClick(View view) {

        switch (view.getId()) {
            case R.id.signup:
                validateForm();
                break;
        }
    }


    /**
     * Validating user details form
     */
    private void validateForm() {
        name = inputName.getText().toString().trim();
        email = inputEmail.getText().toString().trim();
        mobile = inputMobile.getText().toString().trim();
        pwd = password.getText().toString().trim();
        cpwd = confirmpassword.getText().toString().trim();
        userdistrict = user_district.getSelectedItemPosition()+"";
        userstate = user_state.getSelectedItem().toString().trim();
        usertown = town.getText().toString().trim();

        // validating empty name and email
        if (name.length() == 0 || email.length() == 0) {
            Toast.makeText(getApplicationContext(), "Please enter your details", Toast.LENGTH_SHORT).show();
            return;
        }

        // validating mobile number
        // it should be of 10 digits length
        if (isValidPhoneNumber(mobile) && isValidEmail(email) && pwd.equals(cpwd)) {

            // saving the mobile number in shared preferences
            pref.setMobileNumber(mobile);
            Signup signup = new Signup();
            signup.execute();

        }else{
            Toast.makeText(getApplicationContext(), "Please enter valid information", Toast.LENGTH_SHORT).show();
        }

    }


    /**
     * Regex to validate the mobile number
     * mobile number should be of 10 digits length
     *
     * @param mobile
     * @return
     */
    public static boolean isValidPhoneNumber(String mobile) {
        String regEx = "^[0-9]{10}$";
        return mobile.matches(regEx);
    }

    public static boolean isValidEmail(String email){
        String regEx = "^[_A-Za-z0-9-\\+]+(\\.[_A-Za-z0-9-]+)*@"+"[_A-Za-z0-9-]+(\\.[A-Za-z0-9]+)*(\\.[A-Za-z]{2,})$";
        return email.matches(regEx);
    }

    public class Signup extends AsyncTask<String,String,String>{
        String message,presence;
        boolean success;
        GetId getId;
        @Override
        protected void onPreExecute() {
            getId = new GetId("Select U_Mobile from tblUser where U_Mobile ='"+mobile+"'","U_Mobile");
            dialog =  ProgressDialog.show(RegisterActivity.this,"","Signing...",true);
        }

        @Override
        protected String doInBackground(String... strings) {
            presence = getId.getS_Id();
            try {
                ConnectionHelper con = new ConnectionHelper();
                Connection connect = con.connectionclass();// Connect to database
                if (connect == null) {
                    message = "Check Your Internet Access!";
                } else {
                    if ( presence.equals("notexist")) message = "Phone Number exist";
                    else {
                        String query = "Insert into tblUser(U_Name,Email,Pswd,U_Mobile,U_District,U_TownVillage,U_State,U_Terms," +
                                "U_Type) values ('" + name + "','" + email + "','" + pwd + "','" + mobile + "','" + userdistrict + "','" + usertown + "'" +
                                ",'" + userstate + "','Y','O')";
                        PreparedStatement preStmt = connect.prepareStatement(query);
                        preStmt.execute();
                        message = "Signed successful";
                        connect.close();
                    }
                }
            }catch (Exception ex){
                message = ex.getMessage();
            }
            return message;
        }

        @Override
        protected void onPostExecute(String s) {
            dialog.dismiss();
            Toast.makeText(RegisterActivity.this,s,Toast.LENGTH_LONG).show();
            startActivity(new Intent(RegisterActivity.this,LoginActivity.class));
        }
    }
}
