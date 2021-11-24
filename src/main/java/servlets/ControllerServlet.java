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
            resp.getWriter().print("<link rel=\"stylesheet\" href=\"css/style.css\" type=\"text/css\"/>");
            resp.getWriter().print("<div id=\"content\">");
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

                ArrayList<Double> allowedX = new ArrayList<>();
                for (double i = -4; i <= 4; i++) {
                    allowedX.add(i);
                }
                ArrayList<Double> allowedR = new ArrayList<>();
                for (double i = 1; i <= 3; i += 0.5) {
                    allowedR.add(i);
                }

                String error = "";
                if (!allowedX.contains(x)) {
                    error += "<div class='error'>";
                    error += "Ошибка в формате координаты x! Допустимые значения:</br>все целые числа от -4 до 4 включительно</br>";
                    error += "</div>";
                }
                if (y <= -5 || y >= 5) {
                    error += "<div class='error'>";
                    error += "Ошибка в формате координаты y! Допустимые значения:</br>все числа из промежутка (-5...5)</br>";
                    error += "</div>";
                }
                if (!allowedR.contains(r)) {
                    error += "<div class='error'>";
                    error += "Ошибка в формате радиуса! Допустимые значения:</br>все числа от 1 до 3 включительно с шагом 0.5</br>";
                    error += "</div>";
                }

                if (error.isEmpty()) {
                    req.setAttribute("x", x);
                    req.setAttribute("y", y);
                    req.setAttribute("r", r);
                    RequestDispatcher requestDispatcher = req.getRequestDispatcher("/check");
                    requestDispatcher.forward(req, resp);
                } else {
                    resp.getWriter().print("<link rel=\"stylesheet\" href=\"css/style.css\" type=\"text/css\"/>");
                    resp.getWriter().print("<div id=\"content\">");
                    resp.getWriter().print(error);
                    resp.getWriter().print("</div>");
                    resp.getWriter().print("<center><a href=\"index.jsp\">Назад к форме</a></center>");
                    resp.getWriter().close();
                }
            } catch (NumberFormatException e) {
                resp.getWriter().print("<link rel=\"stylesheet\" href=\"css/style.css\" type=\"text/css\"/>");
                resp.getWriter().print("<div id=\"content\">");
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