package farm.core;

public class DuplicateCustomerException extends Exception {
    public DuplicateCustomerException() {
        super();
    }
    public DuplicateCustomerException(String message) {
        super(message);
    }
}
