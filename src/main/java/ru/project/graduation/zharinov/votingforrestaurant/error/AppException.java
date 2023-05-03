package ru.project.graduation.zharinov.votingforrestaurant.error;

import org.springframework.lang.NonNull;

public class AppException extends RuntimeException {
    private final ErrorType type;
    private final String msgCode;

    public AppException(ErrorType type, @NonNull String msgCode) {
        this.msgCode = msgCode;
        this.type = type;
    }

    public String getMsgCode() {
        return msgCode;
    }

    public ErrorType getType() {
        return type;
    }
}
