package com.vastquery.www.vastquery.PropertyClasses;


import java.util.Date;

public class ReviewClass {
    String user_name,user_review;
    Date date;

    public ReviewClass(String user_name, String user_review,Date date) {
        this.user_name = user_name;
        this.user_review = user_review;
        this.date = date;
    }

    public String getUser_name() {
        return user_name;
    }

    public String getUser_review() {
        return user_review;
    }

    public Date getDate() {
        return date;
    }
}
