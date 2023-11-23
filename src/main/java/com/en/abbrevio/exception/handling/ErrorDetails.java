package com.en.abbrevio.exception.handling;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ErrorDetails {
    private String message;
    private int errorCode;

    public ErrorDetails(String message, int errorCode) {
        this.errorCode = errorCode;
        this.message = message;
    }
}
