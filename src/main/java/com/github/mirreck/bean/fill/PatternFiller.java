package com.github.mirreck.bean.fill;

import com.github.mirreck.BeanUtils;
import com.github.mirreck.FakeFactory;
import com.github.mirreck.FakeFactoryException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.beans.PropertyDescriptor;


/**
 * Created by thomas.decoster on 22/07/2014.
 */
public class PatternFiller<T> extends AbstractFiller<T> {

    private static final Logger LOGGER = LoggerFactory.getLogger(PatternFiller.class);

    protected FakeFactory fakeFactory;
    private String pattern;

    public PatternFiller(FakeFactory fakeFactory, PropertyDescriptor property, String value){
        super(property);
        this.fakeFactory = fakeFactory;
        this.pattern = value;
    }
    public void apply(T bean){
        setValueWithPattern(bean, pattern);
    }
    protected void setValueWithPattern(T bean, String lPattern){
        try {
            final String value = fakeFactory.evaluatePattern(lPattern);
            LOGGER.info("pattern= {} value {}", lPattern, value);
            final Object converted = BeanUtils.matchType(this.property.getPropertyType(), value);
            property.getWriteMethod().invoke(bean, converted);
        } catch (Exception e) {
            throw new FakeFactoryException("Unable to fill property", e);
        }
    }



}
