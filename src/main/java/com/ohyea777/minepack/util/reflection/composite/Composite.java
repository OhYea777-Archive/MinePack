package com.ohyea777.minepack.util.reflection.composite;

import com.ohyea777.minepack.util.reflection.ReflectionUtils;

import java.lang.invoke.MethodHandle;

public abstract class Composite {

    public Class<?> clazz;
    public Object parent;

    public Composite(Object parent) {
        this.parent = parent;
        this.clazz = ReflectionUtils.extractClass(parent);
    }

    public Composite(Class<?> clazz) {
        this.clazz = clazz;

        try {
            parent = clazz.newInstance();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public Class<?> getClazz(String className, boolean isCraftBukkit) {
        return ReflectionUtils.getClass(className, isCraftBukkit);
    }

    public Object invoke(MethodHandle method, Object... args) {
        return ReflectionUtils.invoke(parent, method, args);
    }

    public Object invoke(String methodName, Class<?>[] argClasses, Object... args) {
        return invoke(ReflectionUtils.getMethod(clazz, methodName, argClasses), args);
    }

}
