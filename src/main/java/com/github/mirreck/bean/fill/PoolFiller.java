package com.github.mirreck.bean.fill;

import com.github.mirreck.bean.BeanPool;

import java.lang.reflect.Method;

/**
 * Created by thomas.decoster on 22/07/2014.
 */
public class PoolFiller<T> extends AbstractFiller<T> implements Filler<T> {

    private BeanPool pool;
    public PoolFiller(Method writerMethod, BeanPool pool){
        super(writerMethod);
        this.pool = pool;
    }
    
	@Override
	protected Object generateValue() {
		return pool.pick(writerMethod.getParameterTypes()[0]);
	}


}
