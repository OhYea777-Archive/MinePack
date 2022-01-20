package com.ohyea777.minepack.util.reflection.composite;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

public interface HookListener {

    public Object activate(Object self, Method method, Method proxyCallback, Hook hook, Object[] args) throws InvocationTargetException, IllegalAccessException;

}
