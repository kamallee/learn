package com.crazyfish.kamal.test.TestSomething;

/**
 * Created by kamal on 15/8/24.
 */
public class UserLoginProxy implements IUserLogin{
    public IUserLogin login;
    public UserLoginProxy(IUserLogin login){
        this.login = login;
    }

    public boolean login(String str) {
        System.out.println("查找权限，result-->i can't login ,but by proxy");//do something others;
        login.login(str);
        return false;
    }

    public boolean register() {
        System.out.println("i can't register ,but by proxy");
        login.register();
        return false;
    }
    public static void main(String args[]){
        IUserLogin lo = new UserLoginImpl();
        IUserLogin pr = new UserLoginProxy(lo);
        pr.login("lpp");
        pr.register();
    }
}
