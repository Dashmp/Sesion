package models;

/*
Descripción:
  Esta clase representa un ítem dentro del carrito de compras.
  Cada ítem está compuesto por un producto y la cantidad seleccionada.
  La identidad del ítem depende únicamente del producto, NO de la cantidad.

Autor: Dilan Salazar
Fecha: 2025/13/11
*/

import java.util.Objects;

public class ItemCarro {

    // Cantidad seleccionada del producto
    private int cantidad;

    // Producto asociado al ítem
    private Producto producto;

    // Constructor: inicializa el ítem con una cantidad y su producto respectivo
    public ItemCarro(int cantidad, Producto producto) {
        this.cantidad = cantidad;
        this.producto = producto;
    }

    // Getter y Setter para la cantidad del ítem
    public int getCantidad() {
        return cantidad;
    }

    public void setCantidad(int cantidad) {
        this.cantidad = cantidad;
    }

    // Getter y Setter para el producto del ítem
    public Producto getProducto() {
        return producto;
    }

    public void setProducto(Producto producto) {
        this.producto = producto;
    }

    /*
     Se corrigio el metodo equals para que si es el mismo producto solo se sume la cantidad
     La cantidad no forma parte de la comparación, y de esta manera evitamos duplicados
     en el carrito y podemos simplemente incrementar la cantidad.
     */
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        ItemCarro itemCarro = (ItemCarro) o;
        return Objects.equals(producto.getId(), itemCarro.producto.getId());
    }

    /*
     hashCode debe ser coherente con equals(),sirve para que los objetos
     de esa clase puedan funcionar correctamente dentro de estructuras,
     permite que se puedan ubicar esos objetos en la misma
     posición de memoria y compararlos correctamente.
     Aqui solo se usa el ID del producto como identificador único.
     */
    @Override
    public int hashCode() {
        return Objects.hash(producto.getId());
    }

    // Calcula el subtotal oara enviarlo a la clase DetalleCarro
    //alli calcula el total sumado el IVA
    public double getSubtotal() {
        return cantidad * producto.getPrecio();
    }
}
