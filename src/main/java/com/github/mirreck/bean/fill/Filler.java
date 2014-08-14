package com.github.mirreck.bean.fill;

/**
 * Created by thomas.decoster on 22/07/2014.
 */
public interface Filler<T> {
    void apply(T bean);
}
