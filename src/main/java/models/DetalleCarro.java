package models;
/*
Descripción: Esta clase representa el carrito de compras,
gestionando la adición de productos y el cálculo del total.
Autor: Dilan Salazar
Fecha: 2025/12/11
*/
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class DetalleCarro {
    // Lista que contiene los ítems que forman parte del carrito de compras
    private List<ItemCarro> items;

    // Constructor que inicializa la lista de ítems vacía
    public DetalleCarro (){this.items=new ArrayList<>();}

    // Método que agrega un ítem al carrito
    public void addItemCarro(ItemCarro itemCarro) {
        // Si el ítem ya existe en el carrito, se busca para incrementar su cantidad
        if(items.contains(itemCarro)){
            Optional<ItemCarro> optionalItemCarro= items.stream()
                    .filter(i -> i.equals(itemCarro))
                    .findAny();
            if(optionalItemCarro.isPresent()){
                ItemCarro i = optionalItemCarro.get();
                // Aumenta la cantidad del ítem existente de uno en uno
                //para que no se repita la impresion del producto
                i.setCantidad(i.getCantidad()+1);
            }
        }else {
            // Si no existe, se agrega como un nuevo ítem al carrito
            this.items.add(itemCarro);
        }
    }

    // Retorna la lista completa de ítems del carrito
    public List<ItemCarro> getItems() { return items; }

    // Calcula el total del carrito sumando los subtotales de cada ítem
    public double getTotal(){return items.stream().mapToDouble(ItemCarro::getSubtotal).sum();}
}
