package com.vastquery.www.vastquery.DatabaseConnection;

import android.app.ProgressDialog;
import android.content.Context;
import android.os.AsyncTask;
import android.widget.TextView;
import android.widget.Toast;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.text.SimpleDateFormat;
import java.util.Date;


public class UpdateOwnerMessage extends AsyncTask<String,String,String>{
    int Id;
    String message,ConnectionResult;
    Context context;
    ProgressDialog dialog;
    TextView out;
    Date date;
    SimpleDateFormat formatter;
    java.sql.Date datesql;

    public UpdateOwnerMessage(Context context,int id, String message,TextView out) {
        Id = id;
        this.message = message;
        this.context = context;
        this.out = out;
    }

    @Override
    protected void onPreExecute() {
        dialog =  ProgressDialog.show(context,"","Loading...",true);
        formatter = new SimpleDateFormat("MM/dd/yyyy hh:mm:ss a");
        date = new Date();
        datesql = new java.sql.Date(date.getTime());
    }

    @Override
    protected String doInBackground(String... strings) {
        try{
            ConnectionHelper con = new ConnectionHelper();
            Connection connect = con.connectionclass();// Connect to database
            if (connect == null) {
                ConnectionResult = "Check Your Internet Access!";
            }else{
                String query = "Update tblMessageBox set Reply='"+message+"',Reply_datetime=?,Status='R' where Id="+Id;
                PreparedStatement preStmt = connect.prepareStatement(query);
                preStmt.setDate(1,datesql);
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
