package web.servlet;
/*
  User: admin
  Cur_date: 09.08.2022
  Cur_time: 11:11
*/

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@WebServlet("/menu")
public class MainMenuServlet extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        System.out.println("MainMenu#doGet");
        System.out.println("Forwarding to menu.jsp");
        System.out.println("~~~~~~~~~~~");

        req.setAttribute("attr", "Glad to see you here!");

        req.getRequestDispatcher("WEB-INF/jsp/menu.jsp").forward(req, resp);

    }
}
