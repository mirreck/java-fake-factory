package com.github.mirreck.bean.fill;

import com.github.mirreck.FakeFactory;
import com.github.mirreck.RandomUtils;

import java.beans.PropertyDescriptor;
import java.lang.reflect.Method;
import java.util.List;

/**
 * Created by thomas.decoster on 22/07/2014.
 */
public class ValueListFiller<T> extends PatternFiller<T> implements Filler<T> {

    private List<String> values;
    public ValueListFiller(FakeFactory fakeFactory, Method writerMethod, List<String> values){
        super(fakeFactory,writerMethod,"");
        this.values = values;
    }

	protected String getPattern(){
		return RandomUtils.randomElement(fakeFactory.getRandom(), values);
	}


}
