package services;

/*
Descripción:
  Clase de excepciones para manejar errores específicos y lanzar mensajes controlados.
  Extiende RuntimeException para permitir propagación sin obligación
  de captura por parte del programador.

Autor: Dilan Salazar
Fecha: 2025/11/19
*/

public class AppException extends RuntimeException {

    // Constructor que recibe solo un mensaje
    public AppException(String mensaje) {
        super(mensaje);
    }

    // Constructor que permite incluir una causa del error
    public AppException(String mensaje, Throwable cause) {
        super(mensaje, cause);
    }
}
