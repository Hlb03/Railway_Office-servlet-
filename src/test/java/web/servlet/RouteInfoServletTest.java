package web.servlet;

import dao.DbException;
import entity.Settlement;
import entity.Train;
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
import java.math.BigDecimal;
import java.sql.Date;
import java.sql.Time;
import java.util.HashMap;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.*;


class RouteInfoServletTest {

    private final RouteInfoServlet servlet = new RouteInfoServlet();

    private final HttpServletRequest req = mock(HttpServletRequest.class);
    private final HttpServletResponse resp = mock(HttpServletResponse.class);
    private final ServletContext context = mock(ServletContext.class);
    private final ServletConfig config = mock(ServletConfig.class);

    private final TripService tripService = mock(TripService.class);
    private final int tripId = 15;
    private final Trip trip = new Trip(Date.valueOf("2022-09-15"), Time.valueOf("12:53:31"), Date.valueOf("2022-09-15"),
            Time.valueOf("20:21:07"), 32, BigDecimal.valueOf(293.32), new Train(14));


    @BeforeEach
    void init() throws ServletException {
        trip.setStartStation("Kyiv");
        trip.setFinalStation("Kovel");

        servlet.init(config);

        when(req.getServletContext()).thenReturn(context);
        when(context.getAttribute("tripService")).thenReturn(tripService);
        when(req.getParameter("trip_id")).thenReturn(String.valueOf(tripId));
        when(req.getParameter("start")).thenReturn(trip.getStartStation());
        when(req.getParameter("depart")).thenReturn(trip.getDepartureDate().toString());
    }

    @Test
    void doGetPositiveScenario() throws ServletException, IOException, DbException {
        String forwardTo = "WEB-INF/jsp/ticket_view.jsp";
        String buyOpp = "false";

        Map<Integer, Settlement> tripHasSettlement = new HashMap<>();
        tripHasSettlement.put(1, new Settlement("Kyiv"));
        tripHasSettlement.put(2, new Settlement("Kovel"));

        RequestDispatcher dispatcher = mock(RequestDispatcher.class);

        when(tripService.getTrip(tripId)).thenReturn(trip);
        when(tripService.tripContainsSettlements(tripId)).thenReturn(tripHasSettlement);
        when(req.getRequestDispatcher(forwardTo)).thenReturn(dispatcher);

        doNothing().when(req).setAttribute("route", trip.getStartStation() + "--" + trip.getFinalStation());
        doNothing().when(req).setAttribute("trip", trip);
        doNothing().when(req).setAttribute("allStations", tripHasSettlement);

        doNothing().when(req).setAttribute("buyOpportunity", buyOpp);

        servlet.doGet(req, resp);

        verify(req, never()).getSession();

        verifyPart();

        verify(tripService, times(1)).getTrip(tripId);
        verify(tripService, times(1)).tripContainsSettlements(tripId);

        verify(req, times(1)).getRequestDispatcher(forwardTo);
        verify(dispatcher, times(1)).forward(req, resp);
    }

    @Test
    void doGetExceptionThrowingScenario() throws ServletException, IOException, DbException {
        String redirectTo = "errorHandler";
        HttpSession session = mock(HttpSession.class);

        doThrow(DbException.class).when(tripService).getTrip(tripId);
        when(req.getSession()).thenReturn(session);

        servlet.doGet(req, resp);

        verifyPart();
        verify(req, times(1)).getSession();
        verify(session, times(1)).setAttribute("errorMsg", null);
        verify(resp, times(1)).sendRedirect(redirectTo);

        assertThrows(DbException.class, () -> tripService.getTrip(tripId));
    }

    private void verifyPart() throws DbException {
        verify(req, times(1)).getServletContext();
        verify(context, times(1)).getAttribute("tripService");

        verify(req, times(1)).setAttribute("url",
                            "routeInfo?trip_id=15&start=Kyiv&depart=2022-09-15");
        verify(req, times(2)).getParameter("trip_id");
        verify(req, times(1)).getParameter("buy");

        verify(tripService, times(1)).getTrip(tripId);
    }
}