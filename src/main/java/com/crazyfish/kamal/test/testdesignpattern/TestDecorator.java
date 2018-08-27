package com.crazyfish.kamal.test.testdesignpattern;

import java.lang.reflect.*;

/**
 * Created by kamal on 15/10/9.
 */
public class TestDecorator {
    public static void main(String args[]){
        //Coffee cof = new Espresso();
        //Coffee c1 = new AddMilk(cof);

        //Coffee coff = new Decaf();
        //Coffee c2 = new AddSoy(coff);
//        cof = new Decorator(cof,AddMilk.class);
//        cof = new Decorator(cof,AddSoy.class);
//        cof = new Decorator(cof,AddSoy.class);
//        cof = new Decorator(cof,AddSoy.class);
        //cof.cost();

        try {
            Method m = Espresso.class.getDeclaredMethod("cox");
            if( !m.isAccessible() ){
                m.setAccessible(true);
            }
            m.invoke(new Espresso());
        } catch (NoSuchMethodException e) {
            e.printStackTrace();
        } catch (InvocationTargetException e) {
            e.printStackTrace();
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        }
        //Coffee c3 = new AddSoy(cof);
        //System.out.println("c1:" + c1.cost() + "c2:" + c2.cost() + "c3:" + c3.cost());
    }
}
interface Coffee{//抽象构件
    double cost();//总花费
}
class Espresso implements Coffee{//具体构件
    public double cost(){
        System.out.println("espresso...");
        return 3;
    }
    private void cox(){
        System.out.println("xxx");
    }
}
class Decaf implements Coffee{//具体构件
    public double cost(){
        System.out.println("decaf...");
        return 5;
    }
}

interface Condiment{//装饰
    double cost1();
}

class AddMilk implements Condiment{//具体装饰
    Coffee coffee;
    public AddMilk(){}
    public AddMilk(Coffee coffee){
        this.coffee = coffee;
    }
    public double cost1(){
        System.out.println("add milk...");
        return 2.5;
    }
}
class AddSoy implements Condiment{//具体装饰
    Coffee coffee;
    public AddSoy(){}
    public AddSoy(Coffee coffee){
        this.coffee = coffee;
    }
    public double cost1(){
        System.out.println("add soy...");
        return 3.5;
    }
}
class Decorator implements Coffee{
    private Coffee coffee;
    private Class<? extends Condiment> clz;
    public Decorator(Coffee coffee,Class<? extends Condiment> clz){
        this.coffee = coffee;
        this.clz = clz;
    }
    @Override
    public double cost() {
        InvocationHandler handler = new InvocationHandler() {
            @Override
            public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {
                Object obj = null;
                if(Modifier.isPublic(method.getModifiers())){
                    obj = method.invoke(clz.newInstance(),args);
                }
                coffee.cost();
                return obj;
            }
        };
        ClassLoader cl = getClass().getClassLoader();
        Condiment proxy = (Condiment) Proxy.newProxyInstance(cl,clz.getInterfaces(),handler);
        proxy.cost1();
        return 0;
    }
}