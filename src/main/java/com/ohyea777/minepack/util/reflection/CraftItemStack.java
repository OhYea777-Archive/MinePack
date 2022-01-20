package com.ohyea777.minepack.util.reflection;

import com.ohyea777.minepack.util.reflection.composite.Composite;
import org.bukkit.inventory.ItemStack;

public class CraftItemStack extends Composite {

    private static Class<?> CRAFT_ITEM_STACK_CLASS = ReflectionUtils.getClass("inventory.CraftItemStack", true);
    
    public CraftItemStack(Object parent) {
        super(parent);
    }

    public static MinecraftItemStack asNMSCopy(ItemStack itemStack) {
        return new MinecraftItemStack(ReflectionUtils.invokeStatic(CRAFT_ITEM_STACK_CLASS, "asNMSCopy", new Class<?>[] { ItemStack.class }, itemStack));
    }

    public static ItemStack asCraftMirror(MinecraftItemStack minecraftItemStack) {
        return (ItemStack) ReflectionUtils.invokeStatic(CRAFT_ITEM_STACK_CLASS, "asCraftMirror", new Class<?>[] { minecraftItemStack.clazz }, minecraftItemStack.parent);
    }

}
