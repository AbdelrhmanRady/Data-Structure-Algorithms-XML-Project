package application;


import java.util.ArrayList;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Validator extends ArrStack{

    public Validator()
    {
    }

    boolean isCorrect(String XML)
    {
        Pattern bracket1 = Pattern.compile("<");
        Matcher matcher1 = bracket1.matcher(XML);
        Pattern bracket2 = Pattern.compile(">");
        Matcher matcher2 = bracket2.matcher(XML);
        int push =0;
        int pop =0;
        while(matcher1.find()) {
            push("<");
            push++;
            if (matcher2.find()) {
                pop();
                pop++;
            }
        }
        System.out.println(push);
        System.out.println(pop);
        if(top()==null)
        {
            System.out.println("true");
            return true;
        }
        else {
            System.out.println("false");
            return false;
        }
    }
    
    public void Fix_Error(String[] Uncorrect_File)
    {
        Pattern open_bracket = Pattern.compile("<");            /*writing < symbol in open_bracket*/
        Pattern close_bracket = Pattern.compile(">");           /*writing > symbol in close_bracket*/
        ArrayList<String> Fixed_file = new ArrayList<String>();
        ArrStack S1 = new ArrStack();
        String s="";
        int counter=0;
        for(int i=0;i<Uncorrect_File.length;i++)
        {
            Fixed_file.add(Uncorrect_File[i]);
            Matcher matcher1 = open_bracket.matcher(Uncorrect_File[i]);           /*searching for < in the xml file*/
            Matcher matcher2 = close_bracket.matcher(Uncorrect_File[i]);
            while (matcher1.find()) {
                if(!matcher2.find()){
                    s=Fixed_file.get(i)+">";
                    Fixed_file.set(i, s);
                }
            }
            while (matcher2.find()) {
                if(!matcher1.find()){
                    s=Fixed_file.get(i);
                    while(Fixed_file.get(i).charAt(counter) == ' '){
                        counter++;
                    }
                    s=s.substring(0, counter)+"<"+s.substring(counter);
                    Fixed_file.set(i, s);
                }
            }
            System.out.println(Fixed_file.get(i));
            counter=0;
        }


    }
}
