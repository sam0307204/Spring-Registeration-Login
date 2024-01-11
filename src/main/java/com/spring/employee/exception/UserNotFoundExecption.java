package com.spring.employee.exception;

public class UserNotFoundExecption  extends RuntimeException{
    public UserNotFoundExecption(){
        super("could not  found  the user  with id");
        }
}
