package com.example.demo.exceptions;


public class DoesNotExist extends RuntimeException{

    public DoesNotExist(String message) {
        super(message);
    }
}
