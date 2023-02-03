//
// Source code recreated from a .class file by IntelliJ IDEA
// (powered by FernFlower decompiler)
//

package parsing;

import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Parsing {
    static List<String> NameList = new ArrayList();

    public Parsing() {
    }

    public static List<String> parse(String attribute, String XML) {
        String patternString1 = "<" + attribute + ">";
        String patternString2 = "</" + attribute + ">";
        Pattern pattern1 = Pattern.compile(patternString1);
        Matcher matcher1 = pattern1.matcher(XML);
        Pattern pattern2 = Pattern.compile(patternString2);
        Matcher matcher2 = pattern2.matcher(XML);

        while(matcher1.find()) {
            if (matcher2.find()) {
                NameList.add(XML.substring(matcher1.end(), matcher2.start()).trim());
            }
        }

        return NameList;
    }
}
