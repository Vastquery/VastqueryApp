package com.vastquery.www.vastquery.PropertyClasses;


public class ProfClass {
    String name,address,pincode,city,code,description,skills;
    byte[] photo;
    int addedby;

    public ProfClass(String name, String address, String pincode, String city, byte[] photo, String code, String description,int addedby, String skills) {
        this.name = name;
        this.address = address;
        this.pincode = pincode;
        this.city = city;
        this.code = code;
        this.description = description;
        this.skills = skills;
        this.photo = photo;
        this.addedby = addedby;
    }



    public int getAddedby() {
        return addedby;
    }

    public String getName() {
        return name;
    }

    public String getAddress() {
        return address;
    }

    public String getPin_Code() {
        return pincode;
    }

    public String getCity() {
        return city;
    }

    public String getCode(){ return code; }

    public byte[] getPhoto(){return photo;}

    public String getSkills(){ return skills ; }

    public String getDescription() {
        return description;
    }
}
