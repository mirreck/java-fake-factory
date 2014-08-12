package com.github.mirreck.bean;

import com.github.mirreck.FakeFactory;
import com.github.mirreck.FakeFactoryException;
import com.github.mirreck.bean.fill.*;
import com.google.common.collect.Lists;

import org.ho.yaml.Yaml;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.beans.IntrospectionException;
import java.beans.Introspector;
import java.beans.PropertyDescriptor;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by thomas.decoster on 11/07/2014.
 */
@Deprecated
public class FakeBeanFactory<T> {

    private static final Logger LOGGER = LoggerFactory.getLogger(FakeBeanFactory.class);
    public static final String FAKE_CFG_YML = "fake-cfg.yml";

    private Map<String, Object> configuration = new HashMap<String, Object>();

    private List<Filler<T>> fillers = new ArrayList<Filler<T>>();
    private FakeFactory fakeFactory;

    public FakeBeanFactory(Class<T> targetClass){
        // find configuration
        Map<String, Object> conf = loadConfiguration(targetClass);
        Object valuesMap = ((Map<String, Object>) conf).get(targetClass.getName());
        if(!(valuesMap instanceof Map)){
            throw new FakeFactoryException("Error while loading configuration !");
        }
        configuration = (Map<String, Object>) valuesMap;
        initializeFactory();
        initializeFillers(targetClass);
    }

	private Map<String, Object> loadConfiguration(Class<T> targetClass) {
        final InputStream inputStream = targetClass.getResourceAsStream(FAKE_CFG_YML);
        if(inputStream != null){
            LOGGER.info("specific configuration");
            final Object yaml = Yaml.load(inputStream);
            if(!(yaml instanceof Map)){
                throw new FakeFactoryException("Error while loading configuration !");
            }
            return (Map<String, Object>) yaml;
        } else {
            LOGGER.warn("no configuration found for class {}", targetClass.getName());
            return new HashMap<String, Object>();
        }
	}

    private void initializeFactory() {
        this.fakeFactory = new FakeFactory();
    }

    private void initializeFillers(Class targetClass){
        try {
            final PropertyDescriptor[] propertyDescriptors = Introspector.getBeanInfo(targetClass).getPropertyDescriptors();
            for (PropertyDescriptor propertyDescriptor : propertyDescriptors) {
                Object propConf = configuration.get(propertyDescriptor.getName());
                if (propConf != null) {
                    final Filler filler = filler(propertyDescriptor, propConf);
                    fillers.add(filler);
                } else {
                    final Filler filler = defaultFiller(propertyDescriptor);
                    fillers.add(filler);
                }
            }
        } catch (IntrospectionException e) {
            throw new FakeFactoryException("Unable to initialize Bean Factory",e);
        }
    }

    private Filler defaultFiller(PropertyDescriptor propertyDescriptor) {
        if(propertyDescriptor.getPropertyType().isEnum()){
            return defaultEnumFiller(propertyDescriptor);
        }
        return new NoopFiller();
    }

    private Filler defaultEnumFiller(PropertyDescriptor propertyDescriptor)  {
        try {
            Class<Enum> enumClass = (Class<Enum>) propertyDescriptor.getPropertyType();
            final Enum<?>[] values = (Enum<?>[]) enumClass.getDeclaredMethod("values").invoke(null);
            return new ValueListFiller(fakeFactory, propertyDescriptor.getWriteMethod(), Lists.newArrayList(values));
        } catch (Exception e) {
            throw new FakeFactoryException("Unable to initialize Filler",e);
        }
    }

    private Filler filler(PropertyDescriptor propertyDescriptor, Object configuration){
        if(!(configuration instanceof Map) && !(configuration instanceof List)){
            if(configuration instanceof String) {
                if ("sequence".equals(configuration)) {
                    return new SequenceFiller(propertyDescriptor.getWriteMethod());
                } else if ("object".equals(configuration)) {
                    return new RecursiveFiller(fakeFactory, propertyDescriptor);
                } else {
                    String pattern = (String) configuration;
                    return new PatternFiller(fakeFactory, propertyDescriptor.getWriteMethod(), pattern);
                }
            } else {
                return new ValueFiller(propertyDescriptor.getWriteMethod(), configuration);
            }
        }
        throw new FakeFactoryException("Cannot create filler for "+propertyDescriptor.getName());
    }

    public void fillBean(T bean){
         for(Filler filler : fillers){
             if(filler != null) {
                 LOGGER.info("Fill = {}", filler);
                 filler.apply(bean);
             }
        }
    }


}
