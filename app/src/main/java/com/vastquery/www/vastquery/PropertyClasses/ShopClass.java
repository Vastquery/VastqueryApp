package com.vastquery.www.vastquery.PropertyClasses;


public class ShopClass {
    String pincode,email,phone,mobile1,mobile2,website,ownername;
    byte[] logo,owner;

    public ShopClass( String pincode, String email, String phone, String mobile1, String mobile2, String website, byte[] logo, byte[] owner, String ownername) {
        this.pincode = pincode;
        this.email = email;
        this.phone = phone;
        this.mobile1 = mobile1;
        this.mobile2 = mobile2;
        this.website = website;
        this.ownername = ownername;
        this.logo = logo;
        this.owner = owner;
    }

    public String getPincode() {
        return pincode;
    }

    public String getEmail() {
        return email;
    }

    public String getPhone() {
        return phone;
    }

    public String getMobile1() {
        return mobile1;
    }

    public String getMobile2() {
        return mobile2;
    }

    public String getWebsite() {
        return website;
    }

    public String getOwnername() {
        return ownername;
    }

    public byte[] getLogo() {
        return logo;
    }

    public byte[] getOwner() {
        return owner;
    }
}
