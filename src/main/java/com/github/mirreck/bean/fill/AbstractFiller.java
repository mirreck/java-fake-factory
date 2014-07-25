package com.github.mirreck.bean.fill;

import com.github.mirreck.BeanUtils;

import java.beans.PropertyDescriptor;
import java.lang.reflect.InvocationTargetException;

/**
 * Created by thomas.decoster on 23/07/2014.
 */
public abstract class AbstractFiller<T> implements Filler<T> {
    protected PropertyDescriptor property;

    public AbstractFiller(PropertyDescriptor property) {
        this.property = property;
    }

    @Override
    public String toString() {
        return "[Filler] property=" + property == null?"none":property.getName();
    }

    protected void setValue(T bean, Object value){
        try {
            property.getWriteMethod().invoke(bean, value);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }
}
