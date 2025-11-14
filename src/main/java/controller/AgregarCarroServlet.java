package controller;
/*
Descripción:Este servlet maneja la adición de productos al carrito de compras.
Recupera el ID del producto de la solicitud, lo busca, y si existe,
lo añade al carrito de la sesión del usuario.
Autor: Dilan Salazar
Fecha: 2025/11/11
*/

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import models.DetalleCarro;
import models.ItemCarro;
import models.Producto;
import services.ProductoService;
import services.ProductoServiceImpl;

import java.io.IOException;
import java.util.Optional;

@WebServlet("/agregar-carro")
public class AgregarCarroServlet extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {

        // Se crea una instancia del servicio para obtener productos
        ProductoService service = new ProductoServiceImpl();

        /*
        ---ERROR 500 POR PARTE DEL SERVIDOR---
        Llama al Servlet getDateHeader(String).
        Esta API está diseñada para leer *cabeceras que contienen fechas* y devuelve
        un `long` con el número de milisegundos desde epoch (1970-01-01T00:00:00Z).
        Si la cabecera no existe o no es una fecha válida, devuelve -1.
        el String.valueOf convierte el valor 'long' en string par que no exista error 500
         */
        Long id = Long.parseLong(req.getParameter("id"));

        // Busca el producto por su ID
        Optional<Producto> producto = service.porid(id);

        // Si el producto existe, se crea un ítem y se lo agrega al carrito
        if (producto.isPresent()) {
            ItemCarro item = new ItemCarro(1, producto.get());
            // Se obtiene la sesión del usuario
            HttpSession session = req.getSession();
            DetalleCarro detalleCarro;

            // Si el carrito no existe en la sesión, se crea uno nuevo
            if (session.getAttribute("carro") == null) {
                detalleCarro = new DetalleCarro();
                session.setAttribute("carro", detalleCarro);
            } else {
                // Si ya existe, se recupera el carrito actual de la sesión
                detalleCarro = (DetalleCarro) session.getAttribute("carro");
            }

            // Se agrega el ítem (producto) al carrito
            detalleCarro.addItemCarro(item);
        }

        //Se redirige al usuario a la vista del carrito
        resp.sendRedirect(req.getContextPath() + "/ver-carro.jsp");
    }
}
