package com.tokio.filme.exceptions;

import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

@Slf4j
@ControllerAdvice
public class GlobalException {

    @ExceptionHandler(RuntimeException.class)
    public String handleRuntimeException(Exception exception, Model model){

        model.addAttribute("status", HttpStatus.NOT_FOUND.value());
        model.addAttribute("message", exception.getMessage());

        log.error(exception.getMessage());

        return "error";
    }

    @ExceptionHandler(SaveException.class)
    public String SaveExceptionHandler(Exception exception, Model model){
        model.addAttribute("Status", HttpStatus.EXPECTATION_FAILED.value());
        model.addAttribute("message", exception.getMessage());

        return "error";
    }

    @ExceptionHandler(UsernameOrEmailExistException.class)
    public String usernameOrEmailExistHandler(Exception exception, Model model){
        model.addAttribute("Status", HttpStatus.EXPECTATION_FAILED.value());
        model.addAttribute("message", exception.getMessage());

        return "error";
    }
}
