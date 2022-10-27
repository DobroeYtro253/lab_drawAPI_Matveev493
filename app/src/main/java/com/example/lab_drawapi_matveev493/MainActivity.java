package com.example.lab_drawapi_matveev493;

import androidx.appcompat.app.AppCompatActivity;

import android.app.Activity;
import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.View;
import android.widget.CompoundButton;
import android.widget.Switch;
import android.widget.TextView;
import android.widget.Toast;

import org.json.JSONObject;

public class MainActivity extends AppCompatActivity {


    public static DB base;
    TextView tv_login;
    TextView tv_pass;
    Switch sv;

    Activity ctx;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        tv_login = findViewById(R.id.editTextTextLogin);
        tv_pass = findViewById(R.id.editTextTextPassword);
        sv = findViewById(R.id.switchSave);

        ctx = this;

        base = new DB(this, "save.db", null, 1);

        String token = base.selectToken();
        String n = "null";
        if(token.equals(n))
        {

        }
        else
            {
                Request.token = token;
                Intent i = new Intent(ctx, Graphs.class);
                i.putExtra("token", token);
                startActivity(i);
            }
    }



    public void on_login(View v)
    {
        Request r = new Request()
        {

            @Override
            public void onSuccess(String res) throws Exception
            {
                try
                {
                    JSONObject obj = new JSONObject(res);

                    String token = obj.getString("token");
                    Request.token = token;
                    if(sv.isChecked() == true)
                    {
                        MainActivity.base.addToken(token);
                    }
                    Intent i = new Intent(ctx, Graphs.class);
                    i.putExtra("token", token);
                    startActivity(i);
                }
                catch (Exception e){}

                Toast t = Toast.makeText(ctx, res, Toast.LENGTH_SHORT);
                t.show();
            }
        };



        String name = tv_login.getText().toString();
        String secret = tv_pass.getText().toString();

        r.send(this, "PUT", "/session/open?name=" + name + "&secret=" + secret);

    }

    public void onRegister(View v)
    {
        Intent i = new Intent(ctx, Registration.class);
        startActivity(i);
    }

}