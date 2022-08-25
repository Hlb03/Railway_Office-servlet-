package web.servlet.admin_servlets;
/*
  User: admin
  Cur_date: 19.08.2022
  Cur_time: 12:00
*/

import dao.DbException;
import entity.Train;
import exception.FailedInsertException;
import service.TrainService;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@WebServlet("/newTrain")
public class NewTrainFormServlet extends HttpServlet {

    //Add some kind of info that train was (or wasn't created)
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        TrainService tService = (TrainService) req.getServletContext().getAttribute("trainService");

        String trainNumber = req.getParameter("trainNumber");


        //Add some interface "Entity" and make one util class that checks all needed
        try {
            tService.createTrain(new Train(trainNumber));
            resp.sendRedirect("menu");
        } catch (DbException ex){
            System.out.println("Can't add train in servlet");
        } catch (FailedInsertException e){
            throw new RuntimeException(e.getMessage());
        }
    }
}
