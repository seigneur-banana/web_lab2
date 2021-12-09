<%@ page import="other.Point" %>
<%@ page import="other.HistoryBean" %>
<%@ page import="java.text.SimpleDateFormat" %>

<%@ page contentType="text/html;charset=UTF-8" %>
<html>
<head>
    <title>Error</title>
</head>
<body>
<link rel=\"stylesheet\" href=\"css/styles.css\" type=\"text/css\"/>
    <div class=\"content\">
        <%
            Double err = 0.0;
            try{
                err = Double.parseDouble(request.getAttribute("error").toString());
            }catch (Exception e) {
                e.printStackTrace();
            }

            if (err == 0){
                out.println("        <div class='error'>\n" +
                        "            <p>Ошибка: один или несколько передаваемых праматеров не указаны!</p>\n" +
                        "        </div>");
            }
            if (err == 1){
                out.println("<div class='error'>Ошибка в формате координаты x! Допустимые значения:</br>все целые числа от -2 до 2 включительно</div>");
            }
            if (err == 2){
                out.println("<div class='error'>Ошибка в формате координаты y! Допустимые значения:</br>все числа из промежутка (-3...3)</div>");
            }
            if (err == 3){
                out.println("<div class='error'>Ошибка в формате радиуса! Допустимые значения:</br>все числа от 1 до 5 включительно с шагом 1</div>");
            }
            out.println("<a href=\"index.jsp\">Назад к форме</a>");
        %>
    </div>
</body>
</html>
