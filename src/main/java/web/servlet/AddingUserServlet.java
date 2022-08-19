package web.servlet;
/*
  User: admin
  Cur_date: 08.08.2022
  Cur_time: 11:37
*/

import dao.DbException;
import entity.User;
import service.UserService;
import util.Check;
import web.exception.UserExistsException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@WebServlet("/newUser")
public class AddingUserServlet extends HttpServlet {

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
//        System.out.println("AddingUser#doPost");

        UserService uService = (UserService) getServletContext().getAttribute("userService");
        String login = req.getParameter("login");

//        System.out.println(login);
//        System.out.println("~~~~~~~~~~");

        User u = null;
        try {
            u = uService.getByLogin(login);
        } catch (DbException ex){
            resp.sendError(500, "failed to getUser");
        }

        try {
            Check.ifAlreadyExists(u);

            String password = req.getParameter("password");
            String firstName = req.getParameter("firstName");
            String lastName = req.getParameter("lastName");

            try {
                uService.insertNewUser(new User(login, password, firstName, lastName));
            } catch (DbException ex){
                //LOG
                System.out.println("DB exception");
            }

        } catch (UserExistsException ex){
            //LOG
            System.out.println("User already exists exception " + login);
            req.setAttribute("emailExists", login + "is already registered");
            req.getRequestDispatcher("WEB-INF/jsp/registration.jsp").forward(req, resp);
        }

//        if (Check.ifAlreadyExists(u)){
//            req.setAttribute("emailExists", login +  " is already registered");
//            System.out.println("Forwarding to registr form again");
//            req.getRequestDispatcher("/WEB-INF/jsp/registration.jsp").forward(req, resp);
//        } else {
//            String password = req.getParameter("password");
//            String firstName = req.getParameter("firstName");
//            String lastName = req.getParameter("lastName");
//
//            try {
//                uService.createNewUser(new User(login, password, firstName, lastName));
//            } catch (DbException ex){
//                resp.sendError(500);
//            }
////        System.out.println("Forwarding to sign in form");
//            System.out.println("Redirecting to signIn url");
//            resp.sendRedirect("signIn");
////        req.getRequestDispatcher("WEB-INF/jsp/sign_in.jsp").forward(req, resp);
//        }
    }

}
