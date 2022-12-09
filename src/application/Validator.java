package application;

import java.util.ArrayList;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Validator extends ArrStack {                       /*class validator used to check either the xml file
                                                                format is correct or not*/
    public Validator() {
    }                                                           /*empty constructor*/

    void Bracket_isCorrect(String XML) {
        Pattern open_bracket = Pattern.compile("<");            /*writing < symbol in open_bracket*/
        Matcher matcher1 = open_bracket.matcher(XML);           /*searching for < in the xml file*/
        Pattern close_bracket = Pattern.compile(">");           /*writing > symbol in close_bracket*/
        Matcher matcher2 = close_bracket.matcher(XML);          /*searching for > in the xml file*/
        int push = 0;
        int pop = 0;
        push("10");
        while (matcher1.find()) {
            push("<");                                       /*push every < in stack*/
            push++;
        }
        while (matcher2.find()) {
            pop();                                          /*pop every < in stack when we found '>'*/
            pop++;
        }
        System.out.println("number of bracket pushes = " + push);
        System.out.println("number of bracket pops = " + pop);
        if (top() == "10") {
            System.out.println("XML file brackets are in the correct format");
            pop();
        } else if (top() == "<") {
            System.out.println("there is a missing '>' in the file");
        } else
            System.out.println("there is a missing '<' in the file");
    }


    void Tag_isCorrect(String XML) {
        Pattern open_bracket = Pattern.compile("<");            /*writing < symbol in open_bracket*/
        Matcher matcher1 = open_bracket.matcher(XML);           /*searching for < in the xml file*/
        Pattern close_bracket = Pattern.compile(">");           /*writing > symbol in close_bracket*/
        Matcher matcher2 = close_bracket.matcher(XML);          /*searching for > in the xml file*/
        while (matcher1.find()) {
            if (matcher2.find()) {
                if (XML.substring(matcher1.end(), matcher2.start()).charAt(0) != '/') {
                    push(XML.substring(matcher1.end(), matcher2.start()));
                } else {
                    if (top().equals(XML.substring(matcher1.end() + 1, matcher2.start()))) {
                        pop();
                    }
                }
            }
        }
        if (top() == null)
            System.out.println("XML tags are in correct format");
        else {
            System.out.println("there is a problem in tag:");
            System.out.println(top());
        }
    }
    /*  for errors like <id or id> or <id>1/id> or <id1</id> or id>1</id> or <id>1</id  */
    public void Fix_Missing_Bracket_Error(String[] Uncorrect_File)          /*function to fix missing brackets errors if the file is not correct*/
    {
        Pattern open_bracket = Pattern.compile("<");                        /*writing < symbol in open_bracket*/
        Pattern close_bracket = Pattern.compile(">");                       /*writing > symbol in close_bracket*/
        ArrayList<String> Fixed_file = new ArrayList<>();                   /*creating array list to save the file after being corrected*/
        String s="";                                                        /*empty string to save corrected file temporarily*/
        int space_counter=0;                                                /*counter to count number of spaces before tag*/
        int open_tag_index = 0;
        int tag_number_character=0;                                         /*integer that used to save tag number of characters*/
        for(int i=0;i<Uncorrect_File.length;i++)                            /*for loop that loop line by line in the xml file*/
        {
            Fixed_file.add(Uncorrect_File[i]);                              /*put the uncorrected file to the new array that is corrected*/
            Matcher matcher1 = open_bracket.matcher(Uncorrect_File[i]);     /*searching for < in the xml file*/
            Matcher matcher2 = close_bracket.matcher(Uncorrect_File[i]);    /*searching for > in the xml file*/
            s = Fixed_file.get(i);
            while (Fixed_file.get(i).charAt(space_counter) == ' ') {    /*get number of spaces before the tag*/
                space_counter++;
            }
            if(matcher1.find())                                             /*if there is < in the line*/
            {
                open_tag_index=matcher1.start();                            /*save the < index in the line*/
                if (!matcher2.find()) {                                /*if there is only one tag in the line and there is missing > */
                s = Fixed_file.get(i) + ">";                            /*put the missing < in the end of array*/
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
                    tag_number_character = matcher2.start() - open_tag_index;
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
            open_tag_index=0;
        }
    }
}