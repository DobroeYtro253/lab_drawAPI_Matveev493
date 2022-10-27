package com.example.lab_drawapi_matveev493;

import android.app.Activity;
import android.content.Intent;
import android.widget.Toast;

import org.json.JSONObject;

import java.io.BufferedInputStream;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;

public class Request {
    String base = "http://nodegraph.spbcoit.ru:5000";
    public static String token;

    public void onSuccess(String res) throws Exception {

    }
    public void onFail()
    {

    }
    public void send(Activity ctx, String method, String request)
    {
        Runnable r = new Runnable() {
            @Override
            public void run() {
                try {
                    URL url = new URL(base + request);
                    HttpURLConnection con = (HttpURLConnection) url.openConnection();

                    con.setRequestMethod(method);

                    InputStream is = con.getInputStream();
                    BufferedInputStream inp = new BufferedInputStream(is);

                    byte[] buf = new byte[512];
                    String str = "";

                    while (true)
                    {
                        int len = inp.read(buf);
                        if(len < 0)break;

                        str += new String(buf, 0, len);
                    }
                    con.disconnect();

                    final String res = str;
                    ctx.runOnUiThread(() -> {
                        try{onSuccess(res);} catch (Exception e){}
                    });

                }
                catch (Exception e)
                {
                    ctx.runOnUiThread(() ->
                    {
                      //  Toast t = Toast.makeText(ctx,  "Request failed.", Toast.LENGTH_SHORT);
                        // t.show();
                    });
                    onFail();
                }
            }
        };

        Thread t = new Thread(r);
        t.start();
    }
    public static void DestroiApp(Activity ctx)
    {

        Request r = new Request()
        {
            @Override
            public void onSuccess(String res) throws Exception {

            }
        };

        r.send(ctx, "DELETE", "/session/close?token=" + Request.token);

    }
}
