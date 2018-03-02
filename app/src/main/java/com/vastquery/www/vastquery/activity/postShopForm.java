package com.vastquery.www.vastquery.activity;

import android.annotation.SuppressLint;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.BitmapDrawable;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Environment;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.WindowManager;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.Toast;

import com.vastquery.www.vastquery.DatabaseConnection.ConnectionHelper;
import com.vastquery.www.vastquery.R;
import com.vastquery.www.vastquery.helper.PrefManager;

import java.io.ByteArrayOutputStream;
import java.io.FileNotFoundException;
import java.io.InputStream;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;

import static com.vastquery.www.vastquery.activity.RegisterActivity.isValidEmail;
import static com.vastquery.www.vastquery.activity.RegisterActivity.isValidPhoneNumber;

public class postShopForm extends AppCompatActivity implements View.OnClickListener {

    private static final int RESULT_LOAD_IMAGE = 1;
    String[] names = {"-select the shop type-","Agriculture","Accommodation","Automobile","Bakeries","Beauty salon","Book shop",
                "Butcher shop","Caterers","Civil Contractor","Computers showroom","Daily Needs","Dance & Music",
                "Fitness","Driving School","Education & Training","Electronics","Emergency","Fruit & vegetables",
                "Furniture Shop","Hospitals","Hotels","House keeping","Jewelry Shop","Jobs consultancy",
                "Mobile showroom","Pet shop","Printing shop","Real Estate","Repairs","Transporters","Travels",
                "Utensils shop","Wedding"};

    private AutoCompleteTextView shop_name,shop_email,shop_address, shop_city, shop_pincode, shop_phone, shop_mobile1, shop_mobile2, shop_website, shop_owner;
    private Spinner shop_type, shop_state, shop_district;
    private Button shop_clear,shop_submit;
    private ImageView image_photo,owner_photo,image_logo;
    private ImageButton shoplogo_button,shopphoto_button,ownerphoto_button;
    int value;
    byte[] shop,logo,owner;
    String shopname,shoptype,shopstate,shopemail,shopdistrict,shopaddress,shopcity,shoppincode,shopphone,shopmobile1,shopmobile2,shopwebsite,ownername;
    private ProgressDialog dialog;

    PrefManager pref;
    HashMap<String, String> profile;
    SimpleDateFormat formattedDate;
    Date inputDate;
    @SuppressLint("SimpleDateFormat")
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
        shop_mobile1 = findViewById(R.id.shop_mobile1);
        shop_mobile2 = findViewById(R.id.shop_mobile2);
        shop_website = findViewById(R.id.shop_website);
        shop_owner = findViewById(R.id.shop_owner);
        image_photo = findViewById(R.id.image_photo);
        owner_photo = findViewById(R.id.owner_photo);
        image_logo = findViewById(R.id.image_logo);
        shoplogo_button = findViewById(R.id.shoplogo_button);
        shopphoto_button = findViewById(R.id.shopphoto_button);
        ownerphoto_button = findViewById(R.id.ownerphoto_button);

