package web.servlet.error_handler;

import org.junit.jupiter.api.Test;
import static org.mockito.Mockito.*;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

class ErrorHandlerServletTest {

    private final ErrorHandlerServlet servlet = new ErrorHandlerServlet();

    private final HttpServletRequest req = mock(HttpServletRequest.class);
    private final HttpServletResponse resp = mock(HttpServletResponse.class);


    @Test
    void doGet() throws ServletException, IOException {
        String forwardTo = "/WEB-INF/error_pages/500_error.jsp";
        RequestDispatcher dispatcher = mock(RequestDispatcher.class);

        when(req.getRequestDispatcher(forwardTo)).thenReturn(dispatcher);

        servlet.doGet(req, resp);

        verify(req, never()).getSession();
        verify(resp, never()).sendRedirect("errorHandler");

        verify(req, times(1)).getRequestDispatcher(forwardTo);
        verify(dispatcher, times(1)).forward(req, resp);
    }
}