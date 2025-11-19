package services;

/*
Descripción:
  Implementación concreta de ProductoService utilizando JDBC.
  Se encarga de gestionar las operaciones de consulta a través del
  repositorio correspondiente, manejando posibles excepciones SQL
  y garantizando una correcta propagación de errores.

Autor: Dilan Salazar
Fecha: 2025/12/11
*/

import models.Producto;
import repositorio.ProductoRepositoryJdbcImpl;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.List;
import java.util.Optional;

public class ProductoServiceJdbcImpl implements ProductoService {

    // Repositorio encargado de la interacción con la base de datos
    private final ProductoRepositoryJdbcImpl repositoryJdbc;

    // Constructor que recibe y utiliza la conexión proporcionada por el filtro
    public ProductoServiceJdbcImpl(Connection connection) {
        this.repositoryJdbc = new ProductoRepositoryJdbcImpl(connection);
    }

    @Override
    public List<Producto> listar() {
        try {
            return repositoryJdbc.listar();
        } catch (SQLException ex) {
            throw new ServiceJdbcException(
                    "Error al listar los productos: " + ex.getMessage(),
                    ex
            );
        }
    }

    @Override
    public Optional<Producto> porId(Long id) {
        try {
            return Optional.ofNullable(repositoryJdbc.porId(id));
        } catch (SQLException ex) {
            throw new ServiceJdbcException(
                    "Error al obtener el producto con ID " + id + ": " + ex.getMessage(),
                    ex
            );
        }
    }
}
