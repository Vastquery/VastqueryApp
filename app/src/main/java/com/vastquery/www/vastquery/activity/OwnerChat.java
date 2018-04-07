package com.vastquery.www.vastquery.activity;

import android.content.Context;
import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.vastquery.www.vastquery.DatabaseConnection.ConnectionHelper;
import com.vastquery.www.vastquery.PropertyClasses.ClassListItems;
import com.vastquery.www.vastquery.R;
import com.vastquery.www.vastquery.helper.ChatListAdapter;
import com.vastquery.www.vastquery.helper.PrefManager;
import com.vastquery.www.vastquery.helper.SimpleStringRecyclerViewAdapter;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.HashMap;
import java.util.List;

public class OwnerChat extends AppCompatActivity {

    ProgressBar progressBar;
    RecyclerView recyclerview;
    LinearLayoutManager linearLayoutManager;
    PrefManager pref;
    HashMap<String,String> profile;
    int user_id;
    public List<ClassListItems> itemArrayList;
    Context context;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_required_list);

        Toolbar toolbar = findViewById(R.id.toolbar_requiredlist);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        progressBar = findViewById(R.id.progressBar);
        recyclerview = findViewById(R.id.recyclerview_requiredlist);

        recyclerview = findViewById(R.id.recyclerview_requiredlist);
        linearLayoutManager = new LinearLayoutManager(context);
        recyclerview.setHasFixedSize(true);
        recyclerview.setLayoutManager(linearLayoutManager);

    }

    public class SyncData_customerpost extends AsyncTask<String,String,String> {

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
                    String query = "select Group_Id,SubCategory_Id,SubCategory_Name,SubCategory_Address,SubCategory_Logo from tblSubCategory " +
                            "join tblCategoryCustomer on tblSubCategory.SubCategory_Id = tblCategoryCustomer.Shop_Id " +
                            "where tblCategoryCustomer.User_Id='"+user_id+"'";
                    Statement stmt = connect.createStatement();
                    ResultSet rs = stmt.executeQuery(query);
                    if(rs.next()) {
                        do {
                            itemArrayList.add(new ClassListItems(rs.getString("Group_Id"), rs.getString("SubCategory_Id"), rs.getString("SubCategory_Name"), rs.getString("SubCategory_Address"), rs.getBytes("SubCategory_Logo")));
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
                ChatListAdapter chatListAdapter = new ChatListAdapter(OwnerChat.this,itemArrayList,false);
                recyclerview.setAdapter(chatListAdapter);
            }
            else {
                Toast.makeText(context,s,Toast.LENGTH_LONG).show();

            }
        }

    }
}
