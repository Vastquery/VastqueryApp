package com.vastquery.www.vastquery.PropertyClasses;

/**
 * Created by aj-ajay on 2/19/18.
 */

public class SearchClass {
    String name,group_id,sub_cateId;

    public SearchClass( String name,String group_id,String sub_cateId) {
        this.name = name;
        this.group_id = group_id;
        this.sub_cateId = sub_cateId;
    }

    public String getName() {
        return name;
    }

    public String getGroup_id() {
        return group_id;
    }

    public String getSub_cateId() {
        return sub_cateId;
    }
}
