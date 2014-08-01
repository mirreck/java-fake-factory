package com.github.mirreck.bean.fill;


/**
 * Created by thomas.decoster on 22/07/2014.
 */
public class NoopFiller<T> implements Filler<T> {
    public void apply(T bean){
        // nothing to DO
    }


}
