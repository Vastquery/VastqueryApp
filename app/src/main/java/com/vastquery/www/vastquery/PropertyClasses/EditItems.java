package com.vastquery.www.vastquery.PropertyClasses;

/**
 * Created by aj-ajay on 4/10/18.
 */

public class EditItems {


    public byte[] img; //Image URL
    public String name,address,s_id,group_id;
    int addedby,id;
    public EditItems(String group_id,String s_id,String name,String address,byte[] img,int id,int addedby){
        this.group_id = group_id;
        this.id = id;
        this.img = img;
        this.name = name;
        this.address = address;
        this.addedby = addedby;
        this.s_id = s_id;
    }

    public int getAddedby() {
        return addedby;
    }

    public String getGroup_id(){return  group_id;}

    public String getS_id() {
        return s_id;
    }

    public int getId() {
        return id;
    }

    public byte[] getImg() {
        return img;
    }

    public String getName() {
        return name;
    }

    public String getAddress() {
        return address;
    }

}
