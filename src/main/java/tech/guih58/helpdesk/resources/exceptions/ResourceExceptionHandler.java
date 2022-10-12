package tech.guih58.helpdesk.resources.exceptions;


import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import tech.guih58.helpdesk.services.execeptions.DataIntegrityViolationExeception;
import tech.guih58.helpdesk.services.execeptions.ObjectnotFoundException;

import javax.servlet.http.HttpServletRequest;

@ControllerAdvice
public class ResourceExceptionHandler {

    @ExceptionHandler(ObjectnotFoundException.class)
    public ResponseEntity<StandardError> objectnotFoundException(ObjectnotFoundException ex, HttpServletRequest request){

        StandardError error = new StandardError(System.currentTimeMillis(), HttpStatus.NOT_FOUND.value(),"Object Not Found", ex.getMessage(),request.getRequestURI());

        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(error);
    }

    @ExceptionHandler(DataIntegrityViolationExeception.class)
    public ResponseEntity<StandardError> dataIntegrityViolationException(DataIntegrityViolationExeception ex, HttpServletRequest request){

        StandardError error = new StandardError(System.currentTimeMillis(), HttpStatus.BAD_REQUEST.value(),"Violação de dados", ex.getMessage(),request.getRequestURI());

        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(error);
    }
    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<StandardError> validationErrors(MethodArgumentNotValidException ex, HttpServletRequest request){

       ValidationError erros = new ValidationError(System.currentTimeMillis(),HttpStatus.BAD_REQUEST.value(),"Erro na validação dos campos", "Erro na validação dos campos", request.getRequestURI());

       for(FieldError x : ex.getBindingResult().getFieldErrors()){
           erros.addError(x.getField(), x.getDefaultMessage());
       }

        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(erros);
    }
}
