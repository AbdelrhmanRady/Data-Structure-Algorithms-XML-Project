package application;

import java.util.ArrayList;

public class Prettify {

    public StringBuilder sb;

    public Prettify(StringBuilder s) {
        sb = s;
    }

    //writing in the empty passed string builder
    public void write(String str, boolean newln) {

        if (newln == true) {
            sb.append(str + '\n'); // if newln is true, append passed string and go to new line
        } else {
            sb.append(str);  // if newln is false, append passed string and stay on same line 
        }
    }

    public void prettify(Node root) {
        Node node = root;
        ArrayList<Node> children;
        children = root.getChildren(); // bring children of current root (if it has)

        // return if current node is null (base condition)
        if (node == null) {
            return;
        }

        for (int i = 0; i < node.getDepth(); i++)  // ident using depth of tree
        {
            write("    ", false); /* indent without a new line */
        }

        // writing opening tags
        write("<" + node.getTagName() + ">", true); // print current node and go to new line

        if (node.getTagValue() != "")  // if current node has a tag value, ident using depth of node ,then print that tag value and go to new line
        {
            for (int i = 0; i < node.getDepth(); i++) {
                write("     ", false);
            }

            write(node.getTagValue(), true);
        }

        if (node != null) {
            for (Node child : children) {
                prettify(child); // prettify each child node if exists
            }
        }

        for (int i = 0; i < node.getDepth(); i++)  //ident using depth of tree
        {
            write("    ", false);
        }

        //print the closing tags
        write("<" + "/" + node.getTagName() + ">", true);
    }
}

