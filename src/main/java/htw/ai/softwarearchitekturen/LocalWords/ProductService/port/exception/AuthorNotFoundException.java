package htw.ai.softwarearchitekturen.LocalWords.ProductService.port.exception;

import java.util.UUID;

public class AuthorNotFoundException extends RuntimeException{
    public AuthorNotFoundException(UUID id) {
        super("Could not find author " +id);
    }

    public AuthorNotFoundException(String name) {
        super("Could not find author " +name);
    }
}
