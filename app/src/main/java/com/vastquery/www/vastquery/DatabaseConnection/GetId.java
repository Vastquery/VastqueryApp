package com.vastquery.www.vastquery.DatabaseConnection;

import android.annotation.SuppressLint;
import android.content.Context;
import android.os.AsyncTask;
import android.widget.Toast;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.Statement;

@SuppressLint("StaticFieldLeak")
public class GetId {

    public String query,get,s_id;
    Boolean isSuccess=false;


    public GetId(String query, String get) {
        this.query = query;
        this.get = get;
    }



    public String getS_Id() {
        try {
            ConnectionHelper con = new ConnectionHelper();
            Connection connect = con.connectionclass();// Connect to database
            if (connect == null) {

            } else {
                Statement stmt = connect.createStatement();
                ResultSet rs = stmt.executeQuery(query);
                if(rs.next()) {
                    isSuccess = true;
                    s_id = rs.getString(get);
                }
            }
            connect.close();
        }catch (Exception ignored){
        }
        if(isSuccess) return s_id;
        return "notexist";
    }

}
