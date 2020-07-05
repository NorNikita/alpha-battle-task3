package ru.alpha.task3.controller.handler;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import ru.alpha.task3.model.dto.ExceptionDto;
import ru.alpha.task3.model.exception.AlphaTaskException;

@ControllerAdvice(basePackages = {"ru.alpha.task3.controller"})
public class ExceptionAdvice {

    @ExceptionHandler(AlphaTaskException.class)
    public ResponseEntity<ExceptionDto> testNotFound(AlphaTaskException exception) {
        return ResponseEntity
                .status(exception.getExceptionMessage().getStatus())
                .body(ExceptionDto
                        .builder()
                        .message(exception.getMessage())
                        .build());
    }
}

