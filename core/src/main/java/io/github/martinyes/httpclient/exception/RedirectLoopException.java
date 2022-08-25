package io.github.martinyes.httpclient.exception;

public class RedirectLoopException extends Exception {

    public RedirectLoopException(String message) {
        super(message);
    }
}