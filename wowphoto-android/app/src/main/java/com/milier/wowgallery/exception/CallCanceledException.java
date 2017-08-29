package com.milier.wowgallery.exception;

/**
 * Created by xubo on 16/1/13.
 */
public class CallCanceledException extends Exception {

    public CallCanceledException() {
        super();
    }

    public CallCanceledException(final String message) {
        super(message);
    }

    public CallCanceledException(final String message, final Throwable cause) {
        super(message, cause);
    }

    public CallCanceledException(final Throwable cause) {
        super(cause);
    }
}