package com.ohyea777.minepack.util.reflection;

import javassist.util.proxy.MethodHandler;
import javassist.util.proxy.ProxyFactory;
import org.apache.commons.lang.ArrayUtils;
import org.bukkit.Bukkit;
import org.bukkit.Server;

import java.lang.invoke.MethodHandle;
import java.lang.invoke.MethodHandles;
import java.lang.reflect.Method;

public class ReflectionUtils {

    private static String PRE_CLASS_CRAFTBUKKIT = "org.bukkit.craftbukkit";
    private static String PRE_CLASS_MINECRAFT = "net.minecraft.server";
    private static boolean forge;

    static {
        if (Bukkit.getServer() != null) {
            if (Bukkit.getVersion().contains("MCPC") || Bukkit.getVersion().contains("Cauldron") || Bukkit.getVersion().contains("Forge"))
                forge = true;

            Server server = Bukkit.getServer();
            Class<?> bukkitServerClass = server.getClass();

            String[] pas = bukkitServerClass.getName().split("\\.");
            if (pas.length == 5) {
                PRE_CLASS_CRAFTBUKKIT += "." + pas[3];
            }

            try {
                Object handle = ReflectionUtils.invoke(server, "getHandle", null);
                Class<?> handleClass = handle.getClass();

                pas = handleClass.getName().split("\\.");
                if (pas.length == 5) {
                    PRE_CLASS_MINECRAFT += "." + pas[3];
                }
            } catch (Exception ignored) { }
        }
    }

    public static boolean isForge() {
        return isForge();
    }

    public static Class<?> extractClass(Object obj) {
        Class<?> clazz;

        if (obj instanceof Class<?>) {
            clazz = (Class<?>) obj;
        } else {
            clazz = obj.getClass();
        }

        return clazz;
    }

    public static Class<?> getClass(String className, boolean isCraftBukkit) {
        try {
            return Class.forName((isCraftBukkit ? PRE_CLASS_CRAFTBUKKIT : PRE_CLASS_MINECRAFT) + "." + className);
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }

        return null;
    }

    public static MethodHandle getMethod(Class<?> clazz, String methodName, Class<?>[] argClasses) {
        try {
            Method method = clazz.getDeclaredMethod(methodName, argClasses);
            method.setAccessible(true);

            return MethodHandles.lookup().unreflect(method);
        } catch (Exception e) {
            e.printStackTrace();
        }

        return null;
    }

    public static Object invoke(Object object, String methodName, Class<?>[] argClasses, Object... args) {
        return invoke(object, getMethod(object.getClass(), methodName, argClasses), args);
    }

    public static Object invoke(Object object, MethodHandle handle, Object... args) {
        try {
            return handle.invokeWithArguments(ArrayUtils.add(args, 0, object));
        } catch (Throwable throwable) {
            throwable.printStackTrace();
        }
        return null;
    }

    public static Object invokeStatic(Class<?> clazz, String methodName, Class<?>[] argClasses, Object... args) {
        try {
            return getMethod(clazz, methodName, argClasses).invokeWithArguments(args);
        } catch (Throwable throwable) {
            throwable.printStackTrace();
        }
        return null;
    }

    public static Object invokeStatic(String className, boolean isCraftBukkit, String methodName, Class<?>[] argClasses, Object... args) {
        return invokeStatic(getClass(className, isCraftBukkit), methodName, argClasses, args);
    }

    public static Object createProxy(Object source, MethodHandler handler, boolean createIdentical, Class<?>[] paramTypes, Object... args) {
        ProxyFactory pf = new ProxyFactory();
        pf.setSuperclass(ReflectionUtils.extractClass(source));

        try {
            Object proxy = pf.create(paramTypes, args, handler);

            return proxy;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

}
