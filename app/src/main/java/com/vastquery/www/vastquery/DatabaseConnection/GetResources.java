package com.vastquery.www.vastquery.DatabaseConnection;

import android.app.ProgressDialog;
import android.content.Context;
import android.os.AsyncTask;
import android.support.v7.widget.RecyclerView;
import android.widget.Toast;

import com.vastquery.www.vastquery.PropertyClasses.CategoryDetails;
import com.vastquery.www.vastquery.helper.MyAdapter;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by aj-ajay on 2/7/18.
 */

public class GetResources extends AsyncTask<String,String,String> {

    ProgressDialog progressDialog;
    String ConnectionResult,group;
    boolean isSuccess=false;
    Context context;
    RecyclerView recyclerView;
    List<CategoryDetails> details;

    public GetResources(Context context,RecyclerView recyclerView,String group){
        this.context = context;
        this.recyclerView = recyclerView;
        this.group = group;
        details = new ArrayList<>();
    }
    @Override
    protected void onPreExecute(){
        progressDialog =  ProgressDialog.show(context,"","Loading...",true);
    }

    @Override
    protected String doInBackground(String... strings) {

        try {
            ConnectionHelper con = new ConnectionHelper();
            Connection connect = con.connectionclass();// Connect to database
            if (connect == null) {
                ConnectionResult = "Check Your Internet Access!";
            } else {
                String query = "select Cat_Id,Category_Name,Category_Icon1 from tblCategory where Group_Id='"+group+"'";
                Statement stmt = connect.createStatement();
                ResultSet rs = stmt.executeQuery(query);
                if(rs.next()){
                    do{
                        details.add(new CategoryDetails(rs.getString("Cat_Id"),rs.getString("Category_Name"),rs.getBytes("Category_Icon1")));
                    }while (rs.next());
                }
                isSuccess = true;
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
        progressDialog.dismiss();
        if(isSuccess){
            MyAdapter myAdapter = new MyAdapter(context, details);
            recyclerView.setAdapter(myAdapter);
        }
        else Toast.makeText(context,s, Toast.LENGTH_LONG).show();
    }
}
