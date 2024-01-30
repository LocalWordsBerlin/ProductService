package htw.ai.softwarearchitekturen.LocalWords.ProductService.port.exception;

public class ProductCreationException extends RuntimeException{
    public ProductCreationException(String message) {
        super(message);
    }
}
