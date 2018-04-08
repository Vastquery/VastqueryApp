package com.vastquery.www.vastquery.activity;

import android.app.ProgressDialog;
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

import com.bumptech.glide.Glide;
import com.vastquery.www.vastquery.DatabaseConnection.AddToGroup;
import com.vastquery.www.vastquery.DatabaseConnection.ConnectionHelper;
import com.vastquery.www.vastquery.DatabaseConnection.GetContact;
import com.vastquery.www.vastquery.PropertyClasses.ContactInfo;
import com.vastquery.www.vastquery.PropertyClasses.ProfClass;
import com.vastquery.www.vastquery.R;
import com.vastquery.www.vastquery.helper.PrefManager;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.HashMap;

public class ProfessionalActivity extends AppCompatActivity {

    String id,group_id;
    int user_id;
    TextView phone_number,address,sub_type,vp_id,mail_id,decription;
    ImageView group;

    ProfClass prof;
    ProgressDialog dialog;
    CollapsingToolbarLayout collapsingToolbar;
    ContactInfo info;
    PrefManager pref;
    HashMap<String,String> profile;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_professional);
        Intent intent = getIntent();
        id = intent.getStringExtra("id");
        group_id = intent.getStringExtra("group_id");
        /*name = intent.getStringExtra("name");
        Address = intent.getStringExtra("address");
        bitmap = intent.getParcelableExtra("image");*/

        phone_number = findViewById(R.id.phonenumber);
        vp_id = findViewById(R.id.vp_id);
        address = findViewById(R.id.detail_address);
        sub_type = findViewById(R.id.sub_type);
        mail_id = findViewById(R.id.maild_id);
        decription = findViewById(R.id.description);
        group = findViewById(R.id.group);

        info = new ContactInfo();
        Toolbar toolbar = findViewById(R.id.detail_toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        pref = new PrefManager(ProfessionalActivity.this);
        profile = pref.getUserDetails();
        user_id = Integer.parseInt(profile.get("id"));
        collapsingToolbar = findViewById(R.id.collapsing_toolbar_details);

        group.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(prof.getAddedby() != user_id) {
                    AddToGroup addToGroup = new AddToGroup(ProfessionalActivity.this, id, group_id, user_id);
                    addToGroup.execute();
                }else
                    Toast.makeText(ProfessionalActivity.this,"you are the owner",Toast.LENGTH_LONG).show();

            }
        });

        SyncData_prof syncDataProf = new SyncData_prof();
        syncDataProf.execute();
    }
    void loadbackdrop(byte[] bytes){
        ImageView prof_image = findViewById(R.id.backdrop_prof);
        Glide.with(this).load(bytes).asBitmap().centerCrop().into(prof_image);
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
            dialog =  ProgressDialog.show(ProfessionalActivity.this,"","Loading...",true);
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

                    GetContact contact = new GetContact(id,ProfessionalActivity.this);
                    info = contact.getInfo();
                    String query = "select SubCategory_Name,SubCategory_Address,SubCategory_Pincode,SubCategory_City,SubCategory_Logo,RollNo,SubCategory_Description,SubCategory_Addby,tblSubcategorySkills.Skill_desc as skill from tblSubCategory join tblSubcategorySkills on " +
                            "tblSubCategory.SubCategory_Id=tblSubcategorySkills.SubCategory_Id  where tblSubCategory.SubCategory_Id='"+id+"'";
                    Statement stmt = connect.createStatement();
                    ResultSet rs = stmt.executeQuery(query);
                    if(rs.next()) {
                        isSuccess = true;
                        prof = new ProfClass(rs.getString("SubCategory_Name"),rs.getString("SubCategory_Address"),rs.getString("SubCategory_Pincode")
                        ,rs.getString("SubCategory_City"),rs.getBytes("SubCategory_Logo"),rs.getString("RollNo"),rs.getString("SubCategory_Description"), rs.getInt("SubCategory_Addby"),rs.getString("skill"));
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
                dialog.dismiss();
                collapsingToolbar.setTitle(prof.getName());
                sub_type.setText(prof.getCode());
                address.setText(prof.getAddress()+"\n"+prof.getCity()+"\n"+prof.getPin_Code()+"\n"+prof.getSkills());
                decription.setText(prof.getDescription());
                phone_number.setText(info.getMobile());
                mail_id.setText(info.getEmail()+"\n"+info.getWebsite()+"\nwhatsapp:"+info.getWhatsapp());
                loadbackdrop(prof.getPhoto());
            }
            else {
                Toast.makeText(ProfessionalActivity.this,id,Toast.LENGTH_LONG).show();
            }
        }

    }


}
