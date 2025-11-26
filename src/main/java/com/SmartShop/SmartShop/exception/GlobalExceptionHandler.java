package com.SmartShop.SmartShop.exception;


import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(BadRequestException.class)
    public ResponseEntity handleBadRequestException(BadRequestException e) {
        return ResponseEntity.status(400).body(e.getMessage());
    }
    @ExceptionHandler(NotFoundException.class)
    public ResponseEntity handleNotFoundException(NotFoundException e){
        return ResponseEntity.status(404).body(e.getMessage());
    }
    @ExceptionHandler(UnauthorizedException.class)
    public ResponseEntity handleUnauthorizedException(UnauthorizedException e){
        return ResponseEntity.status(401).body(e.getMessage());
    }
    @ExceptionHandler(BusinessException.class)
    public ResponseEntity handleBusinessException(BusinessException e){
        return ResponseEntity.status(409).body(e.getMessage());
    }
    @ExceptionHandler(ForbiddenException.class)
    public ResponseEntity handleForbiddenException(ForbiddenException e){
        return ResponseEntity.status(403).body(e.getMessage());
    }
    @ExceptionHandler(Exception.class)
    public ResponseEntity handleException(Exception e){
        return ResponseEntity.status(500).body(e.getMessage());
    }
}
