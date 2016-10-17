package com.fly.common.test.reflect;

import com.fly.common.reflect.TypeUtil;

import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.lang.reflect.Type;
import java.lang.reflect.TypeVariable;
import java.util.List;

/**
 * Created by jinxiaofei on 16/9/21.
 */

public class TypeTest {
    public static void main(String[] args) throws Exception {
        GenericClass genericClass=new GenericClass();
        BaseGeneircInteface.classGeneric();
        BaseGeneircInteface.methodGeneric();
        BaseGeneircInteface.fieldGeneric();
    }
}


abstract class BaseGeneircInteface<T> implements GeneircInteface<T> {

    protected T result;

    @Override
    public T method1(T obj) {
        return obj;
    }
    static void classGeneric() {
        System.out.println("\n--------------------- classGeneric ---------------------");
        GenericClass gc = new GenericClass();
        Type[] gis = gc.getClass().getGenericInterfaces(); // 接口的泛型信息
        Type gps = gc.getClass().getGenericSuperclass(); // 父类的泛型信息
        TypeVariable[] gtr = gc.getClass().getTypeParameters(); // 当前接口的参数信息
        System.out.println("============== getGenericInterfaces");
        for (Type t : gis) {
            System.out.println(t + " : " + TypeUtil.getClass(t, 0));
        }
        System.out.println("============== getGenericSuperclass");
        System.out.println(TypeUtil.getClass(gps, 0));
        System.out.println("============== getTypeParameters");
        for (TypeVariable t : gtr) {
            StringBuilder stb = new StringBuilder();
            for (Type tp : t.getBounds()) {
                stb.append(tp + " : ");
            }

            System.out.println(t + " : " + t.getName() + " : " + stb);
        }

    }

    static void methodGeneric() throws Exception {
        System.out.println("\n--------------------- methodGeneric ---------------------");
        GenericClass gc = new GenericClass();
        Method method3 = gc.getClass().getDeclaredMethod("method3", new Class[]{Object.class});

        Type[] gpt3 = method3.getGenericParameterTypes();
        Type[] get3 = method3.getGenericExceptionTypes();
        Type gt3 = method3.getGenericReturnType();
        System.out.println("============== getGenericParameterTypes");
        for (Type t : gpt3) {
            System.out.println(t + " : " + TypeUtil.getClass(t, 0));
        }
        System.out.println("============== getGenericExceptionTypes");
        for (Type t : get3) {
            System.out.println(t + " : " + TypeUtil.getClass(t, 0));
        }
        System.out.println("============== getType");
        System.out.println(gt3 + " : " + TypeUtil.getClass(gt3, 0));
    }

    static void fieldGeneric() throws Exception {
        System.out.println("\n--------------------- fieldGeneric ---------------------");
        GenericClass gc = new GenericClass();
        Field field = gc.getClass().getSuperclass().getDeclaredField("result");

        Type gt = field.getGenericType();
        Type ft = field.getType();
        System.out.println("============== getGenericType");
        System.out.println(gt + " : " + TypeUtil.getClass(gt, 0));
        System.out.println("============== getType");
        System.out.println(ft + " : " + TypeUtil.getClass(ft, 0));
    }

}

class GenericClass extends BaseGeneircInteface<List> implements GeneircInteface<List> {


    @Override
    public List method1(List obj) {
        result = obj;
        return obj;
    }
    public Integer method2(Integer obj) {
        return obj;
    }

    public <T, E extends Throwable> T method3(T obj) throws E {
        return obj;
    }

}