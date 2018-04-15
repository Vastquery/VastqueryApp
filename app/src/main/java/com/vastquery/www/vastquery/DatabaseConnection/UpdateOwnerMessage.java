package com.vastquery.www.vastquery.DatabaseConnection;

import android.app.ProgressDialog;
import android.content.Context;
import android.os.AsyncTask;
import android.widget.TextView;
import android.widget.Toast;

import com.vastquery.www.vastquery.activity.UserChat;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.util.Date;


public class UpdateOwnerMessage extends AsyncTask<String,String,String>{
    int Id;
    String message,ConnectionResult,date;
    Context context;
    ProgressDialog dialog;
    TextView out;
    public UpdateOwnerMessage(Context context,int id, String message, String date,TextView out) {
        Id = id;
        this.message = message;
        this.date = date;
        this.context = context;
        this.out = out;
    }

    @Override
    protected void onPreExecute() {
        dialog =  ProgressDialog.show(context,"","Loading...",true);

    }

    @Override
    protected String doInBackground(String... strings) {
        try{
            ConnectionHelper con = new ConnectionHelper();
            Connection connect = con.connectionclass();// Connect to database
            if (connect == null) {
                ConnectionResult = "Check Your Internet Access!";
            }else{
                String query = "Update tblMessageBox set Reply='"+message+"',Reply_datetime=?,Status='R'";
                PreparedStatement preStmt = connect.prepareStatement(query);
                preStmt.setDate(1, java.sql.Date.valueOf(date));
                preStmt.execute();
                ConnectionResult = "updated successful";
                connect.close();
            }
        }catch (Exception ex){
            ConnectionResult = ex.getMessage();
        }
        return ConnectionResult;
    }


    @Override
    protected void onPostExecute(String s) {
        dialog.dismiss();
        Toast.makeText(context,s,Toast.LENGTH_LONG).show();
        out.setText(message);
    }
}
