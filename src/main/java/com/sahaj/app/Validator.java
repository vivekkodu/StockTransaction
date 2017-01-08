package com.sahaj.app;

public class Validator {
    /**
     * VAlidates if input is negative or not
     * @param value
     * @return True, if input is negative, false otherwise
     */
    public static Boolean ValidateIsNegative(int value){
        if(value < 0){
            return true;
        }else{
            return false;
        }
    }

    /**
     * Validates if input is null or nor
     * @param object
     * @return True, if input is not null, false otherwise.
     */
    public static Boolean ValidateNotNull(Object object){
        if(object == null){
            return false;
        }else{
            return true;
        }
    }
}
