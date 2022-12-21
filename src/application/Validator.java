package application;


import java.util.ArrayList;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Validator extends ArrStack {                       /*class validator used to check either the xml file
                                                                format is correct or not*/
    public Validator() {
    }

    void Bracket_isCorrect(String[] Incorrect_File) {
        Pattern open_bracket = Pattern.compile("<");            /*writing < symbol in open_bracket*/
        Pattern close_bracket = Pattern.compile(">");           /*writing > symbol in close_bracket*/
        String s="";
        int number_of_errors = 0;
        for (int i = 0; i < Incorrect_File.length; i++) {
            Matcher matcher1 = open_bracket.matcher(Incorrect_File[i]);           /*searching for < in the xml file*/
            Matcher matcher2 = close_bracket.matcher(Incorrect_File[i]);          /*searching for > in the xml file*/
            s=Incorrect_File[i];
            if(s.contains("<") && !matcher2.find())
            {
                System.out.println("there is a missing '>' at line:" +(i+1));
                number_of_errors++;
            }
            else if(s.contains(">") && !matcher1.find())
            {
                System.out.println("there is a missing '<' at line:" +(i+1));
                number_of_errors++;
            }
            else if(matcher2.find()) {
                if (!matcher1.find()) {
                    System.out.println("there is a missing '<' at line:" + (i + 1));
                    number_of_errors++;
                }
            }
            else if(matcher1.find()) {
                if (!matcher2.find()) {
                    System.out.println("there is a missing '>' at line:" + (i + 1));
                    number_of_errors++;
                }
            }
        }
        System.out.println("Number of errors:"+number_of_errors);
    }

    public void closing_tag_is_correct(String[] Incorrect_File) {
        Pattern open_bracket = Pattern.compile("<");                            /*writing < symbol in open_bracket*/
        Pattern close_bracket = Pattern.compile(">");                           /*writing > symbol in close_bracket*/
        ArrStack s = new ArrStack();
        String XML = "";                                                        /*empty string to save corrected file temporarily*/
        String tag1 = "";
        String tag2 = "";
        String error_at="";
        String next_line="";
        String next_line_tag="";
        String prev_line="";
        int flag=0;

        for (int i = 0; i < Incorrect_File.length; i++) {
            XML = Incorrect_File[i];
            Matcher matcher1 = open_bracket.matcher(Incorrect_File[i]);          /*searching for < in the xml file*/
            Matcher matcher2 = close_bracket.matcher(Incorrect_File[i]);         /*searching for > in the xml file*/

            /*conditon for geting next line tag*/
            if(i< Incorrect_File.length-1) {
                next_line = Incorrect_File[i + 1];
                Matcher matcher3 = open_bracket.matcher(Incorrect_File[i + 1]);          /*searching for < in the xml next line*/
                Matcher matcher4 = close_bracket.matcher(Incorrect_File[i + 1]);         /*searching for > in the xml next line*/
                if (matcher3.find() && matcher3.find() && matcher4.find() && matcher4.find()) {
                }
                else if (next_line.contains("<") && next_line.contains(">") && !next_line.contains("/")) {
                    next_line_tag = next_line.substring(next_line.indexOf("<") + 1, next_line.indexOf(">"));
                }
                else if (next_line.contains("<") && next_line.contains(">") && next_line.contains("/")) {
                    next_line_tag = next_line.substring(next_line.indexOf("<") + 2, next_line.indexOf(">"));
                }
            }
            if(XML.contains("<") && XML.contains(">"))
            {
                if(matcher1.find() && matcher1.find() && matcher2.find() && matcher2.find())
                {
                    tag1 = XML.substring(XML.indexOf("<")+1,XML.indexOf(">"));
                    tag2 = XML.substring(matcher1.end()+1, matcher2.start());
                    if(!tag1.equals(tag2))
                    {
                        System.out.println("j");
                        error_at = i+"_/"+tag1;
                    }
                }
                else{
                    tag1=XML.substring(XML.indexOf("<")+1, XML.indexOf(">"));
                    if (!XML.contains("/")) {
                        if(flag==1) {
                            error_at = error_at+"   "+i+"_/"+s.top();
                            flag=0;
                        }
                        if(tag1.equals(s.top()))              /*if two opening tags entered in the stack and equal -> error*/
                        {
                            error_at = error_at+"   "+i+"_/"+s.top();
                            flag=0;
                        }
                        else {
                            s.push(tag1);
                            flag = 0;
                        }
                    }
                    else
                    {
                        tag2 = XML.substring(XML.indexOf("<") + 2, XML.indexOf(">"));
                        if (tag2.equals(s.top())) {
                            s.pop();
                            flag=0;
                        }
                        else if(next_line_tag.equals(s.top()) || s.top()==null) {
                            error_at = error_at+"   "+(i-1) + "_" + "remove";
                            flag=0;
                        }
                        else
                        {
                            prev_line=Incorrect_File[i-1];
                            if(!prev_line.contains(">") && !prev_line.contains("<"))
                            {
                                error_at = error_at+"   "+(i-1)+"_"+tag2;
                            }
                            error_at = error_at+"   "+i + "_/" + s.top();
                        }
                    }
                }
            }
            else if(!XML.isEmpty())
            {
                flag=1;
            }
        }
        if(!s.isEmpty())
        {
            error_at = error_at+"   "+(Incorrect_File.length-1)+"_/"+s.top();
        }
        if(error_at.isEmpty())
        {
            System.out.println("tags are correct");
        }
        else
            System.out.println(error_at);
    }


    /*  for errors like <id or id> or <id>1/id> or <id1</id> or id>1</id> or <id>1</id  */
    public void Fix_Missing_Bracket_Error(String[] Incorrect_File)          /*function to fix missing brackets errors if the file is not correct*/
    {
        Pattern open_bracket = Pattern.compile("<");                        /*writing < symbol in open_bracket*/
        Pattern close_bracket = Pattern.compile(">");                       /*writing > symbol in close_bracket*/
        ArrayList<String> Fixed_file = new ArrayList<>();                   /*creating array list to save the file after being corrected*/
        String s="";                                                        /*empty string to save corrected file temporarily*/
        int space_counter=0;                                                /*counter to count number of spaces before tag*/
        int tag_number_character=0;                                         /*integer that used to save tag number of characters*/
        for(int i=0;i<Incorrect_File.length;i++)                            /*for loop that loop line by line in the xml file*/
        {
            Fixed_file.add(Incorrect_File[i]);                              /*put the uncorrected file to the new array that is corrected*/
            Matcher matcher1 = open_bracket.matcher(Incorrect_File[i]);     /*searching for < in the xml file*/
            Matcher matcher2 = close_bracket.matcher(Incorrect_File[i]);    /*searching for > in the xml file*/
            s = Fixed_file.get(i);
            while (Fixed_file.get(i).charAt(space_counter) == ' ') {        /*get number of spaces before the tag*/
                space_counter++;
            }
            if(matcher1.find())                                              /*if there is < in the line*/
            {
                if (!matcher2.find()) {                                      /*if there is only one tag in the line and there is missing > */
                    s = Fixed_file.get(i) + ">";                             /*put the missing < in the end of array*/
                }
                else if(matcher1.find())                                         /*if there is another < in the line(two tags in the same line)*/
                {
                    if(matcher2.start()>matcher1.end())          /*if there is only one > in the line that has two tags in it*/
                    {                                                               /*check either the missing > is in the first or in the second tag*/
                        tag_number_character = matcher2.start() - matcher1.end();   /*get tag size when there is two tags in the same line*/
                        if (!matcher2.find()) {
                            s = s.substring(0, (space_counter + tag_number_character)) + ">" + s.substring((tag_number_character + space_counter), matcher1.end() + tag_number_character +1);
                            /*put in string s from 0 to the end of tag1 then add the missing > then put the rest of the string*/
                            Fixed_file.set(i, s);                           /*put string s in the fixed file arraylist*/
                        }
                    }
                    else if (!matcher2.find()) {                             /*if there are two tags in the line and there is missing > */
                        s = Fixed_file.get(i) + ">";                         /*put the missing < in the end of array*/
                    }
                }
                else if (matcher2.find() && s.charAt(space_counter)=='<')                                             /*if there is a missing < in the second tag if there is two tags at the same line*/
                {
                    tag_number_character = s.indexOf('>')-s.indexOf('<');
                    s = s.substring(0, matcher2.end() - tag_number_character - 1) + "<" + s.substring(matcher2.end() - tag_number_character - 1, matcher2.end());
                    /*put in string s from index zero in the line to end of the line minus the tag length then put the missing < and continue the tag*/
                }
                else if(s.charAt(space_counter) != '<')
                {
                    s=s.substring(0, space_counter)+"<"+s.substring(space_counter); /*put the missing < in the end of the empty space*/
                }
            }
            else if(s.contains(">"))                                               /*check if there is only one tag in the file*/
            {
                s=s.substring(0, space_counter)+"<"+s.substring(space_counter); /*put the missing < in the end of the empty space*/
            }
            Fixed_file.set(i, s);                                                   /*put string s in the fixed file arraylist*/
            System.out.println(Fixed_file.get(i));                                  /*finally print the corrected file*/
            space_counter=0;                                                        /*clear the space counter and the tag count every line*/
            tag_number_character=0;
        }
    }


    public void adding_missing_tag(String[] Incorrect_File,int line,String missing_tag)
    {
        Pattern open_bracket = Pattern.compile("<");                        /*writing < symbol in open_bracket*/
        Pattern close_bracket = Pattern.compile(">");                       /*writing > symbol in close_bracket*/
        Matcher matcher1 = open_bracket.matcher(Incorrect_File[line]);     /*searching for < in the xml file*/
        Matcher matcher2 = close_bracket.matcher(Incorrect_File[line]);    /*searching for > in the xml file*/
        ArrayList<String> Fixed_file = new ArrayList<>();                   /*creating array list to save the file after being corrected*/
        for(int i=0;i<Incorrect_File.length;i++)                            /*for loop that loop line by line in the xml file*/
        {
            Fixed_file.add(Incorrect_File[i]);
        }
        String str = "";                                                    /*empty string to save corrected file temporarily*/
        str = Incorrect_File[line];
        if(matcher1.find() && matcher1.find() && matcher2.find() && matcher2.find())
        {
            str = str.substring(0, matcher1.end())+"/"+missing_tag+">" ;
        }
        else if(missing_tag.equals("remove"))
        {
            Fixed_file.remove(line);
        }
        else if(line == Incorrect_File.length-1)
        {
            str = str+"\n<"+ missing_tag+">" ;
        }
        else
        {
            str = "<"+ missing_tag+">"+ "\n" +str ;
        }
        Fixed_file.set(line, str);
        for(int i=0;i<Fixed_file.size();i++)
        {
            System.out.println(Fixed_file.get(i));
        }
    }

}