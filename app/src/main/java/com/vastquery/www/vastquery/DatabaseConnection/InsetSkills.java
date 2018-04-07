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

public class InsetSkills extends AsyncTask<String,String,String> {

    String query,check,message;
    Context context;

    public InsetSkills(Context context,String query,String check) {
        this.query = query;
        this.check = check;
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
                ResultSet rs = stmt.executeQuery(check);
                if(rs.next()){
                    message = "already added";
                }else {
                    message = "successfully added";
                    PreparedStatement preStmt = connect.prepareStatement(query);
                    preStmt.execute();
                }
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
