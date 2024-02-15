package it.epicode.w6d4.exceptions;

public class BadRequestException extends Exception {
    public BadRequestException(String msg) {
        super(msg);
    }
    public BadRequestException() {
        super();
    }
}