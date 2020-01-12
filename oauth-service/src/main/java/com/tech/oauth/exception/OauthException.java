package com.tech.oauth.exception;

/**
 * @author Yorker
 * @since 1/10/2020 7:53 PM
 */
public class OauthException extends RuntimeException  {
    public OauthException() {
        super();
    }

    public OauthException(String msg) {
        super(msg);
    }

    public OauthException(String msg, Throwable innerException) {
        super(msg, innerException);
    }
}

