package com.globallogic.usuarios.exceptions;



public class DuplicateEmailException extends Exception {

    public DuplicateEmailException() {
        super("Email duplicado");
    }
}
