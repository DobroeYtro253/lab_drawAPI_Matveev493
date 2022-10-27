package com.example.lab_drawapi_matveev493;

import android.app.Activity;
import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.SurfaceView;

import com.example.lab_drawapi_matveev493.graphEdit.EditorActivity;
import com.example.lab_drawapi_matveev493.graphEdit.Graph;
import com.example.lab_drawapi_matveev493.graphEdit.Link;
import com.example.lab_drawapi_matveev493.graphEdit.Node;

public class GraphView extends SurfaceView {
    Graph g = new Graph();
    Paint p;

    Activity ctx;
    int selected1 = -1;
    int lasthit = -1;
    int selected2 = -1;
    int s1, s2;

    float rad = 100.0f;
    float halfside = 100.0f;

    float last_x;
    float last_y;

    public void add_node(float x, float y, String txt)
    {
        g.add_node(x, y, txt);
        invalidate();
    }
    public void remove_node(Activity ctx)
    {
        if(selected1 < 0) return;
        Request r1 = new Request()
        {
            @Override
            public void onSuccess(String res) throws Exception {
                invalidate();
            }

            @Override
            public void onFail() {

            }
        };
        for (int i = 0; i < g.link.size(); i++)
        {
            if (g.link.get(i).a == g.node.get(selected1).id)
            {
                r1.send(ctx, "DELETE", "/link/delete?token=" + Request.token + "&id=" + g.link.get(i));
            }
            if (g.link.get(i).b == g.node.get(selected1).id)
            {
                r1.send(ctx, "DELETE", "/link/delete?token=" + Request.token + "&id=" + g.link.get(i));
            }
        }


        Request r = new Request()
        {
            @Override
            public void onSuccess(String res) throws Exception {
                invalidate();
            }

        };
        r.send(ctx, "DELETE", "/node/delete?token=" + Request.token + "&id=" + g.node.get(selected1).id);
        selected1 = -1;
        invalidate();
    }

    public void addText_node(String txt, Activity ctx) {
        Request r = new Request()
        {
            @Override
            public void onSuccess(String res) throws Exception {

            }

        };
        r.send(ctx, "POST", "/node/update?token=" + Request.token + "&id=" +  g.node.get(selected1).id + "&x=" + g.node.get(selected1).x + "&y=" + g.node.get(selected1).y + "&name=" + txt);

    }

    public void link_node(Activity ctx)
    {
        if(selected1 < 0) return;
        if(selected2 < 0) return;
        Request r = new Request()
        {
            @Override
            public void onSuccess(String res) throws Exception {
                invalidate();
            }

        };
        r.send(ctx, "PUT", "/link/create?token=" + Request.token + "&source=" + g.node.get(selected1).id + "&target=" + g.node.get(selected2).id+ "&value=" + 1);
        invalidate();
    }
    public void link_nodeAB(int a, int b)
    {
        selected1 = a;
        selected2 = b;
        g.add_link(a, b);
        invalidate();
    }
    public GraphView(Context context, AttributeSet attrs)
    {
        super(context, attrs);
        p  =new Paint();
        p.setAntiAlias(true);
        setWillNotDraw(false);
    }

    public int get_link_at_xy(float x, float y)
    {
        for(int i = 0; i < g.link.size(); i++)
        {
            Link l = g.link.get(i);
            Node na = g.node.get(l.a);
            Node nb = g.node.get(l.b);
            float bx = (na.x + nb.x) * 0.5f;
            float by = (na.y + nb.y) * 0.5f;
            if(x >= bx - halfside && x <= bx + halfside && y >= by - halfside && y <= by + halfside) return i;

        }
        return -1;
    }
    public void Act(Activity ctxA)
    {
        ctx = ctxA;
    }
    public int get_node_at_xy(float x, float y)
    {
        for(int i = g.node.size() - 1; i >= 0; i--)
        {
            Node n = g.node.get(i);
            float dx = x - n.x;
            float dy = y - n.y;
            if(dx * dx + dy * dy <= rad * rad) return i;
        }
        return -1;
    }

    public boolean onTouchEvent(MotionEvent event)
    {
        int action = event.getAction();
        float x = event.getX();
        float y = event.getY();
        switch (action) {
            case MotionEvent.ACTION_DOWN:

                int i = get_node_at_xy(x, y);

                lasthit = i;
                if (i < 0) {
                    selected1 = -1;
                    selected2 = -1;
                } else {
                    if (selected1 >= 0) selected2 = i;
                    else selected1 = i;
                }
                last_x = x;
                last_y = y;
                invalidate();
                return true;

            case MotionEvent.ACTION_UP:
                Request r = new Request()
                {
                    @Override
                    public void onSuccess(String res) throws Exception {

                    }

                };
                if (lasthit >= 0) {
                    if(selected2 == -1)
                    {
                        Node n = g.node.get(lasthit);
                        n.x += x - last_x;
                        n.y += y - last_y;
                        r.send(ctx, "POST", "/node/update?token=" + Request.token + "&id=" +  g.node.get(selected1).id + "&x=" + n.x + "&y=" + n.y + "&name=" + g.node.get(selected1).text);
                        invalidate();
                    }

                }

              return true;

            case MotionEvent.ACTION_MOVE: {
                if (lasthit >= 0) {
                    Node n = g.node.get(lasthit);
                    n.x += x - last_x;
                    n.y += y - last_y;

                    invalidate();
                }
                last_x = x;
                last_y = y;
                return true;

            }
        }
        return super.onTouchEvent(event);

    }

    protected void onDraw(Canvas canvas)
    {
        canvas.drawColor(Color.rgb(255, 255, 255));

        for(int i = 0; i < g.node.size(); i++)
        {
            Node n = g.node.get(i);

            p.setStyle(Paint.Style.FILL);

            if(i == selected1) p.setColor(Color.argb(50, 127, 0, 255));
            else if (i == selected2) p.setColor(Color.argb(50, 255, 0, 50));
            else p.setColor(Color.argb(50, 0, 127, 255));

            canvas.drawCircle(n.x, n.y, rad, p);

            p.setStyle(Paint.Style.STROKE);

            if (i == selected1) p.setColor(Color.rgb(127, 0, 255));
            else if (i == selected2) p.setColor(Color.rgb(255, 0, 50));
            else p.setColor(Color.rgb(0, 127, 255));

            canvas.drawCircle(n.x, n.y, rad, p);
            p.setTextSize(50);
            p.setColor(Color.BLACK);
            canvas.drawText(n.text, n.x, n.y + 145, p);
        }
        for(int i = 0; i < g.link.size(); i++)
        {
            try {
                Link l = g.link.get(i);
                Node na = g.node.get(0);
                Node nb = g.node.get(1);
                for (int j = 0; j < g.node.size(); j++)
                {
                    if (g.node.get(j).id == l.a)
                    {
                        na = g.node.get(j);
                    }
                    if (g.node.get(j).id == l.b)
                    {
                        nb = g.node.get(j);

                    }
                }

                p.setColor(Color.argb(127, 0, 0, 0));
                canvas.drawLine(na.x, na.y, nb.x, nb.y, p);

            }
            catch (Exception e){}

        }
    }
}
