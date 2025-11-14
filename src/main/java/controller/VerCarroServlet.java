package controller;
/*
Descripcion:Este servlet redirige al usuario a la página JSP que
muestra el contenido del carrito de compras.
Autor: Dilan Salazar
Fecha: 13/11/2025
 */
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;

@WebServlet("/ver-carro")
public class VerCarroServlet extends HttpServlet {
    //Metodo que permite redireccionar al carrito en el archivo .jsp
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        getServletContext().getRequestDispatcher("/ver-carro.jsp").forward(req,resp);
    }
}
