<%@ page import="other.Point" %>
<%@ page import="other.HistoryBean" %>
<%@ page import="java.text.SimpleDateFormat" %>

<%@ page contentType="text/html;charset=UTF-8" %>
<%!
    private boolean pointIsInTriangle(double x, double y, double r) {
        return (y <= 2 * x + r) && (y >= 0) && (x <= 0);
    }

    private boolean pointIsInCircle(double x, double y, double r) {
        return (x * x + y * y <= r * r) && (y <= 0) && (x >= 0);
    }

    private boolean pointIsInRectangle(double x, double y, double r) {
        return (y <= r) && (y >= 0) && (x <= r / 2) && (x >= 0);
    }
%>
<!doctype html>
<html lang="ru">
<head>
    <meta charset="utf-8"/>
    <title>privet web lab2</title>
    <link rel="stylesheet" href="css/styles.css" type="text/css"/>
    <script src="https://code.jquery.com/jquery-3.5.1.min.js"
            integrity="sha256-9/aliU8dGd2tb6OSsuzixeV4y/faTqgFtohetphbbj0=" crossorigin="anonymous"></script>
</head>
<body>

<%
    HistoryBean hb;
    response.setContentType("text/html");
    response.setCharacterEncoding("UTF-8");

    hb = (HistoryBean) request.getSession().getAttribute("hb");
    if (request.getAttribute("x") != null && request.getAttribute("y") != null && request.getAttribute("r") != null) {
        double x = (Double) request.getAttribute("x");
        double y = (Double) request.getAttribute("y");
        double r = (Double) request.getAttribute("r");
        boolean isInArea = pointIsInTriangle(x, y, r) || pointIsInCircle(x, y, r) || pointIsInRectangle(x, y, r);

        hb.getHistory().add(new Point(x, y, r, isInArea));
        request.getSession().setAttribute("hb", hb);
    }
%>
<link rel=\"stylesheet\" href=\"css/styles.css\" type=\"text/css\"/>
<div id=\"content\">
    <%
        if (hb.getHistory().isEmpty()) {
            out.println("<div class='error'>История запросов пуста.</div>");
        } else {
    %>
    <table class='history'>
        <thead>
        <tr>
            <th>Значение X</th>
            <th>Значение Y</th>
            <th>Значение R</th>
            <th>Попадание</th>
            <th>Дата и время</th>
        </tr>
        </thead>
        <tbody>
        <%
                for (Point p : hb.getHistory()) {
                    out.println("<tr>");
                    out.println("<td>" + p.getX() + "</td>");
                    out.println("<td>" + p.getY() + "</td>");
                    out.println("<td>" + p.getR() + "</td>");
                    out.println("<td>" + (p.isInArea() ? "Да" : "Нет") + "</td>");
                    out.println("<td>" + (new SimpleDateFormat("dd.MM.yy HH:mm:ss")).format(p.getDate()) + "</td>");
                    out.println("</tr>");
                }
            }
        %>
        </tbody>
    </table>
    <a href=index.jsp>Назад к форме</a>
</div>
</body>
</html>

