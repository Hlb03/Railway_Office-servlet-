package web.listener;
/*
  User: admin
  Cur_date: 07.08.2022
  Cur_time: 13:19
*/


import javax.naming.Context;
import javax.naming.InitialContext;
import javax.naming.NamingException;
import javax.sql.DataSource;


import javax.servlet.ServletContextListener;
import javax.servlet.annotation.WebListener;
import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContext;


@WebListener
public class ContextListener implements ServletContextListener {

    // bootstrap of the application
    @Override
    public void contextInitialized(ServletContextEvent sce) {
//        jakarta.servlet.jsp.jstl.fmt.LocaleSupport l;
//        LOG.debug("Start context initialization");
        ServletContext context = sce.getServletContext();
        initDatasource(context);
//        LOG.debug("DataSource initialized");
//        initServices(context);
//        LOG.debug("Services initialized");
    }

    private void initDatasource(ServletContext context) throws IllegalStateException {
        String dataSourceName = context.getInitParameter("dataSource");
        Context jndiContext = null;
        try {
            jndiContext = (Context) new InitialContext().lookup("java:/comp/env");
            DataSource dataSource = (DataSource) jndiContext.lookup(dataSourceName); // "jdbc/RailwayDB"
            context.setAttribute("dataSource", dataSource);
//            LOG.trace("context.setAttribute 'dataSource': {}", dataSource.getClass().getName());
        } catch (NamingException e) {
            throw new IllegalStateException("Cannot initialize dataSource", e);
        }
    }
}
