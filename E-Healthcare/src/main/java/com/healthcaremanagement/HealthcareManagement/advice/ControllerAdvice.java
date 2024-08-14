package com.healthcaremanagement.HealthcareManagement.advice;

import com.healthcaremanagement.HealthcareManagement.exception.PatientNotFoundException;
import com.healthcaremanagement.HealthcareManagement.exception.SaveErrorException;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.util.HashMap;
import java.util.Map;

@RestControllerAdvice
public class ControllerAdvice {

    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ExceptionHandler(MethodArgumentNotValidException.class)
    public Map<String, String> handleInvalidContent(MethodArgumentNotValidException ex) {

        Map<String, String> errorMap = new HashMap<>();
        ex.getBindingResult().getFieldErrors().forEach(error -> {

            errorMap.put(error.getField(), error.getDefaultMessage());
        });
        return errorMap;
    }

    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    @ExceptionHandler(PatientNotFoundException.class)
    public Map<String, String> patientNotFound(PatientNotFoundException ex) {
        Map<String, String> errorMap = new HashMap<>();
        errorMap.put("Error message : ", ex.getMessage());
        return errorMap;
    }

    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    @ExceptionHandler(SaveErrorException.class)
    public Map<String,String> handleRuntimeEx(SaveErrorException ex){
        Map<String,String> errorMap = new HashMap<>();
        errorMap.put("Error Message",ex.getMessage());
        return errorMap;
    }
}
