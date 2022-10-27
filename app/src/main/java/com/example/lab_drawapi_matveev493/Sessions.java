package com.example.lab_drawapi_matveev493;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.app.Activity;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ListView;

import com.example.lab_drawapi_matveev493.items.GraphItem;
import com.example.lab_drawapi_matveev493.items.SessionItem;

import org.json.JSONArray;

public class Sessions extends AppCompatActivity {

    Activity ctx;

    ArrayAdapter<SessionItem> adp;

    ListView lv;

    String token = Request.token;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sessions);

        lv = findViewById(R.id.ListViewSession);

        ctx = this;

        adp = new ArrayAdapter<SessionItem>(this, android.R.layout.simple_list_item_1);
        lv.setAdapter(adp);


        refresh();

        lv.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                Request r = new Request()
                {
                    @Override
                    public void onSuccess(String res) throws Exception {

                    }
                };

                SessionItem si = adp.getItem(i);
                String token = si.token;
                String token2 = Request.token;
                if (token.equals(token2))
                {
                    r.send(ctx, "DELETE", "/session/close?token=" + si.token);
                    MainActivity.base.deleteSave(si.token);
                    Intent in = new Intent(ctx, MainActivity.class);
                    startActivity(in);
                }
                else {
                    r.send(ctx, "DELETE", "/session/close?token=" + si.token);
                    lv.invalidate();
                    refresh();
                    lv.invalidate();
                }


            }
        });
    }

    public void on_close(View v) {
        Intent i = new Intent(ctx, Graphs.class);
        ctx.startActivity(i);
    }
    public void refresh()
    {
        Request r = new Request()
        {
            @Override
            public void onSuccess(String res) throws Exception {
                JSONArray arr = new JSONArray(res);

                lv.setAdapter(null);
                adp = new ArrayAdapter<SessionItem>(ctx, android.R.layout.simple_list_item_1);
                for(int i = 0; i < arr.length(); i++)
                {
                    adp.add(new SessionItem(arr.getJSONObject(i)));
                }
                adp.notifyDataSetChanged();
                lv.setAdapter(adp);
                adp.notifyDataSetChanged();
                lv.invalidate();
            }

        };

        r.send(this, "GET", "/session/list?token=" + token);
    }
    public void on_password(View v)
    {
        Request r = new Request()
        {
            @Override
            public void onSuccess(String res) throws Exception {

            }

        };
        AlertDialog.Builder alert = new AlertDialog.Builder(this);
        alert.setTitle("New password");
        alert.setMessage("Enter new password");
        final EditText input = new EditText(this);
        alert.setView(input);

        alert.setPositiveButton("Ok", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int whichButton) {
                r.send(ctx, "POST", "/account/update?token=" + token + "&secret=" + input.getText());
                refresh();
            }
        });

        alert.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int whichButton) {
            }
        });

        alert.show();
    }
}