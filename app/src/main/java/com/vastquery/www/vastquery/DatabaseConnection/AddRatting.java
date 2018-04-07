package com.vastquery.www.vastquery.DatabaseConnection;

import android.content.Context;
import android.os.AsyncTask;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;



public class AddRatting extends AsyncTask<String,String,String> {

    String Shop_Id,message;
    int user_id,rating;
    Context context;

    public AddRatting(Context context,String shop_Id,int user_id, int rating) {
        Shop_Id = shop_Id;
        this.user_id = user_id;
        this.rating = rating;
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
                String query = "select * from tblRatting where RU_ID="+user_id+" and RS_ID='"+Shop_Id+"'";
                Statement stmt = connect.createStatement();
                ResultSet rs = stmt.executeQuery(query);
                if(rs.next()){
                    message = "already ratted";
                }else{
                    String insertquery = "Insert into tblRatting(RU_ID,RS_ID,Rating) " +
                            "values("+user_id+",'"+Shop_Id+"',"+rating+")";
                    PreparedStatement preStmt = connect.prepareStatement(insertquery);
                    preStmt.execute();
                    message = "successfully ratted";
                }

                connect.close();
            }
        } catch (Exception ex) {
            message = ex.getMessage();
        }
        return message;
    }
}
