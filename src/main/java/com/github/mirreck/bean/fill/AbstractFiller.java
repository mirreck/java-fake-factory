package com.github.mirreck.bean.fill;


import com.github.mirreck.BeanUtils;
import com.github.mirreck.FakeFactoryException;

import java.lang.reflect.Method;

/**
 * Created by thomas.decoster on 23/07/2014.
 */
public abstract class AbstractFiller<T> implements Filler<T> {


    protected Method writerMethod;

    public AbstractFiller(Method writerMethod) {
        this.writerMethod = writerMethod;
    }

    @Override
    public String toString() {
        return "[" + this.getClass().getSimpleName() + "] property=" + ((writerMethod == null) ? "none" : writerMethod.getName());
    }

    @Override
    public void apply(T bean) {
        try {
            Object value = BeanUtils.cast(generateValue(), this.writerMethod.getParameterTypes()[0]);
            writerMethod.invoke(bean, value);
        } catch (Exception e) {
            throw new FakeFactoryException("Unable to set property value", e);
        }

    }

    protected abstract Object generateValue();
}
