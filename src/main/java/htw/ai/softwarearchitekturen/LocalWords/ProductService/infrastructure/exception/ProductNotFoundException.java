package htw.ai.softwarearchitekturen.LocalWords.ProductService.infrastructure.exception;

public class ProductNotFoundException extends RuntimeException {

    public ProductNotFoundException(int id) {
        super("Could not find product " +id);
    }
}

