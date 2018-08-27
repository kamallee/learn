package com.crazyfish.kamal.test.TestSomething;

import java.io.*;

/**
 * Created by kamal on 15/8/6.
 */
public class TestSerialization {
    public void testSerialize() {
        Employee emp = new Employee();
        emp.name = "lpp";
        emp.address = "beijing china";
        emp.SSN = 123455;
        emp.number = 988;
        try {
            File file = new File("tmp.ser");
            FileOutputStream fos = new FileOutputStream("tmp.ser");
            ObjectOutputStream oos = new ObjectOutputStream(fos);
            oos.writeObject(emp);
            oos.close();
            fos.close();
            System.out.println("serialize end");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void testDeSerialize() {
        Employee e = null;
        try {
            FileInputStream fileIn = new FileInputStream("tmp.ser");
            ObjectInputStream in = new ObjectInputStream(fileIn);
            e = (Employee) in.readObject();
            in.close();
            fileIn.close();
        } catch (IOException i) {
            i.printStackTrace();
            return;
        } catch (ClassNotFoundException c) {
            System.out.println("Employee class not found");
            c.printStackTrace();
            return;
        }
        System.out.println("Deserialized Employee...");
        System.out.println("Name: " + e.name);
        System.out.println("Address: " + e.address);
        System.out.println("SSN: " + e.SSN);
        System.out.println("Number: " + e.number);
    }

    public static void main(String args[]) {
        TestSerialization test = new TestSerialization();
        test.testSerialize();
        test.testDeSerialize();
        System.out.println("end");
    }
}
