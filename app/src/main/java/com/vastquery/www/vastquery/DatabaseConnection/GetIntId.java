package com.vastquery.www.vastquery.DatabaseConnection;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.Statement;

/**
 * Created by aj-ajay on 4/2/18.
 */

public class GetIntId {

    public String query,get;
    boolean isSuccess = false;
    public int id;

    public GetIntId(String query, String get) {
        this.query = query;
        this.get = get;
    }

    public int getId() {
        try {
            ConnectionHelper con = new ConnectionHelper();
            Connection connect = con.connectionclass();// Connect to database
            if (connect == null) {
            } else {
                Statement stmt = connect.createStatement();
                ResultSet rs = stmt.executeQuery(query);
                if (rs.next()) {
                    isSuccess = true;
                    id = rs.getInt(get);
                }
            }
        } catch (Exception ex) {
        }
        return id;
    }
}
