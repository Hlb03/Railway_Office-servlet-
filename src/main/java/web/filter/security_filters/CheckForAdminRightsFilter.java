package web.filter.security_filters;
/*
  User: admin
  Cur_date: 30.08.2022
  Cur_time: 12:10
*/

import javax.servlet.*;
import javax.servlet.annotation.WebFilter;
import javax.servlet.http.HttpServletRequest;
import java.io.IOException;

@WebFilter(urlPatterns = {"/editTrip", "/newTrip"})
public class CheckForAdminRightsFilter implements Filter {
    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException, ServletException {
        HttpServletRequest req = (HttpServletRequest) request;

        if (req.getSession().getAttribute("userRole") == null ||req.getSession().getAttribute("userRole").equals("user")) {
            //LOG
            System.out.println("User/stranger has no rights to access that page");
            request.getRequestDispatcher("WEB-INF/error_pages/access_denied.html").forward(request, response);
        } else {
            chain.doFilter(request, response);
        }
    }
}