package web.filter.encoding_filter;
/*
  User: admin
  Cur_date: 28.08.2022
  Cur_time: 9:58
*/


import javax.servlet.*;
import javax.servlet.annotation.WebFilter;
import javax.servlet.annotation.WebInitParam;
import javax.servlet.http.HttpServletRequest;
import java.io.IOException;

@WebFilter(urlPatterns = "/*",
        initParams = {@WebInitParam(name = "encoding", value = "UTF-8")},
        dispatcherTypes = DispatcherType.REQUEST)
public class EncodingFilter implements Filter {

    private String encoding;

    @Override
    public void init(FilterConfig filterConfig) throws ServletException {
        this.encoding = filterConfig.getInitParameter("encoding");
        System.out.println("Encoding filter was initialized with param: " + encoding);
        //LOG (Filter encoding was initialized)
    }

    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException, ServletException {
        HttpServletRequest req = (HttpServletRequest) request;
        String characterEncoding = req.getCharacterEncoding();

//        System.out.println(characterEncoding + " before setting encoding");
        if (characterEncoding == null){
            //LOG
            req.setCharacterEncoding(encoding);
        }

//        System.out.println(req.getCharacterEncoding() + " after setting encoding");

        chain.doFilter(request, response);
    }
}
