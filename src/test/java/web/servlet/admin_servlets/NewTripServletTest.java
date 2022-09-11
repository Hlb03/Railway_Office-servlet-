package web.servlet.admin_servlets;

import dao.DbException;
import entity.Train;
import entity.Trip;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import service.TripService;

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

import static org.mockito.Mockito.*;

import static org.junit.jupiter.api.Assertions.*;


class NewTripServletTest {

    private final NewTripServlet servlet = new NewTripServlet();

    private final String depDate = "2022-09-11";
    private final String depTime = "12:08:33";
    private final String arrDate = "2022-09-11";
    private final String arrTime = "19:20:21";
    private final String train = "25";
    private final String seats = "55";
    private final String price = "318.52";

    private final Trip trip = new Trip(Date.valueOf(depDate), Time.valueOf(depTime), Date.valueOf(arrDate),
            Time.valueOf(arrTime), Integer.parseInt(seats),
            BigDecimal.valueOf(Double.parseDouble(price)), new Train(Integer.parseInt(train)));

    private final int[] stations = {11, 3, 28, 13};

    private final HttpServletRequest req = mock(HttpServletRequest.class);
    private final HttpServletResponse resp = mock(HttpServletResponse.class);
    private final ServletContext context = mock(ServletContext.class);
    private final ServletConfig config = mock(ServletConfig.class);

    private final TripService tripService = mock(TripService.class);

    @BeforeEach
    void init() throws ServletException {
        servlet.init(config);

        when(req.getServletContext()).thenReturn(context);
        when(context.getAttribute("tripService")).thenReturn(tripService);

        when(req.getParameter("depDate")).thenReturn(depDate);
        when(req.getParameter("depTime")).thenReturn(depTime);
        when(req.getParameter("arrDate")).thenReturn(arrDate);
        when(req.getParameter("arrTime")).thenReturn(arrTime);
        when(req.getParameter("train")).thenReturn(train);
        when(req.getParameter("seats")).thenReturn(seats);
        when(req.getParameter("price")).thenReturn(price);

        when(req.getParameter("stationsAmount")).thenReturn(String.valueOf(stations.length));
        when(req.getParameter("settlement1")).thenReturn(String.valueOf(stations[0]));
        when(req.getParameter("settlement2")).thenReturn(String.valueOf(stations[1]));
        when(req.getParameter("settlement3")).thenReturn(String.valueOf(stations[2]));
        when(req.getParameter("settlement4")).thenReturn(String.valueOf(stations[3]));
    }

    @Test
    void doPostPositiveScenario() throws ServletException, IOException, DbException {
        String redirectTo = "menu";

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

        doThrow(DbException.class).when(tripService).createTrip(trip, stations[0], stations[stations.length - 1],stations);
        when(req.getSession()).thenReturn(session);

        servlet.doPost(req, resp);

        verifyPart();

        verify(req, times(1)).getSession();
        verify(session, times(1)).setAttribute("errorMsg", null);
        verify(resp, times(1)).sendRedirect(redirectTo);

        assertThrows(DbException.class,
                () -> tripService.createTrip(trip, stations[0], stations[stations.length - 1], stations));
    }

    private void verifyPart() throws DbException {
        verify(req, times(1)).getServletContext();
        verify(context, times(1)).getAttribute("tripService");

        verify(req, times(1)).getParameter("depDate");
        verify(req, times(1)).getParameter("depTime");
        verify(req, times(1)).getParameter("arrDate");
        verify(req, times(1)).getParameter("arrTime");
        verify(req, times(1)).getParameter("seats");
        verify(req, times(1)).getParameter("train");
        verify(req, times(1)).getParameter("price");

        verify(req, times(1)).getParameter("settlement1");
        verify(req, times(1)).getParameter("settlement2");
        verify(req, times(1)).getParameter("settlement3");
        verify(req, times(1)).getParameter("settlement4");

        verify(tripService, times(1))
                .createTrip(trip, stations[0], stations[stations.length - 1], stations);
    }
}