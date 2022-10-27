package com.example.lab_drawapi_matveev493.items;

import android.os.Build;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.RequiresApi;

import com.example.lab_drawapi_matveev493.R;

import org.json.JSONException;
import org.json.JSONObject;

import java.time.LocalDateTime;
import java.time.Month;
import java.time.format.DateTimeFormatter;
import java.util.Date;
import java.util.GregorianCalendar;

public class SessionItem {
    public int id;
    public String token, timestamp;

    public SessionItem(int id, String token, String timestamp)
    {
        this.id = id;
        this.token = token;
        this.timestamp = timestamp;
    }


    public SessionItem(JSONObject obj) throws JSONException
    {
        id = obj.getInt("id");
        token = obj.getString("token");
        timestamp = obj.getString("timestamp");
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
    public String toString(){return "(" + String.valueOf(id) + ") " + token + " " + date(timestamp);}
}
