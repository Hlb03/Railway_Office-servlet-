package web.filter.encoding_filter;
/*
  User: admin
  Cur_date: 28.08.2022
  Cur_time: 9:58
*/


import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import javax.servlet.*;
import javax.servlet.annotation.WebFilter;
import javax.servlet.annotation.WebInitParam;
import javax.servlet.http.HttpServletRequest;
import java.io.IOException;

@WebFilter(urlPatterns = "/*",
        initParams = {@WebInitParam(name = "encoding", value = "UTF-8")},
        dispatcherTypes = DispatcherType.REQUEST)
public class EncodingFilter implements Filter {

    private static final Logger LOG = LogManager.getLogger(EncodingFilter.class);

    private String encoding;

    @Override
    public void init(FilterConfig filterConfig) throws ServletException {
        this.encoding = filterConfig.getInitParameter("encoding");

        LOG.trace("Encoding filter is initialized with params:" + encoding + ". Will set this encoding to each page.");

    }

    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException, ServletException {
        HttpServletRequest req = (HttpServletRequest) request;
        String characterEncoding = req.getCharacterEncoding();

//        LOG.trace("Before setting encoding to site its encoding is:" + characterEncoding);
        if (characterEncoding == null){
            req.setCharacterEncoding(encoding);
        }
//        LOG.trace("After:" + encoding);

        chain.doFilter(request, response);
    }
}
