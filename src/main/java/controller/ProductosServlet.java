package controller;
/*
Descripción: Esta clase se encarga de mostrar los productos disponibles en formato HTML.
Si el usuario está autenticado, también muestra los precios de los productos y un botón de compra.
Si no está autenticado, oculta los precios y el botón de compra.
Autor: Dilan Salazar
Fecha: 2025/12/11
*/
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import services.LoginService;
import services.LoginServiceSessionImpl;
import services.ProductoService;
import services.ProductoServiceImpl;
import models.Producto;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.List;
import java.util.Optional;

@WebServlet({"/productos", "/productos.html", "/producto.json" })
public class ProductosServlet extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {

        // Instancia del servicio que maneja la lógica de productos
        ProductoService service = new ProductoServiceImpl();
        List<Producto> productos = service.listar(); // Obtiene la lista de productos

        // Servicio para verificar si el usuario está autenticado (sesión activa)
        LoginService auth = new LoginServiceSessionImpl();
        Optional<String> usernameOptional = auth.getUsername(req);

        // Configura el tipo de contenido HTML
        resp.setContentType("text/html;charset=UTF-8");

        // Generación dinámica del contenido HTML
        try (PrintWriter out = resp.getWriter()) {
            out.println("<!DOCTYPE html>");
            out.println("<html>");
            out.println("<head>");
            out.println("<title>Lista de Prdocutos</title>");
            // Estilos CSS para la tabla y el diseño
            out.println("<style>");
            out.println("body {font-family: Arial, sans-serif; background-color:#f4f4f4; margin:0; padding:0;}");
            out.println(".container {max-width:900px; margin:40px auto; background:white; padding:30px; border-radius:10px; box-shadow:0 0 10px rgba(0,0,0,0.2); text-align:center;}");
            out.println("h1 {color:#0a3d62;}");
            out.println("table {width:90%; margin:0 auto; border-collapse:collapse;}");
            out.println("th, td {padding:12px 15px; border:1px solid #999;}");
            out.println("th {background:#0a3d62; color:white;}");
            out.println("tr:nth-child(even) {background:#f0f0f0;}");
            out.println("tr:hover {background:#dff0ff;}");
            out.println(".btn {background:#0a3d62; color:white; padding:10px 20px; border-radius:8px; border:none; cursor:pointer;}");
            out.println(".btn:hover {background:#1e90ff;}");
            out.println(".btn-comprar {background:#006699; padding:8px 15px; border-radius:6px; color:white; text-decoration:none;}");
            out.println(".btn-comprar:hover {background:#0088cc;}");
            out.println(".welcome {color:blue; font-weight:bold; margin-bottom:15px;}");
            out.println("</style>");
            out.println("</head>");
            out.println("<body>");

            out.println("<div class='container'>");
            out.println("<h1>Listado de Productos</h1>");

            // Si el usuario está logueado, muestra un mensaje de bienvenida, de lo contrario salta el bucle
            if (usernameOptional.isPresent()) {
                out.println("<div class='welcome'>Bienvenido de nuevo " + usernameOptional.get() + " !</div>");
            }
            //  Tabla de productos
            out.println("<table>");
            out.println("<tr>");
            out.println("<th>ID</th>");
            out.println("<th>Nombre</th>");
            out.println("<th>Tipo</th>");
            // Solo muestra el precio y el botón de compra si el usuario está autenticado
            if (usernameOptional.isPresent()) {
                out.println("<th>Precio</th>");
                out.println("<th>Acciones</th>");
            }
            out.println("</tr>");

            // Itera sobre los productos y los muestra en la tabla
            productos.forEach(producto -> {
                out.println("<tr>");
                out.println("<td>" + producto.getId() + "</td>");
                out.println("<td>" + producto.getNombre() + "</td>");
                out.println("<td>" + producto.getTipo() + "</td>");
                // Si el usuario está autenticado, muestra el precio y el botón de compra para anadir al carrito
                if (usernameOptional.isPresent()) {
                    out.println("<td>$" + producto.getPrecio() + "</td>");
                    out.println("<td><a class='btn-comprar' href='"
                            + req.getContextPath()
                            + "/agregar-carro?id=" + producto.getId() + "'>Comprar</a></td>");
                }
                out.println("</tr>");
            });

            out.println("</table>");
            // Botón para regresar al inicio
            out.println("<div>");
            out.println("<br></br>");
            out.println("<button class='btn' onclick=\"location.href='" + req.getContextPath() + "/index.html'\">Volver al Inicio</button>");
            out.println("</div>");
            out.println("</div>");
            out.println("</body>");
            out.println("</html>");
        }
    }
}
