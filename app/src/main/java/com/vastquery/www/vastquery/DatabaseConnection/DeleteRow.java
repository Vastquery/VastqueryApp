package com.vastquery.www.vastquery.DatabaseConnection;

import android.content.Context;
import android.os.AsyncTask;
import android.widget.Toast;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;

/**
 * Created by aj-ajay on 4/5/18.
 */

public class DeleteRow extends AsyncTask<String,String,String> {

    String query,message;
    Context context;

    public DeleteRow(Context context,String query) {
        this.query = query;
        this.context = context;
    }

    @Override
    protected String doInBackground(String... strings) {
        try {
            ConnectionHelper con = new ConnectionHelper();
            Connection connect = con.connectionclass();// Connect to database
            if (connect == null) {
            }else{
                Statement stmt = connect.createStatement();
                ResultSet rs = stmt.executeQuery(query);
                message = "deleted";
                connect.close();
            }
        }catch(Exception ex){
            message = ex.getMessage();
        }
        return message;
    }

    @Override
    protected void onPostExecute(String s) {
        Toast.makeText(context,s,Toast.LENGTH_LONG).show();
    }
}
