package co.edu.uniquindio.inventory.services.excepciones;

import co.edu.uniquindio.inventory.dto.Response;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.time.LocalDateTime;


@RestControllerAdvice
public class HandlerException {
    @ExceptionHandler(Exception.class)
    public ResponseEntity<Response<String>> handlerGenericException(Exception e) {
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(new Response<>(e.getMessage() + " ----- dateTime:" + LocalDateTime.now()));
    }

    @ExceptionHandler(InventoryNotFoundException.class)
    public ResponseEntity<Response<String>> handlerCustomerNotFoundException(InventoryNotFoundException e) {
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(new Response<>(e.getMessage()));
    }
}
