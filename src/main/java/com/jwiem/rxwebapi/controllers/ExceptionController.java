package com.jwiem.rxwebapi.controllers;

import com.jwiem.rxwebapi.api.response.Response;
import com.jwiem.rxwebapi.utils.ErrorCode;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import javax.persistence.EntityNotFoundException;

@RestControllerAdvice
public class ExceptionRestController {
  @ExceptionHandler(EntityNotFoundException.class)
  public ResponseEntity<Response> handleEntityNotFoundException() {
    return ResponseEntity.status(HttpStatus.NOT_FOUND).body(Response.error(ErrorCode.ENTITY_NOT_FOUND));
  }
}
