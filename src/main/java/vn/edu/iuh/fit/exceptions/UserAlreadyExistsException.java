package vn.edu.iuh.fit.exceptions;

public class UserAlreadyExistsException  extends  RuntimeException{
    public UserAlreadyExistsException(String message) {
        super(message);
    }
}
