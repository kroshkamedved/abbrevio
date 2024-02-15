package com.en.abbrevio.exception.handling;

import lombok.Getter;

@Getter
public enum ErrorCode {
    ParsingError(40401, "Parse source not valid or another problem occur during parsing process"),
    DuplicateRecordError(40402, " Transmitted in the request body record already present in the DB or contains fields which are already assigned to another record or some other problem related to irrelevant income data. Check the \"synonym\" field first"),
    NoSuchSynonymError(40403, "Requested synonym is not present in the database");

    private final int errorCode;
    private final String errorMessage;

    private ErrorCode(int code, String message) {
        this.errorCode = code;
        this.errorMessage = message;
    }
}
