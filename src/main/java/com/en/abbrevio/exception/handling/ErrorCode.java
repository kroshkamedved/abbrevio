package com.en.abbrevio.exception.handling;

public enum ErrorCode {
    ParsingError(40401, "Parse source not valid or another problem occur during parsing process"),
    DuplicateRecordError(40402, " Transmitted in the request body record already present in the DB or contains fields which are already assigned to another record or some other problem related to irrelevant income data. Check the \"synonym\" field first");

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
