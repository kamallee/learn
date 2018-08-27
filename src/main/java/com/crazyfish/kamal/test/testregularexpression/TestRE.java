package com.crazyfish.kamal.test.testregularexpression;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Created by kamal on 15/9/9.
 */
public class TestRE {
    public static void main(String args[]) {
        String text = "|19200";
        String patternString = ".*\\|(\\d+)";
        Pattern pattern = Pattern.compile(patternString);
        Matcher m = pattern.matcher(text);
        if( m.matches() ){
            String baudRate = m.group(m.groupCount());
            System.out.println("bau:" + baudRate);
        }
    }
}
