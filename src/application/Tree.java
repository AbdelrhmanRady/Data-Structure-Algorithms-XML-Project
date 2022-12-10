package application;

import java.io.FileInputStream;
import java.io.IOException;

class Tree
{
    private Node root;
    public Tree(Node root)
    {
        this.root = root;
    }

    public Tree()
    {
        root = null;
    }




    public Node getTreeRoot()
    {
        return this.root.getChildren().get(0);
    }
    
    private void parseFileToTree(String reader, int index, Node node,boolean flag) throws IOException {

        char character;

        while (index <reader.length() )
        {
            character = (char)reader.charAt(index++);
            if (index > reader.length() ) {
                break;
            }
            
            if (character == '<' && reader.charAt(index) != '/' )
            {
                String name = "";
                String value = "";
                Node child = new Node("", "", "", node.getDepth() + 1, node);
                node.getChildren().add(child);
                character = (char)reader.charAt(index++);
                if (index > reader.length() ) {
                    break;
                }
                while (character != '>')
                {
                    if (character == '\n' || character == '\r' || character == '\t')
                    {
                        character = (char)reader.charAt(index++);
                        if (index > reader.length() ) {
                            break;
                        }
                        continue;
                    }
                    name += character;
                    character = (char)reader.charAt(index++);
                    if (index > reader.length() ) {
                        break;
                    }
                }
                child.setTagName(name);

                while (reader.charAt(index) == '\n' || reader.charAt(index) == '\t' || reader.charAt(index) == '\r' || reader.charAt(index) == ' ')
                {
                    character = (char)reader.charAt(index++);
                    if (index > reader.length() ) {
                        break;
                    }
                }
                if (reader.charAt(index) == '<')
                {
                    parseFileToTree(reader,index ,child,false);
                    break;
                }
                else
                {
                    character = (char)reader.charAt(index++);
                    if (index > reader.length() ) {
                        break;
                    }
                    while (character != '<')
                    {
                        if (character == '\n' || character == '\r' || character == '\t')
                        {
                            character = (char)reader.charAt(index++);
                            if (index > reader.length() ) {
                                break;
                            }
                            continue;
                        }
                        if ((character == ' ' && reader.charAt(index) == ' ') || (character == ' ' && reader.charAt(index) == '<'))
                        {
                            character = (char)reader.charAt(index++);
                            if (index > reader.length() ) {
                                break;
                            }
                            continue;
                        }
                        value += character;
                        character = (char)reader.charAt(index++);
                        if (index > reader.length() ) {
                            break;
                        }

                    }
                    child.setTagValue(value);
                }

            }
            else if (character == '<' && reader.charAt(index) == '/')
            {
                if(node.getParent() == null) {
                    return;
                };
                parseFileToTree(reader, index,node.getParent(),false);
                break;
            }


        }
    }


    public void insertXML(FileInputStream reader) throws IOException {
        StringBuilder sb = new StringBuilder();
        int ch;
        while((ch = reader.read()) != -1){
            sb.append((char)ch);
        }
        String x =  sb.toString();

        Node parent = new Node();
        root = parent;
        parent.setDepth(-1);

        parseFileToTree(x, 0,parent,true);
    }
}
