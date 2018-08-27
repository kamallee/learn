package com.crazyfish.kamal.test.testaop;

import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

/**
 * Created by kamal on 15/8/27.
 */
public class Monkey implements IPeople {
    @BusinessAnnotation(moduleName="monkey",option="偷桃子")//自定义注解
    public void stealPeaches(String name){
        System.out.println(name+"正在偷桃...");
    }

    public String buyPeaches(String name){
        return name;
    }

    public void eatPeaches(String name){
        this.stealPeaches("不错，");
        System.out.println(name + "吃了个桃子");
        //throw new RuntimeException("出现exception");
    }
    public static void main(String[] args) {
        //cglib
        Monkey monkey = MonkeyProxyFactory.getAuthInstance();
        //monkey.eatPeaches("xxxx");

        System.out.println("max:" + Integer.MAX_VALUE + ":" + Integer.MIN_VALUE);
        ApplicationContext context = new ClassPathXmlApplicationContext("conf/applicationContext.xml");
        IPeople people = (IPeople) context.getBean("monkey");
        try {
            people.eatPeaches("YY");
            //people.stealPeaches("lee");
            //people.buyPeaches("lpp");
            //people.eatPeaches("kamal");
        }
        catch(Exception e) {}
    }
}
