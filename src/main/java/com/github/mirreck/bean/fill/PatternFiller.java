package com.github.mirreck.bean.fill;

import java.beans.PropertyDescriptor;
import java.lang.reflect.Method;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.github.mirreck.FakeFactory;


/**
 * Created by thomas.decoster on 22/07/2014.
 */
public class PatternFiller<T> extends AbstractFiller<T> {

    private static final Logger LOGGER = LoggerFactory.getLogger(PatternFiller.class);

    protected FakeFactory fakeFactory;
    private String pattern;
    private Class expectedType;

    public PatternFiller(FakeFactory fakeFactory, Method writerMethod, String value){
        this(fakeFactory,writerMethod,value,writerMethod.getParameterTypes()[0]);
    }

    public PatternFiller(FakeFactory fakeFactory, Method writerMethod, String value, Class expectedType){
        super(writerMethod);
        this.fakeFactory = fakeFactory;
        this.pattern = value;
        this.expectedType = expectedType;

    }
	@Override
	protected Object generateValue() {
		return fakeFactory.evaluatePattern(pattern, expectedType);
	}




}
