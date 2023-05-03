package ru.project.graduation.zharinov.votingforrestaurant.error;

public class IllegalRequestDataException extends RuntimeException {
    public IllegalRequestDataException(String msg) {
        super(msg);
    }
}