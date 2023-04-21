package com.secondWind.modooDiary.common.exception;

import com.secondWind.modooDiary.common.result.ResponseHandler;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.context.support.DefaultMessageSourceResolvable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class CustomExceptionHandler {

    @ExceptionHandler({ApiException.class})
    public ResponseEntity<?> exceptionHandler(HttpServletRequest request, final ApiException e) {
        return ResponseHandler.failResultGenerate()
                .status(e.getStatus())
                .errorMessage(e.getErrorEntity().getError().getMessage())
                .errorCode(e.getErrorEntity().getError().getCode())
                .build();
    }

    @ExceptionHandler({CustomAuthException.class})
    public ResponseEntity<?> exceptionHandler(HttpServletRequest request, final CustomAuthException e) {
        return ResponseHandler.failResultGenerate()
                .status(HttpStatus.UNAUTHORIZED)
                .errorMessage(e.getErrorEntity().getError().getMessage())
                .errorCode(e.getErrorEntity().getError().getCode())
                .build();
    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<?> methodArgumentNotValidException(MethodArgumentNotValidException e) {
        return ResponseEntity.badRequest().body(extractErrorMessages(e));
    }

    private Object extractErrorMessages(MethodArgumentNotValidException e) {
        return e.getBindingResult()
                .getAllErrors()
                .stream()
                .map(DefaultMessageSourceResolvable::getDefaultMessage);
    }
}
