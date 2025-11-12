package services;
/*
Descripcion: Interfaz para el servicio de productos que define las operaciones disponibles.
Autor: Dilan Salazar
Fecha: 2025/11/11
 */
import models.Producto;
import java.util.List;

public interface ProductoService {
    /*
     * Retorna una lista de todos los productos disponibles.
     * @return Lista de objetos Producto.
     */
    List<Producto> listar();

}

