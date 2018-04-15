package com.vastquery.www.vastquery.activity;

import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.view.WindowManager;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.Toast;

import com.vastquery.www.vastquery.DatabaseConnection.ConnectionHelper;
import com.vastquery.www.vastquery.PropertyClasses.ClassListItems;
import com.vastquery.www.vastquery.R;
import com.vastquery.www.vastquery.helper.SimpleStringRecyclerViewAdapter;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.Statement;

public class EditShop extends AppCompatActivity {

    private AutoCompleteTextView description,shop_name,shop_email,shop_address, shop_city, shop_pincode, shop_phone, whatsapp, facebook,twitter, shop_website, shop_owner;
    private Spinner shop_state, shop_district;
    private Button shop_type,shop_clear,shop_submit;
    private ImageView image_photo,owner_photo,image_logo;
    private ImageButton shoplogo_button,shopphoto_button,ownerphoto_button;

    byte[] shop,logo,owner;
    String state,subcategory_id,rollnum,rollid,shopdescription,shopname,shopstate,shopemail,shopdistrict,shopaddress,shopcity,shoppincode,shopphone,shopwhatsapp,shopfacebook,shopwebsite,ownername;

    int shop_id;
    ClassListItems items;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_post_shop_form);

        shop_clear = findViewById(R.id.shop_clear);
        shop_submit = findViewById(R.id.shop_submit);
        shop_name = findViewById(R.id.shop_name);
        shop_type = findViewById(R.id.shop_type);
        shop_email = findViewById(R.id.shop_email);
        shop_state = findViewById(R.id.shop_state);
        shop_district = findViewById(R.id.shop_district);
        shop_address = findViewById(R.id.shop_address);
        shop_city = findViewById(R.id.shop_city);
        shop_pincode = findViewById(R.id.shop_pincode);
        shop_phone = findViewById(R.id.shop_phone);
        whatsapp = findViewById(R.id.whatsapp);
        facebook = findViewById(R.id.facebook);
        shop_website = findViewById(R.id.shop_website);
        shop_owner = findViewById(R.id.shop_owner);
        twitter = findViewById(R.id.twitter);
        image_photo = findViewById(R.id.image_photo);
        owner_photo = findViewById(R.id.owner_photo);
        image_logo = findViewById(R.id.image_logo);
        shoplogo_button = findViewById(R.id.shoplogo_button);
        shopphoto_button = findViewById(R.id.shopphoto_button);
        ownerphoto_button = findViewById(R.id.ownerphoto_button);
        description = findViewById(R.id.description);

        shop_id = getIntent().getIntExtra("shop_id",0);

        this.getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_HIDDEN);


    }
    

    public class SyncData extends AsyncTask<String,String,String> {

        String ConnectionResult;
        boolean isSuccess,isShop=false;

        @Override
        protected void onPreExecute() {

        }

        @Override
        protected String doInBackground(String... strings) {

            try {
                ConnectionHelper con = new ConnectionHelper();
                Connection connect = con.connectionclass();// Connect to database
                if (connect == null) {
                    ConnectionResult = "Check Your Internet Access!";
                } else {
                    String query = "select SubCategory_Name,SubCategory_Address,SubCategory_City" +
                            ",SubCategory_Country,SubCategory_State,SubCategory_District," +
                            "SubCategory_Pincode,SubCategory_Logo,SubCategory_FrontLogo,SubCategory_OwnerName,SubCategory_OwnerPhoto" +
                            " from tblSubCategory  where Id='"+shop_id+"'";
                    Statement stmt = connect.createStatement();
                    ResultSet rs = stmt.executeQuery(query);
                    if(rs.next()) {
                    isSuccess =true;


                    }
                    ConnectionResult = "successful";
                    isSuccess = true;
                    connect.close();
                }
            } catch (Exception ex) {
                ConnectionResult = ex.getMessage();
            }
            return ConnectionResult;
        }

        @Override
        protected void onPostExecute(String s) {

            if(isSuccess){
            }
            else {
                Toast.makeText(EditShop.this,s,Toast.LENGTH_LONG).show();

            }
        }

    }
}