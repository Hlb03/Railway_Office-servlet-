package web.servlet;

import org.junit.jupiter.api.Test;
import static org.mockito.Mockito.*;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import java.io.IOException;

class SignInServletTest {

    private final SignInServlet signInServlet = new SignInServlet();

    @Test
    void doGet() throws ServletException, IOException {
        String forwardTo = "WEB-INF/jsp/sign_in.jsp";

        HttpServletRequest req = mock(HttpServletRequest.class);
        HttpServletResponse resp = mock(HttpServletResponse.class);
        RequestDispatcher dispatcher = mock(RequestDispatcher.class);

        when(req.getRequestDispatcher(forwardTo)).thenReturn(dispatcher);

        signInServlet.doGet(req, resp);

        verify(req, never()).getSession();
        verify(resp, never()).sendRedirect("authorize");
        verify(req, times(1)).getRequestDispatcher(forwardTo);
        verify(dispatcher, times(1)).forward(req, resp);
    }
}