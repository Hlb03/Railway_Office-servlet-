package web.filter.internationalization;
/*
  User: admin
  Cur_date: 31.08.2022
  Cur_time: 18:16
*/

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import web.filter.encoding_filter.EncodingFilter;

import javax.servlet.*;
import javax.servlet.annotation.WebFilter;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@WebFilter("/*")
public class SessionLocaleFilter implements Filter {

    private static final Logger LOG = LogManager.getLogger(SessionLocaleFilter.class);

    @Override
    public void init(FilterConfig filterConfig) throws ServletException {
        LOG.trace("Filter session locale initialized");
        Filter.super.init(filterConfig);
    }

    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException, ServletException {

        HttpServletRequest req = (HttpServletRequest) request;
        HttpServletResponse resp = (HttpServletResponse) response;

        if (req.getParameter("cookieLocale") != null) {
            Cookie cookie = new Cookie("lang", req.getParameter("cookieLocale"));
            resp.addCookie(cookie);
        }

        chain.doFilter(request, response);
    }
}