package com.github.mirreck.bean.fill;

import com.github.mirreck.FakeFactory;
import com.github.mirreck.RandomUtils;

import java.beans.PropertyDescriptor;
import java.lang.reflect.Method;
import java.util.List;

/**
 * Created by thomas.decoster on 22/07/2014.
 */
public class ValueListFiller<T> extends AbstractFiller<T> {

    protected FakeFactory fakeFactory;
    private List<String> values;
    public ValueListFiller(FakeFactory fakeFactory, Method writerMethod, List<String> values){
        super(writerMethod);
        this.fakeFactory = fakeFactory;
        this.values = values;
    }

    @Override
    protected Object generateValue() {
        Object val = getPattern();
        if(val instanceof String){
            return fakeFactory.evaluatePattern((String) val, writerMethod.getParameterTypes()[0]);
        }
        return val;
    }

	protected Object getPattern(){
		return RandomUtils.randomElement(fakeFactory.getRandom(), values);
	}


}
