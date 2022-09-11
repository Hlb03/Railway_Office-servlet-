package web.servlet.admin_servlets;

import dao.DbException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import service.TrainService;

import static org.mockito.Mockito.*;

import javax.servlet.ServletConfig;
import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;

import static org.junit.jupiter.api.Assertions.*;

class DeleteTrainServletTest {

    private final DeleteTrainServlet servlet = new DeleteTrainServlet();
    private final String trainNumber = "059A";

    private final HttpServletRequest req = mock(HttpServletRequest.class);
    private final HttpServletResponse resp = mock(HttpServletResponse.class);
    private final ServletContext context = mock(ServletContext.class);
    private final ServletConfig config = mock(ServletConfig.class);

    private final TrainService trainService = mock(TrainService.class);

    @BeforeEach
    void init() throws ServletException {
        servlet.init(config);

        when(servlet.getServletContext()).thenReturn(context);
        when(context.getAttribute("trainService")).thenReturn(trainService);

        when(req.getParameter("trainNumber")).thenReturn(trainNumber);
    }

    @Test
    void doPostPositiveScenario() throws ServletException, IOException, DbException {
        final String redirectTo = "newTrip";

        servlet.doPost(req, resp);

        verify(req, never()).getSession();
        verify(req, never()).getRequestDispatcher("WEB-INF/jsp/new_trip_form.jsp");

        verifyPart();

        verify(resp, times(1)).sendRedirect(redirectTo);
    }

    @Test
    void doPostExceptionThrowingScenario() throws ServletException, IOException, DbException {
        String redirectTo = "errorHandler";
        HttpSession session = mock(HttpSession.class);

        doThrow(DbException.class).when(trainService).deleteTrain(trainNumber);
        when(req.getSession()).thenReturn(session);

        servlet.doPost(req, resp);

        verifyPart();

        verify(req, times(1)).getSession();
        verify(session, times(1)).setAttribute("errorMsg", null);
        verify(resp, times(1)).sendRedirect(redirectTo);

        assertThrows(DbException.class, () -> trainService.deleteTrain(trainNumber));
    }

    private void verifyPart() throws DbException {
        verify(context, times(1)).getAttribute("trainService");
        verify(req, times(1)).getParameter("trainNumber");
        verify(trainService, times(1)).deleteTrain(trainNumber);
    }
}