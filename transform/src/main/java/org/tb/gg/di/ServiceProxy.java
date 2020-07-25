package org.tb.gg.di;

public class ServiceProxy {
    private Object obj = null;

    Object get() {
        return obj;
    }

    void setObject(Object obj) {
        this.obj = obj;
    }
}
