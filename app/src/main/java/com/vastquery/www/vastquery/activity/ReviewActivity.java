package com.vastquery.www.vastquery.activity;

import android.annotation.SuppressLint;
import android.app.Dialog;
import android.app.ProgressDialog;
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
import android.widget.Button;
import android.widget.ImageView;
import android.widget.RatingBar;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.vastquery.www.vastquery.DatabaseConnection.ConnectionHelper;

import com.vastquery.www.vastquery.DatabaseConnection.GetContact;
import com.vastquery.www.vastquery.PropertyClasses.ContactInfo;
import com.vastquery.www.vastquery.PropertyClasses.ShopClass;
import com.vastquery.www.vastquery.R;


import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.Statement;


public class ReviewActivity extends Fragment{
    Context context;
    RatingBar ratingBar,rate;
    TextView ratingtext,owner_name,phonenumber,detail_addres;
    ImageView imageView_owner,imageView_logo;
    ShopClass details;
    String Shop_id ;
    float r;
    ContactInfo info;
    Dialog rankDialog;
    ProgressDialog dialog;

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


        info = new ContactInfo();

        Shop_id = getArguments().getString("data");
        SyncData_details syncData_details = new SyncData_details();
        syncData_details.execute();

        ratingtext.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                rankDialog = new Dialog(view.getContext() , R.style.FullHeightDialog);
                rankDialog.setContentView(R.layout.activity_rate);
                rankDialog.setCancelable(true);

                Button canel = rankDialog.findViewById(R.id.cancel);
                canel.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        rankDialog.dismiss();
                    }
                });

                rate = rankDialog.findViewById(R.id.ratingbar_id);
                rate.setOnRatingBarChangeListener(new RatingBar.OnRatingBarChangeListener() {
                    @Override
                    public void onRatingChanged(RatingBar ratingBar, float v, boolean b) {
                        Toast.makeText(context,"Thank you for rating",Toast.LENGTH_LONG).show();
                        rankDialog.dismiss();
                    }
                });

                rankDialog.show();

            }
        });

        return view;
    }

    public class SyncData_details extends AsyncTask<String,String,String> {

        String ConnectionResult;
        boolean isSuccess;

        @Override
        protected void onPreExecute() {
            dialog =  ProgressDialog.show(context,"","Loading...",true);

        }

        @Override
        protected String doInBackground(String... strings) {

            try {
                ConnectionHelper con = new ConnectionHelper();
                Connection connect = con.connectionclass();// Connect to database
                if (connect == null) {
                    ConnectionResult = "Check Your Internet Access!";
                } else {
                    GetContact contact = new GetContact(Shop_id,context);
                    info = contact.getInfo();
                    String query = "select SubCategory_Address,SubCategory_City,SubCategory_District,SubCategory_Pincode,SubCategory_FrontLogo,SubCategory_OwnerName,SubCategory_OwnerPhoto,avg(Rating) as rate from"+
                            " tblSubCategory inner join tblRatting on tblSubcategory.SubCategory_Id = tblRatting.RS_ID"+
                            " where tblSubcategory.SubCategory_Id ='"+Shop_id+"' group by "+
                            " SubCategory_Address,SubCategory_City,SubCategory_District,SubCategory_Pincode,SubCategory_FrontLogo,SubCategory_OwnerName,SubCategory_OwnerPhoto";
                    Statement shop_stmt = connect.createStatement();
                    ResultSet shop_rs = shop_stmt.executeQuery(query);
                    if(shop_rs.next()){
                        details = new ShopClass(shop_rs.getString("SubCategory_Address"),shop_rs.getString("SubCategory_City"),shop_rs.getString("SubCategory_District"),shop_rs.getString("SubCategory_Pincode"),
                                shop_rs.getBytes("SubCategory_FrontLogo"),
                                shop_rs.getString("SubCategory_OwnerName"),shop_rs.getBytes("SubCategory_OwnerPhoto"),shop_rs.getFloat("rate"));
                    }else {
                        String shop_query = "select SubCategory_Address,SubCategory_City,SubCategory_District,SubCategory_Pincode,SubCategory_FrontLogo,SubCategory_OwnerName,SubCategory_OwnerPhoto from tblSubCategory where SubCategory_Id='" +Shop_id+"'";
                        Statement stmt = connect.createStatement();
                        ResultSet rs = stmt.executeQuery(shop_query);
                        if(rs.next()){

                            details = new ShopClass(rs.getString("SubCategory_Address"),rs.getString("SubCategory_City"),rs.getString("SubCategory_District"),rs.getString("SubCategory_Pincode"),
                                    rs.getBytes("SubCategory_FrontLogo"),
                                    rs.getString("SubCategory_OwnerName"),rs.getBytes("SubCategory_OwnerPhoto"),0);
                        }
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

        @SuppressLint("SetTextI18n")
        @Override
        protected void onPostExecute(String s) {
            dialog.dismiss();
            if(isSuccess) {
                try {
                    owner_name.setText(details.getOwnerName());
                    detail_addres.setText(details.getAddress() + "\n" + details.getPincode() + "\n"+info.getEmail()+"\n"+info.getWebsite());
                    phonenumber.setText( info.getMobile()+"\n"+info.getWhatsapp() + "\n"+info.getTwitter() );
                    imageView_owner.setImageBitmap(getBitmap(details.getOwner()));
                    Glide.with(getActivity()).load(details.getOwner()).asBitmap().centerCrop().into(imageView_owner);
                    imageView_logo.setImageBitmap(getBitmap(details.getLogo()));
                    ratingBar.setRating(details.getRating());
                }catch(Exception e){
                    Toast.makeText(context,e.toString(),Toast.LENGTH_LONG).show();
                }
            }else {
                Toast.makeText(context,s,Toast.LENGTH_LONG).show();
            }
        }

    }
    public static Bitmap getBitmap(byte[] decodestring){
            return  BitmapFactory.decodeByteArray(decodestring,0,decodestring.length);

    }


}
