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
        double error = -1;
        try{
            error = (Double) req.getAttribute("error");
        }catch (Exception e){
            e.printStackTrace();
        }

        if(error != -1){
            RequestDispatcher requestDispatcher = req
                    .getRequestDispatcher("/Error.jsp");
            requestDispatcher.forward(req, resp);
        }
        resp.setContentType("text/html");
        resp.setCharacterEncoding("UTF-8");
        try {
            String coordinateX = req.getParameter("coordinateX");
            String coordinateY = req.getParameter("coordinateY");
            String radius = req.getParameter("radius");
            double x = Double.parseDouble(coordinateX);
            double y = Double.parseDouble(coordinateY);
            double r = Double.parseDouble(radius);
            req.setAttribute("x", x);
            req.setAttribute("y", y);
            req.setAttribute("r", r);
            //RequestDispatcher requestDispatcher = req.getRequestDispatcher("/check");
            //requestDispatcher.forward(req, resp);
            RequestDispatcher requestDispatcher = req
                    .getRequestDispatcher("/AreaCheckServlets.jsp");
            requestDispatcher.forward(req, resp);
        }catch (Exception e){
            resp.getWriter().print(e);;
        }
    }
}