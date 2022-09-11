package web.servlet;

import dao.DbException;
import entity.Trip;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import service.TripService;

import static org.mockito.Mockito.*;

import javax.servlet.*;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import java.io.IOException;
import java.sql.Date;
import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;


class SearchTripServletTest {

    private final SearchTripServlet servlet = new SearchTripServlet();

    private final HttpServletRequest req = mock(HttpServletRequest.class);
    private final HttpServletResponse resp = mock(HttpServletResponse.class);
    private final ServletContext context = mock(ServletContext.class);
    private final ServletConfig config = mock(ServletConfig.class);
    private final TripService tripService = mock(TripService.class);
    private final String startStation = "Kyiv";
    private final String finalStation = "Lviv";
    private final int currentPage = 1;
    private final int recordsPerPage = 5;

    @BeforeEach
    void init() throws ServletException {
        servlet.init(config);

        when(servlet.getServletContext()).thenReturn(context);
        when(context.getAttribute("tripService")).thenReturn(tripService);

        when(req.getParameter("from")).thenReturn(startStation);
        when(req.getParameter("to")).thenReturn(finalStation);
    }

    @Test
    void doGetTestSearchWithNoDate() throws ServletException, IOException, DbException {
        String forwardTo = "WEB-INF/jsp/menu.jsp";
        String date = "";
        int amountOfData = 9;

        List<Trip> tripsFromSearch = Arrays.asList(new Trip(1), new Trip(15), new Trip(27));

        RequestDispatcher dispatcher = mock(RequestDispatcher.class);

        when(req.getParameter("date")).thenReturn(date);

        when(tripService.getByRoute(startStation, finalStation, currentPage, recordsPerPage)).thenReturn(tripsFromSearch);
        when(tripService.amountOfFoundTripsByRoute(startStation, finalStation)).thenReturn(amountOfData);

        when(req.getRequestDispatcher(forwardTo)).thenReturn(dispatcher);

        servlet.doGet(req, resp);

        verify(req, never()).getSession();
        verify(resp, never()).sendRedirect("menu");

        verifyPart();

        verify(req, times(1)).setAttribute("tripsFromSearch", tripsFromSearch);
        verify(req, times(1)).setAttribute("from", startStation);
        verify(req, times(1)).setAttribute("to", finalStation);
        verify(req, times(1)).setAttribute("date", date);

        verify(req, times(1)).setAttribute("pagesAmount", 2);
        verify(req, times(1)).setAttribute("currentPage", currentPage);
        verify(req, times(1)).setAttribute("recordsPerPage", recordsPerPage);
    }

    @Test
    void doGetTestSearchWithDatePresence() throws ServletException, IOException, DbException {
        String forwardTo = "WEB-INF/jsp/menu.jsp";
        String date = "2022-09-21";

        List<Trip> tripsFromSearch = Arrays.asList(new Trip(1), new Trip(15), new Trip(27));

        RequestDispatcher dispatcher = mock(RequestDispatcher.class);

        servlet.init(config);

        when(servlet.getServletContext()).thenReturn(context);
        when(context.getAttribute("tripService")).thenReturn(tripService);

        when(req.getParameter("from")).thenReturn(startStation);
        when(req.getParameter("to")).thenReturn(finalStation);
        when(req.getParameter("date")).thenReturn(date);

        when(tripService.getByRouteAndDate(startStation, finalStation, Date.valueOf(date))).thenReturn(tripsFromSearch);

        when(req.getRequestDispatcher(forwardTo)).thenReturn(dispatcher);

        servlet.doGet(req, resp);

        verify(req, never()).getSession();
        verify(resp, never()).sendRedirect("menu");

        verifyPart();

        verify(req, times(1)).setAttribute("tripsFromSearch", tripsFromSearch);
        verify(req, times(1)).setAttribute("from", startStation);
        verify(req, times(1)).setAttribute("to", finalStation);
        verify(req, times(1)).setAttribute("date", date);

        verify(req, times(1)).setAttribute("pagesAmount", 0);
        verify(req, times(1)).setAttribute("currentPage", currentPage);
        verify(req, times(1)).setAttribute("recordsPerPage", recordsPerPage);
    }

    @Test
    void doGetExceptionThrowingScenario() throws ServletException, IOException, DbException {
        String redirectTo = "errorHandler";
        String date = "";
        HttpSession session = mock(HttpSession.class);

        when(req.getParameter("date")).thenReturn(date);
        doThrow(DbException.class).when(tripService).getByRoute(startStation, finalStation, currentPage, recordsPerPage);
        when(req.getSession()).thenReturn(session);

        servlet.doGet(req, resp);

        verifyPart();

        verify(req, times(1)).getSession();
        verify(session, times(1)).setAttribute("errorMsg", null);
        verify(resp, times(1)).sendRedirect(redirectTo);

        assertThrows(DbException.class, () -> tripService.getByRoute(startStation, finalStation, currentPage, recordsPerPage));
    }

    private void verifyPart() {
        verify(context, times(1)).getAttribute("tripService");

        verify(req, times(1)).getParameter("from");
        verify(req, times(1)).getParameter("to");
        verify(req, times(1)).getParameter("date");
    }

}