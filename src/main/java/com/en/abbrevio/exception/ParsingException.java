package com.en.abbrevio.exception;

/**
 * ParsingException is a RuntimeException thrown when error occurs during the parsing of the source.
 */
public class ParsingException extends RuntimeException {
    public ParsingException(String message) {
        super(message);
    }
}
