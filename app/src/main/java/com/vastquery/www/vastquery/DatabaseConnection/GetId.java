package com.vastquery.www.vastquery.DatabaseConnection;

import android.os.AsyncTask;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.Statement;

public class GetId extends AsyncTask<String,String,String> {

    String query,get,id;

    public GetId(String query,String get) {
        this.query = query;
        this.get = get;
    }


    @Override
    protected String doInBackground(String... strings) {
        try {
            ConnectionHelper con = new ConnectionHelper();
            Connection connect = con.connectionclass();// Connect to database
            if (connect == null) {

            } else {
                Statement stmt = connect.createStatement();
                ResultSet rs = stmt.executeQuery(query);
                if(rs.next()) {
                    id = rs.getString(get);
                }
            }
            connect.close();
        }catch (Exception ex){

        }

        return id;
    }
}
