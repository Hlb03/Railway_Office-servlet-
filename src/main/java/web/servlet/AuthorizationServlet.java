package web.servlet;
/*
  User: admin
  Cur_date: 09.08.2022
  Cur_time: 11:00
*/

import dao.DbException;
import entity.User;
import service.UserService;
import util.UserCheck;
import exception.PasswordException;
import exception.UserNotExistsException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@WebServlet("/authorize")
public class AuthorizationServlet extends HttpServlet {

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String login = req.getParameter("login");
        String password = req.getParameter("password");

        UserService uService = (UserService) getServletContext().getAttribute("userService");

        User u = null;
        try {
            u = uService.getByLogin(login);
        } catch (DbException ex){
            resp.sendError(500, "failed to find user");
        }


        try {
            UserCheck.ifNotExists(u);

            try {
                UserCheck.ifPasswordsSame(password, u.getPassword());

                String userLog = u.getFirstName() + " " + u.getLastName() + " (" + login + ")";
                String userRole = u.getRole().getRoleName();

                String balance = u.getBalance().toString();

                req.getSession().setAttribute("userLog", userLog);
                req.getSession().setAttribute("userRole", userRole);
                req.getSession().setAttribute("balance", balance);

                resp.sendRedirect("menu");

            } catch (PasswordException ex){
                //LOG
                System.out.println("Password exception");
                System.out.println("Password not same: " + password + " != " + u.getPassword());
                req.setAttribute("failedAuthorize", "Incorrect login or password");
                req.getRequestDispatcher("WEB-INF/jsp/sign_in.jsp").forward(req, resp);
            }

        } catch (UserNotExistsException ex){
            //LOG
            System.out.println("User not exists exception");
            req.setAttribute("failedAuthorize", login + " user does not exist");
            req.getRequestDispatcher("WEB-INF/jsp/sign_in.jsp").forward(req, resp);
        }
    }
}
