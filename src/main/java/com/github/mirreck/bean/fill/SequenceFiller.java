package com.github.mirreck.bean.fill;

import java.lang.reflect.Method;

public class SequenceFiller<T> extends AbstractFiller<T> {

    private Long index = 1L;

    public SequenceFiller(Method writerMethod) {
        super(writerMethod);
    }

    @Override
    protected String generateValue() {
        index++;
        return index.toString();
    }


}
