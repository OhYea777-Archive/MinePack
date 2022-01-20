package com.ohyea777.minepack.util.reflection.composite;

import java.lang.invoke.MethodHandle;

public class Hook {

    public HookListener listener;
    public MethodHandle method;

    boolean wasHandled;

    public boolean getWasHandled() {
        return this.wasHandled;
    }

    public void setWasHandled(boolean wasHandled) {
        this.wasHandled = wasHandled;
    }

}
