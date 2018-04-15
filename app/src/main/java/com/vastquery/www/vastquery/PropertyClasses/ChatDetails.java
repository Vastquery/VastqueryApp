package com.vastquery.www.vastquery.PropertyClasses;

import net.sourceforge.jtds.jdbc.DateTime;


/**
 * Created by aj-ajay on 4/9/18.
 */

public class ChatDetails {
    int user_id,owner_id,id;
    String message,reply,cat_id,status;
    DateTime m_time,r_time;


    public ChatDetails(int user_id, String message, String reply, String cat_id,int owner_id,String status,int id) {
        this.user_id = user_id;
        this.owner_id = owner_id;
        this.message = message;
        this.reply = reply;
        this.cat_id = cat_id;
        this.status = status;
        this.id = id;
    }

    public int getId() {
        return id;
    }

    public String getStatus() {
        return status;
    }

    public int getUser_id() {
        return user_id;
    }

    public int getOwner_id() {
        return owner_id;
    }

    public String getMessage() {
        return message;
    }

    public String getReply() {
        return reply;
    }

    public String getCat_id() {
        return cat_id;
    }

    public DateTime getM_time() {
        return m_time;
    }

    public DateTime getR_time() {
        return r_time;
    }
}
