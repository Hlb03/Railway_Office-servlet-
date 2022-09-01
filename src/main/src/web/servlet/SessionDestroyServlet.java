package web.servlet;
/*
  User: admin
  Cur_date: 10.08.2022
  Cur_time: 11:44
*/

import org.apache.logging.log4j.LogManager;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@WebServlet("/signOut")
public class SessionDestroyServlet extends HttpServlet {

    private static final org.apache.logging.log4j.Logger LOG = LogManager.getLogger(SessionDestroyServlet.class);

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        LOG.trace("User " + req.getSession().getAttribute("userLogin") + " is signed out.");
        req.getSession().invalidate();

        resp.sendRedirect("signIn");
    }
}