package com.crazyfish.kamal.test.testdesignpattern.testfactorymodel;

/**
 * @author lipengpeng
 * @desc 简单工厂模式
 * @date 2016-02-03 下午3:58
 */
public class SimpleFactory {
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

    public static class SimpleFactorys{
        public Computer buyComputer(String type){
            if( type.equals("apple")){
                return new Apple();
            }else{
                return new Ibm();
            }
        }
    }

    public static void main(String args[]){
        SimpleFactorys factorys = new SimpleFactorys();
        Computer com = factorys.buyComputer("apple");
        com.run();
    }
}
