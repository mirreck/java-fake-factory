package com.github.mirreck.bean;

import com.github.mirreck.FakeFactory;
import com.github.mirreck.FakeFactoryException;
import com.github.mirreck.RandomUtils;
import com.github.mirreck.bean.fill.*;
import com.google.common.collect.Lists;
import org.ho.yaml.Yaml;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.beans.IntrospectionException;
import java.beans.Introspector;
import java.beans.PropertyDescriptor;
import java.io.InputStream;
import java.lang.reflect.InvocationTargetException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by thomas.decoster on 11/07/2014.
 */
public class FakeBeanFactory<T> {

    private static final Logger LOGGER = LoggerFactory.getLogger(FakeBeanFactory.class);

    private static Map<String, Object> configuration = new HashMap<String, Object>();

    private List<Filler> fillers = new ArrayList<Filler>();
    private FakeFactory fakeFactory;

    public FakeBeanFactory(Class<T> targetClass){
        // find configuration
        final String propertiesFileName = targetClass.getSimpleName() + "-fake-cfg.yml";
        final InputStream inputStream = targetClass.getResourceAsStream(propertiesFileName);
        if(inputStream != null){
            LOGGER.info("specific configuration");
            final Object yaml = Yaml.load(inputStream);
            if(!(yaml instanceof Map)){
                throw new FakeFactoryException("Error while loading configuration !");
            }
            Object valuesMap = ((Map<String, Object>) yaml).get(targetClass.getName());
            if(!(valuesMap instanceof Map)){
                throw new FakeFactoryException("Error while loading configuration !");
            }
            configuration = (Map<String, Object>) valuesMap;


        } else {
            LOGGER.warn("no configuration found for class {}", targetClass.getName());
        }
        initializeFactory();
        initializeFillers(targetClass);
    }

    private void initializeFactory() {
        this.fakeFactory = new FakeFactory();
    }

    private void initializeFillers(Class targetClass){
        try {
            final PropertyDescriptor[] propertyDescriptors = Introspector.getBeanInfo(targetClass).getPropertyDescriptors();
            for (int i = 0; i < propertyDescriptors.length; i++) {
                PropertyDescriptor propertyDescriptor = propertyDescriptors[i];
                Object propConf = configuration.get(propertyDescriptor.getName());
                if(propConf != null){
                    final Filler filler = filler( propertyDescriptor, propConf);
                    fillers.add(filler);
                } else {
                    final Filler filler = defaultFiller(propertyDescriptor);
                    fillers.add(filler);
                }
            }
        } catch (IntrospectionException e) {
            throw new RuntimeException(e);
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
            return new ValueListFiller(fakeFactory, propertyDescriptor, Lists.newArrayList(values));
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }


    private PropertyDescriptor getPropertyDescriptor(Class targetClass, String name){
        try {
            final PropertyDescriptor[] propertyDescriptors = Introspector.getBeanInfo(targetClass).getPropertyDescriptors();
            for (int i = 0; i < propertyDescriptors.length; i++) {
                PropertyDescriptor propertyDescriptor = propertyDescriptors[i];
                if(propertyDescriptor.getName().equals(name)){
                    return propertyDescriptor;
                }
            }
        } catch (IntrospectionException e) {
            throw new RuntimeException("Property not found :"+name, e);
        }
        throw new RuntimeException("Property not found :"+name);
    }

    private Filler filler(PropertyDescriptor propertyDescriptor, Object configuration){
        if(configuration instanceof Map){
            // this is an ObjectFiller
            LOGGER.info("ObjectFiller : {}",configuration);
            return new NoopFiller();
        } else if(configuration instanceof List){
            // this is an ObjectFiller
            LOGGER.info("List Filler : {}",configuration);
            return new ValueListFiller(fakeFactory, propertyDescriptor,(List<String>) configuration);
        } else if(configuration instanceof String){
            String pattern = (String) configuration;
            return new PatternFiller(fakeFactory, propertyDescriptor, pattern);
        }
        throw new RuntimeException("Cannot create filler for "+propertyDescriptor.getName());
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
