package hu.zeletrik.couponsvc.service.exception;

/**
 * Exception class to throw when a provided territory not exists.
 */
public class NonExistingTerritoryException extends RuntimeException{

    public NonExistingTerritoryException(String message) {
        super(message);
    }
}
