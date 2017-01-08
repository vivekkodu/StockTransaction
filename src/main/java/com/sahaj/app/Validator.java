package com.sahaj.app;

public class Validator {
    public static Boolean ValidateIsNegative(int value){
        if(value < 0){
            return true;
        }else{
            return false;
        }
    }

    public static Boolean ValidateNotNull(Object object){
        if(object == null){
            return false;
        }else{
            return true;
        }
    }
}
