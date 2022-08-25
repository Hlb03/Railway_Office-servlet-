package web.servlet;

/*
  User: admin
  Cur_date: 07.08.2022
  Cur_time: 13:31
*/

import com.sun.org.slf4j.internal.Logger;
import com.sun.org.slf4j.internal.LoggerFactory;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@WebServlet("/registration")
public class RegistrationServlet extends HttpServlet {

    private final static Logger LOG = LoggerFactory.getLogger(RegistrationServlet.class);

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        LOG.debug("WelcomeServlet#doGet");
        LOG.debug("Forwarding to registration jsp");

        req.getRequestDispatcher("/WEB-INF/jsp/registration.jsp").forward(req, resp);

    }
}
