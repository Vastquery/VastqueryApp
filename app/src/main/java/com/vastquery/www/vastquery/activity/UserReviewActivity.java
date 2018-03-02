package com.vastquery.www.vastquery.activity;

import android.content.Intent;
import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.vastquery.www.vastquery.DatabaseConnection.ConnectionHelper;
import com.vastquery.www.vastquery.PropertyClasses.ReviewClass;
import com.vastquery.www.vastquery.R;
import com.vastquery.www.vastquery.helper.ReviewAdapter;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

public class UserReviewActivity extends AppCompatActivity {

    public int Shop_id;
    AutoCompleteTextView  comment_box;
    ProgressBar progressBar;
    Toolbar toolbar;
    RecyclerView recyler_review_resoruce;
    LinearLayoutManager linearLayoutManager;
    List<ReviewClass> reviews;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_review);

        Intent intent = getIntent();

        Shop_id = intent.getIntExtra("shop_id",0);
        comment_box = findViewById(R.id.comment_box);
        progressBar = findViewById(R.id.progressBar_reviews);
        toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        recyler_review_resoruce = findViewById(R.id.recyler_review_resoruce);
        linearLayoutManager = new LinearLayoutManager(UserReviewActivity.this);
        recyler_review_resoruce.setHasFixedSize(true);
        recyler_review_resoruce.setLayoutManager(linearLayoutManager);
        reviews = new ArrayList<>();


        SyncData_reviews details = new SyncData_reviews();
        details.execute();
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

    public class SyncData_reviews extends AsyncTask<String,String,String> {

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
                    String query = "select U_Name,Reviews,Date from tblUser,tblReviews where tblReviews.RIV_UID=tblUser.Usr_ID and tblReviews.RIV_SID="+Shop_id;
                    Statement stmt = connect.createStatement();
                    ResultSet rs = stmt.executeQuery(query);
                    if(rs.next()) {
                        isSuccess = true;
                        do {
                            reviews.add(new ReviewClass(rs.getString("U_Name"),rs.getString("Reviews"),rs.getDate("Date")));
                        }while (rs.next());
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
            progressBar.setVisibility(View.GONE);
            if(isSuccess){
                ReviewAdapter myAdapter = new ReviewAdapter(UserReviewActivity.this, reviews);
                recyler_review_resoruce.setAdapter(myAdapter);
            }
            else Toast.makeText(UserReviewActivity.this,"No Reviews yet,You add it",Toast.LENGTH_LONG).show();
        }

    }

    public void updateReviews(String comment){

    }



}
