package com.vastquery.www.vastquery.DatabaseConnection;

import android.content.Context;
import android.os.AsyncTask;
import android.widget.Toast;

import com.vastquery.www.vastquery.PropertyClasses.ContactInfo;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.Statement;

public class GetContact  {

    String id,ConnectionResult;
    ContactInfo info;
    Boolean isSuccess=false;
    Context context;

    public GetContact(String id,Context context)
    {
        this.id = id;
        this.context = context;
    }


    public ContactInfo getInfo() {
        try {
            ConnectionHelper con = new ConnectionHelper();
            Connection connect = con.connectionclass();// Connect to database
            if (connect == null) {
                ConnectionResult = "Check Your Internet Access!";
            } else {
                // Change below query according to your own database.
                String query = "select Mobile,Email,Website,FaceBook,Twitter,Whatsapp from tblSubCatContact where SubCategory_Id='" + id + "'";
                Statement stmt = connect.createStatement();
                ResultSet rs = stmt.executeQuery(query);
                if (rs.next()) {
                    isSuccess = true;
                    info = new ContactInfo(rs.getString("Mobile"), rs.getString("Email"), rs.getString("Website"), rs.getString("FaceBook"), rs.getString("Twitter"), rs.getString("Whatsapp"));
                }
                ConnectionResult = "successful";
                connect.close();
            }
        }catch (Exception ex) {
            ConnectionResult = ex.getMessage();
        }
        if(!isSuccess) {
            info = new ContactInfo("", "", "", "", "", "");
        }
        return info;
    }

}
