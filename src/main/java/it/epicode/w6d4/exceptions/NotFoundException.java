package it.epicode.w6d4.exceptions;

public class NotFoundException extends Exception {
    public NotFoundException(String msg) {
        super(msg);
    }
    public NotFoundException() {
        super();
    }
}
