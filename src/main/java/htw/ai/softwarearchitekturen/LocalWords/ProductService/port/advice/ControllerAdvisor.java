package htw.ai.softwarearchitekturen.LocalWords.ProductService.port.advice;

import htw.ai.softwarearchitekturen.LocalWords.ProductService.port.exception.*;
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
        return new ResponseEntity<>(createResponseBody(ex), HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler(NotAuthorizedException.class)
    ResponseEntity<Object> handleNotAuthorizedException(NotAuthorizedException ex){
        return new ResponseEntity<>(createResponseBody(ex), HttpStatus.UNAUTHORIZED);
    }

    @ExceptionHandler(CreationException.class)
    ResponseEntity<Object> handleProductCreationException(CreationException ex){
        return new ResponseEntity<>(createResponseBody(ex), HttpStatus.INTERNAL_SERVER_ERROR);
    }




    public Map<String, Object> createResponseBody(Exception exception){
        Map<String, Object> body = new LinkedHashMap<>();
        body.put("timestamp", LocalDateTime.now());
        body.put("message", exception.getMessage());
        return body;
    }
}
