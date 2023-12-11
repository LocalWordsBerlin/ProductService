package htw.ai.softwarearchitekturen.LocalWords.ProductService.port.exception;

import java.util.UUID;

public class OutOfStockException extends RuntimeException{
    public OutOfStockException(UUID id) {
        super("Product " +id+ " is out of Stock");
    }
}
