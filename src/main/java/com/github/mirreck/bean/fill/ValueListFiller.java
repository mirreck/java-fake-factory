package com.github.mirreck.bean.fill;

import com.github.mirreck.FakeFactory;
import com.github.mirreck.RandomUtils;

import java.beans.PropertyDescriptor;
import java.lang.reflect.InvocationTargetException;
import java.util.List;

/**
 * Created by thomas.decoster on 22/07/2014.
 */
public class ValueListFiller<T> extends PatternFiller<T> implements Filler<T> {

    private List<Object> values;
    public ValueListFiller(FakeFactory fakeFactory, PropertyDescriptor property, List<Object> values){
        super(fakeFactory,property,"");
        this.values = values;
    }
    public void apply(T bean){
        final Object randomElement = RandomUtils.randomElement(fakeFactory.getRandom(), values);
        if(randomElement instanceof String){
            setValueWithPattern(bean, (String) randomElement );
        } else {
            setValue(bean, randomElement);
        }
    }


}
