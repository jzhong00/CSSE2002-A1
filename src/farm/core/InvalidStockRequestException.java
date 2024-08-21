package farm.core;

public class InvalidStockRequestException extends Exception {
    public InvalidStockRequestException() {
        super();
    }
    public InvalidStockRequestException(String message) {
        super(message);
    }
}
