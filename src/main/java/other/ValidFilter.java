package other;

import java.io.IOException;
import java.util.ArrayList;
import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;

public class ValidFilter implements Filter {
    private FilterConfig config = null;
    private boolean active = false;

    @Override
    public void init(FilterConfig filterConfig) throws ServletException {
        config = filterConfig;
        String act = config.getInitParameter("active");
        if (act != null)
            active = (act.toUpperCase().equals("TRUE"));
    }

    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain)
            throws IOException, ServletException {
        double x = 0, y = 0, r = 0;
        if (active) {
            request.setAttribute("error", -1);
            String coordinateX = request.getParameter("coordinateX");
            String coordinateY = request.getParameter("coordinateY");
            String radius = request.getParameter("radius");
            try{
                if (coordinateX == null || coordinateY == null || radius == null) {
                    request.setAttribute("error", 0);
                }
                else {
                    try {
                        x = Double.parseDouble(coordinateX);
                        y = Double.parseDouble(coordinateY);
                        r = Double.parseDouble(radius);
                        ArrayList<Double> allowedR = new ArrayList<>();
                        for (double i = 1; i <= 5; i++) {
                            allowedR.add(i);
                        }
                        if (x <= -3 || x>=3) {
                            request.setAttribute("error", 1);
                        }
                        if (y <= -5 || y >= 5) {
                            request.setAttribute("error", 2);
                        }
                        if (!allowedR.contains(r)) {
                            request.setAttribute("error", 3);
                        }

                    } catch (NumberFormatException e) {
                        e.printStackTrace();
                    }
                }
            }catch (Exception e){
                e.printStackTrace();
            }
            chain.doFilter(request, response);
        }
    }

        public void destroy() {
            config = null;
        }
    }
