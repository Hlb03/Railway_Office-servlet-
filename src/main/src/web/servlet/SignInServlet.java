package web.servlet;
/*
  User: admin
  Cur_date: 08.08.2022
  Cur_time: 13:04
*/

import org.apache.logging.log4j.LogManager;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@WebServlet("/signIn")
public class SignInServlet extends HttpServlet {

    private static final org.apache.logging.log4j.Logger LOG = LogManager.getLogger(SignInServlet.class);

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        LOG.trace("Forward to sign in form");
        req.getRequestDispatcher("WEB-INF/jsp/sign_in.jsp").forward(req, resp);
    }
}
