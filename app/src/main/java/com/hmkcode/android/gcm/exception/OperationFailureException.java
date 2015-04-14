package com.hmkcode.android.gcm.exception;

/**
 * Created by ekondrashev on 2/13/15.
 */
public class OperationFailureException extends BaseException {

    public OperationFailureException() {
        super();
    }

    public OperationFailureException(Throwable t) {
        super(t);
    }

    public OperationFailureException(String message) {
        super(message);
    }


    public OperationFailureException(String detailMessage, Throwable throwable) {
        super(detailMessage, throwable);
    }
}
