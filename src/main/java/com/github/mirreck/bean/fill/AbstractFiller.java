package com.github.mirreck.bean.fill;


import java.beans.PropertyDescriptor;
import java.lang.reflect.Method;

import com.github.mirreck.BeanUtils;
import com.github.mirreck.FakeFactoryException;

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
        return "[Filler] property=" + ((writerMethod == null)?"none":writerMethod.getName());
    }
	
    @Override
	public void apply(T bean) {
    	try {
    		Object value = BeanUtils.matchType(this.writerMethod.getParameterTypes()[0], generateValue());
			writerMethod.invoke(bean, value);
		} catch (Exception e) {
            throw new FakeFactoryException("Unable to set property value", e);
        }
		
	}
	
    protected abstract String generateValue();
}
