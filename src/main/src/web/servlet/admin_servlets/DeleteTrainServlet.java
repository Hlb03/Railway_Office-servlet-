package web.servlet.admin_servlets;
/*
  User: admin
  Cur_date: 28.08.2022
  Cur_time: 13:09
*/

import dao.DbException;
import entity.Train;
import org.apache.logging.log4j.LogManager;
import service.TrainService;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@WebServlet("/deleteTrain")
public class DeleteTrainServlet extends HttpServlet {

    private static final org.apache.logging.log4j.Logger LOG = LogManager.getLogger(DeleteTrainServlet.class);

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        TrainService trainService = (TrainService) getServletContext().getAttribute("trainService");

        String number = req.getParameter("trainNumber");

        try{
            trainService.deleteTrain(new Train(number));
            LOG.trace("Train with number: " + number + " was deleted.");

            resp.sendRedirect("newTrip");
        } catch (DbException ex){
            LOG.debug(ex.getMessage(), ex);
            //resp.sendError(500);
        }
    }
}
