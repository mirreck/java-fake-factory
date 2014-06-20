package com.github.mirreck;

public class FakeFactoryException extends RuntimeException {

    private static final long serialVersionUID = 1L;

    public FakeFactoryException(String string, Exception e) {
        super(string, e);
    }

    public FakeFactoryException(String string) {
        super(string);
    }

}
