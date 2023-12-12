package htw.ai.softwarearchitekturen.LocalWords.ProductService.port.exception;

import java.util.UUID;

public class ProductAlreadyExistsException extends RuntimeException{
    public ProductAlreadyExistsException(String isbn) {
            super("Product with given ISBN " + isbn + " already exists");
        }

}
