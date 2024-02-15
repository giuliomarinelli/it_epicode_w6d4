package it.epicode.w6d4.exceptions;

public class PaginationSearchException extends BadRequestException {
    public PaginationSearchException(String msg) {
        super(msg);
    }
    public PaginationSearchException() {
        super();
    }
}