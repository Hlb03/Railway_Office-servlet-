package web.servlet;
/*
  User: admin
  Cur_date: 08.08.2022
  Cur_time: 11:37
*/

import dao.DbException;
import entity.User;
import org.apache.logging.log4j.LogManager;
import service.UserService;
import util.EntityCheck;
import exception.EntityExistsException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@WebServlet("/newUser")
public class NewUserServlet extends HttpServlet {

    private static final org.apache.logging.log4j.Logger LOG = LogManager.getLogger(NewUserServlet.class);

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        UserService uService = (UserService) getServletContext().getAttribute("userService");

        String login = req.getParameter("login");
        String password = req.getParameter("password");
        String firstName = req.getParameter("firstName");
        String lastName = req.getParameter("lastName");

        User u = null;

        try {
            u = uService.getByLogin(login);
        } catch (DbException ex) {
            req.getSession().setAttribute("errorMsg", ex.getMessage());
            resp.sendRedirect("errorHandler");
            LOG.debug(ex.getMessage(), ex);
        }

        try {
            EntityCheck.ifAlreadyExists(u);

            try {
                uService.insertNewUser(new User(login, password, firstName, lastName));

                LOG.trace("New user with login " + login + " was created");
                resp.sendRedirect("signIn");
            } catch (DbException ex) {
                req.getSession().setAttribute("errorMsg", ex.getMessage());
                resp.sendRedirect("errorHandler");
                LOG.debug(ex.getMessage(), ex);
            }

        } catch (EntityExistsException ex) {
            LOG.trace("User with login " + login + " already exists exception. Forward back to registration form.");
            req.setAttribute("emailExists", login + "is already registered");
            req.getRequestDispatcher("WEB-INF/jsp/registration.jsp").forward(req, resp);
        }
    }

}
