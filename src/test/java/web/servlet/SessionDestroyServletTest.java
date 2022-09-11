package web.servlet;

import org.junit.jupiter.api.Test;
import static org.mockito.Mockito.*;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import java.io.IOException;

class SessionDestroyServletTest {

    private final SessionDestroyServlet destroyServlet = new SessionDestroyServlet();

    @Test
    void doGet() throws ServletException, IOException {
        final String redirectTo = "signIn";

        HttpServletRequest req = mock(HttpServletRequest.class);
        HttpServletResponse resp = mock(HttpServletResponse.class);
        HttpSession session = mock(HttpSession.class);

        when(req.getSession()).thenReturn(session);
        when(session.getAttribute("userLogin")).thenReturn("testCase@ukr.net");

        destroyServlet.doGet(req, resp);

        verify(session, times(1)).invalidate();
        verify(session, times(1)).getAttribute("userLogin");
        verify(req, times(2)).getSession();
        verify(resp, never()).sendRedirect("WEB-INF/jsp/sing_in.jsp");
        verify(resp, times(1)).sendRedirect(redirectTo);
    }
}