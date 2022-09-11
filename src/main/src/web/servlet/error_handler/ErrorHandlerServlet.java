package web.servlet.error_handler;
/*
  User: admin
  Cur_date: 07.09.2022
  Cur_time: 20:00
*/

import org.apache.logging.log4j.LogManager;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@WebServlet("/errorHandler")
public class ErrorHandlerServlet extends HttpServlet {

    private static final org.apache.logging.log4j.Logger LOG = LogManager.getLogger(ErrorHandlerServlet.class);

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        LOG.trace("Exception occurs. Redirecting to custom 500 error page");
        req.getRequestDispatcher("/WEB-INF/error_pages/500_error.jsp").forward(req, resp);
    }
}
