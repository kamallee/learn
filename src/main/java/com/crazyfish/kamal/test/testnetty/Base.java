package com.crazyfish.kamal.test.testnetty;

/**
 * @author lipengpeng
 * @desc
 * @date 2016-01-29 下午5:48
 */
import com.crazyfish.kamal.test.testgeneric.Man;
import com.crazyfish.kamal.test.testgeneric.Work;

import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;
//Work<Man>是ParameterizedType（参数化类型），因为它需要参数Man或其他参数，Man不是参数化类型
public class Base<T> extends Work<Man> {//去掉extends Work<Man>则无法实例化entityClass
    private Class<T> entityClass;

    public Base(){
        //entityClass =(Class<T>)((ParameterizedType)getClass().getGenericSuperclass()).getActualTypeArguments()[0];
        try {
            Class<?> clazz = getClass(); //获取实际运行的类的 Class
            Type type = clazz.getGenericSuperclass(); //获取实际运行的类的直接超类的泛型类型
            if(type instanceof ParameterizedType){ //如果该泛型类型是参数化类型
                Type[] parameterizedType = ((ParameterizedType)type).getActualTypeArguments();//获取泛型类型的实际类型参数集
                entityClass = (Class<T>) parameterizedType[0]; //取出第一个(下标为0)参数的值
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    //泛型的实际类型参数的类全名
    public String getEntityName(){
        return entityClass.getName();
    }

    //泛型的实际     类型参数的类名
    public String getEntitySimpleName(){
        return entityClass.getSimpleName();
    }

    //泛型的实际类型参数的Class
    public Class<T> getEntityClass() {
        return entityClass;
    }

}
