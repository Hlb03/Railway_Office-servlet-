package web.servlet;

import dao.DbException;
import entity.Trip;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import service.TripService;

import static org.mockito.Mockito.*;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletConfig;
import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import java.io.IOException;
import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class MainMenuServletTest {

    private final MainMenuServlet servlet = new MainMenuServlet();

    private final HttpServletRequest req = mock(HttpServletRequest.class);
    private final HttpServletResponse resp = mock(HttpServletResponse.class);
    private final ServletContext context =  mock(ServletContext.class);
    private final ServletConfig config = mock(ServletConfig.class);
    private final TripService tripService = mock(TripService.class);
    private final int currentPage = 1;
    private final int recordsPerPage = 5;

    @BeforeEach
    void init() throws ServletException {
        servlet.init(config);

        when(servlet.getServletContext()).thenReturn(context);
        when(context.getAttribute("tripService")).thenReturn(tripService);
    }

    @Test
    void doGetPositiveScenario() throws ServletException, IOException, DbException {
        String forwardTo = "WEB-INF/jsp/menu.jsp";
        int totalAmountOfTrips = 24;
        int pages = 5;

        List<Trip> allTrips = Arrays.asList(
                new Trip(1), new Trip(2), new Trip(3), new Trip(4), new Trip(5)
        );

        RequestDispatcher dispatcher = mock(RequestDispatcher.class);

        when(tripService.getTripsAmount()).thenReturn(totalAmountOfTrips);
        when(tripService.getTrips(currentPage, recordsPerPage)).thenReturn(allTrips);

        when(req.getRequestDispatcher(forwardTo)).thenReturn(dispatcher);

        servlet.doGet(req, resp);

        verify(req, never()).getSession();
        verify(resp, never()).sendRedirect("menu");

        verifyPart();

        verify(req, times(1)).setAttribute("pagesAmount", pages);
        verify(req, times(1)).setAttribute("recordsPerPage", recordsPerPage);
        verify(req, times(1)).setAttribute("currentPage", currentPage);
        verify(dispatcher, times(1)).forward(req, resp);
    }

    @Test
    void doGetExceptionThrowingScenario() throws ServletException, IOException, DbException {
        String redirectTo = "errorHandler";
        HttpSession session = mock(HttpSession.class);

        doThrow(DbException.class).when(tripService).getTrips(currentPage, recordsPerPage);
        when(req.getSession()).thenReturn(session);

        servlet.doGet(req, resp);

        verifyPart();

        verify(req, times(1)).getSession();
        verify(session, times(1)).setAttribute("errorMsg", null);
        verify(resp, times(1)).sendRedirect(redirectTo);

        assertThrows(DbException.class, () -> tripService.getTrips(currentPage, recordsPerPage));
    }

    private void verifyPart() throws DbException {
        verify(context, times(1)).getAttribute("tripService");

        verify(tripService, times(1)).getTrips(currentPage, recordsPerPage);
    }
}