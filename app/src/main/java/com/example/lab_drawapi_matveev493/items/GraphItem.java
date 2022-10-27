package com.example.lab_drawapi_matveev493.items;

import android.os.Build;

import androidx.annotation.RequiresApi;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.Date;
import java.util.GregorianCalendar;

public class GraphItem {
    public int id;
    String name, timestamp, nodes;

    public GraphItem(int id, String name, String timestamp, String nodes)
    {
        this.id = id;
        this.name = name;
        this.timestamp = timestamp;
        this.nodes = nodes;
    }

    public GraphItem(JSONObject obj) throws JSONException
    {
        id = obj.getInt("id");
        name = obj.getString("name");
        timestamp = obj.getString("timestamp");
        nodes = obj.getString("nodes");
    }

    @RequiresApi(api = Build.VERSION_CODES.O)
    public String date(String s)
    {
        GregorianCalendar calendar = new GregorianCalendar(1, 0, 1);
        calendar.setTimeInMillis(calendar.getTimeInMillis()+Long.valueOf(s)*1000);
        Date date = calendar.getTime();
        return date.toGMTString();
    }
    @RequiresApi(api = Build.VERSION_CODES.O)
    public String toString(){return "(" + String.valueOf(id) + ") " + name + " " + date(timestamp) + " nodes: " + nodes;}
}
