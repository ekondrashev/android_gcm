package com.hmkcode.android.gcm.exception;

/**
 * Created by ekondrashev on 2/13/15.
 */
public class BaseException extends Exception{


    public BaseException(String detailMessage, Throwable throwable) {
        super(detailMessage, throwable);
    }
    public BaseException() {
        super();
    }

    public BaseException(Throwable t) {
        super(t);
    }

    public BaseException(String message) {
        super(message);
    }
}
