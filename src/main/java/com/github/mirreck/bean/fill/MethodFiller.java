package com.github.mirreck.bean.fill;

import com.github.mirreck.FakeFactory;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.beans.PropertyDescriptor;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

/**
 * Created by thomas.decoster on 22/07/2014.
 */
public class MethodFiller<T> extends AbstractFiller<T> {

    private static final Logger LOGGER = LoggerFactory.getLogger(MethodFiller.class);

    private FakeFactory fakeFactory;
    private Method method;

    public MethodFiller(FakeFactory fakeFactory, PropertyDescriptor property, String value){
        super(property);
        String methodName = value.substring(0,value.indexOf('('));
        LOGGER.info("method filler : {} {}",value,methodName);
        this.fakeFactory = fakeFactory;
        fakeFactory.evaluatePattern(value);
        try {
            method = fakeFactory.getClass().getDeclaredMethod(methodName, (Class[]) null);
        } catch (NoSuchMethodException e) {
            throw new RuntimeException(e);
        }
    }
    public void apply(T bean){
        try {
            final String value = (String) method.invoke(fakeFactory);
            property.getWriteMethod().invoke(bean, value);
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        } catch (InvocationTargetException e) {
            e.printStackTrace();
        }
    }



}
