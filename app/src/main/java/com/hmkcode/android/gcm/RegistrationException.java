package com.hmkcode.android.gcm;

/**
 * Created by ekondrashev on 2/11/15.
 */
public class RegistrationException extends Exception {
    public RegistrationException() {
        super();
    }

    public RegistrationException(Throwable t) {
        super(t);
    }

    public RegistrationException(String message) {
        super(message);
    }
}
