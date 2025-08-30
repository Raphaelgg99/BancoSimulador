package com.simuladorbanco.BancoDigital.controller;

import com.simuladorbanco.BancoDigital.exception.NomeNullException;
import com.simuladorbanco.BancoDigital.exception.SenhaNullException;
import com.simuladorbanco.BancoDigital.exception.SenhaRepetidaException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import java.util.HashMap;
import java.util.Map;

@ControllerAdvice
public class ContaControllerAdvice {

    @ExceptionHandler(NomeNullException.class)
    public ResponseEntity<Object> handleNomeNullException() {
        Map<String, Object> body = new HashMap<String, Object>();
        body.put("message", "Nome vazio" );
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(body);
    }

    @ExceptionHandler(SenhaNullException.class)
    public ResponseEntity<Object> handleSenhaNullException() {
        Map<String, Object> body = new HashMap<String, Object>();
        body.put("message", "Senha vazia" );
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(body);
    }

    @ExceptionHandler(SenhaRepetidaException.class)
    public ResponseEntity<Object> handleSenhaRepetidaException() {
        Map<String, Object> body = new HashMap<String, Object>();
        body.put("message", "Senha já existe" );
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(body);
    }
}


