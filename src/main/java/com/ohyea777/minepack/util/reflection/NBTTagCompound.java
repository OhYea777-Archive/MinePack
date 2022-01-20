package com.ohyea777.minepack.util.reflection;

import com.ohyea777.minepack.util.reflection.composite.Composite;

public class NBTTagCompound extends Composite {

    private static Class<?> NBT_TAG_COMPOUND_CLASS = ReflectionUtils.getClass("NBTTagCompound", false);

    public NBTTagCompound(Object parent) {
        super(parent);
    }

    public NBTTagCompound() {
        super(NBT_TAG_COMPOUND_CLASS);
    }

    @Override
    public String toString() {
        return fixString(parent.toString());
    }

    private String fixString(String tag) {
        String fixedString = "";

        for (int i = 0; i < tag.length(); i ++) {
            if (tag.charAt(i) == ',' && i + 1 < tag.length() && (tag.charAt(i + 1) == '}' || tag.charAt(i + 1) == ']'))
                continue;

            fixedString += tag.charAt(i);
        }

        return fixedString;
    }

}
