package com.crazyfish.kamal.test.TestSomething;

/**
 * Created by kamal on 15/8/6.
 */
public class Employee implements java.io.Serializable {
    public String name;
    public String address;
    public transient int SSN;
    public int number;

    public void mailCheck() {
        System.out.println("Mainling a check to " + name + " " + address);
    }
}