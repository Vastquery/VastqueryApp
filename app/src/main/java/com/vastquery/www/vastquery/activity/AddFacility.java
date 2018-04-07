package com.vastquery.www.vastquery.activity;

import android.content.Intent;
import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.Toast;

import com.vastquery.www.vastquery.DatabaseConnection.ConnectionHelper;
import com.vastquery.www.vastquery.R;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.Statement;

public class AddFacility extends AppCompatActivity {

    AutoCompleteTextView facility;
    Button add;
    String cat_id,facilitytext;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_facility);

        facility = findViewById(R.id.facility);
        add = findViewById(R.id.add);


        cat_id = getIntent().getStringExtra("catid");

        add.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                facilitytext = facility.getText().toString().trim();
                if(!validate(facilitytext)) {
                    InsertFacility insertFacility = new InsertFacility();
                    insertFacility.execute();
                }else{
                    Toast.makeText(AddFacility.this,"enter the facility",Toast.LENGTH_LONG).show();
                }
            }
        });
    }
    public boolean validate(String text){
        return text.equals("");
    }

    public class InsertFacility extends AsyncTask<String,String,String>{
        String message;
        boolean exist=false;
        @Override
        protected String doInBackground(String... strings) {
            try{
                ConnectionHelper connect = new ConnectionHelper();
                Connection con = connect.connectionclass();
                if(con == null){
                    message = "check internet access";
                }else{
                    String check = "select * from tblSubCategoryFacility where Cat_Id='"+cat_id+"'" +
                            " and Facilities_desc='"+facilitytext+"'";
                    Statement stmt = con.createStatement();
                    ResultSet rs = stmt.executeQuery(check);
                    if(rs.next()){
                        exist = true;
                        message = "facility already exist";
                    }
                    con.close();
                }
            }catch (Exception ex){
                message = ex.getMessage();
            }
            return message;
        }

        @Override
        protected void onPostExecute(String s) {
            if(exist){
                Toast.makeText(AddFacility.this,message,Toast.LENGTH_LONG).show();
            }else{
                Intent intent = new Intent(AddFacility.this,ServicePostForm.class);
                intent.putExtra("catId",cat_id);
                intent.putExtra("facilitydesc",facilitytext);
                intent.putExtra("new",true);
                startActivity(intent);
            }
        }
    }
}
