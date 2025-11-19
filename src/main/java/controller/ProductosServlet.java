package controller;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import models.Producto;
import services.ProductoService;
import services.ProductoServiceJdbcImpl;

import java.io.IOException;
import java.io.PrintWriter;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.List;
import java.util.Optional;

@WebServlet("/productos")
public class ProductosServlet extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        Connection conn = null;
        try {
            conn = (Connection) req.getServletContext().getAttribute("conn");
            // Traemos la conexión
            ProductoService service = new ProductoServiceJdbcImpl(conn);
            // ProductosService service = new ProductoServiceJdbcImpl(conn);
            List<Producto> productos = service.listar();

            resp.setContentType("text/html;charset=UTF-8");
            try (PrintWriter out = resp.getWriter()) {
                out.println("<!DOCTYPE html>");
                out.println("<html>");
                out.println("  <head>");
                out.println("    <meta charset=\"UTF-8\">");
                out.println("    <title>Listado de Productos</title>");
                out.println("  </head>");
                out.println("  <body>");
                out.println("    <h1>Listado de Productos</h1>");

                if (productos != null && !productos.isEmpty()) {
                    out.println("<table style=\"border: 1px solid black;\">");
                    out.println("  <tr>");
                    out.println("    <th>ID</th>");
                    out.println("    <th>Nombre</th>");
                    out.println("    <th>Precio</th>");
                    out.println("    <th>Fecha Producción</th>");
                    out.println("  </tr>");

                    for (Producto p : productos) {
                        out.println("  <tr>");
                        out.println("    <td>" + p.getId() + "</td>");
                        out.println("    <td>" + p.getNombre() + "</td>");
                        out.println("    <td>" + p.getPrecio() + "</td>");
                        out.println("    <td>" + p.getFechaProduccion() + "</td>");
                        out.println("  </tr>");
                    }
                    out.println("</table>");
                } else {
                    out.println("<p>No hay productos disponibles.</p>");
                }

                out.println("  </body>");
                out.println("</html>");
            }
        } catch (SQLException e) {
            throw new ServletException(e);
        } finally {
            if (conn != null) {
                try {
                    conn.close();
                } catch (SQLException e) {
                    e.printStackTrace();
                }
            }
        }
    }
}