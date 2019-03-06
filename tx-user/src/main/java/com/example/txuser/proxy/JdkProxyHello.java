package com.example.txuser.proxy;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;
import java.lang.reflect.Proxy;

public class JdkProxyHello implements InvocationHandler {

    /**
     *   被代理的对象，目标对象
     */
    private Object proxy;

    JdkProxyHello(Object proxy) {
        this.proxy = proxy;
    }

    /**
     * 获取被代理的对象，目标对象
     * 通过反射创建对象
     * @return
     */
    public Object getProxy() {
        Object instance = Proxy.newProxyInstance(proxy.getClass().getClassLoader(), proxy.getClass().getInterfaces(), this);
        return instance;
    }

    @Override
    public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {
        System.out.println("method" + method);
        method.invoke(this.proxy, args);
        return null;
    }
}
