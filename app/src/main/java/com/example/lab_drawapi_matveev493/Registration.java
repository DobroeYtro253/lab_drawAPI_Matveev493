package com.example.lab_drawapi_matveev493;

import androidx.appcompat.app.AppCompatActivity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

public class Registration extends AppCompatActivity {

    Activity ctx;

    TextView tv_login;
    TextView tv_pass;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_registration);


        tv_login = findViewById(R.id.editTextTextLogin);
        tv_pass = findViewById(R.id.editTextTextPassword);

        ctx = this;
    }

    public void on_cancel(View v)
    {
        Intent i = new Intent(ctx, MainActivity.class);
        startActivity(i);
    }
    public void on_reg(View v)
    {
        Request r = new Request()
        {
            @Override
            public void onSuccess(String res) throws Exception {

                Toast t = Toast.makeText(ctx,  "Account created.", Toast.LENGTH_SHORT);
                t.show();
                Intent i = new Intent(ctx, MainActivity.class);
                startActivity(i);
            }
        };

        String name = tv_login.getText().toString();
        String secret = tv_pass.getText().toString();

        r.send(this, "PUT", "/account/create?name=" + name + "&secret=" + secret);
    }

}