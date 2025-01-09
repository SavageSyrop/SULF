package org.example.exception;

import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

@ControllerAdvice
@Slf4j
public class ControllerExceptionHandler {


    @ExceptionHandler(RuntimeException.class)
    public void handle(Exception exception) {
        System.out.println(exception.getMessage());
    }
}
