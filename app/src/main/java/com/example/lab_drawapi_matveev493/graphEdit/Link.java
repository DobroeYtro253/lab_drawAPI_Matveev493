package com.example.lab_drawapi_matveev493.graphEdit;

import org.json.JSONException;
import org.json.JSONObject;

public class Link {
    public int a, b, id, value;
    public Link(int a, int b, int id, int value)
    {
        this.a = a;
        this.b = b;
        this.id = id;
        this.value = value;
    }
    public Link(JSONObject obj) throws JSONException
    {
        a = (int)obj.getDouble("source");
        b = (int)obj.getDouble("target");
        id = obj.getInt("id");
        value = obj.getInt("value");
    }
}
