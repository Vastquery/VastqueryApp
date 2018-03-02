package com.vastquery.www.vastquery.activity;

import android.content.Intent;
import android.graphics.Bitmap;
import android.os.AsyncTask;
import android.support.design.widget.CollapsingToolbarLayout;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.vastquery.www.vastquery.DatabaseConnection.ConnectionHelper;
import com.vastquery.www.vastquery.PropertyClasses.ProfClass;
import com.vastquery.www.vastquery.R;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.Statement;

public class ProfessionalActivity extends AppCompatActivity {

    String name,Address;
    int id;
    TextView phone_number,address,sub_type,vp_id,mail_id;
    Bitmap bitmap;
    ProfClass prof;
    ProgressBar progressBar;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_professional);
        Intent intent = getIntent();
        id = intent.getIntExtra("id",0);
        name = intent.getStringExtra("name");
        Address = intent.getStringExtra("address");
        bitmap = intent.getParcelableExtra("image");


        phone_number = findViewById(R.id.phonenumber);
        vp_id = findViewById(R.id.vp_id);
        address = findViewById(R.id.detail_address);
        sub_type = findViewById(R.id.sub_type);
        mail_id = findViewById(R.id.maild_id);
        progressBar = findViewById(R.id.progressBar_prof);



        Toolbar toolbar = findViewById(R.id.detail_toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        CollapsingToolbarLayout collapsingToolbar = findViewById(R.id.collapsing_toolbar_details);
        collapsingToolbar.setTitle(name);

        loadbackdrop();

        SyncData_prof syncDataProf = new SyncData_prof();
        syncDataProf.execute();
    }
    void loadbackdrop(){
        ImageView prof_image = findViewById(R.id.backdrop_prof);
        prof_image.setImageBitmap(bitmap);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if(item.getItemId() == android.R.id.home){
            onBackPressed();
            return true;
        }
        return super.onOptionsItemSelected(item);
    }



    public class SyncData_prof extends AsyncTask<String,String,String> {

        String ConnectionResult;
        boolean isSuccess;

        @Override
        protected void onPreExecute() {
            progressBar.setVisibility(View.VISIBLE);
        }

        @Override
        protected String doInBackground(String... strings) {

            try {
                ConnectionHelper con = new ConnectionHelper();
                Connection connect = con.connectionclass();// Connect to database
                if (connect == null) {
                    ConnectionResult = "Check Your Internet Access!";
                } else {
                    // Change below query according to your own database.
                    String query = "select Pin_Code,Email,Phone,Mobile1,Mobile2,Website,Sub_Type from tblProffesional where Prof_ID="+id;
                    Statement stmt = connect.createStatement();
                    ResultSet rs = stmt.executeQuery(query);
                    if(rs.next()) {
                        isSuccess = true;
                        prof = new ProfClass(rs.getString("Pin_Code"),rs.getString("Email"),rs.getString("Phone")
                        ,rs.getString("Mobile1"),rs.getString("Mobile2"),rs.getString("Website"),rs.getString("Sub_Type"));
                    }
                    ConnectionResult = "successful";
                    connect.close();
                }
            } catch (Exception ex) {
                ConnectionResult = ex.getMessage();
            }
            return ConnectionResult;
        }

        @Override
        protected void onPostExecute(String s) {
            if(isSuccess){
                progressBar.setVisibility(View.GONE);
                sub_type.setText(prof.getSub_Type());
                address.setText(Address+"\n"+prof.getPin_Code()+"\n"+prof.getWebsite());
                phone_number.setText(prof.getPhone()+"\n"+prof.getMobile1()+"\n"+prof.getMobile2());
                mail_id.setText(prof.getEmail());

            }
            else Toast.makeText(ProfessionalActivity.this,s,Toast.LENGTH_LONG).show();
        }

    }


}
