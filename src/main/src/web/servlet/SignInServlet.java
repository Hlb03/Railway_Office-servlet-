package web.servlet;
/*
  User: admin
  Cur_date: 08.08.2022
  Cur_time: 13:04
*/

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@WebServlet("/signIn")
public class SignInServlet extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {

        req.getRequestDispatcher("WEB-INF/jsp/sign_in.jsp").forward(req, resp);
    }
}
