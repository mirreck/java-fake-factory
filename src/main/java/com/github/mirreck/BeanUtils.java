package com.github.mirreck;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

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
    public static Object matchType(Class targetType, String arg){
        if(targetType.isEnum()){
            Class<Enum> enumClass = (Class<Enum>) targetType;
            try {
                return enumClass.getDeclaredMethod("valueOf",String.class).invoke(null,arg);
            } catch (Exception e) {
                e.printStackTrace();
                throw new FakeFactoryException(e+"Unsupported enum argument type : %s", targetType.getName());
            }
        } else if(targetType.equals(String.class)){
            return arg;
        } else if(targetType.equals(Integer.class) || targetType.equals(int.class)){
            return Integer.valueOf(arg);
        } else if(targetType.equals(Long.class) || targetType.equals(Long.class)){
            return Long.valueOf(arg);
        } else if(targetType.equals(Float.class) || targetType.equals(Float.class)){
            return Float.valueOf(arg);
        } else if(targetType.equals(Double.class) || targetType.equals(Double.class)){
            return Double.valueOf(arg);
        } else if(targetType.equals(Boolean.class) || targetType.equals(boolean.class)){
            return Boolean.valueOf(arg);
        }
        throw new FakeFactoryException("Unsupported argument type : %s", targetType.getName());
    }

}