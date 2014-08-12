package com.github.mirreck.bean.fill;

import java.lang.reflect.Method;

/**
 * Created by thomas.decoster on 22/07/2014.
 */
public class ValueFiller<T> extends AbstractFiller<T> implements Filler<T> {

    private Object value;
    public ValueFiller(Method writerMethod, Object value){
        super(writerMethod);
        this.value = value;
    }
    
	@Override
	protected Object generateValue() {
		return value;
	}


}
