package htw.ai.softwarearchitekturen.LocalWords.ProductService.port.exception;

public class NotAuthorizedException extends RuntimeException{
    public NotAuthorizedException(String message) {
        super(message);
    }
}
