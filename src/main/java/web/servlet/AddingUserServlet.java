package web.servlet;
/*
  User: admin
  Cur_date: 08.08.2022
  Cur_time: 11:37
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

@WebServlet("/newUser")
public class AddingUserServlet extends HttpServlet {

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        System.out.println("AddingUser#doPost");

        DataSource ds = (DataSource) getServletContext().getAttribute("dataSource");
        String login = req.getParameter("login");

        System.out.println(login);
        System.out.println("~~~~~~~~~~");

        UserDAO<User> dao = new UserDAOImpl(ds);

        if (Check.ifAlreadyExists(dao.getUserByLogin(login))){
            req.setAttribute("emailExists", login +  " is already registered");
            System.out.println("Forwarding to registr form again");
            req.getRequestDispatcher("/WEB-INF/jsp/registration.jsp").forward(req, resp);
        } else {
            String password = req.getParameter("password");
            String firstName = req.getParameter("firstName");
            String lastName = req.getParameter("lastName");

            dao.insertNewUser(new User(login, password, firstName, lastName));
//        System.out.println("Forwarding to sign in form");
            System.out.println("Redirecting to signIn url");
            resp.sendRedirect("signIn");
//        req.getRequestDispatcher("WEB-INF/jsp/sign_in.jsp").forward(req, resp);
        }
    }

}
