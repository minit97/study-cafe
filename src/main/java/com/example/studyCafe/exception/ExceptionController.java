package com.example.studyCafe.exception;

import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.method.annotation.MethodArgumentTypeMismatchException;

import static com.example.studyCafe.util.ApiUtil.ErrorResponse;

@Slf4j
@RestControllerAdvice   // BingdingResult 반복작업 대신 사용
public class ExceptionController {

    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ExceptionHandler(MethodArgumentNotValidException.class)    // @Valid Ex
    public ErrorResponse invalidRequestHandler(MethodArgumentNotValidException e) {
        log.warn("exceptionHandler error : ", e);

        ErrorResponse response = ErrorResponse.builder()
                .code("400")
                .message("잘못된 요청입니다.")
                .build();
        for (FieldError fieldError : e.getFieldErrors()) {
            response.addValidation(fieldError.getField(), fieldError.getDefaultMessage());
        }
        return response;
    }


    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ExceptionHandler(MethodArgumentTypeMismatchException.class)    // 컨트롤러 @PathVariable TypeMismatch
    public ErrorResponse methodArgumentTypeMismatchException(MethodArgumentTypeMismatchException e) {
        ErrorResponse response = ErrorResponse.builder()
                .code("400")
                .message("잘못된 요청입니다.")
                .build();
        response.addValidation("pathvariable error", "url parameter 확인");

        return response;
    }

    @ExceptionHandler(GlobalException.class)    // 비즈니스 Ex
    public ResponseEntity<ErrorResponse> GlobalException(GlobalException e) {
        int statusCode = e.getStatusCode();

        ErrorResponse body = ErrorResponse.builder()
                .code(String.valueOf(statusCode))
                .message(e.getMessage())
                .validation(e.getValidation())
                .build();

        ResponseEntity<ErrorResponse> response = ResponseEntity.status(statusCode)
                                                                .body(body);
        return response;
    }


    @ExceptionHandler(Exception.class)
    public ResponseEntity<ErrorResponse> exception(Exception e) {
        log.error("예외 발생", e);

        ErrorResponse body = ErrorResponse.builder()
                .code(String.valueOf(500))
                .message(e.getMessage())
                .build();
        ResponseEntity<ErrorResponse> response = ResponseEntity.status(500)
                .body(body);

        return response;
    }
}
