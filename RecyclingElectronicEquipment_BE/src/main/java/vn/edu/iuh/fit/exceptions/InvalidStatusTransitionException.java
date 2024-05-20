package vn.edu.iuh.fit.exceptions;

public class InvalidStatusTransitionException extends   RuntimeException{
    public InvalidStatusTransitionException(String message) {
        super(message);
    }
}
