package com.vastquery.www.vastquery.PropertyClasses;



public class ClassListItems {


    public byte[] img; //Image URL
    public String name,address,id,group_id;
    int addedby;
    public ClassListItems(String group_id,String id,String name,String address,byte[] img,int addedby){
        this.group_id = group_id;
        this.id = id;
        this.img = img;
        this.name = name;
        this.address = address;
        this.addedby = addedby;
    }

    public int getAddedby() {
        return addedby;
    }

    public String getGroup_id(){return  group_id;}

    public String getId() {
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
