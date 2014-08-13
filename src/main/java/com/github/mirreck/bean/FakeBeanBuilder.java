package com.github.mirreck.bean;

import java.beans.IntrospectionException;
import java.beans.Introspector;
import java.beans.PropertyDescriptor;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.github.mirreck.BeanUtils;
import com.github.mirreck.YamlUtils;
import com.github.mirreck.bean.fill.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.github.mirreck.FakeFactory;
import com.github.mirreck.FakeFactoryException;

public class FakeBeanBuilder<T> {
    public static final String FAKE_CFG_YML = "fake-cfg.yml";
    private static final Logger LOGGER = LoggerFactory.getLogger(FakeBeanBuilder.class);

    private Class<T> beanClass;
    private Map<String,Filler<T>> fillers = new HashMap<String,Filler<T>>();
    private FakeFactory fakeFactory;
    private BeanPool beanPool;

	public <U> FakeBeanBuilder(Class<U> beanClass, FakeFactory fakeFactory, BeanPool beanPool)
    {  
    	this.beanClass = (Class<T>) beanClass;
        this.fakeFactory = fakeFactory;
        this.beanPool = beanPool;
    }
    public FakeBeanBuilder(Class<T> beanClass)
    {  
    	this(beanClass, new FakeFactory(), null);
    }

    public List<T> build(int count){
        List<T> result = new ArrayList<T>();
        for (int i = 0; i < count; i++) {
             result.add(build());
        }
        return result;
    }
    public T build(){
    	try {
			T bean = beanClass.newInstance();
			for (Filler<T> filler : fillers.values()) {
                LOGGER.info("filler:"+filler);

				filler.apply(bean);
			}
            if(beanPool != null) {
                beanPool.add(bean);
            }
			return bean;
		} catch (InstantiationException e) {
			throw new FakeFactoryException("can not create fake bean", e);
		} catch (IllegalAccessException e) {
			throw new FakeFactoryException("can not create fake bean", e);
		}
    }

	public FakeBeanBuilder<T> withParameterPattern(String parameterName, String pattern) {
        PropertyDescriptor propertyDescriptor = BeanUtils.descriptorForName(parameterName, beanClass);
        Method writeMethod = propertyDescriptor.getWriteMethod();
        addFiller(parameterName, new PatternFiller<T>(this.fakeFactory, writeMethod, pattern));
		return this;
	}

    public FakeBeanBuilder<T> withParameterSequence(String parameterName) {
        PropertyDescriptor propertyDescriptor = BeanUtils.descriptorForName(parameterName,beanClass);
        Method writeMethod = propertyDescriptor.getWriteMethod();
        addFiller(parameterName, new SequenceFiller<T>(writeMethod));
        return this;
    }

    public FakeBeanBuilder<T> withParameterObject(String parameterName) {
        PropertyDescriptor propertyDescriptor = BeanUtils.descriptorForName(parameterName, beanClass);
        addFiller(parameterName, new RecursiveFiller(this.fakeFactory, propertyDescriptor, this.beanPool));
        return this;
    }

    public FakeBeanBuilder<T> withParameterPool(String parameterName) {
        PropertyDescriptor propertyDescriptor = BeanUtils.descriptorForName(parameterName, beanClass);
        addFiller(parameterName, new PoolFiller(propertyDescriptor.getWriteMethod(), this.beanPool));
        return this;
    }

    public FakeBeanBuilder<T> withParameterValue(String parameterName, Object value) {
        PropertyDescriptor propertyDescriptor = BeanUtils.descriptorForName(parameterName,beanClass);
        Method writeMethod = propertyDescriptor.getWriteMethod();
        addFiller(parameterName, new ValueFiller(writeMethod, value));
        return this;
    }

    private void withFiller(String paramName, Object configuration){
        LOGGER.info("withFiller : {} {}", paramName, configuration);
        if(configuration instanceof String) {
            if ("sequence".equals(configuration)) {
                withParameterSequence(paramName);
            } else if ("object".equals(configuration)) {
                withParameterObject(paramName);
            } else {
                withParameterPattern(paramName,(String) configuration);
            }
        } else if(!(configuration instanceof Map) && !(configuration instanceof List)) {
            withParameterValue(paramName,configuration);
        } else {
            throw new FakeFactoryException("Cannot create filler for " + paramName );
        }
    }

    public static Filler filler(FakeFactory fakeFactory, PropertyDescriptor propertyDescriptor, Object configuration){
        if(!(configuration instanceof Map) && !(configuration instanceof List)){
            if(configuration instanceof String) {
                if ("sequence".equals(configuration)) {
                    return new SequenceFiller(propertyDescriptor.getWriteMethod());
                } else if ("object".equals(configuration)) {
                    return new RecursiveFiller(fakeFactory, propertyDescriptor, null);
                } else {
                    String pattern = (String) configuration;
                    return new PatternFiller(fakeFactory, propertyDescriptor.getWriteMethod(), pattern, propertyDescriptor.getPropertyType());
                }
            } else {
                return new ValueFiller(propertyDescriptor.getWriteMethod(), configuration);
            }
        }
        throw new FakeFactoryException("Cannot create filler for "+propertyDescriptor.getName());
    }

    public FakeBeanBuilder<T> initWithConfigurationFile() {
        return initWithConfigurationFile(FAKE_CFG_YML);
    }
    public FakeBeanBuilder<T> initWithConfigurationFile(String resourcePath) {

        final Map<String, Object> conf = YamlUtils.loadYamlResource(beanClass, resourcePath, new HashMap<String, Object>());
        // bean fields
        final Map<String, Object> beanConfiguration = YamlUtils.fetchMap(conf, beanClass.getName());
        for (String key : beanConfiguration.keySet()) {
            LOGGER.info("initWithConfigurationFile bean  key:"+key);
            withFiller(key, beanConfiguration.get(key));
        }
        // extend fakefactory
        final Map<String, Object> extendFactory = YamlUtils.fetchMap(conf, "extend/faker");
        for (String key : extendFactory.keySet()) {
            LOGGER.info("initWithConfigurationFile ff  key:"+key);
        }
        this.fakeFactory.extendWithMap(extendFactory);
        return this;
    }

    public FakeBeanBuilder<T> withGuessedFillers() {
        try {
            PropertyDescriptor[] propertyDescriptors = Introspector.getBeanInfo(beanClass).getPropertyDescriptors();
            for (PropertyDescriptor propertyDescriptor : propertyDescriptors) {
                String parameterName = propertyDescriptor.getName();
                if(fillers.get(parameterName) == null){
                    Method writeMethod = propertyDescriptor.getWriteMethod();
                    fillers.put(parameterName,guessFiller(propertyDescriptor));
                }
            }
        } catch (IntrospectionException e) {
            throw new FakeFactoryException("can not initialize builder", e);
        }
        return this;
    }

    private Filler<T> guessFiller(PropertyDescriptor propertyDescriptor) {
        return null;
    }


    private void addFiller(String parameterName, Filler filler){
        if(fillers.get(parameterName) != null){
            LOGGER.warn("filler has already been defined for parameter : {}", parameterName);
        }
        fillers.put(parameterName, filler);
    }
}
