package application;


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
}
