package web.servlet;

/*
  User: admin
  Cur_date: 07.08.2022
  Cur_time: 13:31
*/

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@WebServlet("/welcome")
public class WelcomeServlet extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        System.out.println("WelcomeServlet#doGet");
        System.out.println("Forwarding to registr jsp");
        // example of redirecting to JSP file that is located in WEB-INF
        req.getRequestDispatcher("/WEB-INF/jsp/registration.jsp").forward(req, resp);

    }
}
