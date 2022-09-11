package web.servlet.admin_servlets;

import dao.DbException;
import entity.Train;
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
import java.math.BigDecimal;
import java.sql.Date;
import java.sql.Time;

import static org.junit.jupiter.api.Assertions.*;


class EditTripServletTest {

    private final EditTripServlet servlet = new EditTripServlet();

    private final String depDate = "2022-09-11";
    private final String depTime = "09:25:18";
    private final String arrDate = "2022-09-11";
    private final String arrTime = "16:57:46";
    private final String train = "099A";
    private final String seats = "24";
    private final String price = "219.99";
    private final String tripId = "28";

    private final Trip t = new Trip(Integer.parseInt(tripId));
    private final Trip trip = new Trip(Date.valueOf(depDate), Time.valueOf(depTime), Date.valueOf(arrDate),
            Time.valueOf(arrTime), Integer.parseInt(seats),
            BigDecimal.valueOf(Double.parseDouble(price)), new Train(train));

    private final HttpServletRequest req = mock(HttpServletRequest.class);
    private final HttpServletResponse resp = mock(HttpServletResponse.class);
    private final ServletContext context = mock(ServletContext.class);
    private final ServletConfig config = mock(ServletConfig.class);

    private final TripService tripService = mock(TripService.class);

    @BeforeEach
    void init() throws ServletException, DbException {
        trip.setId(Integer.parseInt(tripId));

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
        when(req.getParameter("tripId")).thenReturn(tripId);

    }

    @Test
    void doPostTestWithUpdateOnlyTripParameters() throws ServletException, IOException, DbException {
        String redirectTo = "menu";

        when(tripService.getTrip(t.getId())).thenReturn(t);

        servlet.doPost(req, resp);

        verify(req, never()).getSession();
        verify(req, never()).getRequestDispatcher("WEB-INF/jsp/edit_form.jsp");

        verifyPart();

        verify(tripService, times(1)).getTrip(t.getId());
        verify(tripService, times(1)).updateTripParameters(trip);

        verify(resp, times(1)).sendRedirect(redirectTo);
    }

    @Test
    void doPostTestWithUpdateTripParamsAndStations() throws ServletException, IOException, DbException {
        String redirectTo = "menu";
        int[] tripsSettlements = {12, 11, 18, 19, 3};

        when(tripService.getTrip(t.getId())).thenReturn(t);

        when(req.getParameter("settlement1")).thenReturn(String.valueOf(tripsSettlements[0]));
        when(req.getParameter("stationsAmount")).thenReturn(String.valueOf(tripsSettlements.length));

        when(req.getParameter("settlement1")).thenReturn(String.valueOf(tripsSettlements[0]));
        when(req.getParameter("settlement2")).thenReturn(String.valueOf(tripsSettlements[1]));
        when(req.getParameter("settlement3")).thenReturn(String.valueOf(tripsSettlements[2]));
        when(req.getParameter("settlement4")).thenReturn(String.valueOf(tripsSettlements[3]));
        when(req.getParameter("settlement5")).thenReturn(String.valueOf(tripsSettlements[4]));

        doNothing().when(tripService).updateAllTripInfo(trip, tripsSettlements);

        servlet.doPost(req, resp);

        verify(req, never()).getSession();
        verify(req, never()).getRequestDispatcher("WEB-INF/jsp/edit_form.jsp");

        verifyPart();

        verify(req, times(3)).getParameter("settlement1");
        verify(req, times(1)).getParameter("settlement2");
        verify(req, times(1)).getParameter("settlement3");
        verify(req, times(1)).getParameter("settlement4");
        verify(req, times(1)).getParameter("settlement5");

        verify(tripService, times(1)).updateAllTripInfo(trip, tripsSettlements);

        verify(resp, times(1)).sendRedirect(redirectTo);
    }

    @Test
    void doPostTestWithExceptionThrowing() throws ServletException, IOException, DbException {
        String redirectTo = "errorHandler";
        HttpSession session = mock(HttpSession.class);

        doThrow(DbException.class).when(tripService).getTrip(t.getId());
        when(req.getSession()).thenReturn(session);

        servlet.doPost(req, resp);

        verifyPart();

        verify(req, times(1)).getSession();
        verify(session, times(1)).setAttribute("errorMsg", null);
        verify(resp, times(1)).sendRedirect(redirectTo);

        assertThrows(DbException.class, () -> tripService.getTrip(t.getId()));
    }

    private void verifyPart() throws DbException {
        verify(req, times(1)).getServletContext();
        verify(req, times(1)).getParameter("depDate");
        verify(req, times(1)).getParameter("depTime");
        verify(req, times(1)).getParameter("arrDate");
        verify(req, times(1)).getParameter("arrTime");
        verify(req, times(1)).getParameter("train");
        verify(req, times(1)).getParameter("seats");
        verify(req, times(1)).getParameter("price");
        verify(req, times(1)).getParameter("tripId");

        verify(tripService, times(1)).getTrip(t.getId());
    }
}