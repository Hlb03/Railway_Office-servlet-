package web.servlet.admin_servlets;
/*
  User: admin
  Cur_date: 19.08.2022
  Cur_time: 12:00
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

@WebServlet("/newTrain")
public class NewTrainServlet extends HttpServlet {

    //What will happen if admin will add already existed train number??? -- fix
    //Add some kind of info that train was (or wasn't created)
    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        TrainService tService = (TrainService) req.getServletContext().getAttribute("trainService");

        String trainNumber = req.getParameter("trainNumber");
        System.out.println(trainNumber + " train was created");

        try {
            tService.createTrain(new Train(trainNumber));

            resp.sendRedirect("newTrip");
        } catch (DbException ex){
            System.out.println("Can't add train in servlet");
            ex.printStackTrace();
        }
    }
}
