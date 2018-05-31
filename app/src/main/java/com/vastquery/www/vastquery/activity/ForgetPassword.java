package com.vastquery.www.vastquery.activity;

import android.app.ProgressDialog;
import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.view.WindowManager;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.Toast;

import com.vastquery.www.vastquery.DatabaseConnection.ConnectionHelper;
import com.vastquery.www.vastquery.DatabaseConnection.GetIntId;
import com.vastquery.www.vastquery.R;

import java.sql.Connection;
import java.sql.PreparedStatement;

public class ForgetPassword extends AppCompatActivity {

    ProgressDialog dialog;
    AutoCompleteTextView mobile,newPassword,confirmpassword;
    Button setpassword;
    String password,number;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_forget_password);

        mobile = findViewById(R.id.mobile);
        newPassword = findViewById(R.id.newpassword);
        confirmpassword = findViewById(R.id.confirmPassword);
        setpassword = findViewById(R.id.setpassword);

        this.getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_HIDDEN);

        setpassword.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                password = newPassword.getText().toString().trim();
                number = mobile.getText().toString().trim();
                if(password.equals(confirmpassword.getText().toString().trim())) {
                    InsertPassword insertPassword = new InsertPassword();
                    insertPassword.execute();
                }else Toast.makeText(ForgetPassword.this,"Password Mismatch",Toast.LENGTH_LONG).show();
            }
        });



    }


    public class InsertPassword extends AsyncTask<String,String,String> {
        int id;
        String message;
        GetIntId userid;

        @Override
        protected void onPreExecute() {
            userid = new GetIntId("select Usr_ID from tblUser where U_Mobile='"+number+"'","Usr_ID");
            dialog =  ProgressDialog.show(ForgetPassword.this,"","Updating...",true);

        }

        @Override
        protected String doInBackground(String... strings) {
            id = userid.getId();
            try {
                ConnectionHelper con = new ConnectionHelper();
                Connection connect = con.connectionclass();// Connect to database
                if (connect == null) {
                    message = "Check Your Internet Access!";
                }else {
                    if(id == 0) message = "Phone Number does not exist";
                    else {
                        String query = "Update tblUser set Pswd='"+ password +"' where U_Mobile='"+number+"'";
                        PreparedStatement preStmt = connect.prepareStatement(query);
                        preStmt.execute();
                        message = "updated successful";
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
            Toast.makeText(ForgetPassword.this,s,Toast.LENGTH_LONG).show();
        }
    }
}
