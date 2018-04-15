package com.vastquery.www.vastquery.activity;

import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.BitmapDrawable;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Environment;
import android.support.v7.app.AlertDialog;
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
import com.vastquery.www.vastquery.DatabaseConnection.GetId;
import com.vastquery.www.vastquery.DatabaseConnection.GetIntId;
import com.vastquery.www.vastquery.DatabaseConnection.InsetSkills;
import com.vastquery.www.vastquery.R;
import com.vastquery.www.vastquery.helper.PrefManager;

import java.io.ByteArrayOutputStream;
import java.io.FileNotFoundException;
import java.io.InputStream;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;

import static com.vastquery.www.vastquery.activity.RegisterActivity.isValidEmail;
import static com.vastquery.www.vastquery.activity.RegisterActivity.isValidPhoneNumber;
import static com.vastquery.www.vastquery.activity.postShopForm.GetRollId;

public class postProfForm extends AppCompatActivity implements View.OnClickListener {

    private static final int RESULT_LOAD_IMAGE = 1;
    private AutoCompleteTextView skillset,description,prof_name,prof_email,prof_address,prof_subtye,prof_city,prof_pincode,prof_phone,whatsapp,facebook,prof_website;
    private Spinner prof_state,prof_district;
    private Button prof_type,prof_clear,prof_submit;
    private ImageButton shoplogo_button;
    private ImageView image_photo;
    byte[] byteArray;
    ProgressDialog dialog;
    SimpleDateFormat formattedDate;
    Date inputDate;
    PrefManager pref;
    HashMap<String, String> profile;
    String rollnum,rollid,state,skillsettext,shopdescribtion,subcategory_id,shopname,shoptype,shopstate,shopemail,shopdistrict,shopaddress,shopcity,shoppincode,shopphone,shopmobile1,shopmobile2,shopwebsite,ownername;
    int id,skill_id;

    boolean[] checkedItems;
    List<String> names,Cat_Id;
    ArrayList<Integer> mUserItems;



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
        whatsapp = findViewById(R.id.whatsapp);
        facebook = findViewById(R.id.facebook);
        prof_website = findViewById(R.id.prof_website);
        shoplogo_button = findViewById(R.id.shoplogo_button);
        image_photo = findViewById(R.id.image_photo);
        prof_email = findViewById(R.id.prof_email);
        description = findViewById(R.id.description);
        skillset = findViewById(R.id.skillset);
        prof_submit = findViewById(R.id.prof_submit);

