package application;

import java.util.ArrayList;

public  class Node
{
    private String tagName;
    private String tagValue;
    private String tagAttributes;
    private int depth;
    private Node parent;
    private ArrayList<Node> children = new ArrayList<Node>();

    public Node(String tagName, String tagValue, String tagAttributes, int depth, Node parent)
    {
        this.tagName = tagName;
        this.tagValue = tagValue;
        this.depth = depth;
        this.tagAttributes = tagAttributes;

        this.parent = parent;
    }

    public Node()
    {
        tagName = null;
        tagValue = null;
        tagAttributes = "";
        depth = 0;
        parent = null;
    }


    public Node getParent()
    {
        return parent;
    }

    public void setParent(Node parent)
    {
        this.parent = parent;
    }

    public ArrayList<Node> getChildren()
    {
        return children;
    }

    public String getTagName()
    {
        return tagName;
    }

    public String getTagValue()
    {
        return tagValue;
    }

    public int getDepth()
    {
        return depth;
    }

    public String getTagAttributes()
    {
        return tagAttributes;
    }


    public void setTagName(String tn)
    {
        tagName = tn;
    }
    public void setTagValue(String tv)
    {
        tagValue = tv;
    }

    public void setDepth(int d)
    {
        depth = d;
    }

    public void setTagAttributes(String ta)
    {
        tagAttributes = ta;
    }
}

