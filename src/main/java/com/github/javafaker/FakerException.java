package com.github.javafaker;

public class FakerException extends RuntimeException {

    private static final long serialVersionUID = 1L;

    public FakerException(String string, Exception e) {
        super(string, e);
    }

    public FakerException(String string) {
        super(string);
    }

}
