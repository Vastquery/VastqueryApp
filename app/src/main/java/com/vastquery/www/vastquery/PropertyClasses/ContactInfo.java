package com.vastquery.www.vastquery.PropertyClasses;

public class ContactInfo {

    String mobile,email,website,facebook,twitter,whatsapp;


    public ContactInfo(){}


    public ContactInfo(String mobile, String email, String website, String facebook, String twitter, String whatsapp) {
        this.mobile = mobile;
        this.email = email;
        this.website = website;
        this.facebook = facebook;
        this.twitter = twitter;
        this.whatsapp = whatsapp;
    }

    public String getMobile() {
        return mobile;
    }

    public String getEmail() {
        return email;
    }

    public String getWebsite() {
        return website;
    }

    public String getFacebook() {
        return facebook;
    }

    public String getTwitter() {
        return twitter;
    }

    public String getWhatsapp() {
        return whatsapp;
    }
}
