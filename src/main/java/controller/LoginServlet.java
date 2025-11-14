package controller;
/*
Descripción: Esta clase se encarga de manejar el inicio de sesión de los usuarios.
Si el usuario ya ha iniciado sesión, muestra un mensaje de bienvenida y el contador de visitas.
Si no ha iniciado sesión, muestra el formulario de login.
Si las creedenciales son incorrectas, devuelve un error 401.
Autor: Dilan Salazar
Fecha: 2025/11/11
*/
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.*;
import models.Producto;
import services.LoginService;
import services.LoginServiceSessionImpl;
import services.ProductoService;
import services.ProductoServiceImpl;

import javax.swing.text.html.Option;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.List;
import java.util.Optional;
import java.util.logging.Logger;

@WebServlet({"/login", "/login.html"})
public class LoginServlet extends HttpServlet {
    final static String USERNAME = "admin";
    final static String PASSWORD = "123456";
    private static int contadorVisitas = 0; // Contador global de visitas

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        HttpSession session = req.getSession();
        Boolean visitado = (Boolean) session.getAttribute("visitado");

        // Incrementa el contador solo la primera vez que el usuario entra en
        // la sesión para que el metodo doGet no lo incremente cada vez que recarga la página
        if (visitado == null || !visitado) {
            contadorVisitas++;
            session.setAttribute("visitado", true);
        }

        // Comprueba si el usuario está autenticado
        LoginService auth = new LoginServiceSessionImpl();
        Optional<String> usernameOptional = auth.getUsername(req);

        // Configura el tipo de contenido HTML
        resp.setContentType("text/html;charset=UTF-8");

        try (PrintWriter out = resp.getWriter()) {
            // Si el usuario está logueado, muestra su panel, de lo contrario muestra el login
            if (usernameOptional.isPresent()) {
                out.println("<!DOCTYPE html>");
                out.println("<html>");
                out.println("<head>");
                out.println("<title>Hola " + usernameOptional.get() + "</title>");
                // Estilos CSS
                out.println("<style>");
                out.println("body {font-family: Arial; background:#f4f4f4; margin:0; padding:0;}");
                out.println(".container {max-width:600px; margin:80px auto; background:white; padding:40px; border-radius:10px; text-align:center; box-shadow:0 0 10px rgba(0,0,0,0.2);}");
                out.println("h1 {color:#0a3d62;}");
                out.println(".btn {background:#0a3d62; color:white; padding:10px 20px; border-radius:8px; border:none; cursor:pointer;}");
                out.println(".btn:hover {background:#1e90ff;}");
                out.println("</style>");

                // Script JS para confirmar cierre de sesión
                out.println("<script>");
                out.println("function confirmarCerrarSesion() {");
                out.println("  if(confirm('¿Seguro que deseas cerrar sesión?')) {");
                out.println("    window.location.href = '" + req.getContextPath() + "/logout';");
                out.println("  }");
                out.println("}");
                out.println("</script>");
                out.println("</head>");
                out.println("<body>");
                out.println("<div class='container'>");
                out.println("<h1>Hola " + usernameOptional.get() + "</h1>");
                out.println("<p>Haz iniciado sesión con éxito!</p>");
                out.println("<p>Contador de visitas: <b>" + contadorVisitas + "</b></p>");
                // Botones de navegación
                out.println("<button class='btn' onclick=\"location.href='" + req.getContextPath() + "/index.html'\">Volver al Inicio</button>");
                out.println("<button class='btn' onclick='confirmarCerrarSesion()'>Cerrar Sesión</button>");
                out.println("</div>");
                out.println("</body>");
                out.println("</html>");
            } else {
                // Si no está logueado, muestra el JSP del login
                req.setAttribute("contador", contadorVisitas);
                getServletContext().getRequestDispatcher("/login.jsp").forward(req, resp);
            }
        }
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        // Captura credenciales enviadas por el formulario
        String username = req.getParameter("username");
        String password = req.getParameter("password");

        // Validación del usuario y contraseña del jsp
        if (USERNAME.equals(username) && PASSWORD.equals(password)) {
            HttpSession session = req.getSession();
            session.setAttribute("username", username);
            resp.sendRedirect(req.getContextPath() + "/login.html");
        } else {
            // En caso de error, muestra mensaje de no autorizado
            resp.sendError(HttpServletResponse.SC_UNAUTHORIZED, "Lo sentimos, no esta autorizado a ingresar a esta página");
        }
    }
}