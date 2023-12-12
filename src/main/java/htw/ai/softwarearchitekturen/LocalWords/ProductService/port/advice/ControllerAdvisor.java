package htw.ai.softwarearchitekturen.LocalWords.ProductService.port.advice;

import htw.ai.softwarearchitekturen.LocalWords.ProductService.port.exception.AuthorNotFoundException;
import htw.ai.softwarearchitekturen.LocalWords.ProductService.port.exception.OutOfStockException;
import htw.ai.softwarearchitekturen.LocalWords.ProductService.port.exception.ProductAlreadyExistsException;
import htw.ai.softwarearchitekturen.LocalWords.ProductService.port.exception.ProductNotFoundException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import java.time.LocalDateTime;
import java.util.LinkedHashMap;
import java.util.Map;

@ControllerAdvice
public class ControllerAdvisor extends ResponseEntityExceptionHandler {
    @ExceptionHandler(ProductAlreadyExistsException.class)
    ResponseEntity<Object> handleProductAlreadyExistsException(
            ProductAlreadyExistsException ex) {
        return new ResponseEntity<>(createResponseBody(ex), HttpStatus.CONFLICT);
    }

    @ExceptionHandler(OutOfStockException.class)
    ResponseEntity<Object> handleOutOfStockException(OutOfStockException ex){
        return new ResponseEntity<>(createResponseBody(ex), HttpStatus.CONFLICT);
    }

    @ExceptionHandler(ProductNotFoundException.class)
    ResponseEntity<Object> handleProductNotFoundException(ProductNotFoundException ex){
        return new ResponseEntity<>(createResponseBody(ex), HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler(AuthorNotFoundException.class)
    ResponseEntity<Object> handleAuthorNotFoundException(AuthorNotFoundException ex){
        return new ResponseEntity<>(createResponseBody(ex), HttpStatus.NOT_FOUND)
    }

    public Map<String, Object> createResponseBody(Exception exception){
        Map<String, Object> body = new LinkedHashMap<>();
        body.put("timestamp", LocalDateTime.now());
        body.put("message", exception.getMessage());
        return body;
    }
}
