package ru.self.printlab_backend.exception;

import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import ru.self.printlab_backend.stl.StlParseException;

import java.time.Instant;
import java.util.Map;

@Slf4j
@RestControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(StlParseException.class)
    public ResponseEntity<Map<String, Object>> handleStl(StlParseException e) {
        log.warn("Ошибка разбора STL: {}", e.getMessage());
        return ResponseEntity.badRequest().body(body(HttpStatus.BAD_REQUEST, e.getMessage()));
    }

    @ExceptionHandler(IllegalStateException.class)
    public ResponseEntity<Map<String, Object>> handleIllegalState(IllegalStateException e) {
        log.warn("Невозможно выполнить расчёт: {}", e.getMessage());
        return ResponseEntity.unprocessableEntity().body(body(HttpStatus.UNPROCESSABLE_ENTITY, e.getMessage()));
    }

    @ExceptionHandler(Exception.class)
    public ResponseEntity<Map<String, Object>> handleOther(Exception e) {
        log.error("Необработанная ошибка", e);
        return ResponseEntity.internalServerError()
                .body(body(HttpStatus.INTERNAL_SERVER_ERROR, "Внутренняя ошибка сервера"));
    }

    private Map<String, Object> body(HttpStatus status, String message) {
        return Map.of(
                "timestamp", Instant.now().toString(),
                "status", status.value(),
                "error", status.getReasonPhrase(),
                "message", message);
    }
}