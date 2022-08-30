package web.servlet;
/*
  User: admin
  Cur_date: 26.08.2022
  Cur_time: 14:44
*/

import dao.DbException;
import entity.User;
import service.UserService;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.math.BigDecimal;

@WebServlet("/balanceReplenishment")
public class BalanceReplenishmentServlet extends HttpServlet {
    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        UserService userService = (UserService) req.getServletContext().getAttribute("userService");

        BigDecimal balance = BigDecimal.valueOf(Double.parseDouble(req.getParameter("balanceSum")));
        int userId = (int) req.getSession().getAttribute("userId");

        try {
            userService.updateBalance(userId, balance);

            User u = userService.getByLogin((String) req.getSession().getAttribute("userLogin"));

            req.getSession().setAttribute("balance", u.getBalance());
            resp.sendRedirect("menu");
        } catch (DbException ex){
            //  LOG
            ex.printStackTrace();
        }
    }
}
