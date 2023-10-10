package com.example.studyCafe.util;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

import java.util.HashMap;
import java.util.Map;

public class ApiUtil {
    /** ex)
     * {
     *     "code": "400",
     *     "message": "잘못된 요청입니다.",
     *     "validation" : {
     *         "title" : "값을 입력해주세요."
     *     }
     * }
     */
    @Getter
    @JsonInclude(value = JsonInclude.Include.NON_EMPTY) // validation 필드가 비어있다면 응답 미포함
    public static class ErrorResponse {
        private final String code;
        private final String message;
        private final Map<String, String> validation;

        @Builder
        public ErrorResponse(String code, String message, Map<String, String> validation) {
            this.code = code;
            this.message = message;
            this.validation = validation;
        }

        public void addValidation(String fieldName, String errorMessage) {
            if (fieldName == null || errorMessage == null) {
                return ;
            }
            this.validation.put(fieldName, errorMessage);
        }
    }

}