        this.getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_HIDDEN);

        //shop type adapter
        ArrayAdapter<String> shopTypeAdapter = new ArrayAdapter<String>(postShopForm.this,
                android.R.layout.simple_list_item_1, names);
        //to get string from string values getResources().getStringArray(R.array.shoptype)
        shopTypeAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        shop_type.setAdapter(shopTypeAdapter);

        setDistrictState(postShopForm.this,shop_state,shop_district);

        pref = new PrefManager(getApplicationContext());
        profile = pref.getUserDetails();

        formattedDate = new SimpleDateFormat("MM/dd/yyyy HH:mm:ss");
        inputDate = new Date();

        //Button action
        shop_clear.setOnClickListener(this);
        ownerphoto_button.setOnClickListener(this);
        shopphoto_button.setOnClickListener(this);
        shoplogo_button.setOnClickListener(this);
        shop_submit.setOnClickListener(this);
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.shop_submit:
                validateForm();
                break;
            case R.id.shop_clear:
                clearData();
                break;
            case R.id.ownerphoto_button:
                value = 1;
                selectImage();
                break;
            case R.id.shopphoto_button:
                value = 2;
                selectImage();
                break;
            case R.id.shoplogo_button:
                value = 3;
                selectImage();
                break;
        }
    }

    public  void selectImage(){
        if (Environment.getExternalStorageState().equals(Environment.MEDIA_MOUNTED)&& !Environment.getExternalStorageState().equals(Environment.MEDIA_CHECKING)) {
            Intent galleryIntent = new Intent();
            galleryIntent.setType("image/*");
            galleryIntent.setAction(Intent.ACTION_GET_CONTENT);

            //startActivityForResult(galleryIntent, RESULT_LOAD_IMAGE);
            startActivityForResult(Intent.createChooser(galleryIntent,"Select Picture"), RESULT_LOAD_IMAGE);
        }

    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == RESULT_LOAD_IMAGE && resultCode == RESULT_OK && null != data) {
            // getting the selected image, setting in imageview and converting it to byte and base 64
            Bitmap originBitmap = null;
            Uri selectedImage = data.getData();
            Toast.makeText(postShopForm.this, selectedImage.toString(), Toast.LENGTH_LONG).show();
            InputStream imageStream;
            try {
                imageStream = getContentResolver().openInputStream(selectedImage);
                originBitmap = BitmapFactory.decodeStream(imageStream);
            } catch (FileNotFoundException e) {
                Toast.makeText(postShopForm.this, e.getMessage().toString(), Toast.LENGTH_SHORT).show();
            }
            if (originBitmap != null) {
                loadImage(originBitmap);
                Log.w("Image Setted in", "Done Loading Image");
            }
        }
        else
        {
            System.out.println("Error Occured");
        }
    }

    public void loadImage(Bitmap originBitmap){
        switch (value){
            case 1:
                owner_photo.setImageBitmap(originBitmap);
                owner = getByteArray(originBitmap);
                break;
            case 2:
                image_photo.setImageBitmap(originBitmap);
                shop = getByteArray(originBitmap);
                break;
            case 3:
                image_logo.setImageBitmap(originBitmap);
                logo = getByteArray(originBitmap);
                break;
        }
    }
    //clear button
    private void clearData() {
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
        shop_email.setText("");
        image_photo.setImageDrawable(null);
        image_logo.setImageDrawable(null);
        owner_photo.setImageDrawable(null);
    }


    public void validateForm(){
        shopname = shop_name.getText().toString().trim();
        shoptype = shop_type.getSelectedItem().toString().trim();
        shopstate = shop_state.getSelectedItem().toString().trim();
        shopaddress = shop_address.getText().toString().trim();
        shopcity = shop_city.getText().toString().trim();
        shoppincode = shop_pincode.getText().toString().trim();
        shopphone = shop_phone.getText().toString().trim();
        shopmobile1 = shop_mobile1.getText().toString().trim();
        shopmobile2 = shop_mobile2.getText().toString().trim();
        shopwebsite = shop_website.getText().toString().trim();
        ownername = shop_owner.getText().toString().trim();
        shopdistrict = shop_district.getSelectedItemPosition()+"";
        shopemail = shop_email.getText().toString().trim();
        if(shopname.length()==0 || shoptype.equals("-select the shop type-") || shopstate.equals("-select the state-") || shopaddress.length()==0||
                shopcity.length()==0 || shoppincode.length()==0 || ownername.length()==0 || shopdistrict.equals("-select the District-")){
            Toast.makeText(postShopForm.this,"EnterValid Details",Toast.LENGTH_LONG).show();
        }else{
            if(!isValidPhoneNumber(shopphone)&&!isValidEmail(shopemail)){
                Toast.makeText(postShopForm.this,"Enter valid phone number",Toast.LENGTH_LONG).show();
            }else{
                if(image_logo.getDrawable()==null && image_photo.getDrawable()==null && owner_photo.getDrawable() == null){
                    Toast.makeText(postShopForm.this,"select the pohotos",Toast.LENGTH_LONG).show();
                }else{
                        InputData inputData = new InputData();
                        inputData.execute();
                }
            }
        }

    }
    public byte[] getByteArray(Bitmap bitmap){

        ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
        try {
            ;
            bitmap.compress(Bitmap.CompressFormat.JPEG, 100, byteArrayOutputStream);

        } catch (Exception e) {
            Log.w("OOooooooooo", "exception");
        }
        return byteArrayOutputStream.toByteArray();
    }

    public static class GetData {

        Connection connect;
        String ConnectionResult = "";
        Boolean isSuccess = false;
        String query, column;

        public GetData(String query, String column) {
            this.query = query;
            this.column = column;
        }


        public List<String> doInBackground() {
            final List<String> data = new ArrayList<>();
            Runnable r = new Runnable() {
                @Override
                public void run() {
                    try {
                        ConnectionHelper conStr = new ConnectionHelper();
                        connect = conStr.connectionclass();        // Connect to database
                        if (connect == null) {
                            ConnectionResult = "Check Your Internet Access!";
                        } else {
                            // Change below query according to your own database.
                            Statement stmt = connect.createStatement();
                            ResultSet rs = stmt.executeQuery(query);
                            while (rs.next()) {
                                data.add(rs.getString(column));
                            }
                            ConnectionResult = " successful";
                            isSuccess = true;
                            connect.close();
                        }
                    } catch (Exception ex) {
                        isSuccess = false;
                        ConnectionResult = ex.getMessage();
                    }
                }
            };
            Thread helperthread = new Thread(r);
            helperthread.start();
            return data;
        }
    }

    public static void setDistrictState(final Context context, final Spinner state, final Spinner district) {
        //state
        List<String> list_states = null;
        GetData getData = new GetData("select State_Name from tblStates", "State_Name");
        list_states = getData.doInBackground();
        list_states.add(0, "-select the state-");


        //state dropdown
        ArrayAdapter<String> stateAdapter = new ArrayAdapter<>(context,
                android.R.layout.simple_list_item_1, list_states);
        stateAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        state.setAdapter(stateAdapter);

        state.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                //district dropdown
                String query = "select Dt_Name from tblTN_DTs where St_ID=(select  State_ID from tblStates where State_Name='"+state.getSelectedItem().toString()+"')";
                GetData getData_district = new GetData(query, "Dt_Name");
                List<String> list_districts = null;
                list_districts = getData_district.doInBackground();
                list_districts.add(0, "-select the District-");
                ArrayAdapter<String> districtAdapter = new ArrayAdapter<String>(context,
                        android.R.layout.simple_list_item_1, list_districts);
                districtAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                district.setAdapter(districtAdapter);

            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {
            }
        });
    }
    public class InputData extends AsyncTask<String,String,String> {

        String ConnectionResult;
        @Override
        protected void onPreExecute() {
            dialog =  ProgressDialog.show(postShopForm.this,"","Loading...",true);

        }

        @Override
        protected String doInBackground(String... strings) {

            try {
                ConnectionHelper con = new ConnectionHelper();
                Connection connect = con.connectionclass();// Connect to database
                if (connect == null) {
                    ConnectionResult = "Check Your Internet Access!";
                } else {
                    String query = "Insert into tblShop(S_Name,S_type,Address,City,District,State,Pin_Code,Email,"+
                       "Phone,Mobile1,Mobile2,Website,Logo,Shop_Photo,Owner,Owner_Name,Added_By,A_Date,M_Date)"+
                            " values ('"+shopname+"','"+shoptype+"','"+shopaddress+"','"+shopcity+"','"+shopdistrict+"'" +
                            ",'"+shopstate+"','"+shoppincode+"','"+shopemail+"','"+shopphone+"','"+shopmobile1+"'" +
                            ",'"+shopmobile2+"','"+shopwebsite+"',?,?,?,'"+ownername+"','"+profile.get("email")+"'" +
                            ",'"+formattedDate.format(inputDate)+"','"+formattedDate.format(inputDate)+"')";
                    PreparedStatement preStmt = connect.prepareStatement(query);
                    preStmt.setBytes(1,logo);
                    preStmt.setBytes(2,shop);
                    preStmt.setBytes(3,owner);
                    preStmt.execute();
                    ConnectionResult = "Inserted successful";
                    connect.close();
                }
            } catch (Exception ex) {
                ConnectionResult = ex.getMessage();
            }
            return ConnectionResult;
        }

        @Override
        protected void onPostExecute(String s) {
            dialog.dismiss();
            Toast.makeText(postShopForm.this,s,Toast.LENGTH_LONG).show();
        }
    }
}