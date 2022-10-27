package com.example.lab_drawapi_matveev493.graphEdit;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.app.Activity;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;

import com.example.lab_drawapi_matveev493.GraphView;
import com.example.lab_drawapi_matveev493.Graphs;
import com.example.lab_drawapi_matveev493.R;
import com.example.lab_drawapi_matveev493.Request;

import org.json.JSONArray;

public class EditorActivity extends AppCompatActivity {

    static GraphView gv;
    int id = 0;
    int idN = 0;
    int idL = 0;
    Activity ctx;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_editor);

        ctx = this;

        refresh();
        gv.Act(ctx);
        gv.invalidate();
}

    public void refresh()
    {
        gv = findViewById(R.id.graphView);
        Intent i = getIntent();
        id = i.getIntExtra("id", 0);


        Request r = new Request()
        {
            @Override
            public void onSuccess(String res) throws Exception {
                JSONArray arr = new JSONArray(res);
                Graph.node.clear();
                for(int i = 0; i < arr.length(); i++)
                {
                    Graph.node.add(new Node(arr.getJSONObject(i)));

                }

            }

        };

        r.send(this, "GET", "/node/list?token=" + Request.token + "&id=" + id);

        Request r2 = new Request()
        {
            @Override
            public void onSuccess(String res) throws Exception {
                JSONArray arr = new JSONArray(res);
                Graph.link.clear();
                for(int i = 0; i < arr.length(); i++)
                {
                    Graph.link.add(new Link(arr.getJSONObject(i)));
                }

            }

        };
        int id2 = i.getIntExtra("id", 0);
        r2.send(this, "GET", "/link/list?token=" + Request.token + "&id=" + id2);
        gv.invalidate();
    }
    public void on_add(View v)
    {
        Request r = new Request()
        {
            @Override
            public void onSuccess(String res) throws Exception {
                refresh();
                gv.invalidate();
            }

        };
        AlertDialog.Builder alert = new AlertDialog.Builder(this);
        alert.setTitle("New node");
        alert.setMessage("Enter node name");
        final EditText input = new EditText(this);
        alert.setView(input);

        alert.setPositiveButton("Ok", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int whichButton) {
                r.send(ctx, "PUT", "/node/create?token=" + Request.token + "&id=" + id + "&x=" + 100 + "&y=" + 100 + "&name=%20" + input.getText());
                gv.invalidate();
                refresh();
                gv.invalidate();

            }
        });

        alert.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int whichButton) {
            }
        });

        alert.show();

    }

    public void on_remove(View v)
    {
        gv.remove_node(ctx);
        gv.invalidate();
        refresh();
        gv.invalidate();

    }

    public void on_rename(View v)
    {
        Request r = new Request()
        {
            @Override
            public void onSuccess(String res) throws Exception {

            }

        };
        AlertDialog.Builder alert = new AlertDialog.Builder(this);
        alert.setTitle("Graph name");
        alert.setMessage("renamed graph");
        final EditText input = new EditText(this);
        alert.setView(input);

        alert.setPositiveButton("Ok", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int whichButton) {
                r.send(ctx, "POST", "/graph/update?token=" + Request.token + "&id=" + id + "&name=" + input.getText());
                gv.invalidate();
                refresh();
                gv.invalidate();

            }
        });

        alert.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int whichButton) {
            }
        });

        alert.show();
    }

    public void on_link(View v) {
        gv.link_node(ctx);

        gv.invalidate();
        refresh();
        gv.invalidate();

    }

    public void on_close(View v) {
       Intent i = new Intent(ctx, Graphs.class);
       ctx.startActivity(i);
    }

    public void on_text(View v) {
        AlertDialog.Builder alert = new AlertDialog.Builder(this);
        alert.setTitle("Node name");
        alert.setMessage("renamed node");
        final EditText input = new EditText(this);
        alert.setView(input);

        alert.setPositiveButton("Ok", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int whichButton) {
                gv.addText_node(String.valueOf(input.getText()), ctx);
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