package htw.ai.softwarearchitekturen.LocalWords.ProductService.port.exception;

import java.util.UUID;

public class ProductNotFoundException extends RuntimeException {

    public ProductNotFoundException(UUID id) {
        super("Could not find product " +id);
    }

    public ProductNotFoundException() {
        super("Could not find matching product.");
    }


}

