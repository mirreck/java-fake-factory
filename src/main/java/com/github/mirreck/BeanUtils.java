package com.github.mirreck;


import com.github.mirreck.FakeFactoryException;
import com.google.common.collect.Lists;
import com.google.common.primitives.Primitives;

import java.io.Serializable;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.List;

public class BeanUtils {
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

    public static Object matchType(Class targetType, String arg){
        if(targetType.isEnum()){
            return matchEnum(targetType, arg);
        } else if(targetType.equals(String.class)) {
            return arg;
        } else if(VALUE_OF_CLASSES.contains(targetType)){
            return matchPrimitive(targetType, arg);
        }
        throw new FakeFactoryException("Unsupported argument type : %s", targetType.getName());
    }

    private static Object matchEnum(Class targetType, String arg) {
        Class<Enum> enumClass = (Class<Enum>) targetType;
        try {
            return enumClass.getDeclaredMethod("valueOf",String.class).invoke(null,arg);
        } catch (Exception e) {
            throw new FakeFactoryException(e+"Unsupported enum argument type : %s", targetType.getName());
        }
    }

    private static Object matchPrimitive(Class targetType, String arg) {
            // wrap primitive target types
            Class wraped = Primitives.wrap(targetType);
        try {
            return wraped.getDeclaredMethod("valueOf",String.class).invoke(null,arg);
        } catch (Exception e) {
            throw new FakeFactoryException(e+"Unsupported enum argument type : %s",e, targetType.getName());
        }

    }

}