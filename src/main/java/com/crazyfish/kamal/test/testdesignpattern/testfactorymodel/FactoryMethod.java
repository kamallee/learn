package com.crazyfish.kamal.test.testdesignpattern.testfactorymodel;

/**
 * @author lipengpeng
 * @desc
 * @date 2016-02-03 下午4:10
 */
public class FactoryMethod {

    //产品类
    public interface Computer{
        public void run();
    }

    public static class Apple implements Computer{
        @Override
        public void run(){
            System.out.println("我是苹果电脑");
        }
    }

    public static class Ibm implements Computer{
        @Override
        public void run(){
            System.out.println("我是ibm电脑");
        }
    }

    //工厂类
    public interface FactoryMethods{
        public Computer buyComputer();
    }

    public static class BuyApple implements FactoryMethods{

        @Override
        public Computer buyComputer() {
            return new Apple();
        }
    }

    public static class BuyIbm implements FactoryMethods{
        @Override
        public Computer buyComputer(){
            return new Ibm();
        }
    }

    public static void main(String args[]){
        FactoryMethods factoryMethods = new BuyApple();//需要什么我就创建什么
        factoryMethods.buyComputer().run();

        FactoryMethods factoryMethods1 = new BuyIbm();
        factoryMethods1.buyComputer().run();
    }
}
