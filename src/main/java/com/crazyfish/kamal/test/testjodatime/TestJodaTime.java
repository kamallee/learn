package com.crazyfish.kamal.test.testjodatime;

import org.joda.time.DateTime;

/**
 * Created by kamal on 16/1/15.
 */
public class TestJodaTime {
    public static void main(String args[]) {
        DateTime dateTime = new DateTime(2012, 12, 17, 18, 23, 55);
        DateTime dateTime1 = new DateTime(2011, 2, 13, 10, 30, 50, 333);
        String str = dateTime.toString("EEEE dd MMMM,yyyy HH:mm:ssa");
        String str1 = dateTime1.toString("yyyyMMdd HH:mm:ssa");
        System.out.println("str:" + str + "\nstr1:" + str1 + "\ndayof:" + dateTime.getDayOfMonth() + ":" + dateTime.dayOfWeek().get() + ":" + dateTime.dayOfYear().get());
        System.out.println("\ndayof:" + dateTime.centuryOfEra().get() + ":" + dateTime.hourOfDay().get());
    }
}
