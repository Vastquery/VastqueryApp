package com.vastquery.www.vastquery.activity;

import android.app.ProgressDialog;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Environment;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.WindowManager;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.Toast;

import com.vastquery.www.vastquery.DatabaseConnection.ConnectionHelper;
import com.vastquery.www.vastquery.DatabaseConnection.GetId;
import com.vastquery.www.vastquery.DatabaseConnection.GetIntId;
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
import java.util.HashMap;
import java.util.List;

import static com.vastquery.www.vastquery.activity.RegisterActivity.isValidEmail;
import static com.vastquery.www.vastquery.activity.RegisterActivity.isValidPhoneNumber;
import static com.vastquery.www.vastquery.activity.postShopForm.setDistrictState;

public class ServicePostForm extends AppCompatActivity implements View.OnClickListener {

    private static final int RESULT_LOAD_IMAGE = 1;


    private AutoCompleteTextView description, shop_name, shop_email, shop_address, shop_city, shop_pincode, shop_phone, whatsapp, facebook, twitter, shop_website, shop_owner;
    private Spinner shop_state, shop_district;
    private Button shop_clear, shop_submit;
    private ImageView image_photo, owner_photo, image_logo;
    private ImageButton shoplogo_button, shopphoto_button, ownerphoto_button;
    int value;
    byte[] shop, logo, owner;
    String state, facilitydesc,subcategory_id,cat_Id, rollnum, rollid, shopdescription, shopname, shopstate, shopemail, shopdistrict, shopaddress, shopcity, shoppincode, shopphone, shopwhatsapp, shopfacebook, shopwebsite, ownername;
    int id;
    private ProgressDialog dialog;

