package web.servlet.admin_servlets;

import dao.DbException;
import entity.Settlement;
import entity.Train;
import entity.Trip;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import service.SettlementService;
import service.TrainService;
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
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.*;


class ForwardToEditPageServletTest {

    private final ForwardToEditPageServlet servlet = new ForwardToEditPageServlet();

    private final HttpServletRequest req = mock(HttpServletRequest.class);
    private final HttpServletResponse resp = mock(HttpServletResponse.class);
    private final ServletContext context = mock(ServletContext.class);
    private final ServletConfig config = mock(ServletConfig.class);
    private final RequestDispatcher dispatcher = mock(RequestDispatcher.class);

    private final TripService tripService = mock(TripService.class);
    private final SettlementService settlementService = mock(SettlementService.class);
    private final TrainService trainService = mock(TrainService.class);

    private final Trip trip = new Trip(18);

    @BeforeEach
    void init() throws ServletException {
        servlet.init(config);

        when(servlet.getServletContext()).thenReturn(context);
        when(context.getAttribute("tripService")).thenReturn(tripService);
        when(context.getAttribute("settlementService")).thenReturn(settlementService);
        when(context.getAttribute("trainService")).thenReturn(trainService);

        when(req.getParameter("tripId")).thenReturn(String.valueOf(trip.getId()));
    }

    @Test
    void doGetPositiveScenario() throws ServletException, IOException, DbException {
        String forwardTo = "WEB-INF/jsp/edit_form.jsp";
        trip.setStartStation("Chernivtsi");
        trip.setFinalStation("Rivne");

        Map<Integer, Settlement> tripHasSettlement = new HashMap<>();
        tripHasSettlement.put(1, new Settlement("Chernivtsi"));
        tripHasSettlement.put(2, new Settlement("Lviv"));
        tripHasSettlement.put(3, new Settlement("Rivne"));

        List<Settlement> allSettlements = Arrays.asList(
                new Settlement("Rivne"), new Settlement("Lviv"),
                new Settlement("Ivano-Frankivsk"), new Settlement("Chernivtsi")
        );

        List<Train> allTrains = Arrays.asList(
                new Train("001A"), new Train("075Д")
        );

        when(tripService.getTrip(trip.getId())).thenReturn(trip);
        when(tripService.tripContainsSettlements(trip.getId())).thenReturn(tripHasSettlement);
        when(settlementService.getSettlements()).thenReturn(allSettlements);
        when(trainService.getTrains()).thenReturn(allTrains);

        when(req.getRequestDispatcher(forwardTo)).thenReturn(dispatcher);

        servlet.doGet(req, resp);

        verify(req, never()).getSession();
        verify(resp, never()).sendRedirect("editTrip");

        verifyPart();

        verify(tripService, times(1)).tripContainsSettlements(trip.getId());
        verify(settlementService, times(1)).getSettlements();
        verify(trainService, times(1)).getTrains();

        verify(req, times(1)).setAttribute("allTrains", allTrains);
        verify(req, times(1)).setAttribute("allSettlements", allSettlements);
        verify(req, times(1)).setAttribute("route",
                                            trip.getStartStation() + " -- " + trip.getFinalStation());
        verify(req, times(1)).setAttribute("trip", trip);
        verify(req, times(1)).setAttribute("allStation", tripHasSettlement);

        verify(req, times(1)).getRequestDispatcher(forwardTo);
        verify(dispatcher, times(1)).forward(req, resp);
    }

    @Test
    void doGetExceptionThrowingScenario() throws ServletException, IOException, DbException {
        String redirectTo = "errorHandler";
        HttpSession session = mock(HttpSession.class);

        doThrow(DbException.class).when(tripService).getTrip(trip.getId());
        when(req.getSession()).thenReturn(session);

        servlet.doGet(req, resp);

        verifyPart();

        verify(req, times(1)).getSession();
        verify(session, times(1)).setAttribute("errorMsg", null);
        verify(resp, times(1)).sendRedirect(redirectTo);

        assertThrows(DbException.class, () -> tripService.getTrip(trip.getId()));
    }

    private void verifyPart() throws DbException {
        verify(context, times(1)).getAttribute("tripService");
        verify(context, times(1)).getAttribute("settlementService");
        verify(context, times(1)).getAttribute("trainService");

        verify(req, times(1)).getParameter("tripId");
        verify(req, times(1)).setAttribute("url", "editTrip?tripId=" + trip.getId());

        verify(tripService, times(1)).getTrip(trip.getId());
    }
}