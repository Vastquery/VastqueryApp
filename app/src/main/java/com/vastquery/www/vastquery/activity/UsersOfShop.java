package com.vastquery.www.vastquery.activity;

import android.content.Context;
import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.vastquery.www.vastquery.DatabaseConnection.ConnectionHelper;
import com.vastquery.www.vastquery.PropertyClasses.ClassListItems;
import com.vastquery.www.vastquery.PropertyClasses.UserDetails;
import com.vastquery.www.vastquery.R;
import com.vastquery.www.vastquery.helper.ChatListAdapter;
import com.vastquery.www.vastquery.helper.PrefManager;
import com.vastquery.www.vastquery.helper.UserListAdapter;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class UsersOfShop extends AppCompatActivity {

    ProgressBar progressBar;
    RecyclerView recyclerview;
    LinearLayoutManager linearLayoutManager;
    PrefManager pref;
    HashMap<String,String> profile;

    String shop_id;
    public List<UserDetails> itemArrayList;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_required_list);

        shop_id = getIntent().getStringExtra("cat_id");

        Toolbar toolbar = findViewById(R.id.toolbar_requiredlist);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        itemArrayList = new ArrayList<>();
        progressBar = findViewById(R.id.progressBar);
        recyclerview = findViewById(R.id.recyclerview_requiredlist);

        recyclerview = findViewById(R.id.recyclerview_requiredlist);
        linearLayoutManager = new LinearLayoutManager(UsersOfShop.this);
        recyclerview.setHasFixedSize(true);
        recyclerview.setLayoutManager(linearLayoutManager);

        SyncData_Users syncData_users = new SyncData_Users();
        syncData_users.execute();


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


    public class SyncData_Users extends AsyncTask<String,String,String> {

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
                    isSuccess = true;
                    String query = "select Usr_ID,U_Name,U_Mobile from tblUser join tblCategoryCustomer on " +
                            "tblUser.Usr_ID = tblCategoryCustomer.User_Id where tblCategoryCustomer.Shop_Id='"+shop_id+"'";
                    Statement stmt = connect.createStatement();
                    ResultSet rs = stmt.executeQuery(query);
                    if(rs.next()) {
                        do {
                            itemArrayList.add(new UserDetails(rs.getInt("Usr_ID"),rs.getString("U_Name"),rs.getString("U_Mobile"),shop_id)) ;
                        } while (rs.next());
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
            progressBar.setVisibility(View.GONE);
            if(isSuccess){
                UserListAdapter userListAdapter = new UserListAdapter(UsersOfShop.this,itemArrayList);
                recyclerview.setAdapter(userListAdapter);
            }
            else {
                Toast.makeText(UsersOfShop.this,s,Toast.LENGTH_LONG).show();

            }
        }

    }
}
