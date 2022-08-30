package web.servlet;
/*
  User: admin
  Cur_date: 08.08.2022
  Cur_time: 11:37
*/

import dao.DbException;
import entity.User;
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
            resp.sendError(500, "failed to getUser");
        }

        try {
            EntityCheck.ifAlreadyExists(u);

            System.out.println(login + " " + password + " " + firstName + " " + lastName);
            try {
                uService.insertNewUser(new User(login, password, firstName, lastName));
                resp.sendRedirect("signIn");
            } catch (DbException ex) {
                //LOG
                System.out.println("DB exception");
            }

        } catch (EntityExistsException ex) {
            //LOG
            System.out.println("User already exists exception " + login);
            req.setAttribute("emailExists", login + "is already registered");
            req.getRequestDispatcher("WEB-INF/jsp/registration.jsp").forward(req, resp);
        }
    }

}
