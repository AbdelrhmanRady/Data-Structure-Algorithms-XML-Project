package parsing;

import java.io.FileWriter;
import java.io.IOException;

import java.util.List;


public class XML2JSON {

    final String space = " ";
    final String openCurlyBraces = "{";
    final String closedCurlyBraces = "}";
    final String openSquareBrackets = "[";
    final String closedSquareBrackets = "]";

    public int first;
    public StringBuilder stringBuilder;

    public XML2JSON(StringBuilder file, int first, String s) {

        stringBuilder = file;
        this.first = first;

    }

    //generate a .json file given a path
    public void createJsonFile() throws IOException {
        String objectJson = String.valueOf(stringBuilder);
        // System.out.println(objectJson);
        FileWriter file = new FileWriter("./output.json");
        file.write(objectJson);
        file.close();
    }


    // writes String s in the Json , if add is true it adds new line after writing
    public void write(String s, boolean newln) {
        if (first == 0) {

            if (newln == true) {

                stringBuilder.append(s + '\n');
            } else {

                stringBuilder.append(s);
            }
            first = 1;
        } else {

            if (newln == true) {

                stringBuilder.append(s + '\n');
            } else {

                stringBuilder.append(s);
            }
        }
    }


    public String addIndentation(int depth){ //adding spacing using depth of node in tree

        String indent = "";
        for (int i = 0; i <= depth; i++) {
            indent += "   ";
        }
        return indent;
    }

    //main function for conversion
    public void Convert(Node node) throws IOException{ //basic function for conversion

        List<Node> children;
        Node parent = node.getParent();
        children = node.getChildren();
        //Stack<String> syntax = new Stack<String>();
        String indentation = addIndentation(node.getDepth());


        //base case
        if (node == null) {
            return;
        }

        //next is responsible for the opening curly braces and square brackets
        if (node.getDepth() == 0) //root handler
        {
            write(openCurlyBraces, true);
            if (children.size() > 0) {
                write(indentation + '"' + node.getTagName() + '"' + ':' + space + openCurlyBraces, true);
            } else {
                write(indentation + '"' + node.getTagName() + '"' + ':' + space + '"' + node.getTagValue() + '"', true);
            }


        } else { //if node has "siblings" of the same tag name (array)
            if (parent.getChildren().size() > 1 && parent.getChildren().get(0).getTagName().equals(parent.getChildren().get(1).getTagName().trim())) {
                handleSameChildrenTypesNodeOpen(node);
            } else if (children.size() == 0) {
                // if node has siblings and no children
                if (parent.getChildren().size() > 1) {

                    handleSibilingsWithNoChildrenNodeOpen(node);
                } else {
                    //if node has no children and no siblings
                    handleNochildrenNoSibilingsNodeOpen(node);
                }

            } else if (children.size() > 0) {

                //if node has children and siblings
                if (parent.getChildren().size() > 1) {
                    handleChildrenAndSibilingsNodeOpen(node);

                } else {
                    //if node has children but no siblings
                    handleChildrenNoSibilingsNodeOpen(node);

                }

            }

        }

        for (Node child : children) {
            Convert(child);
        }

        //next is responsible for the closing curly braces and square brackets
        if (node.getDepth() == 0) //root handler
        {
            if (children.size() > 0) {
                write(indentation + closedCurlyBraces, true);
            }
            write(closedCurlyBraces, true);


        } else {
            //if node has siblings of same type
            if (parent.getChildren().size() > 1 && parent.getChildren().get(0).getTagName().equals(parent.getChildren().get(1).getTagName())) {

                handleSameChildrenTypesNodeClose(node);

            } else if (children.size() > 0) {

                //node with children and siblings
                if (parent.getChildren().size() > 1) {
                    handleChildrenAndSibilingsNodeClose(node);

                } else {
                    //if node has children and no siblings
                    handleChildrenAndNoSibilingsClose(node);

                }

            }

        }

        createJsonFile();
    }





