package com.example.studyCafe.exception.CustomException;


import com.example.studyCafe.exception.GlobalException;

/**
 * status -> 404
 */
public class PostNotFound extends GlobalException {

    private static final String MESSAGE = "존재하지 않는 페이지입니다.";

    public PostNotFound() {
        super(MESSAGE);
    }

    @Override
    public int getStatusCode() {
        return 404;
    }
}
