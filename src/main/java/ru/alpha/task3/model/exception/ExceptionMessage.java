package ru.alpha.task3.model.exception;

import lombok.Getter;
import org.springframework.http.HttpStatus;

@Getter
public enum ExceptionMessage {

    BRANCH_NOT_FOUND(HttpStatus.NOT_FOUND, "branch not found!");

    private HttpStatus status;
    private String message;

    ExceptionMessage(HttpStatus status, String message) {
        this.status = status;
        this.message = message;
    }
}
