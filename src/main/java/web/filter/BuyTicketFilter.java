package web.filter;
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

@WebFilter("/buyTicket")
public class BuyTicketFilter implements Filter {
    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException, ServletException {

        HttpServletRequest req = (HttpServletRequest) request;
        HttpServletResponse resp = (HttpServletResponse) response;
        
        if (req.getSession().getAttribute("userLog") == null)
            resp.sendRedirect("signIn");

        chain.doFilter(request, response);
    }
}
