package com.example.exception;

// Exception class created in case of need.
public class ClientException extends RuntimeException{
    private String text;
    private int code;

    public ClientException(String text, int code){
        super(text);
        this.text = text;
        this.code = code;
    }

    public String getText(){
        return text;
    }
    public int getCode(){
        return code;
    }
    
}
