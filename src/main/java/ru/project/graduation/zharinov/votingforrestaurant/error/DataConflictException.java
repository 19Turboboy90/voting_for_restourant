package ru.project.graduation.zharinov.votingforrestaurant.error;

public class DataConflictException extends RuntimeException {
    public DataConflictException(String msg) {
        super(msg);
    }
}