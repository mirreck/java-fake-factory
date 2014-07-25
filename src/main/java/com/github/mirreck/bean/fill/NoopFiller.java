package com.github.mirreck.bean.fill;

import java.beans.PropertyDescriptor;
import java.lang.reflect.InvocationTargetException;

/**
 * Created by thomas.decoster on 22/07/2014.
 */
public class NoopFiller<T> implements Filler<T> {
    public void apply(T bean){
        // nothing to DO
    }


}
