package models;

/*
Descripción:
  Esta clase representa el carrito de compras.
  Permite agregar ítems, calcular el total, el IVA y el total final.
  Evita duplicados sumando la cantidad cuando se agrega un mismo producto.

Autor: Dilan Salazar
Fecha: 2025/13/11
*/

import java.util.ArrayList;
import java.util.List;

public class DetalleCarro {

    // Lista que contiene todos los ítems que forman parte del carrito
    private List<ItemCarro> items;

    // Constructor: inicializa la lista vacía
    public DetalleCarro() {
        this.items = new ArrayList<>();
    }

    /*
     Agrega un ítem al carrito.
     Si el producto ya existe, simplemente incrementa la cantidad,
     si no existe, lo agrega como un nuevo ítem para
     evitar crear filas duplicadas e innecesarias.
     */
    public void addItemCarro(ItemCarro itemCarro) {

        for (ItemCarro itemExistente : items) {
            // Si el producto ya está en el carrito solo se actualiza la cantidad
            if (itemExistente.getProducto().getId().equals(itemCarro.getProducto().getId())) {
                // Sumar cantidades para evitar filas innecesarias
                itemExistente.setCantidad(
                        itemExistente.getCantidad() + itemCarro.getCantidad()
                );
                return;
            }
        }
        // Si el producto no estaba, se agrega como nuevo ítem
        items.add(itemCarro);
    }

    // Devuelve la lista completa de ítems
    public List<ItemCarro> getItem() {
        return items;
    }

    // Calcula el precio total de la compra (sin IVA)
    public double getTotal() {
        return items.stream()
                .mapToDouble(ItemCarro::getSubtotal)
                .sum();
    }

    // Agrega el IVA (15% del total)
    public double getIva() {
        return getTotal() * 0.15;
    }

    // Total con IVA incluido en la compra total, no por producto
    public double getTotalConIva() {
        return getTotal() + getIva();
    }
}
