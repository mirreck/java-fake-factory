package com.github.mirreck.bean.fill;

import com.github.mirreck.FakeFactory;
import com.github.mirreck.bean.FakeBeanBuilder;

import java.beans.PropertyDescriptor;
import java.util.Map;

/**
 * Created by thomas.decoster on 11/08/2014.
 */
public class RecursiveFiller<T> extends AbstractFiller<T> {

    final FakeBeanBuilder<?> fakeBeanBuilder;

    public RecursiveFiller(FakeFactory fakeFactory, PropertyDescriptor propertyDescriptor) {
        super(propertyDescriptor.getWriteMethod());
        fakeBeanBuilder = FakeBeanBuilder.forClass(propertyDescriptor.getPropertyType(),fakeFactory);
        fakeBeanBuilder.initWithConfigurationFile();
    }

    @Override
    protected Object generateValue() {
        return fakeBeanBuilder.build();
    }
}
