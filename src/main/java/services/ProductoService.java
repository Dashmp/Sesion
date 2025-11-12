package services;
/*
Descripcion: Interfaz para el servicio de productos que define las operaciones disponibles.
Autor: Dilan Salazar
Fecha: 2025/12/11
 */
import models.Producto;
import java.util.List;
import java.util.Optional;

public interface ProductoService {
    /*
     * Retorna una lista de todos los productos disponibles.
     * @return Lista de objetos Producto.
     */
    List<Producto> listar();
    /*
     * Busca un producto específico según su ID.
     * @param id Identificador único del producto.
     * @return Un Optional con el producto encontrado, o vacío si no existe.
     */
    Optional<Producto> porid(Long id);
}

