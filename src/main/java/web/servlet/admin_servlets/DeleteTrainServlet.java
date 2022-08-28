package web.servlet.admin_servlets;
/*
  User: admin
  Cur_date: 28.08.2022
  Cur_time: 13:09
*/

import dao.DbException;
import entity.Train;
import service.TrainService;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@WebServlet("/deleteTrain")
public class DeleteTrainServlet extends HttpServlet {
    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        TrainService trainService = (TrainService) getServletContext().getAttribute("trainService");

        String number = req.getParameter("trainNumber");
        System.out.println(number + " train was deleted");

        try{
            trainService.deleteTrain(new Train(number));
            //LOG (train was deleted)

            resp.sendRedirect("newTrip");
        } catch (DbException ex){
            ex.printStackTrace();
        }
    }
}
