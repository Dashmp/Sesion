package repositorio;

import models.Categoria;
import models.Producto;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class ProductoRepositoryJdbcImpl implements Repository<Producto> {

    // Obtener la conexión a la BBDD
    private Connection conn;

    // Obtenemos la conexión mediante el constructor
    public ProductoRepositoryJdbcImpl(Connection conn) {
        this.conn = conn;
    }

    @Override
    public List<Producto> listar() throws SQLException {
        List<Producto> productos = new ArrayList<>();
        try (Statement stat = conn.createStatement();
             ResultSet rs = stat.executeQuery("select p.*, c.nombreCategoria as categoria FROM producto as p INNER JOIN categoria as c ON (p.idCategoria = c.id) ORDER BY p.id ASC")) {

            while (rs.next()) {
                Producto p = getProductos(rs);
                productos.add(p);
            }
        }
        return productos;
    }

    // Implementamos un método para buscar un registro por ID
    @Override
    public Producto porId(Long id) throws SQLException {
        Producto producto = null;
        try (PreparedStatement stat = conn.prepareStatement("select p.*, c.nombreCategoria as categoria FROM producto as p INNER JOIN categoria as c ON (p.idCategoria = c.id) WHERE p.id = ?")) {
            stat.setLong(1, id);
            try (ResultSet rs = stat.executeQuery()) {
                if (rs.next()) {
                    producto = getProductos(rs);
                }
            }
        }
        return producto;
    }

    @Override
    public void guardar(Producto producto) throws SQLException {
        String sql;
        if (producto.getId() != null && producto.getId() > 0) {
            sql = "UPDATE producto SET nombreCategoria=?, idCategoria=?, stock=?, precio=?, descripcion=?, fecha_elaboracion=?, fecha_caducidad=?, condicion=? WHERE id=?";
            try (PreparedStatement stat = conn.prepareStatement(sql)) {
                stat.setInt(2, Math.toIntExact(producto.getCategoria().getId()));
                stat.setDouble(3, producto.getStock());
                stat.setDouble(4, producto.getPrecio());
                stat.setString(5, producto.getDescripcion());
                stat.setDate(6, Date.valueOf(producto.getFechaElaboracion()));
                stat.setDate(7, Date.valueOf(producto.getFechaCaducidad()));
                stat.setString(8, producto.getCondicion());
                stat.setLong(9, producto.getId());
                stat.executeUpdate();
            }
        } else {
            sql = "INSERT INTO producto (nombreCategoria, idCategoria, stock, precio, descripcion, fecha_elaboracion, fecha_caducidad, condicion) VALUES (?, ?, ?, ?, ?, ?, ?, ?)";
            try (PreparedStatement stat = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {
                stat.setInt(2, Math.toIntExact(producto.getCategoria().getId()));
                stat.setDouble(3, producto.getStock());
                stat.setDouble(4, producto.getPrecio());
                stat.setString(5, producto.getDescripcion());
                stat.setDate(6, Date.valueOf(producto.getFechaElaboracion()));
                stat.setDate(7, Date.valueOf(producto.getFechaCaducidad()));
                stat.setString(8, producto.getCondicion());
                stat.executeUpdate();

                try (ResultSet generatedKeys = stat.getGeneratedKeys()) {
                    if (generatedKeys.next()) {
                        producto.setId(generatedKeys.getLong(1));
                    }
                }
            }
        }
    }

    @Override
    public void eliminar(Long id) throws SQLException {
        String sql = "DELETE FROM producto WHERE id = ?";
        try (PreparedStatement stat = conn.prepareStatement(sql)) {
            stat.setLong(1, id);
            stat.executeUpdate();
        }
    }

    // Método privado para mapear un ResultSet a un objeto Producto
    private Producto getProductos(ResultSet rs) throws SQLException {
        Producto p = new Producto();
        p.setId(rs.getLong("id"));
        p.setStock((int) rs.getDouble("stock"));
        p.setPrecio(rs.getDouble("precio"));
        p.setDescripcion(rs.getString("descripcion"));
        p.setFechaElaboracion(rs.getDate("fecha_elaboracion").toLocalDate());
        p.setFechaCaducidad(rs.getDate("fecha_caducidad").toLocalDate());
        p.setCondicion(Integer.parseInt(rs.getString("condicion")));

        Categoria c = new Categoria();
        c.setId(rs.getLong("idCategoria"));
        p.setCategoria(c);

        return p;
    }
}