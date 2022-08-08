package web.servlet;

/*
  User: admin
  Cur_date: 07.08.2022
  Cur_time: 13:31
*/

import dao.UserDAO;
import dao.UserDAOImpl;
import entity.User;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.sql.DataSource;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.List;
import java.util.Optional;

@WebServlet("/welcome")
public class WelcomeServlet extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        System.out.println("Welcome#doGet");

        PrintWriter pw = resp.getWriter();
        DataSource ds = (DataSource) getServletContext().getAttribute("dataSource");

        UserDAO<User> userDAO = new UserDAOImpl(ds);

        List<User> allUsers = userDAO.getAllUsers();

        for (User u : allUsers){
            pw.println(u);
        }

        String name = req.getParameter("name");

        pw.println(userDAO.getUserByLogin(name));

    }
}
