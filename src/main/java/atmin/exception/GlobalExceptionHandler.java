package atmin.exception;

import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.util.HashMap;
import java.util.Map;

@RestControllerAdvice
@Slf4j
public class GlobalExceptionHandler {

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<Map<String, String>> handleValidationExceptions(MethodArgumentNotValidException ex) {
        Map<String, String> errors = new HashMap<>();

        ex.getBindingResult().getAllErrors().forEach((error) -> {
            String fieldName = ((FieldError) error).getField();
            String errorMessage = error.getDefaultMessage();
            errors.put(fieldName, errorMessage);

            log.warn("Hành vi bất thường/Lỗi dữ liệu đầu vào - Field: {}, Message: {}", fieldName, errorMessage);
        });

        log.error("Request bị từ chối tại Controller do không vượt qua Validation. Chi tiết: {}", errors);

        return new ResponseEntity<>(errors, HttpStatus.BAD_REQUEST);
    }

    // Xử lý các Exception không mong muốn khác
    @ExceptionHandler(Exception.class)
    public ResponseEntity<String> handleGlobalException(Exception ex) {
        log.error("Xảy ra lỗi hệ thống: {}", ex.getMessage(), ex);
        return new ResponseEntity<>("Đã xảy ra lỗi hệ thống, vui lòng thử lại sau.", HttpStatus.INTERNAL_SERVER_ERROR);
    }
}