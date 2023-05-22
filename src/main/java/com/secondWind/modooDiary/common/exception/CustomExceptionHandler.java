package com.secondWind.modooDiary.common.exception;

import com.secondWind.modooDiary.common.exception.code.AuthErrorCode;
import com.secondWind.modooDiary.common.result.ResponseHandler;
import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.UnsupportedJwtException;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.context.support.DefaultMessageSourceResolvable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.validation.ObjectError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.util.List;

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

    @ExceptionHandler({SlackException.class})
    public ResponseEntity<?> exceptionHandler(HttpServletRequest request, final SlackException e) {
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
                .errorMessage(e.getErrorEntity().getError().getCode())
                .errorCode(e.getErrorEntity().getError().getMessage())
                .build();
    }

    @ExceptionHandler({MethodArgumentNotValidException.class})
    public ResponseEntity<?> exceptionHandler(HttpServletRequest request, final MethodArgumentNotValidException e) {
        List<ObjectError> allErrors = e.getBindingResult().getAllErrors();
        StringBuilder stringBuilder = new StringBuilder();
        for (ObjectError error : allErrors) {
            stringBuilder.append(error.getDefaultMessage()).append(" ");
        }
        return ResponseHandler.failResultGenerate()
                .status(HttpStatus.BAD_REQUEST)
                .errorCode(AuthErrorCode.BAD_REQUEST_BODY.getCode())
                .errorMessage(stringBuilder.toString())
                .build();
    }

    @ExceptionHandler({UsernameNotFoundException.class})
    public ResponseEntity<?> exceptionHandler(HttpServletRequest request, final UsernameNotFoundException e) {
        return ResponseHandler.failResultGenerate()
                .status(HttpStatus.UNAUTHORIZED)
                .errorCode("NOT_FOUND_LOGIN_ID")
                .errorMessage(e.getMessage())
                .build();
    }

    @ExceptionHandler({ExpiredJwtException.class})
    public ResponseEntity<?> exceptionHandler(HttpServletRequest request, final ExpiredJwtException e) {
        return ResponseHandler.failResultGenerate()
                .status(HttpStatus.UNAUTHORIZED)
                .errorCode(AuthErrorCode.EXPIRED_TOKEN.getCode())
                .errorMessage(AuthErrorCode.EXPIRED_TOKEN.getMessage())
                .build();
    }

    @ExceptionHandler({UnsupportedJwtException.class})
    public ResponseEntity<?> exceptionHandler(HttpServletRequest request, final UnsupportedJwtException e) {
        return ResponseHandler.failResultGenerate()
                .status(HttpStatus.UNAUTHORIZED)
                .errorCode(AuthErrorCode.UNSUPPORTED_TOKEN.getCode())
                .errorMessage(AuthErrorCode.UNSUPPORTED_TOKEN.getMessage())
                .build();
    }
}
