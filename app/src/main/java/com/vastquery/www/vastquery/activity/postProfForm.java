package com.vastquery.www.vastquery.activity;

import android.app.ProgressDialog;
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
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.List;

import static com.vastquery.www.vastquery.activity.RegisterActivity.isValidEmail;
import static com.vastquery.www.vastquery.activity.RegisterActivity.isValidPhoneNumber;

public class postProfForm extends AppCompatActivity implements View.OnClickListener {

    private static final int RESULT_LOAD_IMAGE = 1;
    private AutoCompleteTextView prof_name,prof_email,prof_address,prof_subtye,prof_city,prof_pincode,prof_phone,prof_mobile1,prof_mobile2,prof_website;
    private Spinner prof_type,prof_state,prof_district;
    private Button prof_clear;
    private ImageButton shoplogo_button;
    private ImageView image_photo;
    byte[] byteArray;
    ProgressDialog dialog;
    SimpleDateFormat formattedDate;
    Date inputDate;
    PrefManager pref;
    HashMap<String, String> profile;
    String shopname,shoptype,shopstate,shopemail,shopdistrict,shopaddress,shopcity,shoppincode,shopphone,shopmobile1,shopmobile2,shopwebsite,ownername;


    String[] names = {"-select the professional type-","Accountant","Adminstrator","Analyst","Architect","Archivist","Artist","Author",
            "Baker","Barber","Bartender","Beautician","Broadcaster","Bookkeeper","Bricklayer",
            "Care worker","Carpenter","Chef","Cleaner","Counselor","Decorater",
            "Doctor","Driver","Electrician","Engineer","Enterpreneur","Farmer",
            "Firefighter","Florist","Instructor","Journalist","Labourer",
            "Lawyer","Librarian","Mechanic","Masseur","Musician",
            "Nurse","Painter","Photographer","Plumber","Police","Politician","Scientist",
            "Social worker","Stock broker","Vocalist","Water supplier"};


    @Override
    protected void onCreate(Bundle savedInstanceState)  {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_post_prof_form);

        prof_clear = findViewById(R.id.prof_clear);
        prof_name = findViewById(R.id.prof_name);
        prof_type = findViewById(R.id.prof_type);
        prof_state = findViewById(R.id.prof_state);
        prof_district = findViewById(R.id.prof_district);
        prof_address = findViewById(R.id.prof_address);
        prof_city = findViewById(R.id.prof_city);
        prof_pincode = findViewById(R.id.prof_pincode);
        prof_phone = findViewById(R.id.prof_phone);
        prof_mobile1 = findViewById(R.id.prof_mobile1);
        prof_mobile2 = findViewById(R.id.prof_mobile2);
        prof_website = findViewById(R.id.prof_website);
        prof_subtye = findViewById(R.id.prof_subtye);
        shoplogo_button = findViewById(R.id.shoplogo_button);
        image_photo = findViewById(R.id.image_photo);
        prof_email = findViewById(R.id.prof_email);

