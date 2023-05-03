package ru.project.graduation.zharinov.votingforrestaurant.error;

public class NotFoundException extends RuntimeException {
    public NotFoundException(String msg) {
        super(msg);
    }
}