package servlets;

import other.HistoryBean;
import other.Point;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.text.SimpleDateFormat;

@WebServlet("/check")
public class AreaCheckServlet extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        resp.setContentType("text/html");
        resp.setCharacterEncoding("UTF-8");

        HistoryBean hb = (HistoryBean) req.getSession().getAttribute("hb");

        if (req.getAttribute("x") != null && req.getAttribute("y") != null && req.getAttribute("r") != null) {
            double x = (Double) req.getAttribute("x");
            double y = (Double) req.getAttribute("y");
            double r = (Double) req.getAttribute("r");
            boolean isInArea = pointIsInTriangle(x, y, r) || pointIsInCircle(x, y, r) || pointIsInRectangle(x, y, r);

            hb.getHistory().add(new Point(x, y, r, isInArea));
            req.getSession().setAttribute("hb", hb);
        }
        resp.getWriter().print("<link rel=\"stylesheet\" href=\"css/style.css\" type=\"text/css\"/>");
        resp.getWriter().print("<div id=\"content\">");
        if (hb.getHistory().isEmpty()) {
            resp.getWriter().print("<div class='error'>История запросов пуста, поэтому таблица не загружена.</div>");
        } else {
            resp.getWriter().print("<table class='history'>");
            resp.getWriter().print("<thead><tr><th>Значение X</th><th>Значение Y</th><th>Значение R</th><th>Попадание</th><th>Дата и время</th></tr></thead>");
            resp.getWriter().print("<tbody>");
            for (Point p : hb.getHistory()) {
                resp.getWriter().print("<tr><td>" + p.getX() + "</td><td>" + p.getY() + "</td><td>" + p.getR() + "</td><td>" + (p.isInArea() ? "Да" : "Нет") + "</td><td>" + (new SimpleDateFormat("dd.MM.yy HH:mm:ss")).format(p.getDate()) + "</td></tr>");
            }
            resp.getWriter().print("</tbody>");
            resp.getWriter().print("</table>");
        }
        resp.getWriter().print("<center><a href=\"index.jsp\">Назад к форме</a></center>");
        resp.getWriter().print("</div>");
        resp.getWriter().close();
    }

    private boolean pointIsInTriangle(double x, double y, double r) {
        return (y <= r / 2 - x) && (y >= 0) && (x >= 0);
    }

    private boolean pointIsInCircle(double x, double y, double r) {
        return (x * x + y * y <= r * r / 4) && (y >= 0) && (x <= 0);
    }

    private boolean pointIsInRectangle(double x, double y, double r) {
        return (y >= -r) && (y <= 0) && (x >= -r / 2) && (x <= 0);
    }
}