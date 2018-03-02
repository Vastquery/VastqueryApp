package com.vastquery.www.vastquery.activity;

import android.annotation.SuppressLint;
import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.widget.SearchView;
import android.view.View;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.vastquery.www.vastquery.DatabaseConnection.ConnectionHelper;
import com.vastquery.www.vastquery.PropertyClasses.SearchClass;
import com.vastquery.www.vastquery.R;
import com.vastquery.www.vastquery.helper.GridAdapter;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

public class SearchActivity extends AppCompatActivity {

    SearchView searchView;
    RecyclerView recyclerView;
    ProgressBar progressBar;
    List<SearchClass> results;
    LinearLayoutManager linearLayoutManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search);

        recyclerView = findViewById(R.id.result_list);
        progressBar = findViewById(R.id.progressBar);
        searchView = findViewById(R.id.search_field);
        searchView.setQueryHint("Search Shops,Products & Proffessionals");

        linearLayoutManager = new LinearLayoutManager(SearchActivity.this);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(linearLayoutManager);
        results = new ArrayList<>();
        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                return false;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                if(newText.length()>1){
                    //recyclerView.setVisibility(View.VISIBLE);
                    SearchTask searchTask = new SearchTask();
                    searchTask.execute(newText);
                }
                return false;
            }
        });

    }

   @SuppressLint("StaticFieldLeak")
   public class SearchTask extends AsyncTask<String,String,String>{

       String message;
       boolean success;
       @Override
       protected void onPreExecute() {
           results.clear();
           progressBar.setVisibility(View.VISIBLE);
       }

       @Override
       protected String doInBackground(String... text) {
           try {
               ConnectionHelper con = new ConnectionHelper();
               Connection connect = con.connectionclass();// Connect to database
               if(connect == null){
                   message = "Check your Internet Access";
               }else{
                   success = true;
                   String query = "(select SubCategory_Name,Group_Id,SubCategory_Id from tblSubCategory where SubCategory_Name like '"+text[0]+"%')" +
                           "union" +
                           "(select Product_name,,Group_Id,SubCategory_Id from tblSubCategoryProducts where Product_name like '"+text[0]+"%')";
                   Statement stmt = connect.createStatement();
                   ResultSet rs = stmt.executeQuery(query);
                   if(rs.next()){
                       do {
                           results.add(new SearchClass(rs.getString("U_Name"),rs.getString(",Group_Id"),rs.getString("SubCategory_Id")));
                       }while(rs.next());
                   }
               }
               connect.close();
           }catch (Exception e){
               message = e.getMessage();
           }
           return message;
       }

       @Override
       protected void onPostExecute(String s) {
            if(success){
                progressBar.setVisibility(View.GONE);
                if(results.isEmpty()) Toast.makeText(SearchActivity.this,"No Results found",Toast.LENGTH_LONG).show();
                else {
                    GridAdapter gridAdapter = new GridAdapter(SearchActivity.this, results);
                    recyclerView.setAdapter(gridAdapter);
                }
            }else Toast.makeText(SearchActivity.this,s,Toast.LENGTH_LONG).show();
       }
   }
}
