package com.hmkcode.android.gcm.exception;

/**
 * Created by ekondrashev on 2/11/15.
 */
public class RegistrationException extends BaseException {
    public RegistrationException() {
        super();
    }

    public RegistrationException(Throwable t) {
        super(t);
    }

    public RegistrationException(String message) {
        super(message);
    }

    public RegistrationException(String detailMessage, Throwable throwable) {
        super(detailMessage, throwable);
    }
}
