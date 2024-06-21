//esta clase se crea por que se realiza una exception personalizada y esta clase es la que le dice que hacer a esa exception
package com.alura.screenmatch.excepcion;

public class ErrorEnConversionDeDuracionException extends RuntimeException{
    private String mensaje;

    public ErrorEnConversionDeDuracionException(String mensaje) {
        this.mensaje = mensaje;

    }

    @Override
    public String getMessage() {
        return this.mensaje;
    }
}
