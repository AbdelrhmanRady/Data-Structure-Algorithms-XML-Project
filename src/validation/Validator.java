package validation;


import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Validator extends ArrStack {                       /*class validator used to check either the xml file
                                                                format is correct or not*/
    public Validator() {
    }

    public List<Integer> Bracket_isCorrect(String[] Incorrect_File) {
        Pattern open_bracket = Pattern.compile("<");            /*writing < symbol in open_bracket*/
        Pattern close_bracket = Pattern.compile(">");           /*writing > symbol in close_bracket*/
        String s="";
        int number_of_errors = 0;
        List<Integer> errorline=new ArrayList<Integer>();
        for (int i = 0; i < Incorrect_File.length; i++) {
        	System.out.println(i);
            Matcher matcher1 = open_bracket.matcher(Incorrect_File[i]);           /*searching for < in the xml file*/
            Matcher matcher2 = close_bracket.matcher(Incorrect_File[i]);          /*searching for > in the xml file*/
            s=Incorrect_File[i];
            if(s.contains("<") && !matcher2.find())
            {
                System.out.println("there is a missing '>' at line:" +(i+1));
                errorline.add(i);
                number_of_errors++;
            }
            else if(s.contains(">") && !matcher1.find())
            {
                System.out.println("there is a missing '<' at line:" +(i+1));
                errorline.add(i);
                number_of_errors++;
            }
            else if(matcher2.find()) {
                if (!matcher1.find()) {
                    System.out.println("there is a missing '<' at line:" + (i + 1));
                    errorline.add(i);
                    number_of_errors++;
                }
            }
            else if(matcher1.find()) {
                if (!matcher2.find()) {
                    System.out.println("there is a missing '>' at line:" + (i + 1));
                    errorline.add(i);
                    number_of_errors++;
                }
            }
        }
        System.out.println("Number of errors:"+number_of_errors);
		return errorline;
    }

    /*  for errors like <id or id> or <id>1/id> or <id1</id> or id>1</id> or <id>1</id  */
    public ArrayList<String> Fix_Missing_Bracket_Error(String[] Incorrect_File)          /*function to fix missing brackets errors if the file is not correct*/
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
            //System.out.println(Fixed_file.get(i));                                  /*finally print the corrected file*/
            space_counter=0;                                                        /*clear the space counter and the tag count every line*/
            tag_number_character=0;
        }
		return Fixed_file;
    }


    public String tag_is_correct(String[] Incorrect_File) {
        Pattern open_bracket = Pattern.compile("<");                            /*writing < symbol in open_bracket*/
        Pattern close_bracket = Pattern.compile(">");                           /*writing > symbol in close_bracket*/
        ArrStack s = new ArrStack();
        String XML = "";                                                        /*empty string to save corrected file temporarily*/
        String open_tag = "";
        String close_tag = "";
        String error_at="^";
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
                    next_line_tag = "0";
                }
                /*getting next line tag to be used*/
                else if (next_line.contains("<") && next_line.contains(">")) {
                    if(next_line.contains("/"))
                    {
                        next_line_tag = next_line.substring(next_line.indexOf("<") + 2, next_line.indexOf(">"));
                    }
                    else
                    {
                        next_line_tag = next_line.substring(next_line.indexOf("<") + 1, next_line.indexOf(">"));
                    }
                }
            }
            else{
                next_line_tag=" ";
            }
            if(XML.contains("<") && XML.contains(">"))
            {
                if(matcher1.find() && matcher1.find() && matcher2.find() && matcher2.find())    /*condition for 2 tags at the same line*/
                {
                    open_tag = XML.substring(XML.indexOf("<")+1,XML.indexOf(">"));
                    close_tag = XML.substring(matcher1.end()+1, matcher2.start());
                    if(!open_tag.equals(close_tag))
                    {
                        error_at += i+"_/"+open_tag+"-";
                        System.out.println("there is an error at line: "+(i+1));
                    }
                }
                else if(XML.contains("?") || XML.contains("!"))
                {
                    continue;
                }
                else{
                    if (!XML.contains("/"))         /*condition for all opening tags*/
                    {
                        open_tag=XML.substring(XML.indexOf("<")+1, XML.indexOf(">"));
                        if(open_tag.equals(s.top()))              /*if two opening tags entered in the stack and equal -> error*/
                        {
                            error_at += i+"_/"+s.top()+"-";
                            System.out.println("there is an error at line: "+(i+1));
                            s.pop();
                        }
                        else {
                            if(flag==0)             /*if there is no error*/
                            {
                                s.push(open_tag);
                            }
                            flag=0;
                        }
                    }
                    else if(XML.contains("/"))                      /*condition for all closing tag*/
                    {
                        close_tag = XML.substring(XML.indexOf("<") + 2, XML.indexOf(">"));
                        if (close_tag.equals(s.top()))              /*if there is no error then pop*/
                        {
                            s.pop();
                        }
                        else if(next_line_tag.equals(s.top()) || s.top()==null) {
                            error_at += (i) + "_" + "remove"+"-";
                            System.out.println("there is an error at line: "+(i+1));

                        }
                        else
                        {
                            prev_line=Incorrect_File[i-1];
                            if(!prev_line.contains(">") && !prev_line.contains("<"))
                            {
                                open_tag= String.valueOf(s.top());
                                s.pop();
                                if(close_tag.equals(s.top())) {
                                    error_at += (i) + "_/" + open_tag+"-";
                                    System.out.println("there is an error at line: " + (i + 1));
                                    i--;
                                }
                                else{
                                    error_at += (i - 1) + "_" + close_tag+"-";
                                    System.out.println("there is an error at line: " + (i -1));
                                    s.push(open_tag);
                                }
                            }
                            else if(close_tag.equals(next_line_tag))
                            {
                                if(!next_line.contains("<"))        /*2 closing tags instead of openning then closing*/
                                {
                                    error_at += (i) + "_+"+"-" ;
                                    System.out.println("there is an error at line: " + (i + 1));
                                    s.push(close_tag);
                                    continue;
                                }
                                open_tag= (String) s.top();
                                s.pop();
                                if (!close_tag.equals(s.top())) {
                                    error_at += (i) + "_" + "remove" + "-";
                                    System.out.println("there is an error at line: " + (i + 1));
                                    s.push(open_tag);
                                } else if (close_tag.equals(s.top())) {
                                    error_at += i + "_/" + open_tag + "-";
                                    s.pop();
                                    System.out.println("there is an error at line: " + (i + 1));
                                }
                            }
                            else
                            {
                                open_tag= (String) s.top();
                                s.pop();
                                if(close_tag.equals(s.top()))
                                {
                                    if (flag==1)
                                    {
                                        error_at += (i) + "_/" + open_tag+"-";
                                        System.out.println("there is an error at line: " + (i + 1));
                                        s.pop();
                                    }
                                    else {
                                        error_at += (i) + "_/" + open_tag + "-";
                                        System.out.println("there is an error at line: " + (i + 1));
                                        s.pop();
                                    }
                                    flag=0;
                                }
                                else
                                {
                                    error_at += (i) + "_" + "remove"+"-";
                                    System.out.println("there is an error at line: " + (i + 1));
                                    s.push(open_tag);
                                    flag=1;
                                }
                            }
                        }
                    }
                }
            }
            else
            {
                if(!next_line.contains("/") && !next_line_tag.equals(s.top()) && !next_line_tag.equals("0"))
                {
                    error_at += (i+1) + "_/" + s.top()+"-";
                    s.pop();
                    System.out.println("there is an error at line: " + (i + 1));
                }
                else if (!next_line.contains("/") && next_line_tag.equals(s.top()))      /*2 openning tags instead of openning then closing*/
                {
                    error_at += (i+1) + "_*" +"-";
                    s.pop();
                    System.out.println("there is an error at line: " + (i + 1));
                    flag=1;
                }
            }

        }
        if(!s.isEmpty())            /*condition of when the file is ended but the stack is not empty*/
        {
            error_at += (Incorrect_File.length-1)+"_/"+s.top()+"-";
            System.out.println("there is an error at line: "+(Incorrect_File.length-1));
        }
        if(error_at.equals("-"))
        {
            return "-1";            /*if the file has no error then return -1 */
        }
        else {
            return error_at;        /*if the file has one error ore more then return the line of the error and its type */
        }
    }


    public ArrayList<String> adding_missing_tag(String errorin , String[] Incorrect_File) {
        int error_line = 0;
        int indexofslash = 0;
        String missing_tag = "";
        Pattern open_bracket = Pattern.compile("<");                        /*writing < symbol in open_bracket*/
        Pattern close_bracket = Pattern.compile(">");                       /*writing > symbol in close_bracket*/
        Pattern slash = Pattern.compile("-");
        Pattern close = Pattern.compile("/");
        Pattern underscore = Pattern.compile("_");
        //Matcher matcher3 = close.matcher(errorin);
        Matcher matcher4 = slash.matcher(errorin);
        Matcher matcher5 = underscore.matcher(errorin);
        ArrayList<String> Fixed_file = new ArrayList<>();                   /*creating array list to save the file after being corrected*/
        for (int i = 0; i < Incorrect_File.length; i++)                            /*for loop that loop line by line in the xml file*/ {
            Fixed_file.add(Incorrect_File[i]);
        }
        if (errorin == "-1") {
            System.out.println("tags are correct");
        }
        else
        {
            /*area where we seperate the line of error and type of error to be used*/
            while (matcher4.find() && matcher5.find()) {
                if (errorin.contains("^")) {
                    indexofslash = matcher4.start();
                    error_line = Integer.parseInt(errorin.substring(errorin.indexOf("^") + 1, matcher5.start()));
                    missing_tag = errorin.substring(matcher5.end(), matcher4.start());
                    errorin = errorin.substring(errorin.indexOf("^") + 1,errorin.length());
                }
                else {
                    error_line = Integer.parseInt(errorin.substring(indexofslash , matcher5.start()-1));
                    missing_tag = errorin.substring(matcher5.end() -1, matcher4.start()-1);
                    indexofslash = matcher4.start();
                }
                Matcher matcher1 = open_bracket.matcher(Incorrect_File[error_line]);     /*searching for < in the xml file*/
                Matcher matcher2 = close_bracket.matcher(Incorrect_File[error_line]);    /*searching for > in the xml file*/
                Matcher matcher3 = close.matcher(Incorrect_File[error_line]);
                String str = "";                                                    /*empty string to save corrected file temporarily*/
                str = Incorrect_File[error_line];
                if (matcher1.find() && matcher2.find() && matcher2.find())    /*to correct line with 2 tags*/
                {
                    if(matcher3.find() && matcher3.find())
                    {
                        missing_tag = missing_tag.substring(2, missing_tag.length());
                        str = "<"+str.substring(matcher1.end()+1, matcher2.end()-missing_tag.length()-3) +"</"+ missing_tag +">";
                    }
                    else if(matcher1.find())
                    {
                        str = str.substring(0, matcher1.end()) + missing_tag + ">";
                    }
                }
                else if (missing_tag.equals("remove"))               /*to remove extra tag*/
                {
                    str = "";
                }
                else if (missing_tag.equals("*")){                /*to correct to opening tags insted of oppening then closing*/
                    str = str.substring(0, str.indexOf("<") + 1) + "/" + str.substring(str.indexOf("<") + 1, str.indexOf(">") + 1);
                }
                else if (missing_tag.equals("+"))                /*to correct to closing tags insted of opening then closing*/
                {
                    str = str.substring(0, str.indexOf("<") + 1) + str.substring(str.indexOf("<") + 2, str.indexOf(">") + 1);
                }
                else if (error_line == Incorrect_File.length - 1)        /*to correct the last line */
                {
                    str = str + "\n<" + missing_tag + ">";
                } else
                {
                    str = "<" + missing_tag + ">" + "\n" + str;
                }
                Fixed_file.set(error_line, str);
            }
        }
        for (int i = 0; i < Fixed_file.size(); i++) {
            System.out.println(Fixed_file.get(i));
        }
        return Fixed_file;      /*return the file after correcting*/
    }
}