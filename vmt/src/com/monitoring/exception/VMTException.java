/*
 * Cognizant Technology Solutions
 */
package com.monitoring.exception;

/**
 * @author Carl, Tao @ Cognizant
 * @date 2018/03/09
 */
public class VMTException extends Exception {
    private static final long serialVersionUID = 7172851229234996966L;

    public VMTException() {
        super();
    }

    public VMTException(String message) {
        super(message);
    }

    public VMTException(String message, Throwable cause) {
        super(message, cause);
    }

    public VMTException(Throwable cause) {
        super(cause);
    }
}
