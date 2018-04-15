package com.vastquery.www.vastquery.activity;

import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.CardView;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.vastquery.www.vastquery.DatabaseConnection.ConnectionHelper;
import com.vastquery.www.vastquery.PropertyClasses.ClassListItems;
import com.vastquery.www.vastquery.R;
import com.vastquery.www.vastquery.helper.SimpleStringRecyclerViewAdapter;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by pooja on 13/3/18.
 */

public class ServiceList extends AppCompatActivity {

    Toolbar toolbar;
    public List<ClassListItems> itemArrayList;
    RecyclerView recyclerView;
    LinearLayoutManager linearLayoutManager;
    int facility_id;
    ProgressBar progressBar;
    TextView addservice;
    CardView addcard;
    String cat_id,facilitydesc;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_service);

        facility_id = getIntent().getIntExtra("facility",0);
        cat_id = getIntent().getStringExtra("catId");
        facilitydesc = getIntent().getStringExtra("facilitydesc");

        toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        addcard = findViewById(R.id.addcard);
        progressBar = findViewById(R.id.progressBar);
        addservice = findViewById(R.id.addservice);
        addservice.setText("Add Your Service");

        addcard.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(ServiceList.this, ServicePostForm.class);
                intent.putExtra("catId",cat_id);
                intent.putExtra("facilitydesc",facilitydesc);
                intent.putExtra("new",false);
                startActivity(intent);
            }
        });
        recyclerView = findViewById(R.id.recyclerview);
        linearLayoutManager = new LinearLayoutManager(ServiceList.this);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(linearLayoutManager);
        itemArrayList = new ArrayList<>();

        SyncFacilityData syncData = new SyncFacilityData();
        syncData.execute();
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

    public class SyncFacilityData extends AsyncTask<String,String,String> {

        String ConnectionResult;
        boolean isSuccess,isShop=false;

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
                    String query = "select tblSubCategory.Group_Id,tblSubCategory.SubCategory_Id,SubCategory_Name,SubCategory_Address,SubCategory_Logo,SubCategory_Addby from tblSubCategory join tblSubCategoryFacility" +
                            " on tblSubCategory.SubCategory_Id = tblSubCategoryFacility.SubCategory_Id" +
                            " where tblSubCategoryFacility.Facility_Id ="+facility_id;
                    Statement stmt = connect.createStatement();
                    ResultSet rs = stmt.executeQuery(query);
                    if(rs.next()) {
                        do {
                            itemArrayList.  add(new ClassListItems(rs.getString("Group_Id"),rs.getString("SubCategory_Id"),rs.getString("SubCategory_Name"),rs.getString("SubCategory_Address"), rs.getBytes("SubCategory_Logo"),rs.getInt("SubCategory_Addby")));
                        }while (rs.next());

                    /* Change below query according to your own database.
                    String query = "select Prof_ID,Prof_Name,Address,Logo from tblProffesional  where Prof_Type='"+requested_list+"'";
                    Statement stmt = connect.createStatement();
                    ResultSet rs = stmt.executeQuery(query);
                    if(rs.next()) {
                        do {
                            itemArrayList.  add(new ClassListItems(rs.getInt("Prof_ID"),rs.getString("Prof_Name"),rs.getString("Address"), rs.getBytes("Logo")));
                        }while (rs.next());
                    }else{
                        String shop_query = "select S_ID,S_Name,Address,Shop_Photo from tblShop where S_Type='"+requested_list+"'";
                        Statement shop_stmt = connect.createStatement();
                        ResultSet shop_rs = shop_stmt.executeQuery(shop_query);
                        while (shop_rs.next()) {
                            itemArrayList.add(new ClassListItems(shop_rs.getInt("S_ID"),shop_rs.getString("S_Name"),shop_rs.getString("Address"), shop_rs.getBytes("Shop_Photo")));
                        }*/

                    }
                    ConnectionResult = "successful";
                    isSuccess = true;
                    connect.close();
                }
            } catch (Exception ex) {
                ConnectionResult = ex.getMessage();
            }
            return ConnectionResult;
        }

        @Override
        protected void onPostExecute(String s) {
            progressBar.setVisibility(View.GONE);

            if(isSuccess){
                SimpleStringRecyclerViewAdapter myAdapter = new SimpleStringRecyclerViewAdapter(ServiceList.this, itemArrayList);
                recyclerView.setAdapter(myAdapter);
            }
            else {
                Toast.makeText(ServiceList.this,s,Toast.LENGTH_LONG).show();

            }
        }

    }
}
