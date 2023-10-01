package com.example.studyCafe.util;

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
    public static class ErrorResponse {
        private final String code;
        private final String message;
        private final Map<String, String> validation = new HashMap<>();

        @Builder
        public ErrorResponse(String code, String message) {
            this.code = code;
            this.message = message;
        }

        public void addValidation(String fieldName, String errorMessage) {
            this.validation.put(fieldName, errorMessage);
        }
    }

}
