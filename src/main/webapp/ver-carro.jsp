<%--
  Created by IntelliJ IDEA.
  User: DASHMP
  Date: 12/11/2025
  Descripcion: Página JSP para ver el carrito de compras.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" import="models.*" %>

<%--
Se importa la clase modelos y se llama a la clase
DetalleCarro para validar la sesion y añadir el carrito los nuevos productos
--%>
<%
    DetalleCarro detalleCarro = (DetalleCarro) session.getAttribute("carro");
%>

<!DOCTYPE html>
<html lang="es">
<head>
    <meta charset="UTF-8">
    <title>Factura - Rum Rum</title>
    <link rel="stylesheet" href="css/carro.css">
    <script>
        // Función para abrir el diálogo de impresión (Guardar como PDF)
        function generarPDF() {
            window.print();
        }
    </script>
</head>

<body>
<div class="container">
    <h1>Factura de Compra</h1>
    <h2>Tienda en Línea</h2>
<%--
Este bucle se encarga de mostrar un mensaje de disculpa si el carrito está vacío
de lo contrario, salta el bucle y muestra la tabla con los productos adquiridos
--%>
    <%
        if (detalleCarro == null || detalleCarro.getItem().isEmpty()) {
    %>
    <p>Lo sentimos, el carrito está vacío.</p>
    <%
    } else {
    %>

    <table>
        <tr>
            <th>ID Producto</th>
            <th>Nombre</th>
            <th>Precio</th>
            <th>Cantidad</th>
            <th>Subtotal</th>
        </tr>

        <%--
        Llama a todos los productos comprados desde la clase ItemCarro para
        recoger todos los datos como nombre, cantidad y precio
        ademas del total, subtotal y calculo del iva
        --%>

        <%
            for (ItemCarro item : detalleCarro.getItem()) {
        %>
        <tr>
            <td><%= item.getProducto().getId() %></td>
            <td><%= item.getProducto().getNombre() %></td>
            <td>$<%= item.getProducto().getPrecio() %></td>
            <td><%= item.getCantidad() %></td>
            <td>$<%= item.getSubtotal() %></td>
        </tr>
        <%
            }
        %>

        <tr>
            <td colspan="4" style="text-align: right;"><strong>Subtotal:</strong></td>
            <td>$<%= detalleCarro.getTotal() %></td>
        </tr>
        <tr>
            <td colspan="4" style="text-align: right;"><strong>IVA (15%):</strong></td>
            <td>$<%= detalleCarro.getIva() %></td>
        </tr>
        <tr>
            <td colspan="4" style="text-align: right;"><strong>Total con IVA:</strong></td>
            <td><strong>$<%= detalleCarro.getTotalConIva() %></strong></td>
        </tr>
    </table>

    <div class="botones">
        <button onclick="generarPDF()">Descargar o Imprimir PDF</button>
        <button class="btn" onclick="location.href='<%= request.getContextPath() %>/productos'">Seguir comprando</button>
        <button class="btn" onclick="location.href='<%= request.getContextPath() %>/index.html'">Volver al inicio</button>
    </div>

    <%
        }
    %>
</div>
</body>
</html>

