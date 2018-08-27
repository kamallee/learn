package com.crazyfish.kamal.test.TestSomething;

/**
 * Created by kamal on 15/8/24.
 */
public class UserLoginImpl implements IUserLogin {
    public boolean login(String str) {
        System.out.println("you can login " + str + "!");
        return false;
    }

    public boolean register() {
        System.out.println("you can register");
        return false;
    }
}
