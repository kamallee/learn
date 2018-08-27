package com.crazyfish.kamal.test.testgeneric;

import com.crazyfish.kamal.test.testnetty.Base;

import java.util.ArrayList;
import java.util.List;

/**
 * @author lipengpeng
 * @desc
 * @date 2016-01-28 下午1:17
 */
public class Main {
    public static void main(String args[]){
        Woman woman = new Woman("Rose");
        Cook cook = new Cook<Woman>();
        //Cook cook1 = new Cook<Man>();//无法编译通过
        Man man = new Man("Tom");
        Kamal kamal = new Kamal("kamal");
        Work work = new Work<Kamal>();

        cook.cook(woman);
        work.working(man);
        work.working(kamal);
        List<Man> list = new ArrayList();
        testSuper(list);

        List<Kamal> list1 = new ArrayList();
        list1.add(kamal);
        testExtends(list1);
        //testSuper(list1);//无法编译
        //testExtends(list);//无法编译

        Base<String> base = new Base<String>();
        System.out.println(base.getEntityClass());

        //编译器进行了类型推导和自动打包，也就是说原来需要我们自己对类型进行的判断和处理，现在编译器帮我们做了
        getTType(1.3,new Man(),"123");

        List<String> listx = new ArrayList<String>();
        listx.set(0,"3");
    }

    //super 下界，？至少是super后面的类或者其父类（？至少应该是Man或者Man的父类
    public static void testSuper(List<? super Man> work){
        Man lee = new Man("lee");
        Human human = new Man("xx");
        //work.add(human);//无法插入
        work.add(lee);//为什么可以add呢，因为？一定是Man或者Man的超类
        try {
            Kamal tmp = (Kamal) work.get(0);//向下类型转换失败，get获取的一定是Man或者Man的父类，因为用super限定类List容器能够存储的类型
        }catch(Exception e){
            //e.printStackTrace();
        }
        Kamal jack = new Kamal("Jack");
        work.add(jack);

        ArrayList<? super Integer> collection = null;//限定通配符下届
        collection = new ArrayList<Object>();
        collection = new ArrayList<Number>();
    }

    //extends 上界，？至少是extends后面的类或者其子类（？至少是Kamal或者其之类）
    public static void testExtends(List<? extends Kamal> work){
        Man lee = new Man("lee");
        //work.add(lee);//无法add,向下类型转换失败,因为add一定是Kamal或者Kamal的子类
        try {
            Kamal tmp = (Kamal) work.get(0);
        }catch(Exception e){
            e.printStackTrace();
        }
        Kamal jack = new Kamal("Jack");
        //work.add(jack);//与work.add(lee)进行对比，这个类型明明是合法的呀，为什么不能add呢，因为work集合中有可能之前存在的是Kamal的子类，则
        //kamal强制转换为kamal的子类失败
        Man k = work.get(0);//向上类型转换，没有问题
        System.out.println(k.getName());

        ArrayList<? extends Number> collection = null;//限定通配符上界
        collection = new ArrayList<Number>();
        collection = new ArrayList<Short>();
    }

    //泛型方法
    public static <T,M,N> void getTType(T t,M m,N n){
        //传入int,long ,double等基本类型时，自动装箱机制
        //会将基本类型包装成相应的对象
        System.out.println(t.getClass().getName());
        System.out.println(m.getClass().getName());
        System.out.println(n.getClass().getName());
    }
}
