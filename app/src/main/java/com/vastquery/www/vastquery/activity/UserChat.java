package com.vastquery.www.vastquery.activity;

import android.app.ProgressDialog;
import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.vastquery.www.vastquery.DatabaseConnection.ConnectionHelper;
import com.vastquery.www.vastquery.PropertyClasses.ChatDetails;
import com.vastquery.www.vastquery.R;
import com.vastquery.www.vastquery.helper.ChatMessagesAdapter;
import com.vastquery.www.vastquery.helper.PrefManager;


import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;

public class UserChat extends AppCompatActivity {

    Toolbar toolbar;


    public List<ChatDetails> itemArrayList;
    RecyclerView recyclerView;
    LinearLayoutManager linearLayoutManager;
    String group_id;
    ProgressBar progressBar;
    int owner_id,user_id;
    String shop_id,message;
    PrefManager pref;
    HashMap<String,String> profile;
    AutoCompleteTextView chatText;
    ImageButton buttonSend;
    Date date;
    ProgressDialog dialog;
    java.sql.Date datesql;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_chat);

        //toolbar

        toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);


        shop_id = getIntent().getStringExtra("shop_id");
        owner_id = getIntent().getIntExtra("addedby",0);

        pref = new PrefManager(UserChat.this);
        profile = pref.getUserDetails();
        user_id = Integer.parseInt(profile.get("id"));


        progressBar = findViewById(R.id.progressBar);

        chatText = findViewById(R.id.chatText);
        buttonSend = findViewById(R.id.buttonSend);

        recyclerView = findViewById(R.id.recyclerview);
        linearLayoutManager = new LinearLayoutManager(UserChat.this);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(linearLayoutManager);
        itemArrayList = new ArrayList<>();

        GetChats getChats = new GetChats();
        getChats.execute();



        buttonSend.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                message = chatText.getText().toString().trim();
                if(message.length()!=0){
                    date = new Date();
                    datesql = new java.sql.Date(date.getTime());
                    UpdateTime updateTime = new UpdateTime();
                    updateTime.execute();

                }
            }
        });

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if(item.getItemId() == android.R.id.home){
            onBackPressed();
            return true;
        }
        return super.onOptionsItemSelected(item);
    }


    public class GetChats extends AsyncTask<String,String,String> {
        String message;
        boolean issuccess=false;

        @Override
        protected void onPreExecute() {
            progressBar.setVisibility(View.VISIBLE);
        }

        @Override
        protected String doInBackground(String... strings) {
            try {
                ConnectionHelper con = new ConnectionHelper();
                Connection connect = con.connectionclass();// Connect to database
                if (connect == null) {
                    message = "Check Your Internet Access!";
                } else {
                    String query = "Select User_Id,Message,Reply,SubCategory_Id,Owner_Id,Status,Id" +
                            " from tblMessageBox where User_Id='"+user_id+"' and SubCategory_Id='"+shop_id+"' " +
                            "and Owner_Id='"+owner_id+"'";
                    Statement stmt = connect.createStatement();
                    ResultSet rs = stmt.executeQuery(query);
                    if (rs.next()) {
                        issuccess = true;
                        do {
                            itemArrayList.add(new ChatDetails(rs.getInt("User_Id"),rs.getString("Message"),
                                   rs.getString("Reply"), rs.getString("SubCategory_Id"), rs.getInt("Owner_Id"),rs.getString("Status"),rs.getInt("Id")));
                        } while (rs.next());

                    }
                }
            } catch (Exception ex) {
                message = ex.getMessage();
            }
            return message;

        }

        @Override
        protected void onPostExecute(String s) {
            progressBar.setVisibility(View.GONE);
            if(issuccess){
                ChatMessagesAdapter chatMessagesAdapter = new ChatMessagesAdapter(UserChat.this,itemArrayList);
                recyclerView.setAdapter(chatMessagesAdapter);
            }else{
                Toast.makeText(UserChat.this,"No chats found",Toast.LENGTH_LONG).show();
            }

        }
    }

    public class UpdateTime extends AsyncTask<String,String,String>{
        String result;
        boolean issuccess=false;

        @Override
        protected void onPreExecute() {
            dialog =  ProgressDialog.show(UserChat.this,"","Loading...",true);
        }

        @Override
        protected String doInBackground(String... strings) {
            try {
                ConnectionHelper con = new ConnectionHelper();
                Connection connect = con.connectionclass();// Connect to database
                if (connect == null) {
                    result = "Check Your Internet Access!";
                } else {
                    issuccess = true;
                    String query = "Insert into tblMessageBox(User_Id,Message,SubCategory_Id,Owner_Id,Msg_Posted_Time,Status)" +
                            " values ('"+user_id+"','"+message+"','"+shop_id+"','"+owner_id+"',?,'N')";
                    PreparedStatement preStmt = connect.prepareStatement(query);
                    preStmt.setDate(1,datesql);
                    preStmt.execute();
                    result = "updated";
                    connect.close();
                }
            } catch (Exception ex) {
                result = ex.getMessage();
            }
            return result;

        }

        @Override
        protected void onPostExecute(String s) {
            dialog.dismiss();
            if(issuccess){
                itemArrayList.clear();
                chatText.setText("");
                GetChats getChats = new GetChats();
                getChats.execute();
                Toast.makeText(UserChat.this,s,Toast.LENGTH_LONG).show();
            }else{
                Toast.makeText(UserChat.this,"Error in Updating",Toast.LENGTH_LONG).show();
            }

        }

    }

}
