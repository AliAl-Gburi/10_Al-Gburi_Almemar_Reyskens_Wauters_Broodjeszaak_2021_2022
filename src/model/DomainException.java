package model;

public class DomainException extends RuntimeException{
    private String message;

    public DomainException(String message) {
        this.message = message;
    }

    public DomainException() {
        super();
    }

}
