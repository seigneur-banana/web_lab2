package servlets;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.ArrayList;

@WebServlet("/controller")
public class ControllerServlet extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        resp.setContentType("text/html");
        resp.setCharacterEncoding("UTF-8");

        String coordinateX = req.getParameter("coordinateX");
        String coordinateY = req.getParameter("coordinateY");
        String radius = req.getParameter("radius");

        if (coordinateX == null || coordinateY == null || radius == null) {
            resp.getWriter().print("<link rel=\"stylesheet\" href=\"css/styles.css\" type=\"text/css\"/>");
            resp.getWriter().print("<div class=\"content\">");
            resp.getWriter().print("<div class='error'>");
            resp.getWriter().print("Ошибка: один или несколько передаваемых праматеров не указаны!");
            resp.getWriter().print("</div>");
            resp.getWriter().print("<center><a href=\"index.jsp\">Назад к форме</a></center>");
            resp.getWriter().print("</div>");
            resp.getWriter().close();
        } else {
            try {
                double x = Double.parseDouble(coordinateX);
                double y = Double.parseDouble(coordinateY);
                double r = Double.parseDouble(radius);
                ArrayList<Double> allowedR = new ArrayList<>();
                for (double i = 1; i <= 5; i++) {
                    allowedR.add(i);
                }

                String error = "";
                if (x <= -7.5 || x>=7.5) {
                    error += "<div class='error'>";
                    error += "Ошибка в формате координаты x! Допустимые значения:</br>все целые числа от -2 до 2 включительно</br>";
                    error += "</div>";
                }
                if (y <= -5 || y >= 5) {
                    error += "<div class='error'>";
                    error += "Ошибка в формате координаты y! Допустимые значения:</br>все числа из промежутка (-3...3)</br>";
                    error += "</div>";
                }
                if (!allowedR.contains(r)) {
                    error += "<div class='error'>";
                    error += "Ошибка в формате радиуса! Допустимые значения:</br>все числа от 1 до 5 включительно с шагом 1</br>";
                    error += "</div>";
                }

                if (error.isEmpty()) {
                    req.setAttribute("x", x);
                    req.setAttribute("y", y);
                    req.setAttribute("r", r);
                    RequestDispatcher requestDispatcher = req.getRequestDispatcher("/check");
                    requestDispatcher.forward(req, resp);
                } else {
                    resp.getWriter().print("<link rel=\"stylesheet\" href=\"css/styles.css\" type=\"text/css\"/>");
                    resp.getWriter().print("<div id=\"content\">");
                    resp.getWriter().print(error);
                    resp.getWriter().print("</div>");
                    resp.getWriter().print("<center><a href=\"index.jsp\">Назад к форме</a></center>");
                    resp.getWriter().close();
                }
            } catch (NumberFormatException e) {
                resp.getWriter().print("<link rel=\"stylesheet\" href=\"css/styles.css\" type=\"text/css\"/>");
                resp.getWriter().print("<div class=\"content\">");
                resp.getWriter().print("<div class='error'>");
                resp.getWriter().print("Ошибка в формате: один или несколько переданных параметров</br>не являются числом!");
                resp.getWriter().print("</div>");
                resp.getWriter().print("<center><a href=\"index.jsp\">Назад к форме</a></center>");
                resp.getWriter().print("</div>");
                resp.getWriter().close();
            }
        }
    }
}