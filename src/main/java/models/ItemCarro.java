package models;
/*
Descripción:Esta clase representa un ítem individual dentro del carrito de compras,
incluyendo la cantidad y precio de un producto específico.
Autor: Dilan Salazar
Fecha: 2025/12/11
*/
import java.util.Objects;

public class ItemCarro {
    private int cantidad;
    private Producto producto;

    // Constructor: inicializa el ítem con una cantidad y un producto
    public ItemCarro(int cantidad, Producto producto) {
        this.cantidad = cantidad;
        this.producto = producto;
    }

    // Getter y Setter para cantidad
    public int getCantidad() {
        return cantidad;
    }
    public void setCantidad(int cantidad) {
        this.cantidad = cantidad;
    }

    // Getter y Setter para producto
    public Producto getProducto() {return producto;
    }
    public void setProducto(Producto producto) { this.producto = producto;}

    // Método equals que compara dos ítems por el ID del producto y su cantidad
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        ItemCarro itemCarro = (ItemCarro) o;
        return Objects.equals(producto.getId(), itemCarro.producto.getId())
                && Objects.equals(cantidad, itemCarro.cantidad);
    }

    // Calcula el subtotal del ítem (cantidad * precio del producto)
    // para devolver al modelo la funcion getSubtotal
    public double getSubtotal() {return cantidad * producto.getPrecio();}
}
