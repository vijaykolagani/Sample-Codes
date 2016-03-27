package com.onetaxi.validations;

 
public interface Validation {

    String getErrorMessage();

    boolean isValid(String text);

}