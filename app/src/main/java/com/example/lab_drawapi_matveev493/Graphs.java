package com.example.lab_drawapi_matveev493;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.app.Activity;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ListView;

import com.example.lab_drawapi_matveev493.graphEdit.EditorActivity;
import com.example.lab_drawapi_matveev493.items.GraphItem;

import org.json.JSONArray;

public class Graphs extends AppCompatActivity {

    Activity ctx;

    ArrayAdapter<GraphItem> adp;

    ListView lv;

    String token = Request.token;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_graphs);

        lv = findViewById(R.id.listViewGraphs);

        adp = new ArrayAdapter<GraphItem>(this, android.R.layout.simple_list_item_1);
        lv.setAdapter(adp);

        lv.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                GraphItem gi = adp.getItem(i);
                Intent it = new Intent(ctx, EditorActivity.class);
                it.putExtra("token", token);
                it.putExtra("id", gi.id);
                startActivity(it);
            }
        });


        refresh();
        ctx = this;

    }

    public void refresh()
    {
        Request r = new Request()
        {
            @Override
            public void onSuccess(String res) throws Exception {
                JSONArray arr = new JSONArray(res);

                for(int i = 0; i < arr.length(); i++)
                {
                    adp.add(new GraphItem(arr.getJSONObject(i)));
                }
                adp.notifyDataSetChanged();
            }

        };

        r.send(this, "GET", "/graph/list?token=" + token);
    }

    public void on_close(View v)
    {
        Request.DestroiApp(this);
        MainActivity.base.deleteSave(Request.token);
        Intent i = new Intent(ctx, MainActivity.class);
        ctx.startActivity(i);
    }
    public void on_session(View v)
    {
        Intent i = new Intent(ctx, Sessions.class);
        ctx.startActivity(i);
    }
    public void on_addNewGraph(View v)
    {
        Request r = new Request()
        {
            @Override
            public void onSuccess(String res) throws Exception {

            }

        };
        AlertDialog.Builder alert = new AlertDialog.Builder(this);
        alert.setTitle("New graph");
        alert.setMessage("Enter graph name");
        final EditText input = new EditText(this);
        alert.setView(input);

        alert.setPositiveButton("Ok", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int whichButton) {
                r.send(ctx, "PUT", "/graph/create?token=" + token + "&name=" + input.getText());
                adp.clear();
                refresh();
            }
        });

        alert.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int whichButton) {
            }
        });

        alert.show();
    }
    public void on_deleteGraph(View v)
    {
        Request r = new Request()
        {
            @Override
            public void onSuccess(String res) throws Exception {

            }

        };
        AlertDialog.Builder alert = new AlertDialog.Builder(this);
        alert.setTitle("Delete graph");
        alert.setMessage("Enter graph id");
        final EditText input = new EditText(this);
        alert.setView(input);

        alert.setPositiveButton("Ok", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int whichButton) {
                r.send(ctx, "DELETE", "/graph/delete?token=" + token + "&id=" + input.getText());
                adp.clear();
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