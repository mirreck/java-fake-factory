package com.github.mirreck.bean;

import com.github.mirreck.RandomUtils;

import java.util.*;

/**
 * Created by thomas.decoster on 13/08/2014.
 */
public class BeanPool {
    private Map<Class<?>, List<Object>> data = new HashMap<Class<?>, List<Object>>();
    private Random random;

    public BeanPool(Random random){
       this.random = random;
    }


    public void add(Object obj){
        List<Object> objList = data.get(obj.getClass());
        if(objList == null){
            objList = new ArrayList<Object>();
            data.put(obj.getClass(), objList);
        }
        objList.add(obj);
    }
    public Object pick(Class clazz){
        List<Object> objList = data.get(clazz);
        if(objList == null){
            return null;
        }
        return objList.get(RandomUtils.intInInterval(random, 0, objList.size()-1));
    }


}
