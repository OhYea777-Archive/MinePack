package com.ohyea777.minepack.util.reflection;

import com.ohyea777.minepack.util.reflection.composite.Composite;

public class MojangsonParser extends Composite {

    private static Class<?> MOJANSON_PARSER_CLASS = ReflectionUtils.getClass("MojangsonParser", false);

    public MojangsonParser(Object parent) {
        super(parent);
    }

    public static NBTTagCompound parse(String json) {
        return new NBTTagCompound(ReflectionUtils.invokeStatic(MOJANSON_PARSER_CLASS, "parse", new Class<?>[] { String.class }, json));
    }

}
