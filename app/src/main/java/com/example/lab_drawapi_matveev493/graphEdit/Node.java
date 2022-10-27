package com.example.lab_drawapi_matveev493.graphEdit;

import org.json.JSONException;
import org.json.JSONObject;

public class Node {
    public float x, y;
    public int id;
    public String text;

    public Node(float x, float y, int id, String text)
    {
        this.x = x;
        this.y = y;
        this.id = id;
        this.text = text;
    }
    public Node(JSONObject obj) throws JSONException
    {
        x = (float)obj.getDouble("x");
        y = (float)obj.getDouble("y");
        id = obj.getInt("id");
        text = obj.getString("name");

    }

}
