package com.crazyfish.kamal.test.testdesignpattern.testfactorymodel;

/**
 * @author lipengpeng
 * @desc 抽象工厂
 * @date 2016-02-03 下午4:18
 */
public class AbstractFactory {
    public interface Computer {
        public void run();
    }

    public interface CPU {
        public void run();
    }

    public static class Apple implements Computer {
        @Override
        public void run() {
            System.out.println("我是苹果电脑");
        }
    }

    public static class Ibm implements Computer {
        @Override
        public void run() {
            System.out.println("我是ibm电脑");
        }
    }

    public static class IntelCPU implements CPU{
        @Override
        public void run(){
            System.out.println("使用IntelCPU");
        }
    }

    public static class AMDCPU implements CPU{
        @Override
        public void run(){
            System.out.println("使用AMDCPU");
        }
    }

    public interface AbstractFactorys{
        public Computer buyComputer();
        public CPU withCPU();
    }

    public static class AppleWithIntelCPU implements AbstractFactorys{

        @Override
        public Computer buyComputer() {
            return new Apple();
        }

        @Override
        public CPU withCPU() {
            return new IntelCPU();
        }
    }

    public static class IbmWithAMDCPU implements AbstractFactorys{

        @Override
        public Computer buyComputer() {
            return new Ibm();
        }

        @Override
        public CPU withCPU() {
            return new AMDCPU();
        }
    }

    public static void main(String args[]){
        AbstractFactorys abstractFactorys = new AppleWithIntelCPU();
        abstractFactorys.buyComputer().run();
        abstractFactorys.withCPU().run();

        AbstractFactorys abstractFactorys1 = new IbmWithAMDCPU();
        abstractFactorys1.buyComputer().run();
        abstractFactorys1.withCPU().run();
    }
}