package com.github.mirreck;


import com.github.mirreck.FakeFactoryException;
import com.google.common.collect.ImmutableMap;
import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import com.google.common.primitives.Primitives;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.beans.IntrospectionException;
import java.beans.Introspector;
import java.beans.PropertyDescriptor;
import java.io.Serializable;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.List;
import java.util.Map;

public class BeanUtils {

    private static final Logger LOGGER = LoggerFactory.getLogger(BeanUtils.class);
    private BeanUtils() {
    }

    public static Method findMethod(Class clazz, String name, int numArgs) {
        Method[] list = clazz.getMethods();

        for (Method method : list) {
            if (method.getName().equals(name) && method.getParameterTypes().length == numArgs) {
                return method;
            }
        }
        throw new FakeFactoryException("Unable to find method :%s with %s arguments", name, "" + numArgs);
    }
    public static Object[] matchMethodArgTypes(Method method, String[] args){
        Object[] result = new Object[args.length];
        final Class<?>[] parameterTypes = method.getParameterTypes();
        for (int i = 0; i < parameterTypes.length; i++) {
            Class<?> parameterType = parameterTypes[i];
            result[i] =  matchType(parameterType, args[i]);
        }
        return result;
    }

    public static final List<Class> VALUE_OF_CLASSES = Lists.<Class>newArrayList(
            Integer.class, int.class,
            Long.class, long.class,
            Float.class, float.class,
            Double.class, double.class,
            Boolean.class, boolean.class);
    public static final Map<Class,Class> PRIMITIVE_TO_OBJECT = ImmutableMap.<Class,Class>of(
            int.class,Integer.class,
            long.class, Long.class,
            float.class, Float.class,
            double.class, Double.class,
            boolean.class, Boolean.class);


    public  static <T> T cast(Object obj, Class<T> targetType) {
        if(obj == null){
            return null;
        }
        if(obj.getClass() .equals(targetType)){
            return (T)obj;
        } else if(targetType.isEnum()){
            return (T) matchEnum(targetType, obj);
        } else if(targetType.equals(String.class)) {
            return (T) String.valueOf(obj);
        } else if(VALUE_OF_CLASSES.contains(targetType)){
            return (T) matchPrimitive(targetType, obj);
        }
        throw new FakeFactoryException("Unsupported argument type : %s arg : %s", targetType.getName(), obj.getClass().getName());
    }

    public static Object matchType(Class targetType, Object arg){
        if(arg.getClass() .equals(targetType)){
            return arg;
        } else
        if(targetType.isEnum()){
            return matchEnum(targetType, arg);
        } else if(targetType.equals(String.class)) {
            return arg;
        } else if(VALUE_OF_CLASSES.contains(targetType)){
            return matchPrimitive(targetType, arg);
        }
        throw new FakeFactoryException("Unsupported argument type : %s arg : %s", targetType.getName(), arg.getClass().getName());
    }

    private static Object matchEnum(Class targetType, Object arg) {
        Class<Enum> enumClass = (Class<Enum>) targetType;
        try {
            return enumClass.getDeclaredMethod("valueOf",String.class).invoke(null,arg);
        } catch (Exception e) {
            throw new FakeFactoryException(e+"Unsupported enum argument type : %s", targetType.getName());
        }
    }

    private static Object matchPrimitive(Class targetType, Object arg) {
        if(targetType.equals(arg.getClass())|| PRIMITIVE_TO_OBJECT.get(targetType).equals(arg.getClass())){
            return arg;
        }
        LOGGER.info("matchPrimitive {} {}",targetType,arg.getClass());
        // wrap primitive target types
        Class wraped = Primitives.wrap(targetType);
        try {
            return wraped.getDeclaredMethod("valueOf",arg.getClass()).invoke(null, arg);
        } catch (Exception e) {
            throw new FakeFactoryException(e+"Unsupported enum argument type : %s",e, targetType.getName());
        }

    }

    public static PropertyDescriptor descriptorForName(String parameterName, Class beanClass){
        try {
            PropertyDescriptor[] propertyDescriptors = Introspector.getBeanInfo(beanClass).getPropertyDescriptors();
            for (PropertyDescriptor propertyDescriptor : propertyDescriptors) {
                if(propertyDescriptor.getName().equals(parameterName)){
                    return propertyDescriptor;
                }
            }
            throw new FakeFactoryException("can not find property descriptor for parameter : %s", parameterName);
        } catch (IntrospectionException e) {
            throw new FakeFactoryException("can not find property descriptor for parameter : %s", e, parameterName);
        }
    }

}