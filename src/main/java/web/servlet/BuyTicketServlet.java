package web.servlet;
/*
  User: admin
  Cur_date: 25.08.2022
  Cur_time: 14:33
*/

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@WebServlet("/buyTicket")
public class BuyTicketServlet extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        resp.getWriter().write("You have bought ticket!");
        resp.getWriter().println(req.getSession().getAttribute("userLog"));
    }
}
