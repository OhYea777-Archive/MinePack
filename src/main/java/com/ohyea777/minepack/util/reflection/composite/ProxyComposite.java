package com.ohyea777.minepack.util.reflection.composite;

import com.ohyea777.minepack.util.reflection.ReflectionUtils;
import javassist.util.proxy.MethodHandler;

import java.lang.invoke.MethodHandle;
import java.lang.invoke.MethodHandles;
import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class ProxyComposite extends Composite {

    private Map<String, List<Hook>> hooks;
    private List<Hook> globalHooks;

    static Field f = null;

    static {
        try {
            f = Class.forName("java.lang.invoke.DirectMethodHandle").getDeclaredField("member");
        } catch (Exception e) {
            e.printStackTrace();
        }
        f.setAccessible(true);
    }

    public String createSignature(MethodHandle methodHandle) {
        if (!methodHandle.isVarargsCollector()) {
            try {
                return f.get(methodHandle).toString();
            } catch (IllegalAccessException e) {
                e.printStackTrace();
            }
        }
        return "";
    }

    public void addHook(MethodHandle methodHandle, HookListener listener) {
        String key = createSignature(methodHandle);
        if (!hooks.containsKey(key)) {
            hooks.put(key, new ArrayList<Hook>());
        }

        Hook hook = new Hook();
        hook.listener = listener;
        hook.method = methodHandle;
        hooks.get(key).add(hook);
    }

    public void addHook(String methodName, boolean isCraftBukkit, Class<?>[] argClasses, HookListener listener) {
        addHook(ReflectionUtils.getMethod(parent.getClass().getSuperclass(), methodName, argClasses), listener);
    }

    public void addHook(HookListener listener) {
        Hook hook = new Hook();
        hook.listener = listener;
        globalHooks.add(hook);
    }

    public ProxyComposite(Class<?> clazz, Class<?>[] constructorArgTypes, Object... constructorArgs) {
        this(clazz, false, constructorArgTypes, constructorArgs);
    }

    public ProxyComposite(Object parent, boolean createIdentical, Class<?>[] constructorArgTypes, Object... constructorArgs) {
        super(ReflectionUtils.extractClass(parent));

        if (parent instanceof Class) {
            createIdentical = false;
        }

        hooks = new HashMap<String, List<Hook>>();
        globalHooks = new ArrayList<Hook>();

        this.parent = ReflectionUtils.createProxy(parent, new MethodHandler() {
            @Override
            public Object invoke(Object self, Method thisMethod, Method proceed, Object[] args) throws Throwable {
                thisMethod.setAccessible(true);
                try {
                    MethodHandle mh = MethodHandles.lookup().unreflect(thisMethod);
                    if (hooks.containsKey(createSignature(mh))) {
                        for (Hook hook : hooks.get(createSignature(mh))) {
                            try {
                                hook.wasHandled = false;
                                Object ret = hook.listener.activate(self, thisMethod, proceed, hook, args);

                                if (hook.wasHandled) {
                                    return ret;
                                }
                            } catch (InvocationTargetException e) {
                                throw e.getCause();
                            }
                        }
                    }

                    for (Hook hook : globalHooks) {
                        Object ret = hook.listener.activate(self, thisMethod, proceed, hook, args);
                        if (hook.wasHandled) {
                            return ret;
                        }
                    }

                    try {
                        return proceed.invoke(self, args);
                    } catch (InvocationTargetException e) {
                        throw e.getCause();
                    }
                } catch (Throwable t) {
                    throw t;
                }
            }
        }, createIdentical, constructorArgTypes, constructorArgs);
    }
}