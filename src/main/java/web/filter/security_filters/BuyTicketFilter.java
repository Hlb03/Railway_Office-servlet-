package web.filter.security_filters;
/*
  User: admin
  Cur_date: 25.08.2022
  Cur_time: 14:20
*/

import javax.servlet.*;
import javax.servlet.annotation.WebFilter;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@WebFilter(urlPatterns = "/buyTicket")
public class BuyTicketFilter implements Filter {

    @Override
    public void init(FilterConfig filterConfig) throws ServletException {
        //LOG (filter has been initialized)
        Filter.super.init(filterConfig);
    }

    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException, ServletException {

        HttpServletRequest req = (HttpServletRequest) request;
        HttpServletResponse resp = (HttpServletResponse) response;
        
        if (req.getSession().getAttribute("userLogin") == null)
            resp.sendRedirect("signIn");
        else chain.doFilter(request, response);
    }
}
