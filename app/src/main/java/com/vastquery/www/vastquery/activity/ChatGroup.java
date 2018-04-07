package com.vastquery.www.vastquery.activity;

import android.app.Activity;
import android.content.Context;
import android.os.AsyncTask;
import android.support.annotation.Nullable;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
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
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class ChatGroup extends Fragment {

    private FragmentActivity myContext;
    ProgressBar progressBar;
    RecyclerView recyclerview;
    LinearLayoutManager linearLayoutManager;
    PrefManager pref;
    HashMap<String,String> profile;
    int user_id;
    public List<ClassListItems> itemArrayList;
    Context context;

    @Override
    public void onCreate(Bundle savedInstanceState) {
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
        View view = inflater.inflate(R.layout.activity_chat_group, container, false);
        context = view.getContext();

        Toolbar toolbar = view.findViewById(R.id.toolbar);
        ((AppCompatActivity) getActivity()).setSupportActionBar(toolbar);
        ((AppCompatActivity) getActivity()).getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        progressBar = view.findViewById(R.id.progressBar);
        recyclerview = view.findViewById(R.id.recyclerview);



        itemArrayList = new ArrayList<>();

        pref = new PrefManager(context);
        profile = pref.getUserDetails();
        //user_id = Integer.parseInt(profile.get("id"));

        linearLayoutManager = new LinearLayoutManager(context);
        recyclerview.setHasFixedSize(true);
        recyclerview.setLayoutManager(linearLayoutManager);

        FloatingActionButton myshop = view.findViewById(R.id.myshop);
        myshop.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

            }
        });

        MyGroup myGroup = new MyGroup();
        myGroup.execute();

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
    public class MyGroup extends AsyncTask<String,String,String>{


        String ConnectionResult;
        boolean isSuccess=false;

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
                }else{
                    isSuccess = true;
                    String query = "select Group_Id,SubCategory_Id,SubCategory_Name,SubCategory_Address,SubCategory_Logo from tblSubCategory " +
                            "join tblCategoryCustomer on tblSubCategory.SubCategory_Id = tblCategoryCustomer.Shop_Id " +
                            "where tblCategoryCustomer.User_Id='"+user_id+"'";
                    Statement stmt = connect.createStatement();
                    ResultSet rs = stmt.executeQuery(query);
                    if(rs.next()) {
                        itemArrayList. add(new ClassListItems(rs.getString("Group_Id"),rs.getString("SubCategory_Id"),rs.getString("SubCategory_Name"),rs.getString("SubCategory_Address"), rs.getBytes("SubCategory_Logo")));
                    }
                }
            }catch(Exception ex){
                ConnectionResult = ex.getMessage();
            }
                return ConnectionResult;
        }

        @Override
        protected void onPostExecute(String s) {
            progressBar.setVisibility(View.GONE);
            if(isSuccess){
                ChatListAdapter myAdapter = new ChatListAdapter(context, itemArrayList,true);
                recyclerview.setAdapter(myAdapter);
            }else{
                Toast.makeText(context,s,Toast.LENGTH_LONG).show();
            }

        }
    }
}
