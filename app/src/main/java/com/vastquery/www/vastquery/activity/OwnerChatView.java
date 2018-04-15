package com.vastquery.www.vastquery.activity;

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
import android.widget.ImageButton;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.vastquery.www.vastquery.DatabaseConnection.ConnectionHelper;
import com.vastquery.www.vastquery.PropertyClasses.ChatDetails;
import com.vastquery.www.vastquery.R;
import com.vastquery.www.vastquery.helper.ChatMessagesAdapter;
import com.vastquery.www.vastquery.helper.OwnerChatAdapter;
import com.vastquery.www.vastquery.helper.PrefManager;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class OwnerChatView extends AppCompatActivity {

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

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_chat);

        //toolbar

        toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);


        shop_id = getIntent().getStringExtra("shop_id");
        user_id = getIntent().getIntExtra("user_id",0);

        pref = new PrefManager(OwnerChatView.this);
        profile = pref.getUserDetails();
        owner_id = Integer.parseInt(profile.get("id"));

        progressBar = findViewById(R.id.progressBar);

        chatText = findViewById(R.id.chatText);
        buttonSend = findViewById(R.id.buttonSend);
        chatText.setVisibility(View.GONE);
        buttonSend.setVisibility(View.GONE);

        recyclerView = findViewById(R.id.recyclerview);
        linearLayoutManager = new LinearLayoutManager(OwnerChatView.this);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(linearLayoutManager);
        itemArrayList = new ArrayList<>();

        GetChats getChats =new GetChats();
        getChats.execute();


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
                            "from tblMessageBox where User_Id='"+user_id+"' and SubCategory_Id='"+shop_id+"' " +
                            "and Owner_Id='"+owner_id+"'";
                    Statement stmt = connect.createStatement();
                    ResultSet rs = stmt.executeQuery(query);
                    if (rs.next()) {
                        issuccess = true;
                        message = "successful";
                        do {
                            itemArrayList.add(new ChatDetails(rs.getInt("User_Id"),rs.getString("Message"),rs.getString("Reply"), rs.getString("SubCategory_Id"), rs.getInt("Owner_Id"),rs.getString("Status"),rs.getInt("Id")));
                        }while (rs.next());

                    }
                    connect.close();
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
                chatText.setText("");
                OwnerChatAdapter chatAdapter = new OwnerChatAdapter(OwnerChatView.this,itemArrayList);
                recyclerView.setAdapter(chatAdapter);
            }else{
                Toast.makeText(OwnerChatView.this,s,Toast.LENGTH_LONG).show();
            }

        }
    }

}
