package web.servlet;
/*
  User: admin
  Cur_date: 09.08.2022
  Cur_time: 11:00
*/

import dao.DbException;
import entity.User;
import org.apache.logging.log4j.LogManager;
import service.UserService;
import util.EntityCheck;
import exception.PasswordException;
import exception.EntityNotExistsException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@WebServlet("/authorize")
public class AuthorizationServlet extends HttpServlet {

    private static final org.apache.logging.log4j.Logger LOG = LogManager.getLogger(AuthorizationServlet.class);

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String login = req.getParameter("login");
        String password = req.getParameter("password");

        UserService uService = (UserService) getServletContext().getAttribute("userService");

        User u = null;
        int userAmountOfTrips = 0;
        try {
            u = uService.getByLogin(login);
        } catch (DbException ex){
            req.getSession().setAttribute("errorMsg", ex.getMessage());
            resp.sendRedirect("errorHandler");
            LOG.debug(ex.getMessage(), ex);
        }

        try {
            LOG.trace("Trying to check whether user already exists while registration process.");
            EntityCheck.ifNotExists(u);

            try {
                EntityCheck.ifPasswordsSame(password, u.getPassword());

                int userId = u.getId();
                String userLogin = u.getLogin();
                String userNS = u.getFirstName() + " " + u.getLastName();
                String userRole = u.getRole().getRoleName();
                String balance = u.getBalance().toString();

                try {
                    userAmountOfTrips = uService.totalAmountOfUserTrips(u);
                } catch (DbException ex){
                    req.getSession().setAttribute("errorMsg", ex.getMessage());
                    resp.sendRedirect("errorHandler");
                    LOG.debug(ex.getMessage(), ex);
                }

                req.getSession().setAttribute("userId", userId);
                req.getSession().setAttribute("userLogin", userLogin);
                req.getSession().setAttribute("userNS", userNS);
                req.getSession().setAttribute("userRole", userRole);
                req.getSession().setAttribute("balance", balance);
                req.getSession().setAttribute("userTrips", userAmountOfTrips);

                LOG.trace("User " + u.getLogin() + " is authorized");
                resp.sendRedirect("menu");

            } catch (PasswordException ex){
                LOG.trace("PasswordException caused by not equal passwords." + password + "!=" + u.getPassword() +
                        ". Forward again to authorization form");
                req.setAttribute("failedAuthorize", "Incorrect login or password");
                req.getRequestDispatcher("WEB-INF/jsp/sign_in.jsp").forward(req, resp);
            }

        } catch (EntityNotExistsException ex){
            LOG.trace("User with login " + login + " not exists." +
                    " Throws exception and forwarding again to authorization form");
            req.setAttribute("failedAuthorize", login + " user does not exist");
            req.getRequestDispatcher("WEB-INF/jsp/sign_in.jsp").forward(req, resp);
        }
    }
}
