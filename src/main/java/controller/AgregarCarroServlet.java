package controller;

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
import services.ProductoServiceJdbcImpl;

import java.io.IOException;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.Optional;

@WebServlet("/agregar-carro")
public class AgregarCarroServlet extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        Long id = Long.parseLong(req.getParameter("id"));
        // Traemos la conexión
        Connection conn = (Connection) req.getServletContext().getAttribute("conn");
        ProductoService service = new ProductoServiceJdbcImpl(conn);
        Optional<Producto> producto = service.porId(id);

        if (producto.isPresent()) {
            ItemCarro item = new ItemCarro(1, producto.get());
            HttpSession session = req.getSession();
            DetalleCarro detalleCarro = (DetalleCarro) session.getAttribute("carro");
            if (detalleCarro == null) {
                detalleCarro = new DetalleCarro();
                session.setAttribute("carro", detalleCarro);
            }
            detalleCarro.addItem(item);
        }

        resp.sendRedirect(req.getContextPath() + "/ver-carro");
    }
}