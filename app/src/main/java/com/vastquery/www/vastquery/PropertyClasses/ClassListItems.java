package com.vastquery.www.vastquery.PropertyClasses;



public class ClassListItems {


    public byte[] img; //Image URL
    public String name,address; //Name
    public int id;
    public ClassListItems(int id,String name,String address,byte[] img){
        this.id = id;
        this.img = img;
        this.name = name;
        this.address = address;
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
