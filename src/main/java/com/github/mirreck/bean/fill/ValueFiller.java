package com.github.mirreck.bean.fill;

import java.beans.PropertyDescriptor;
import java.lang.reflect.InvocationTargetException;

/**
 * Created by thomas.decoster on 22/07/2014.
 */
public class ValueFiller<T> implements Filler<T> {

    private PropertyDescriptor property;
    private String value;
    public ValueFiller(PropertyDescriptor property, String value){
        this.property = property;
        this.value = value;
    }
    public void apply(T bean){
        try {

            property.getWriteMethod().invoke(bean, value);

        } catch (IllegalAccessException e) {
            e.printStackTrace();
        } catch (InvocationTargetException e) {
            e.printStackTrace();
        }
    }


}
