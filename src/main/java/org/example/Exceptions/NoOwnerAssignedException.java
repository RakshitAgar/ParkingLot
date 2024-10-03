package org.example.Exceptions;

public class NoOwnerAssignedException extends RuntimeException {
    public NoOwnerAssignedException(String message) {
        super(message);
    }
}