        this.getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_HIDDEN);

        ArrayAdapter<String> shopTypeAdapter = new ArrayAdapter<>(postProfForm.this,
                android.R.layout.simple_list_item_1,names);
        shopTypeAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        prof_type.setAdapter(shopTypeAdapter);

        postShopForm.setDistrictState(postProfForm.this,prof_state,prof_district);

        pref = new PrefManager(getApplicationContext());
        profile = pref.getUserDetails();

        formattedDate = new SimpleDateFormat("MM/dd/yyyy HH:mm:ss");
        inputDate = new Date();

        //Button action
        prof_clear.setOnClickListener(this);
        shoplogo_button.setOnClickListener(this);
    }

    @Override
    public void onClick(View view) {
        switch(view.getId()){
            case R.id.prof_submit:
                validateForm();
                break;
            case R.id.prof_clear:
                clearaboveData();
                break;
            case R.id.shoplogo_button:
                selectImage();
                break;
        }
    }

    //clear button
    private void clearaboveData(){
        prof_name.setText("");
        prof_type.setSelection(0);
        prof_state.setSelection(0);
        prof_address.setText("");
        prof_city.setText("");
        prof_pincode.setText("");
        prof_phone.setText("");
        prof_mobile1.setText("");
        prof_mobile2.setText("");
        prof_website.setText("");
        image_photo.setImageDrawable(null);
    }

    public void validateForm(){
        shopname = prof_name.getText().toString().trim();
        shoptype = prof_type.getSelectedItem().toString().trim();
        shopstate = prof_state.getSelectedItem().toString().trim();
        shopaddress = prof_address.getText().toString().trim();
        shopcity = prof_city.getText().toString().trim();
        shoppincode = prof_pincode.getText().toString().trim();
        shopphone = prof_phone.getText().toString().trim();
        shopmobile1 = prof_mobile1.getText().toString().trim();
        shopmobile2 = prof_mobile2.getText().toString().trim();
        shopwebsite = prof_website.getText().toString().trim();
        shopdistrict = prof_district.getSelectedItemPosition()+"";
        shopemail = prof_email.getText().toString().trim();
        if(shopname.length()==0 || shoptype.equals("-select the shop type-") || shopstate.equals("-select the state-") || shopaddress.length()==0||
                shopcity.length()==0 || shoppincode.length()==0 || ownername.length()==0 || shopdistrict.equals("-select the District-")){
            Toast.makeText(postProfForm.this,"EnterValid Details",Toast.LENGTH_LONG).show();
        }else{
            if(!isValidPhoneNumber(shopphone)&&!isValidEmail(shopemail)){
                Toast.makeText(postProfForm.this,"Enter valid phone number",Toast.LENGTH_LONG).show();
            }else{
                if(image_photo.getDrawable()==null){
                    Toast.makeText(postProfForm.this,"select the pohotos",Toast.LENGTH_LONG).show();
                }else{
                    InputDataProf inputDataProf = new InputDataProf();
                    inputDataProf.execute();
                }
            }
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
            Toast.makeText(postProfForm.this, selectedImage.toString(), Toast.LENGTH_LONG).show();
            InputStream imageStream;
            try {
                imageStream = getContentResolver().openInputStream(selectedImage);
                originBitmap = BitmapFactory.decodeStream(imageStream);
            } catch (FileNotFoundException e) {
                Toast.makeText(postProfForm.this, e.getMessage().toString(), Toast.LENGTH_SHORT).show();
            }
            if (originBitmap != null) {
                this.image_photo.setImageBitmap(originBitmap);
                Log.w("Image Setted in", "Done Loading Image");
                try {
                    Bitmap image = ((BitmapDrawable) image_photo.getDrawable()).getBitmap();
                    ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
                    image.compress(Bitmap.CompressFormat.JPEG, 100, byteArrayOutputStream);
                    byteArray = byteArrayOutputStream.toByteArray();
                    //encodedImage = Base64.encodeToString(byteArray, Base64.DEFAULT);


                } catch (Exception e) {
                    Log.w("OOooooooooo", "exception");
                }
            }
        }
        else
        {
            System.out.println("Error Occured");
        }
    }

    public class InputDataProf extends AsyncTask<String,String,String> {

        String ConnectionResult;
        @Override
        protected void onPreExecute() {
            dialog =  ProgressDialog.show(postProfForm.this,"","Loading...",true);

        }

        @Override
        protected String doInBackground(String... strings) {

            try {
                ConnectionHelper con = new ConnectionHelper();
                Connection connect = con.connectionclass();// Connect to database
                if (connect == null) {
                    ConnectionResult = "Check Your Internet Access!";
                } else {
                    String query = "Insert into tblShop(Prof_Name,Prof_type,Address,City,District,State,Pin_Code,Email,"+
                            "Phone,Mobile1,Mobile2,Website,Logo,Added_By,A_Date,M_Date)"+
                            " values ('"+shopname+"','"+shoptype+"','"+shopaddress+"','"+shopcity+"','"+shopdistrict+"'" +
                            ",'"+shopstate+"','"+shoppincode+"','"+shopemail+"','"+shopphone+"','"+shopmobile1+"'" +
                            ",'"+shopmobile2+"','"+shopwebsite+"',?,'"+ownername+"','"+profile.get("email")+"'" +
                            ",'"+formattedDate.format(inputDate)+"','"+formattedDate.format(inputDate)+"')";
                    PreparedStatement preStmt = connect.prepareStatement(query);
                    preStmt.setBytes(1,byteArray);
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
            Toast.makeText(postProfForm.this,s,Toast.LENGTH_LONG).show();
        }
    }
}
