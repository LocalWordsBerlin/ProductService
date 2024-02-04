package htw.ai.softwarearchitekturen.LocalWords.ProductService.port.exception;

import java.util.UUID;

public class ProductNotFoundException extends RuntimeException {

    public ProductNotFoundException(UUID id) {
        super("Could not find product with id: " +id);
    }

    public ProductNotFoundException() {
        super("Could not find matching product.");
    }


}