    boolean exits;
    ArrayList<Integer> mUserItems;
    PrefManager pref;
    HashMap<String, String> profile;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_service_post_form);

        shop_clear = findViewById(R.id.shop_clear);
        shop_submit = findViewById(R.id.shop_submit);
        shop_name = findViewById(R.id.shop_name);
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

        this.getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_HIDDEN);

        cat_Id = getIntent().getStringExtra("catId");
        facilitydesc = getIntent().getStringExtra("facilitydesc");
        exits =getIntent().getBooleanExtra("new",false);

        setDistrictState(ServicePostForm.this, shop_state, shop_district);
        pref = new PrefManager(getApplicationContext());
        profile = pref.getUserDetails();

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

    public void selectImage() {
        if (Environment.getExternalStorageState().equals(Environment.MEDIA_MOUNTED) && !Environment.getExternalStorageState().equals(Environment.MEDIA_CHECKING)) {
            Intent galleryIntent = new Intent();
            galleryIntent.setType("image/*");
            galleryIntent.setAction(Intent.ACTION_GET_CONTENT);

            //startActivityForResult(galleryIntent, RESULT_LOAD_IMAGE);
            startActivityForResult(Intent.createChooser(galleryIntent, "Select Picture"), RESULT_LOAD_IMAGE);
        }

    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == RESULT_LOAD_IMAGE && resultCode == RESULT_OK && null != data) {
            // getting the selected image, setting in imageview and converting it to byte and base 64
            Bitmap originBitmap = null;
            Uri selectedImage = data.getData();
            Toast.makeText(ServicePostForm.this, selectedImage.toString(), Toast.LENGTH_LONG).show();
            InputStream imageStream;
            try {
                imageStream = getContentResolver().openInputStream(selectedImage);
                originBitmap = BitmapFactory.decodeStream(imageStream);
            } catch (FileNotFoundException e) {
                Toast.makeText(ServicePostForm.this, e.getMessage().toString(), Toast.LENGTH_SHORT).show();
            }
            if (originBitmap != null) {
                loadImage(originBitmap);
                Log.w("Image Setted in", "Done Loading Image");
            }
        } else {
            System.out.println("Error Occured");
        }
    }

    public void loadImage(Bitmap originBitmap) {
        switch (value) {
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
        description.setText("");
        mUserItems.clear();
        shop_state.setSelection(0);
        shop_address.setText("");
        shop_city.setText("");
        shop_pincode.setText("");
        shop_phone.setText("");
        shop_website.setText("");
        shop_owner.setText("");
        whatsapp.setText("");
        facebook.setText("");
        twitter.setText("");
        shop_email.setText("");
        image_photo.setImageDrawable(null);
        image_logo.setImageDrawable(null);
        owner_photo.setImageDrawable(null);
    }

    public void validateForm() {
        shopname = shop_name.getText().toString().trim();
        shopstate = shop_state.getSelectedItem().toString().trim();
        shopaddress = shop_address.getText().toString().trim();
        shopcity = shop_city.getText().toString().trim();
        shoppincode = shop_pincode.getText().toString().trim();
        shopphone = shop_phone.getText().toString().trim();
        shopwhatsapp = whatsapp.getText().toString().trim();
        shopfacebook = facebook.getText().toString().trim();
        shopwebsite = shop_website.getText().toString().trim();
        ownername = shop_owner.getText().toString().trim();
        shopdistrict = shop_district.getSelectedItemPosition() + "";
        shopemail = shop_email.getText().toString().trim();
        shopdescription = description.getText().toString().trim();

        if (shopname.length() == 0 || mUserItems.isEmpty() || shopstate.equals("-select the state-") || shopaddress.length() == 0 ||
                shopcity.length() == 0 || shoppincode.length() == 0 || ownername.length() == 0 || shopdistrict.equals("-select the District-")) {
            Toast.makeText(ServicePostForm.this, "EnterValid Details", Toast.LENGTH_LONG).show();
        } else {
            if (!isValidPhoneNumber(shopphone) && !isValidEmail(shopemail)) {
                Toast.makeText(ServicePostForm.this, "Enter valid phone number", Toast.LENGTH_LONG).show();
            } else {
                if (image_logo.getDrawable() == null && image_photo.getDrawable() == null && owner_photo.getDrawable() == null) {
                    Toast.makeText(ServicePostForm.this, "select the pohotos", Toast.LENGTH_LONG).show();
                } else {
                    GetAllIds getAllIds = new GetAllIds();
                    getAllIds.execute();

                    AddService addService = new AddService();
                    addService.execute();
                }
            }
        }

    }

    public byte[] getByteArray(Bitmap bitmap) {

        ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
        try {

            bitmap.compress(Bitmap.CompressFormat.JPEG, 100, byteArrayOutputStream);

        } catch (Exception e) {
            Log.w("OOooooooooo", "exception");
        }
        return byteArrayOutputStream.toByteArray();
    }

    public class AddService extends AsyncTask<String, String, String> {

        String message;

        @Override
        protected void onPreExecute() {
            dialog =  ProgressDialog.show(ServicePostForm.this,"","Loading...",true);
        }

        @Override
        protected String doInBackground(String... strings) {
            try {
                ConnectionHelper con = new ConnectionHelper();
                Connection connect = con.connectionclass();// Connect to database
                if (connect == null) {
                    message = "Check Your Internet Access!";
                } else {
                    // Change below query according to your own database.
                    String query = "Insert into tblSubCategory(Group_Id,Cat_Id,SubCategory_Id,SubCategory_Name," +
                            "SubCategory_Address,SubCategory_City,SubCategory_Country," +
                            "SubCategory_State,SubCategory_District,SubCategory_Pincode"+
                            ",SubCategory_Logo,SubCategory_FrontLogo,SubCategory_OwnerName,SubCategory_OwnerPhoto,Id,RollNo,RollId," +
                            "SubCategory_Description,SubCategory_Addby)"+
                            " values ('G_3','"+cat_Id+"','"+subcategory_id+"','"+shopname+"','"+shopaddress+"','"
                            +shopcity+"','India','"+state+"'" + ",'"+shopdistrict+"','"+shoppincode
                            +"',?,?,'"+ownername+"',?,'"+id+"','"+rollnum+"','"+rollid+"','"+shopdescription+"','"+profile.get("id")+"')";
                    PreparedStatement preStmt = connect.prepareStatement(query);
                    preStmt.setBytes(1,logo);
                    preStmt.setBytes(2,shop);
                    preStmt.setBytes(3,owner);
                    preStmt.execute();
                    message = "Inserted successful";
                    connect.close();
                }
            } catch (Exception ex) {
                message = ex.getMessage();
            }


            return message;
        }

        @Override
        protected void onPostExecute(String s) {

            Toast.makeText(ServicePostForm.this, message, Toast.LENGTH_LONG).show();
            if(exits){
                InsertFacility insertFacility = new InsertFacility();
                insertFacility.execute();
            }else{
                dialog.dismiss();
            }
        }
    }

    // get subcategory id
    public static String GetsId(String query, String maximum) {
        GetId getid = new GetId(query, maximum);
        return incrementId(getid.getS_Id());
    }

    // increment id
    public static String incrementId(String s) {
        String[] temp = s.split("_");
        int value = Integer.parseInt(temp[1]);
        value++;
        return temp[0] + "_" + value;
    }

    // to get id
    public static int GetId(String query, String maxi) {
        GetIntId getid = new GetIntId(query, maxi);
        return (getid.getId() + 1);
    }

    // to get all the ids
    public class GetAllIds extends AsyncTask<String, String, String> {
        String message = "";

        @Override
        protected String doInBackground(String... strings) {
            GetId getStateId = new GetId("select State_ID where State_Name='" + shopstate + "'", "State_ID");
            try {
                message = "Id created";
                subcategory_id = GetsId("select max(SubCategory_Id) as maximum from tblSubCategory", "maximum");
                id = GetId("select max(Id) as maxi from tblSubCategory", "maxi");
                state = getStateId.getS_Id();
            } catch (Exception ex) {
                message = ex.getMessage();
            }
            return message;
        }

        @Override
        protected void onPostExecute(String s) {
            Toast.makeText(ServicePostForm.this, s, Toast.LENGTH_LONG).show();
        }
    }

    public class InsertFacility extends AsyncTask<String,String,String>{
        String message;
        int facilitiid;
        @Override
        protected String doInBackground(String... strings) {
            try{
                ConnectionHelper connect = new ConnectionHelper();
                Connection con = connect.connectionclass();
                if(con == null){
                    message = "check internet access";
                }else{
                    facilitiid = GetId("select max(Facility_Id) as maxi from tblSubCategoryFacility","maxi");
                    String query = "Insert into tblSubCategoryFacility(Group_Id,Cat_Id,SubCategory_Id,Facility_Id,Facilities_desc) values" +
                            " ('G_3','"+cat_Id+"','"+subcategory_id+"','"+facilitiid+"','"+facilitydesc+"')";
                    PreparedStatement preStmt = con.prepareStatement(query);
                    preStmt.execute();
                    con.close();

                }
            }catch (Exception ex){
                message = ex.getMessage();
            }
            return message;
        }

        @Override
        protected void onPostExecute(String s) {
            dialog.dismiss();
            Toast.makeText(ServicePostForm.this,message,Toast.LENGTH_LONG).show();
        }
    }
}
