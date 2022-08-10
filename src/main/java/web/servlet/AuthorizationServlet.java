package web.servlet;
/*
  User: admin
  Cur_date: 09.08.2022
  Cur_time: 11:00
*/

import dao.UserDAO;
import dao.UserDAOImpl;
import entity.User;
import util.Check;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.sql.DataSource;
import java.io.IOException;

@WebServlet("/authorize")
public class AuthorizationServlet extends HttpServlet {

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String login = req.getParameter("login");
        String password = req.getParameter("password");

        DataSource ds = (DataSource) getServletContext().getAttribute("dataSource");

        UserDAO<User> dao = new UserDAOImpl(ds);

        User u = dao.getUserByLogin(login);

        if (!Check.ifAlreadyExists(u)){
            req.setAttribute("failedAuthorize", login + " user does not exist");
            req.getRequestDispatcher("WEB-INF/jsp/sign_in.jsp").forward(req, resp);
        } else {
            if (!Check.ifPasswordsSame(password, u.getPassword())){
                System.out.println(password + " != " + u.getPassword());
                req.setAttribute("failedAuthorize", "Incorrect login or password");
                req.getRequestDispatcher("WEB-INF/jsp/sign_in.jsp").forward(req, resp);
            } else {
                System.out.println(password + " == " + u.getPassword());
                System.out.println("Redirecting");

                String userLog = u.getFirstName() + " " + u.getLastName() + " (" + login + ")";
                req.getSession().setAttribute("userLog", userLog);

                //Check how long does the session stores
//                System.out.println(req.getSession().getMaxInactiveInterval());
                System.out.println("Current session is: " + req.getSession());

                resp.sendRedirect("menu");
            }
        }

    }
}
