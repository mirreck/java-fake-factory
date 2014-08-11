package com.github.mirreck.bean;

import java.beans.IntrospectionException;
import java.beans.Introspector;
import java.beans.PropertyDescriptor;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.github.mirreck.FakeFactory;
import com.github.mirreck.FakeFactoryException;
import com.github.mirreck.bean.fill.Filler;

public class FakeBeanBuilder<T> {
    private static final Logger LOGGER = LoggerFactory.getLogger(FakeBeanBuilder.class);

    private Class<T> beanClass;
    private List<Filler<T>> fillers = new ArrayList<Filler<T>>();
    private FakeFactory fakeFactory;
    
	private FakeBeanBuilder(Class<T> beanClass, FakeFactory fakeFactory)  
    {  
    	this.beanClass = beanClass;
    }  
	private FakeBeanBuilder(Class<T> beanClass)  
    {  
    	this(beanClass, new FakeFactory());
    }  
	
    public static <U> FakeBeanBuilder<U> forClass(Class<U> clazz){
    	return new FakeBeanBuilder<U>(clazz);
    }
    public T build(){
    	try {
			T bean = beanClass.newInstance();
			for (Filler<T> filler : fillers) {
				filler.apply(bean);
			}
			return bean;
		} catch (InstantiationException e) {
			throw new FakeFactoryException("can not create fake bean", e);
		} catch (IllegalAccessException e) {
			throw new FakeFactoryException("can not create fake bean", e);
		}
    }
	public FakeBeanBuilder<T> withParameterConfiguration(String parameterName,
			String configuration) {
		PropertyDescriptor[] propertyDescriptors;
		try {
			propertyDescriptors = Introspector.getBeanInfo(beanClass).getPropertyDescriptors();
		} catch (IntrospectionException e) {
			throw new FakeFactoryException("can not create fake bean", e);
		}
		for (PropertyDescriptor propertyDescriptor : propertyDescriptors) {
			if(propertyDescriptor.getName().equals(parameterName)){
				Method writeMethod = propertyDescriptor.getWriteMethod();
				
			}
			
		}
		return this;
	}
    
}
