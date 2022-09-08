package taglib;
/*
  User: admin
  Cur_date: 30.08.2022
  Cur_time: 23:06
*/

import org.apache.logging.log4j.LogManager;

import javax.servlet.http.Cookie;
import javax.servlet.jsp.JspException;
import javax.servlet.jsp.JspWriter;
import javax.servlet.jsp.tagext.TagSupport;
import java.io.IOException;

public class RouteInfoTag extends TagSupport {

    private static final org.apache.logging.log4j.Logger LOG = LogManager.getLogger(RouteInfoTag.class);
    private String route;
    private Cookie locale;

    @Override
    public int doStartTag() throws JspException {
        JspWriter out = pageContext.getOut();

        try {
            if (locale.getValue().equals("uk"))
                out.println(String.format("<h3 style=\"text-align: center\">Детальна інформація про маршрут: %s</h3>", route));
            else out.print(String.format("<h3 style=\"text-align: center\">Detailed info about route: %s</h3>", route));
        } catch (IOException ex){
            LOG.debug("Failed to execute own custom tag", ex);
        }

        return SKIP_BODY;
    }

    public String getRoute() {
        return route;
    }

    public void setRoute(String route) {
        this.route = route;
    }

    public Cookie getLocale(){ return locale;}

    public void setLocale(Cookie locale){ this.locale = locale; }
}
