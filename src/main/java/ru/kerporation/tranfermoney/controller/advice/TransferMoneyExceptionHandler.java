package ru.kerporation.tranfermoney.controller.advice;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import ru.kerporation.tranfermoney.exception.TransferMoneyException;

@ControllerAdvice
public class TransferMoneyExceptionHandler {

    @ExceptionHandler(TransferMoneyException.class)
    public final ResponseEntity<String> handleException(Exception e) {
        return ResponseEntity.badRequest().body(e.getMessage());
    }
}
