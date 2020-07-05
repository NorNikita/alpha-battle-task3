package ru.alpha.task3.model.exception;

import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class AlphaTaskException extends RuntimeException {

    private ExceptionMessage exceptionMessage;

    public AlphaTaskException(ExceptionMessage exceptionMessage) {
        super(exceptionMessage.getMessage());
        this.exceptionMessage = exceptionMessage;
    }
}
