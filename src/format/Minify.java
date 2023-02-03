package format;

import java.io.*;

public class Minify {
    public String st="";




    public String minify(String reader) throws IOException {

        char character;
        int index=0;
        while(index<reader.length()) {
            character=reader.charAt(index);

            if (!(character == '\n' || character == '\r' || character == '\t' || character == ' ')) {
                st += reader.charAt(index);
            }

            index++;
        }
        return st;

    }


    public String minifyKeepingSpaces(File file) throws IOException {
        BufferedReader br = new BufferedReader(new FileReader(file));
        String line;
        StringBuilder sb = new StringBuilder();

        while((line=br.readLine())!= null){
            sb.append(line.trim());
        }
        return sb.toString();
    }

}