package com.prlhspt.market.exception;

public class UsernameFromTokenException extends RuntimeException {
    public UsernameFromTokenException() {
        super();
    }

    public UsernameFromTokenException(String message) {
        super(message);
    }

    public UsernameFromTokenException(String message, Throwable cause) {
        super(message, cause);
    }

    public UsernameFromTokenException(Throwable cause) {
        super(cause);
    }

    protected UsernameFromTokenException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }
}
