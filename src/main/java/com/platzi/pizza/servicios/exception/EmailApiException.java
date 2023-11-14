package com.platzi.pizza.servicios.exception;

public class EmailApiException extends RuntimeException{

    public EmailApiException(){
        super("ERROR SENDING EMAIL....");
    }
}
