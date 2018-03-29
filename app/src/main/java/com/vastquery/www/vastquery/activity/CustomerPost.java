package com.vastquery.www.vastquery.activity;


import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Context;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.vastquery.www.vastquery.DatabaseConnection.ConnectionHelper;
import com.vastquery.www.vastquery.PropertyClasses.ClassListItems;
import com.vastquery.www.vastquery.R;
import com.vastquery.www.vastquery.helper.PrefManager;
import com.vastquery.www.vastquery.helper.SimpleStringRecyclerViewAdapter;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.HashMap;
import java.util.List;



public class CustomerPost extends Fragment {

    private FragmentActivity myContext;
    Toolbar toolbar;
    RecyclerView recyclerView;
    LinearLayoutManager linearLayoutManager;
    Context context;
    ProgressBar progressBar;
    public List<ClassListItems> itemArrayList;
    PrefManager pref;
    HashMap<String, String> profile;


    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setHasOptionsMenu(true);
        setRetainInstance(true);
    }

    @Override
    public void onAttach(Activity activity) {
        myContext = (FragmentActivity) activity;
        super.onAttach(activity);
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.activity_required_list, container, false);
        context = view.getContext();
        progressBar = view.findViewById(R.id.progressBar);
        toolbar = view.findViewById(R.id.toolbar_requiredlist);
        ((AppCompatActivity) getActivity()).setSupportActionBar(toolbar);
        ((AppCompatActivity) getActivity()).getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        recyclerView = view.findViewById(R.id.recyclerview_requiredlist);
        linearLayoutManager = new LinearLayoutManager(context);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(linearLayoutManager);

        pref = new PrefManager(getActivity().getApplicationContext());
        profile = pref.getUserDetails();


        SyncData_customerpost synData = new SyncData_customerpost();
        synData.execute();

        return view;
    }


    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if(item.getItemId() == android.R.id.home){
            myContext.onBackPressed();
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    @SuppressLint("StaticFieldLeak")
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
                    String query = "select Group_Id,SubCategory_Id,SubCategory_Name,SubCategory_Address,SubCategory_Logo from tblSubCategory  where SubCategory_Addedby=(select Usr_ID from tblUser where U_Mobile='"+profile.get("mobile")+"'";
                    Statement stmt = connect.createStatement();
                    ResultSet rs = stmt.executeQuery(query);
                    if(rs.next()) {
                        do {
                            itemArrayList.add(new ClassListItems(rs.getString("Group_Id"), rs.getString("SubCategory_Id"), rs.getString("SubCategory_Name"), rs.getString("SubCategory_Address"), rs.getBytes("SubCategory_Logo")));
                        } while (rs.next());
                    }
                    /* Change below query according to your own database.
                    String query = "select Prof_ID,Prof_Name,Address,Logo from tblProffesional ";
                    Statement stmt = connect.createStatement();
                    ResultSet rs = stmt.executeQuery(query);
                    if(rs.next()) {
                        do {
                          //  itemArrayList.add(new ClassListItems(rs.getInt("Prof_ID"),rs.getString("Prof_Name"),rs.getString("Address"), rs.getBytes("Logo")));
                        }while (rs.next());
                    }else{
                        String shop_query = "select S_ID,S_Name,Address,Shop_Photo from tblShop where Added_By='"+profile.get("email")+"'";
                        Statement shop_stmt = connect.createStatement();
                        ResultSet shop_rs = shop_stmt.executeQuery(shop_query);
                        while (shop_rs.next()) {
                            //itemArrayList.add(new ClassListItems(shop_rs.getInt("S_ID"),shop_rs.getString("S_Name"),shop_rs.getString("Address"), shop_rs.getBytes("Shop_Photo")));
                        }
                        isShop=true;
                    }
                    ConnectionResult = "successful";
                    isSuccess = true;*/
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
                SimpleStringRecyclerViewAdapter myAdapter = new SimpleStringRecyclerViewAdapter(context, itemArrayList);
                recyclerView.setAdapter(myAdapter);
            }
            else {
                Toast.makeText(context,s,Toast.LENGTH_LONG).show();

            }
        }

    }

}
