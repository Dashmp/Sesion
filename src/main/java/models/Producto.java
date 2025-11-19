package models;

import java.time.LocalDate;

public class Producto {
    //Creamos las variables añadidas
    private Long id;
    private String nombre;
    private int stock;
    private double precio;
    private Categoria categoria;
    private String descripcion;
    private int condicion;
    private LocalDate fechaElaboracion;
    private LocalDate fechaCaducidad;
    private String tipo;

    public Producto() {
    }

    //Creamos el constructor con las variables añadidas
    public Producto(Long id, int condicion, LocalDate fechaCaducidad, LocalDate fechaElaboracion,
                    String descripcion, int stock, double precio, String nombre, String tipo) {
        this.id = id;
        this.condicion = condicion;
        this.fechaCaducidad = fechaCaducidad;
        this.fechaElaboracion = fechaElaboracion;
        this.descripcion = descripcion;
        this.stock = stock;
        this.precio = precio;
        this.nombre = nombre;
        this.tipo = tipo;
    }

    //Implementamos los métodos setter and getter
    //De las variables añadidas

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getCondicion() {
        return condicion;
    }

    public void setCondicion(int condicion) {
        this.condicion = condicion;
    }

    public LocalDate getFechaCaducidad() {
        return fechaCaducidad;
    }

    public void setFechaCaducidad(LocalDate fechaCaducidad) {
        this.fechaCaducidad = fechaCaducidad;
    }

    public LocalDate getFechaElaboracion() {
        return fechaElaboracion;
    }

    public void setFechaElaboracion(LocalDate fechaElaboracion) {
        this.fechaElaboracion = fechaElaboracion;
    }

    public String getDescripcion() {
        return descripcion;
    }

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }

    public int getStock() {
        return stock;
    }

    public void setStock(int stock) {
        this.stock = stock;
    }

    public double getPrecio() {
        return precio;
    }

    public void setPrecio(double precio) {
        this.precio = precio;
    }

    public Categoria getCategoria() {
        return categoria;
    }

    public void setCategoria(Categoria categoria) {
        this.categoria = categoria;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }
}
