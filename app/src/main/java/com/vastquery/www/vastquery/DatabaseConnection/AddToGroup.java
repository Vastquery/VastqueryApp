package com.vastquery.www.vastquery.DatabaseConnection;

import android.content.Context;
import android.os.AsyncTask;
import android.widget.Toast;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;


public class AddToGroup extends AsyncTask<String,String,String> {

    String Shop_Id,Group_Id,message;
    int user_id;
    boolean isexisted=false;
    Context context;


    public AddToGroup(Context context,String shop_Id, String group_Id, int user_id) {
        this.Shop_Id = shop_Id;
        this.Group_Id = group_Id;
        this.user_id = user_id;
        this.context = context;
    }

    @Override
    protected String doInBackground(String... strings) {
        try {
            ConnectionHelper con = new ConnectionHelper();
            Connection connect = con.connectionclass();// Connect to database
            if (connect == null) {
                message = "Check Your Internet Access!";
            } else {
                String query = "select * from tblCategoryCustomer where User_Id="+user_id+" and Shop_Id='"+Shop_Id+"'";
                Statement stmt = connect.createStatement();
                ResultSet rs = stmt.executeQuery(query);
                if(rs.next()){
                    message = "already added";
                }else{
                    String insertquery = "Insert into tblCategoryCustomer(User_Id,Shop_Id,Group_Id) " +
                            "values('"+user_id+"','"+Shop_Id+"','"+Group_Id+"')";
                    PreparedStatement preStmt = connect.prepareStatement(insertquery);
                    preStmt.execute();
                    message = "successfully added";
                }

                connect.close();
            }
        } catch (Exception ex) {
            message = ex.getMessage();
        }
        return message;
    }

    @Override
    protected void onPostExecute(String s) {
        Toast.makeText(context,s,Toast.LENGTH_LONG).show();
    }
}
