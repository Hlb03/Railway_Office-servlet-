package web.servlet.admin_servlets;

import dao.DbException;
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


class DeleteAllOutdatedTripsServletTest {

    private final DeleteAllOutdatedTripsServlet servlet = new DeleteAllOutdatedTripsServlet();

    private final HttpServletRequest req = mock(HttpServletRequest.class);
    private final HttpServletResponse resp = mock(HttpServletResponse.class);
    private final ServletContext context = mock(ServletContext.class);
    private final ServletConfig config = mock(ServletConfig.class);

    private final TripService tripService = mock(TripService.class);

    @BeforeEach
    void init() throws ServletException {
        servlet.init(config);

        when(servlet.getServletContext()).thenReturn(context);
        when(context.getAttribute("tripService")).thenReturn(tripService);
    }

    @Test
    void doPostPositiveScenario() throws ServletException, IOException, DbException {
        String redirectTo = "menu";

        doNothing().when(tripService).deleteAllOutdatedTrips();

        servlet.doPost(req, resp);

        verify(req, never()).getSession();
        verify(req, never()).getRequestDispatcher("WEB-INF/jsp/new_trip_form.jsp");

        verifyPart();

        verify(resp, times(1)).sendRedirect(redirectTo);
    }

    @Test
    void doPostNegativeScenario() throws ServletException, IOException, DbException {
        String redirectTo = "errorHandler";
        HttpSession session = mock(HttpSession.class);

        doThrow(DbException.class).when(tripService).deleteAllOutdatedTrips();
        when(req.getSession()).thenReturn(session);

        servlet.doPost(req, resp);

        verify(resp, never()).sendRedirect("menu");

        verifyPart();

        verify(req, times(1)).getSession();
        verify(session, times(1)).setAttribute("errorMsg", null);
        verify(resp, times(1)).sendRedirect(redirectTo);

        assertThrows(DbException.class, tripService::deleteAllOutdatedTrips);
    }

    private void verifyPart() throws DbException {
        verify(context, times(1)).getAttribute("tripService");
        verify(tripService, times(1)).deleteAllOutdatedTrips();
    }
}