        this.getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_HIDDEN);

        postShopForm.GetData getNames = new postShopForm.GetData("select Category_Name from tblCategory where Group_Id='G_2'", "Category_Name");
        names = getNames.doInBackground();
        mUserItems = new ArrayList<>();
        postShopForm.setDistrictState(postProfForm.this,prof_state,prof_district);
        postShopForm.GetData getCatIds = new postShopForm.GetData("select Cat_Id from tblCategory where Group_Id='G_2'","Cat_Id");
        Cat_Id = getCatIds.doInBackground();

        prof_type.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String[] listNames = new String[names.size()];
                listNames = names.toArray(listNames);
                final AlertDialog.Builder mBuilder = new AlertDialog.Builder(postProfForm.this);
                mBuilder.setTitle(R.string.dialog_title);
                mBuilder.setMultiChoiceItems(listNames, checkedItems, new DialogInterface.OnMultiChoiceClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int position, boolean isChecked) {
                        if(isChecked){
                            mUserItems.add(position);
                        }else{
                            mUserItems.remove(Integer.valueOf(position));
                        }
                    }
                });
                mBuilder.setCancelable(false);
                mBuilder.setPositiveButton(R.string.ok_label, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {

                    }
                });
                mBuilder.setNegativeButton(R.string.dismiss_label, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        dialogInterface.dismiss();
                    }
                });
                mBuilder.setNeutralButton(R.string.clear_all_label, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        mUserItems.clear();
                    }
                });
                AlertDialog mDialog = mBuilder.create();
                mDialog.show();
            }
        });

        pref = new PrefManager(getApplicationContext());
        profile = pref.getUserDetails();

        formattedDate = new SimpleDateFormat("MM/dd/yyyy HH:mm:ss");
        inputDate = new Date();

        //Button action
        prof_clear.setOnClickListener(this);
        shoplogo_button.setOnClickListener(this);
        prof_submit.setOnClickListener(this);

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
        prof_state.setSelection(0);
        prof_address.setText("");
        prof_city.setText("");
        prof_pincode.setText("");
        prof_phone.setText("");
        whatsapp.setText("");
        facebook.setText("");
        prof_website.setText("");
        image_photo.setImageDrawable(null);
    }

    public void validateForm(){
        shopname = prof_name.getText().toString().trim();
        //shoptype = prof_type.getSelectedItem().toString().trim();
        shopstate = prof_state.getSelectedItem().toString().trim();
        shopaddress = prof_address.getText().toString().trim();
        shopcity = prof_city.getText().toString().trim();
        shoppincode = prof_pincode.getText().toString().trim();
        shopphone = prof_phone.getText().toString().trim();
        shopmobile1 = whatsapp.getText().toString().trim();
        shopmobile2 = facebook.getText().toString().trim();
        shopwebsite = prof_website.getText().toString().trim();
        shopdistrict = prof_district.getSelectedItemPosition()+"";
        shopemail = prof_email.getText().toString().trim();
        shopdescribtion = description.getText().toString().trim();
        skillsettext = skillset.getText().toString().trim();
        if(skillsettext.length() == 0||shopname.length()==0 || shopstate.equals("-select the state-") || shopaddress.length()==0||
                shopcity.length()==0 || shoppincode.length()==0|| mUserItems.size() == 0 || shopdistrict.equals("-select the District-")){
            Toast.makeText(postProfForm.this,"Fill all Details",Toast.LENGTH_LONG).show();
        }else{
            if(!isValidPhoneNumber(shopphone)&&!isValidEmail(shopemail)){
                Toast.makeText(postProfForm.this,"Enter valid phone number",Toast.LENGTH_LONG).show();
            }else{
                if(image_photo.getDrawable()==null){
                    Toast.makeText(postProfForm.this,"select the pohotos",Toast.LENGTH_LONG).show();
                }else{

                    
                    GetAllIdsprof getAllIdsprof = new GetAllIdsprof();
                    getAllIdsprof.execute();

                    if(rollid.length() == 0){
                        Toast.makeText(postProfForm.this,"District full",Toast.LENGTH_LONG).show();
                    }else {

                        final String check = "Select * from tblSubcategorySkills where Skill_Id='" + skill_id + "'";
                        InsetSkills insetSkills = new InsetSkills(postProfForm.this, "Insert into tblSubcategorySkills(Group_Id,Cat_Id,SubCategory_Id,Skill_Id,Skill_desc)" +
                                " values ('G_2','" + Cat_Id + "','" + subcategory_id + "','" + skill_id + "','" + skillsettext + "')", check);
                        insetSkills.execute();
                        for (Integer items : mUserItems){
                            InputDataProf inputDataProf = new InputDataProf();
                            inputDataProf.execute(Cat_Id.get(items));
                        }
                    }

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
                    String query = "Insert into tblSubCategory(Group_Id,Cat_Id,SubCategory_Id,SubCategory_Name," +
                            "SubCategory_Address,SubCategory_City,SubCategory_Country," +
                            "SubCategory_State,SubCategory_District,SubCategory_Pincode"+
                            ",SubCategory_Logo,Id,RollNo,RollId," +
                            "SubCategory_Description,SubCategory_Addby)"+
                            " values ('G_2','"+Cat_Id.get(0)+"','"+subcategory_id+"','"+shopname+"','"+shopaddress+"','"
                            +shopcity+"','India','"+state+"'" + ",'"+shopdistrict+"','"+shoppincode
                            +"',?,'"+id+"','"+rollnum+"','"+rollid+"','"+shopdescribtion+"','"+profile.get("id")+"')";
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

    // get subcategory id
    public static String GetsId(String query, String maximum){
        GetId getid = new GetId(query,maximum);
        if(getid.getS_Id() == null) return incrementId("S_1");
        return incrementId(getid.getS_Id());
    }
    // increment id
    public static String incrementId(String s){
        String[] temp = s.split("_");
        int value = Integer.parseInt(temp[1]);
        value++;
        return temp[0]+"_"+value;
    }
    // to get id
    public static int GetId(String query , String maxi){
        GetIntId getid = new GetIntId(query,maxi);
        return (getid.getId()+1);
    }

    public static String GetStateId(String query,String id){
        GetId getStateId = new GetId(query,id);
        return  getStateId.getS_Id();
    }


    // to get all the ids
    public class GetAllIdsprof extends AsyncTask<String,String,String>{
        String message="";
        @Override
        protected String doInBackground(String... strings) {

            try {
                state = GetStateId("select State_ID from tblStates where State_Name='"+shopstate+"'","State_ID");
                subcategory_id = GetsId("select max(SubCategory_Id) as maximum from tblSubCategory", "maximum");
                id = GetId("select max(Id) as maxi from tblSubCategory", "maxi");
                skill_id = GetId("select max(Skill_Id) as maxi from tblSubCategorySkills","maxi");

                rollid = GetRollId("select max(RollId) as roll from tblSubCategory where" +
                        " SubCategory_District ='21' and SubCategory_State='TN'" +
                        " and Group_Id='G_2'","roll");
                rollnum = state+shopdistrict+"P"+rollid;

                message = rollnum;
            }catch(Exception ex){
                message = ex.getMessage();
            }
            return message;
        }

        @Override
        protected void onPostExecute(String s) {
            Toast.makeText(postProfForm.this,s,Toast.LENGTH_LONG).show();

        }
    }
}
