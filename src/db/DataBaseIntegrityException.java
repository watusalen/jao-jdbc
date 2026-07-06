package db;

public class DataBaseIntegrityException extends RuntimeException {
    public DataBaseIntegrityException(String message) {
        super(message);
    }
}
