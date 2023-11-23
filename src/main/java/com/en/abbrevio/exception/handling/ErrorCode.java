package com.en.abbrevio.exception.handling;

public enum ErrorCode {
    ParsingError(40401, "Parse source not valid or another problem occur during parsing process");

    private final int errorCode;
    private final String errorMessage;

    private ErrorCode(int code, String message) {
        this.errorCode = code;
        this.errorMessage = message;
    }

    public int getErrorCode() {
        return errorCode;
    }

    public String getErrorMessage() {
        return errorMessage;
    }
}
