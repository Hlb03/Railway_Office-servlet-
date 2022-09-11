package web.servlet;

import org.junit.jupiter.api.Test;
import static org.mockito.Mockito.*;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import java.io.IOException;

class RegistrationServletTest {

    private final RegistrationServlet registrationServlet = new RegistrationServlet();

    @Test
    void doGet() throws ServletException, IOException {
        String forwardPath = "/WEB-INF/jsp/registration.jsp";

        HttpServletRequest req = mock(HttpServletRequest.class);
        HttpServletResponse resp = mock(HttpServletResponse.class);
        RequestDispatcher dispatcher = mock(RequestDispatcher.class);

        when(req.getRequestDispatcher(forwardPath)).thenReturn(dispatcher);

        registrationServlet.doGet(req, resp);

        verify(req, never()).getSession();
        verify(resp, never()).sendRedirect("newUser");
        verify(req, times(1)).getRequestDispatcher(forwardPath);
        verify(dispatcher, times(1)).forward(req, resp);
    }
}