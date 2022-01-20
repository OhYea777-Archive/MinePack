package com.ohyea777.minepack.util.reflection;

import com.ohyea777.minepack.util.reflection.composite.Composite;

public class MinecraftItemStack extends Composite {

    private static Class<?> MINECRAFT_ITEM_STACK_CLASS = ReflectionUtils.getClass("ItemStack", false);

    public MinecraftItemStack(Object parent) {
        super(parent);
    }

    public void save(NBTTagCompound tagCompound) {
        invoke("save", new Class<?>[] { tagCompound.clazz }, tagCompound.parent);
    }

    public static MinecraftItemStack createStack(NBTTagCompound tagCompound) {
        return new MinecraftItemStack(ReflectionUtils.invokeStatic(MINECRAFT_ITEM_STACK_CLASS, "createStack", new Class<?>[] { tagCompound.clazz }, tagCompound.parent));
    }

}
