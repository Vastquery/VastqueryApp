package com.vastquery.www.vastquery.activity;

import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.AsyncTask;
import android.support.v4.app.Fragment;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RatingBar;
import android.widget.TextView;
import android.widget.Toast;

import com.vastquery.www.vastquery.DatabaseConnection.ConnectionHelper;

import com.vastquery.www.vastquery.PropertyClasses.ShopClass;
import com.vastquery.www.vastquery.R;


import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.Statement;


public class ReviewActivity extends Fragment{
    Context context;
    RatingBar ratingBar;
    TextView ratingtext,owner_name,phonenumber,detail_addres,review;
    ImageView imageView_owner,imageView_logo;
    ShopClass details;
    int Shop_id ;
    String address;
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.activity_review, container, false);
        context = view.getContext();

        owner_name = view.findViewById(R.id.owner_name);
        phonenumber = view.findViewById(R.id.phonenumber);
        detail_addres = view.findViewById(R.id.detail_address);
        imageView_owner = view.findViewById(R.id.imageView_owner);
        imageView_logo = view.findViewById(R.id.imageView_logo);
        ratingBar = view.findViewById(R.id.ratingbar);
        ratingtext = view.findViewById(R.id.ratingText);
        review = view.findViewById(R.id.reviews);

        review.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(context, UserReviewActivity.class);
                intent.putExtra("shop_id",Shop_id);
                context.startActivity(intent);

            }
        });
        ratingBar.setOnRatingBarChangeListener(new RatingBar.OnRatingBarChangeListener() {
            @Override
            public void onRatingChanged(RatingBar ratingBar, float v, boolean b) {
                ratingtext.setText(v+" Tap here to add your rating");
            }
        });

        Shop_id = getArguments().getInt("data");
        address = getArguments().getString("address");
        SyncData_details syncData_details = new SyncData_details();
        syncData_details.execute();

        return view;
    }

    public class SyncData_details extends AsyncTask<String,String,String> {

        String ConnectionResult;
        boolean isSuccess;

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
        }

        @Override
        protected String doInBackground(String... strings) {

            try {
                ConnectionHelper con = new ConnectionHelper();
                Connection connect = con.connectionclass();// Connect to database
                if (connect == null) {
                    ConnectionResult = "Check Your Internet Access!";
                } else {
                    String shop_query = "select Address,City,District,Pin_Code,Email,Phone,Mobile1,Mobile2,Website,Logo,Owner,Owner_Name from tblShop where S_ID="+Shop_id+"";
                    Statement shop_stmt = connect.createStatement();
                    ResultSet shop_rs = shop_stmt.executeQuery(shop_query);
                    if(shop_rs.next()){
                        details = new ShopClass(shop_rs.getString("Pin_Code"),shop_rs.getString("Email"),shop_rs.getString("Phone"),
                                shop_rs.getString("Mobile1"), shop_rs.getString("Mobile2"),shop_rs.getString("Website"),shop_rs.getBytes("Logo"),
                                shop_rs.getBytes("Owner"),
                                shop_rs.getString("Owner_Name"));
                        isSuccess = true;
                    }

                }
                    ConnectionResult = "successful";
                    isSuccess = true;
                    connect.close();
            } catch (Exception ex) {
                ConnectionResult = ex.getMessage();
            }
            return ConnectionResult;
        }

        @Override
        protected void onPostExecute(String s) {
            if(isSuccess){
                owner_name.setText(details.getOwnername());
                detail_addres.setText(address+"\n"+details.getPincode());
                phonenumber.setText(details.getPhone()+"\n"+details.getMobile1()+"\n"+details.getMobile2());
                imageView_owner.setImageBitmap(getBitmap(details.getOwner()));
                imageView_logo.setImageBitmap(getBitmap(details.getLogo()));
            }
            else {
                Toast.makeText(context,s,Toast.LENGTH_LONG).show();
            }
        }

    }
    public static Bitmap getBitmap(byte[] decodestring){
        Bitmap decodebitmap = BitmapFactory.decodeByteArray(decodestring,0,decodestring.length);;
        return decodebitmap;
    }

}
