package com.mzlalalal.exception;

/**
 * @Desc:
 * @Author: Mzlalalal
 * @Date: 2020/5/14 23:23
 **/
public class ParaException extends RuntimeException {
    private static final long serialVersionUID = 4888200095167386189L;

    public ParaException() {}

    /**
     * @param message
     * @param cause
     * @param enableSuppression
     * @param writableStackTrace
     */
    public ParaException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }

    /**
     * @param message
     * @param cause
     */
    public ParaException(String message, Throwable cause) {
        super(message, cause);
    }

    /**
     * @param message
     */
    public ParaException(String message) {
        super(message);
    }

    /**
     * @param cause
     */
    public ParaException(Throwable cause) {
        super(cause);
    }
}
