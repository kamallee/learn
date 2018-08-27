package com.crazyfish.kamal.test.testaop;

/**
 * Created by kamal on 15/8/27.
 */
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.*;

@Aspect
public class Guardian{

    @Pointcut("execution(* com.crazyfish.kamal.test.testaop.IPeople.*Peaches(..))")
    public String  foundMonkey(){
        System.out.println("大家好");
        return "hoooo";
    }

    @Before(value="foundMonkey()")//在 Jointpoint 指定位置之前执行
    public void foundBefore(){
        System.out.println("果园有所异动...");
    }

    @After("foundMonkey()")
    public void after(JoinPoint joinPoint) {
        System.out.println("after");
    }
    //返回之后执行
    @AfterReturning("foundMonkey() && @annotation(annotation) && args(name,..)")
    public void foundAfter(String name,BusinessAnnotation annotation){
        System.out.println("这里return了..." + name + " log:" + annotation.moduleName());
    }

    @Around("foundMonkey()")
    public void around(ProceedingJoinPoint pjp) throws Throwable {
        System.out.println("around start..环绕开始了");
        try {
            pjp.proceed();
        } catch (Throwable ex) {
            System.out.println("error in around");
            throw ex;
        }
        System.out.println("around end");
    }

    @AfterThrowing(pointcut = "foundMonkey()", throwing = "error")
    public void afterThrowing(JoinPoint jp, Throwable error) {
        System.out.println("error:" + error);
    }

}