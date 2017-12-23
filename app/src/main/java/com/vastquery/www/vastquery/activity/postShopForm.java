package com.vastquery.www.vastquery.activity;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.Spinner;

import com.vastquery.www.vastquery.DatabaseConnection.ConnectionHelper;
import com.vastquery.www.vastquery.R;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

public class postShopForm extends AppCompatActivity implements View.OnClickListener{

    private static AutoCompleteTextView shop_name,shop_address,shop_city,shop_pincode,shop_phone,shop_mobile1,shop_mobile2,shop_website,shop_owner;
    private static Spinner shop_type,shop_state,shop_district;
    private static Button shop_clear;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_post_shop_form);

        shop_clear = findViewById(R.id.shop_clear);
        shop_name = findViewById(R.id.shop_name);
        shop_type = findViewById(R.id.shop_type);
        shop_state = findViewById(R.id.shop_state);
        shop_district = findViewById(R.id.shop_district);
        shop_address = findViewById(R.id.shop_address);
        shop_city = findViewById(R.id.shop_city);
        shop_pincode = findViewById(R.id.shop_pincode);
        shop_phone = findViewById(R.id.shop_phone);
        shop_mobile1 = findViewById(R.id.shop_mobile1);
        shop_mobile2 = findViewById(R.id.shop_mobile2);
        shop_website = findViewById(R.id.shop_website);
        shop_owner = findViewById(R.id.shop_owner);





        GetData getData = new GetData("select State_Name from tblStates","State_Name");
        List<String> list_states = null;
        list_states = getData.doInBackground();
        list_states.add(0,"-please select the state-");

        //shop type adapter
        ArrayAdapter<String> shopTypeAdapter = new ArrayAdapter<String>(postShopForm.this,
                android.R.layout.simple_list_item_1,getResources().getStringArray(R.array.shoptype));
        shopTypeAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        shop_type.setAdapter(shopTypeAdapter);


        //state dropdown
        ArrayAdapter<String> stateAdapter = new ArrayAdapter<>(postShopForm.this,
                android.R.layout.simple_list_item_1,list_states);
        stateAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        shop_state.setAdapter(stateAdapter);
        shop_state.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                //district dropdown
                String query = "select Dt_Name from tblTN_DTs where St_ID="+i;
                GetData getData_district = new GetData(query,"Dt_Name");
                List<String> list_districts = null;
                list_districts = getData_district.doInBackground();
                list_districts.add(0,"-please select the District-");
                ArrayAdapter<String> districtAdapter = new ArrayAdapter<String>(postShopForm.this,
                        android.R.layout.simple_list_item_1,list_districts);
                districtAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                shop_district.setAdapter(districtAdapter);

            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {
            }
        });

        //Button action
        shop_clear.setOnClickListener(this);
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()){
            case R.id.shop_submit:
                break;
            case R.id.shop_clear:
                clearData();
                break;
        }
    }

    //clear button
    public void clearData(){
        shop_name.setText("");
        shop_type.setSelection(0);
        shop_state.setSelection(0);
        shop_address.setText("");
        shop_city.setText("");
        shop_pincode.setText("");
        shop_phone.setText("");
        shop_mobile1.setText("");
        shop_mobile2.setText("");
        shop_website.setText("");
        shop_owner.setText("");

    }

    public class GetData {

        Connection connect;
        String ConnectionResult = "";
        Boolean isSuccess = false;
        String query,column;

        public GetData(String query,String column){
            this.query = query;
            this.column = column;
        }
        public List<String> doInBackground() {

            List<String> data = null;
            data = new ArrayList<>();
            try
            {
                ConnectionHelper conStr=new ConnectionHelper();
                connect =conStr.connectionclass();        // Connect to database
                if (connect == null)
                {
                    ConnectionResult = "Check Your Internet Access!";
                }
                else
                {
                    // Change below query according to your own database.
                    Statement stmt = connect.createStatement();
                    ResultSet rs = stmt.executeQuery(query);
                    while (rs.next()){
                        data.add(rs.getString(column));
                    }
                    ConnectionResult = " successful";
                    isSuccess=true;
                    connect.close();
                }
            }
            catch (Exception ex)
            {
                isSuccess = false;
                ConnectionResult = ex.getMessage();
            }

            return data;
        }


    }

}
