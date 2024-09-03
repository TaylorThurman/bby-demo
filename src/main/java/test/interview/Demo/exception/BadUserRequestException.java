package test.interview.demo.exception;

public class BadUserRequestException extends RuntimeException {

    public BadUserRequestException(String message) {
        super(message);
    }
}
