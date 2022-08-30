package web.listener;
/*
  User: admin
  Cur_date: 07.08.2022
  Cur_time: 13:19
*/


import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import dao.*;
import service.*;
import web.filter.encoding_filter.EncodingFilter;

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

    private static final Logger LOG = LogManager.getLogger(ContextListener.class);
    @Override
    public void contextInitialized(ServletContextEvent sce) {
        LOG.debug("Start context initialization");
        ServletContext context = sce.getServletContext();
        initDatasource(context);
        LOG.debug("DataSource initialized");
        initService(context);
        LOG.debug("Services initialized");
    }

    private void initDatasource(ServletContext context) throws IllegalStateException {
        String dataSourceName = context.getInitParameter("dataSource");
        Context jndiContext;
        try {
            jndiContext = (Context) new InitialContext().lookup("java:/comp/env");
            DataSource dataSource = (DataSource) jndiContext.lookup(dataSourceName); // "jdbc/RailwayDB"
            context.setAttribute("dataSource", dataSource);
            LOG.trace("context.setAttribute 'dataSource':"  + dataSource.getClass().getName());
        } catch (NamingException e) {
            throw new IllegalStateException("Cannot initialize dataSource", e);
        }
    }

    private void initService(ServletContext context) {

        UserDAO userDAO = new UserDAOImpl((DataSource) context.getAttribute("dataSource"));
        LOG.trace("create 'userDAO' " + userDAO);

        UserService userService = new UserServiceImpl(userDAO);
        context.setAttribute("userService", userService);
        LOG.trace("set attribute 'userService' " + userService);
        //~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~
        TrainDAO trainDAO = new TrainDAOImpl((DataSource) context.getAttribute("dataSource"));
        LOG.trace("create 'trainDAO' " + trainDAO);

        TrainService trainService = new TrainServiceImpl(trainDAO);
        context.setAttribute("trainService", trainService);
        LOG.trace("set attribute 'trainService' " + trainService);
        //~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~
        TripDAO tripDAO = new TripDAOImpl((DataSource) context.getAttribute("dataSource"));
        LOG.trace("create 'tripDAO' " + tripDAO);

        TripService tripService = new TripServiceImpl(tripDAO);
        context.setAttribute("tripService", tripService);
        LOG.trace("set attribute 'tripService' " + tripService);
        //~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~
        SettlementDAO settlementDAO = new SettlementDAOImpl((DataSource) context.getAttribute("dataSource"));
        LOG.trace("create 'settlementDAO' " + settlementDAO);

        SettlementService settlementService = new SettlementServiceImpl(settlementDAO);
        context.setAttribute("settlementService", settlementService);
        LOG.trace("set attribute 'settlementService' " + settlementService);
    }
}
