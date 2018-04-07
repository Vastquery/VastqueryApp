package com.vastquery.www.vastquery.activity;

import android.content.Context;
import android.content.res.Resources;
import android.graphics.Rect;
import android.os.AsyncTask;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.os.Bundle;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.TypedValue;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.vastquery.www.vastquery.DatabaseConnection.ConnectionHelper;
import com.vastquery.www.vastquery.PropertyClasses.ProductClass;
import com.vastquery.www.vastquery.R;
import com.vastquery.www.vastquery.helper.ProductAdapter;


import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

public class ProductActivity extends Fragment {

    RecyclerView recyclerView;
    GridLayoutManager gridLayoutManager;
    List<ProductClass> products;
    Context context;
    ProgressBar progressBar;
    public String Shop_id,group_id;


    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.category_activity, container,false);

        Shop_id = getArguments().getString("data");
        context = view.getContext();
        progressBar = view.findViewById(R.id.progressBar);
        recyclerView = view.findViewById(R.id.recyler_view);
        gridLayoutManager = new GridLayoutManager(context,2);
        recyclerView.setLayoutManager(gridLayoutManager);
        recyclerView.setHasFixedSize(true);
        recyclerView.setItemAnimator(new DefaultItemAnimator());


        products = new ArrayList<>();
        SyncData_Products products = new SyncData_Products();
        products.execute();

        return view;
    }

    public class SyncData_Products extends AsyncTask<String,String,String> {

        String ConnectionResult;
        boolean isSuccess=false,hasProduct=false;

        @Override
        protected void onPreExecute() {
            Toast.makeText(context,"Wait data is downloading",Toast.LENGTH_LONG).show();
            progressBar.setVisibility(View.VISIBLE);
        }

        @Override
        protected String doInBackground(String... strings) {

            try {
                ConnectionHelper con = new ConnectionHelper();
                Connection connect = con.connectionclass();// Connect to database
                if (connect == null) {
                    ConnectionResult = "Check Your Internet Access!";
                } else {
                        String query = "select Product_Id,Product_name,Product_price,Front_View,Back_View,Side_View  from tblSubCategoryProducts where SubCategory_Id='"+Shop_id+"'";
                        Statement stmt = connect.createStatement();
                        ResultSet rs = stmt.executeQuery(query);
                        if(rs.next()){
                            hasProduct = true;
                            do{
                                products.add(new ProductClass(rs.getString("Product_Id"),Shop_id,rs.getString("Product_name"),rs.getString("Product_price"),rs.getBytes("Front_View"),rs.getBytes("Back_View"),rs.getBytes("Side_View")));
                            }while (rs.next());
                        }
                    isSuccess = true;
                    ConnectionResult = "successful";
                    connect.close();
                }
            } catch (Exception ex) {
                ConnectionResult = ex.getMessage();
            }
            return ConnectionResult;
        }

        @Override
        protected void onPostExecute(String s) {
            progressBar.setVisibility(View.GONE);
            if(isSuccess){
                if(hasProduct) {
                    ProductAdapter productAdapter = new ProductAdapter(context, products,Shop_id);
                    recyclerView.setAdapter(productAdapter);
                }
                else Toast.makeText(context,"No products Found",Toast.LENGTH_LONG).show();
            }
            else Toast.makeText(context,s,Toast.LENGTH_LONG).show();
        }
    }



}
