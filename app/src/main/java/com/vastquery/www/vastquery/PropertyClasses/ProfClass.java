package com.vastquery.www.vastquery.PropertyClasses;


public class ProfClass {
    String Pin_Code,Email,Phone,Mobile1,Mobile2,Website,Sub_Type;

    public ProfClass(String pin_Code, String email, String phone, String mobile1, String mobile2, String website, String sub_Type) {
        Pin_Code = pin_Code;
        Email = email;
        Phone = phone;
        Mobile1 = mobile1;
        Mobile2 = mobile2;
        Website = website;
        Sub_Type = sub_Type;
    }

    public String getPin_Code() {
        return Pin_Code;
    }

    public String getEmail() {
        return Email;
    }

    public String getPhone() {
        return Phone;
    }

    public String getMobile1() {
        return Mobile1;
    }

    public String getMobile2() {
        return Mobile2;
    }

    public String getWebsite() {
        return Website;
    }

    public String getSub_Type() {
        return Sub_Type;
    }
}
