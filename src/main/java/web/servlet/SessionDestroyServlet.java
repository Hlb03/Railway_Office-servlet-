package web.servlet;
/*
  User: admin
  Cur_date: 10.08.2022
  Cur_time: 11:44
*/

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@WebServlet("/signOut")
public class SessionDestroyServlet extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        System.out.println("SessionDestroy#doGet");
        System.out.println("Destroying session");
        req.getSession().invalidate();

        System.out.println("Redirect to /signIn");
        resp.sendRedirect("signIn");
    }
}

//org.apache.catalina.session.StandardSessionFacade@764a584f
//org.apache.catalina.session.StandardSessionFacade@3cdb6fb9