    /* handlers for different cases for the OPENING curly braces and brackets*/
    public void handleSameChildrenTypesNodeOpen(Node node) {
        List<Node> children;
        Node parent = node.getParent();
        children = node.getChildren();
        String indentation = addIndentation(node.getDepth());


        if (node == parent.getChildren().get(0)) {

            write(indentation + '"' + node.getTagName() + '"' + ':' + space + openSquareBrackets, true);
            if (children.size() == 0) {
                write(indentation + space + '"' + node.getTagValue() + '"' + ',', true);
            } else {
                write(indentation + openCurlyBraces, true);
            }


        } else if (node != parent.getChildren().get(0) && children.size() == 0) {
            if (node == parent.getChildren().get(parent.getChildren().size() - 1)) {
                write(indentation + space + '"' + node.getTagValue() + '"', true);
            } else {
                write(indentation + space + '"' + node.getTagValue() + '"' + ',', true);
            }

        } else if (node != parent.getChildren().get(0) && children.size() > 0) {
            write(indentation + openCurlyBraces, true);

        }


    }

    public void handleSibilingsWithNoChildrenNodeOpen(Node node) {
        Node parent = node.getParent();
        String indentation = addIndentation(node.getDepth());

        if (node == parent.getChildren().get(parent.getChildren().size() - 1)) {
            write(indentation + '"' + node.getTagName() + '"' + ':' + '"' + node.getTagValue() + '"', true);
        } else {
            write(indentation + '"' + node.getTagName() + '"' + ':' + '"' + node.getTagValue() + '"' + ',', true);
        }

    }

    public void handleNochildrenNoSibilingsNodeOpen(Node node) {


        String indentation = addIndentation(node.getDepth());

        write(indentation + '"' + node.getTagName() + '"' + ':' + '"' + node.getTagValue() + '"', true);
    }

    public void handleChildrenAndSibilingsNodeOpen(Node node) {

        Node parent = node.getParent();
        String indentation = addIndentation(node.getDepth());


        if (node != parent.getChildren().get(parent.getChildren().size() - 1)) {
            write(indentation + '"' + node.getTagName() + '"' + ':' + space + openCurlyBraces, true);
        } else {
            write(indentation + '"' + node.getTagName() + '"' + ':' + space + openCurlyBraces, true);
        }


    }

    public void handleChildrenNoSibilingsNodeOpen(Node node) {
        String indentation = addIndentation(node.getDepth());
        write(indentation + '"' + node.getTagName() + '"' + ':' + space + openCurlyBraces, true);
    }



    /* handlers for different cases for the CLOSING curly braces and brackets*/
    public void handleSameChildrenTypesNodeClose(Node node){
        List<Node> children;
        Node parent = node.getParent();
        children = node.getChildren();
        String indentation = addIndentation(node.getDepth());

        if (children.size() == 0 && node == parent.getChildren().get(parent.getChildren().size() - 1)) {
            write(indentation + closedSquareBrackets, true);
        } else if (children.size() > 0) {
            if (node != parent.getChildren().get(parent.getChildren().size() - 1)) {
                write(indentation + closedCurlyBraces + ',', true);
            } else {
                write(indentation + closedCurlyBraces, true);
                write(indentation + closedSquareBrackets, true);
            }

        }
    }

    public void handleChildrenAndSibilingsNodeClose(Node node){

        Node parent = node.getParent();
        String indentation = addIndentation(node.getDepth());

        if (node != parent.getChildren().get(parent.getChildren().size() - 1)) {
            write(indentation + closedCurlyBraces + ',', true);
        } else {
            write(indentation + closedCurlyBraces, true);
        }
    }

    public void handleChildrenAndNoSibilingsClose(Node node){
        String indentation = addIndentation(node.getDepth());
        write(indentation + closedCurlyBraces, true);
    }




}