package com.vastquery.www.vastquery.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.vastquery.www.vastquery.R;
import com.vastquery.www.vastquery.helper.PrefManager;

public class RegisterActivity extends AppCompatActivity implements View.OnClickListener {

    private static String TAG = RegisterActivity.class.getSimpleName();
    private ViewPager viewPager;
    private ViewPagerAdapter adapter;
    private Button btnRequestSms, btnVerifyOtp;
    private AutoCompleteTextView inputName, inputEmail, inputMobile, inputOtp, password, confirmpassword;
    private ProgressBar progressBar;
    private PrefManager pref;
    private ImageButton btnEditMobile;
    private TextView txtEditMobile;
    private LinearLayout layoutEditMobile;


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);
        getWindow().setBackgroundDrawableResource(R.drawable.background);

        viewPager =  findViewById(R.id.viewPagerVertical);
        inputName =  findViewById(R.id.inputName);
        inputEmail =  findViewById(R.id.inputEmail);
        inputMobile =  findViewById(R.id.inputMobile);
        inputOtp =  findViewById(R.id.inputOtp);
        btnRequestSms =  findViewById(R.id.btn_request_sms);
        btnVerifyOtp =  findViewById(R.id.btn_verify_otp);
        progressBar =  findViewById(R.id.progressBar);
        btnEditMobile =  findViewById(R.id.btn_edit_mobile);
        txtEditMobile =  findViewById(R.id.txt_edit_mobile);
        layoutEditMobile =  findViewById(R.id.layout_edit_mobile);
        password = findViewById(R.id.userPassword);
        confirmpassword = findViewById(R.id.userCPassword);

        //view click listener
        btnEditMobile.setOnClickListener(this);
        btnRequestSms.setOnClickListener(this);
        btnVerifyOtp.setOnClickListener(this);

        //
        layoutEditMobile.setVisibility(View.GONE);

        pref = new PrefManager(this);

        // Checking for user session
        // if user is already logged in, take him to main activity
        if (pref.isLoggedIn()) {
            Intent intent = new Intent(RegisterActivity.this, MainActivity.class);
            intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TOP);
            startActivity(intent);
            finish();
        }

        adapter = new ViewPagerAdapter();
        viewPager.setAdapter(adapter);
        viewPager.setOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

            }

            @Override
            public void onPageSelected(int position) {

            }

            @Override
            public void onPageScrollStateChanged(int state) {

            }
        });

        /**
         * Checking if the device is waiting for sms
         * showing the user OTP screen
         */
        if (pref.isWaitingForSms()) {
            viewPager.setCurrentItem(1);
            layoutEditMobile.setVisibility(View.VISIBLE);
        }


    }

    @Override
    public void onClick(View view) {

        switch (view.getId()) {
            case R.id.btn_request_sms:
                validateForm();
                break;

            case R.id.btn_verify_otp:
                verifyOtp();
                break;

            case R.id.btn_edit_mobile:
                viewPager.setCurrentItem(0);
                layoutEditMobile.setVisibility(View.GONE);
                progressBar.setVisibility(View.GONE);
                pref.setIsWaitingForSms(false);
                break;
        }
    }


    /**
     * Validating user details form
     */
    private void validateForm() {
        String name = inputName.getText().toString().trim();
        String email = inputEmail.getText().toString().trim();
        String mobile = inputMobile.getText().toString().trim();
        String pwd = password.getText().toString().trim();
        String cpwd = confirmpassword.getText().toString().trim();

        // validating empty name and email
        if (name.length() == 0 || email.length() == 0) {
            Toast.makeText(getApplicationContext(), "Please enter your details", Toast.LENGTH_SHORT).show();
            return;
        }

        // validating mobile number
        // it should be of 10 digits length
        if (isValidPhoneNumber(mobile) && isValidEmail(email) && pwd.equals(cpwd)) {

            // request for sms
            progressBar.setVisibility(View.VISIBLE);

            // saving the mobile number in shared preferences
            pref.setMobileNumber(mobile);

            //request sms
            requestForSMS(name,email,mobile);

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
    private static boolean isValidPhoneNumber(String mobile) {
        String regEx = "^[0-9]{10}$";
        return mobile.matches(regEx);
    }

    private static boolean isValidEmail(String email){
        String regEx = "^[_A-Za-z0-9-\\+]+(\\.[_A-Za-z0-9-]+)*@"+"[_A-Za-z0-9-]+(\\.[A-Za-z0-9]+)*(\\.[A-Za-z]{2,})$";
        return email.matches(regEx);
    }

    //request sms
    private void requestForSMS(final String name, final String email, final String mobile){
            // checking for error, if not error SMS is initiated
            // device should receive it shortly
            // boolean flag saying device is waiting for sms
            pref.setIsWaitingForSms(true);

            // moving the screen to next pager item i.e otp screen
            viewPager.setCurrentItem(1);
            txtEditMobile.setText(pref.getMobileNumber());
            layoutEditMobile.setVisibility(View.VISIBLE);

            Toast.makeText(getApplicationContext(), "sms is send to you", Toast.LENGTH_SHORT).show();

        }



    //verify otp
    /**
     * sending the OTP to server and activating the user
     */
    private void verifyOtp() {
        String otp = inputOtp.getText().toString().trim();

        if (!otp.isEmpty()) {
            Intent intent = new Intent(getApplicationContext(), MainActivity.class);
            //grapprIntent.putExtra("otp", otp);
            //startService(grapprIntent);
            intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TOP);
            startActivity(intent);
            finish();
        } else {
            Toast.makeText(getApplicationContext(), "Please enter the OTP", Toast.LENGTH_SHORT).show();
        }
    }



    //Adapter class
    class ViewPagerAdapter extends PagerAdapter {

        @Override
        public int getCount() {
            return 2;
        }

        @Override
        public boolean isViewFromObject(View view, Object object) {
            return view == ( object);
        }

        public Object instantiateItem(View collection, int position) {

            int resId = 0;
            switch (position) {
                case 0:
                    resId = R.id.layout_sms;
                    break;
                case 1:
                    resId = R.id.layout_otp;
                    break;
            }
            return findViewById(resId);
        }
    }

}
