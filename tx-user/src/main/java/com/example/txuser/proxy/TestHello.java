package com.example.txuser.proxy;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

public class TestHello {

    public static void main(String[] args) throws IllegalAccessException, InstantiationException, NoSuchMethodException, InvocationTargetException {
//        Hello hello = new HelloImpl();
//        JdkProxyHello jdkProxyHello = new JdkProxyHello(hello);
//        Hello proxy = (Hello)jdkProxyHello.getProxy();
//        proxy.sayHello();

               Class c  = HelloImpl.class;
        Object newInstance = c.newInstance();
        Method sayHello = c.getMethod("sayHello");
        sayHello.invoke(newInstance);


    }


}
