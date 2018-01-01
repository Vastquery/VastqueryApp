package com.vastquery.www.vastquery.activity;



public class ClassListItems {

    public byte[] img; //Image URL
    public String name; //Name

    public ClassListItems(String name,byte[] img)
    {
        this.img = img;
        this.name = name;
    }

    public byte[] getImg() {
        return img;
    }

    public String getName() {
        return name;
    }

}
