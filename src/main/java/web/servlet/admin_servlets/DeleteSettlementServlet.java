package web.servlet.admin_servlets;
/*
  User: admin
  Cur_date: 28.08.2022
  Cur_time: 12:21
*/

import dao.DbException;
import service.SettlementService;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@WebServlet("/deleteSettlement")
public class DeleteSettlementServlet extends HttpServlet {

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        SettlementService settlementService =
                (SettlementService) getServletContext().getAttribute("settlementService");
        String name = req.getParameter("settlementName");
        System.out.println(name);

        try {
            settlementService.deleteSettlement(name);

            resp.sendRedirect("newTrip");
        } catch (DbException ex){

        }
    }
}
