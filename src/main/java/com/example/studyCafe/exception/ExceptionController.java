package com.example.studyCafe.exception;

import com.example.studyCafe.util.ApiUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;

import static com.example.studyCafe.util.ApiUtil.*;

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
}
