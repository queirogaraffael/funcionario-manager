package com.example.funcionario_manager.exceptions;

public class FuncionarioJaExisteException extends RuntimeException{
    public FuncionarioJaExisteException() {
    }

    public FuncionarioJaExisteException(String message) {
        super(message);
    }

    public FuncionarioJaExisteException(String message, Throwable cause) {
        super(message, cause);
    }

    public FuncionarioJaExisteException(Throwable cause) {
        super(cause);
    }

    public FuncionarioJaExisteException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }
}
