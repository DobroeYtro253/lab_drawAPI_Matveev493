package com.example.lab_drawapi_matveev493.graphEdit;

import java.util.ArrayList;

public class Graph {
    public static ArrayList <Node> node = new ArrayList<Node>();
    public static ArrayList <Link> link = new ArrayList<Link>();
    int idN = 0;
    int idL = 0;

    public void add_node(float x, float y, String txt)
    {
        node.add(new Node(x, y, idN, txt));
        idN++;
    }
    public void add_link(int a, int b)
    {
        link.add(new Link(a, b, idL, 1));
        idL++;
    }
    public void remove_node(int index)
    {
        if (index < 0) return;
        node.remove(index);
    }
    public void remove_link(int index)
    {
        if (index < 0) return;

        int size = Graph.link.size() - 1;
        int count = 0;
        for ( int i = size; i >= 0; i--)
        {
            Link l;
            if (i == 0){l = Graph.link.get(i);}
            else {l = Graph.link.get(i - count);}
            if (l.a == index || l.b == index)
            {
                if (i == 0){Graph.link.remove(i);}
                else {Graph.link.remove(i - count);}
                count++;
            }
        }

    }
    public void addText_node(int index, String txt)
    {
        Node l = Graph.node.get(index);
        l.text = txt;
        node.set(index, l);
    }
}
