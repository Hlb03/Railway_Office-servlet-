package web.servlet.admin_servlets;

import dao.DbException;
import entity.Trip;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import service.TripService;

import static org.mockito.Mockito.*;

import javax.servlet.ServletConfig;
import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import java.io.IOException;

import static org.junit.jupiter.api.Assertions.*;

class DeleteTripServletTest {

    private final DeleteTripServlet servlet = new DeleteTripServlet();
    private final HttpServletRequest req = mock(HttpServletRequest.class);
    private final HttpServletResponse resp = mock(HttpServletResponse.class);
    private final ServletContext context = mock(ServletContext.class);
    private final ServletConfig config = mock(ServletConfig.class);

    private final TripService tripService = mock(TripService.class);

    private final Trip trip = new Trip(23);


    @BeforeEach
    void init() throws ServletException {
        trip.setStartStation("Kherson");
        trip.setFinalStation("Konotop");

        servlet.init(config);

        when(servlet.getServletContext()).thenReturn(context);
        when(context.getAttribute("tripService")).thenReturn(tripService);

        when(req.getParameter("tripId")).thenReturn(String.valueOf(trip.getId()));
    }

    @Test
    void doPostPositiveScenario() throws ServletException, IOException, DbException {
        String redirectTo = "menu";

        when(tripService.getTrip(trip.getId())).thenReturn(trip);

        servlet.doPost(req, resp);

        verify(req, never()).getSession();
        verify(req, never()).getRequestDispatcher("WEB-INF/jsp/menu.jsp");

        verifyPart();

        verify(tripService, times(1)).deleteTrip(trip);

        verify(resp, times(1)).sendRedirect(redirectTo);
    }

    @Test
    void doPostExceptionThrowingScenario() throws ServletException, IOException, DbException {
        String redirectTo = "errorHandler";

        HttpSession session = mock(HttpSession.class);

        doThrow(DbException.class).when(tripService).getTrip(trip.getId());
        when(req.getSession()).thenReturn(session);

        servlet.doPost(req, resp);

        verify(req, times(1)).getSession();

        verifyPart();

        verify(session, times(1)).setAttribute("errorMsg", null);
        verify(resp, times(1)).sendRedirect(redirectTo);

        assertThrows(DbException.class, () -> tripService.getTrip(trip.getId()));
    }

    private void verifyPart() throws DbException {
        verify(context, times(1)).getAttribute("tripService");
        verify(req, times(1)).getParameter("tripId");
        verify(tripService, times(1)).getTrip(trip.getId());
    }
}