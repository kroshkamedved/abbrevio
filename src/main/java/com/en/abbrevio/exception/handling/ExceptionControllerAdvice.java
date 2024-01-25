package com.en.abbrevio.exception.handling;

import com.en.abbrevio.exception.ParsingException;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class ExceptionControllerAdvice {
    @ExceptionHandler
    public ResponseEntity<ErrorDetails> handleParsingException(ParsingException e) {
        ErrorDetails errorDetails = new ErrorDetails(e.getMessage() + ErrorCode.ParsingError.getErrorMessage(), ErrorCode.ParsingError.getErrorCode());
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(errorDetails);
    }

    @ExceptionHandler
    public ResponseEntity<ErrorDetails> handleDuplicateKeyOrUniqueConstraintsException(DataIntegrityViolationException e) {
        ErrorDetails errorDetails = new ErrorDetails(ErrorCode.DuplicateRecordError.getErrorMessage() + "\s".repeat(5) + e.getMessage(), ErrorCode.DuplicateRecordError.getErrorCode());
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(errorDetails);
    }
}
