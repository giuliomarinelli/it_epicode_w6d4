package it.epicode.w6d4.exceptions;

public class ConstraintReferenceError extends BadRequestException {
    public ConstraintReferenceError(String msg) {
        super(msg);
    }
    public ConstraintReferenceError() {
        super();
    }
}
