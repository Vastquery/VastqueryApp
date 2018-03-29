package com.vastquery.www.vastquery.PropertyClasses;



public class FacilityClass {
    String Cat_Id,Facilities_desc;
    int Faciliti_Id;

    public FacilityClass(String cat_Id, int faciliti_Id, String facilities_desc) {
        Cat_Id = cat_Id;
        Facilities_desc = facilities_desc;
        Faciliti_Id = faciliti_Id;
    }

    public String getCat_Id() {
        return Cat_Id;
    }

    public String getFacilities_desc() {
        return Facilities_desc;
    }

    public int getFaciliti_Id() {
        return Faciliti_Id;
    }
}
