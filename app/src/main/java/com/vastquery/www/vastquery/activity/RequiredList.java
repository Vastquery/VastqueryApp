package com.vastquery.www.vastquery.activity;

import android.content.Intent;
import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.View;
import android.widget.ProgressBar;
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

public class RequiredList extends AppCompatActivity {

    public static final String extra_name = "";
    Toolbar toolbar;


    public List<ClassListItems> itemArrayList;
    RecyclerView recyclerView;
    LinearLayoutManager linearLayoutManager;
    String requested_list;
    ProgressBar progressBar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_required_list);

        // getting the string using intent
        Intent intent = getIntent();
        requested_list = intent.getStringExtra(extra_name);

        //toolbar
        toolbar = findViewById(R.id.toolbar_requiredlist);
        setSupportActionBar(toolbar);
        progressBar=findViewById(R.id.progressBar_requriedList);

        recyclerView = findViewById(R.id.recyclerview_requiredlist);
        linearLayoutManager = new LinearLayoutManager(RequiredList.this);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(linearLayoutManager);
        itemArrayList = new ArrayList<>();

        SyncData syncData = new SyncData();
        syncData.execute();

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.sample_actions, menu);
        return true;
    }

    public class SyncData extends AsyncTask<String,String,String> {

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
                    // Change below query according to your own database.
                    String query = "select Prof_ID,Prof_Name,Address,Logo from tblProffesional  where Prof_Type='"+requested_list+"'";
                    Statement stmt = connect.createStatement();
                    ResultSet rs = stmt.executeQuery(query);
                    if(rs.next()) {
                        do {
                            itemArrayList.add(new ClassListItems(rs.getInt("Prof_ID"),rs.getString("Prof_Name"),rs.getString("Address"), rs.getBytes("Logo")));
                        }while (rs.next());
                    }else{
                        String shop_query = "select S_ID,S_Name,Address,Shop_Photo from tblShop where S_Type='"+requested_list+"'";
                        Statement shop_stmt = connect.createStatement();
                        ResultSet shop_rs = shop_stmt.executeQuery(shop_query);
                        while (shop_rs.next()) {
                            itemArrayList.add(new ClassListItems(shop_rs.getInt("S_ID"),shop_rs.getString("S_Name"),shop_rs.getString("Address"), shop_rs.getBytes("Shop_Photo")));
                        }
                        isShop=true;
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
                SimpleStringRecyclerViewAdapter myAdapter = new SimpleStringRecyclerViewAdapter(RequiredList.this, itemArrayList,isShop);
                recyclerView.setAdapter(myAdapter);
            }
            else {
                Toast.makeText(RequiredList.this,s,Toast.LENGTH_LONG).show();

            }
        }

    }
}
