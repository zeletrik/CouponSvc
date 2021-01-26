package hu.zeletrik.couponsvc.service.exception;

/**
 * Exception class to throw when the ticket already redeemed.
 */
public class TicketAlreadyExistException extends RuntimeException{

    public TicketAlreadyExistException(String message) {
        super(message);
    }
}
