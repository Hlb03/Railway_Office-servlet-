package web.servlet;

import dao.DbException;
import entity.Trip;
import entity.User;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import service.TripService;
import service.UserService;

import static org.mockito.Mockito.*;

import javax.servlet.ServletConfig;
import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import java.io.IOException;
import java.math.BigDecimal;
import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class BuyTicketServletTest {

    private final BuyTicketServlet servlet = new BuyTicketServlet();

    private final HttpServletRequest req = mock(HttpServletRequest.class);
    private final HttpServletResponse resp = mock(HttpServletResponse.class);
    private final HttpSession session = mock(HttpSession.class);

    private final ServletContext context = mock(ServletContext.class);
    private final ServletConfig config = mock(ServletConfig.class);

    private final TripService tripService = mock(TripService.class);
    private final UserService userService = mock(UserService.class);

    private final User user = new User("test@gmail.com", "password", "Name", "Surname");


    @BeforeEach
    void init() throws ServletException {
        user.setId(3);
        user.setBalance(BigDecimal.valueOf(315.06));

        servlet.init(config);

        when(servlet.getServletContext()).thenReturn(context);
        when(context.getAttribute("tripService")).thenReturn(tripService);
        when(context.getAttribute("userService")).thenReturn(userService);

        when(req.getSession()).thenReturn(session);
        when(req.getParameter("ticketAmount")).thenReturn(String.valueOf(3));
        when(req.getParameter("tripCost")).thenReturn(user.getBalance().toString());
        when(session.getAttribute("userId")).thenReturn(user.getId());
    }

    @Test
    void doPostTestIfUserHasNotSuchTicket() throws ServletException, IOException, DbException {
        int tripId = 5;
        List<Trip> allTrips = Arrays.asList(new Trip(3), new Trip(10), new Trip(17));

        buyTicketTest(tripId, allTrips);
    }

    @Test
    void doPostTestIfUserAlreadyHasSuchTicket() throws ServletException, IOException, DbException {
        int tripId = 2;
        List<Trip> allTrips = Arrays.asList(new Trip(31), new Trip(15), new Trip(2), new Trip(15), new Trip(37));

        buyTicketTest(tripId, allTrips);
    }

    @Test
    void doPostExceptionThrowingScenario() throws ServletException, IOException, DbException {
        String redirectTo = "errorHandler";

        when(session.getAttribute("userLogin")).thenReturn(user.getLogin());
        when(req.getParameter("tripId")).thenReturn("4");
        doThrow(DbException.class).when(userService).getByLogin(user.getLogin());

        servlet.doPost(req, resp);

        verifyPart();

        verify(req, times(3)).getSession();
        verify(session, times(1)).setAttribute("errorMsg", null);
        verify(resp, times(1)).sendRedirect(redirectTo);

        assertThrows(DbException.class, () -> userService.getByLogin(user.getLogin()));
    }

    private void buyTicketTest(int tripId, List<Trip> allTrips) throws ServletException, IOException, DbException {
        String redirectTo = "menu";

        int tripAmount = 25;

        when(req.getParameter("tripId")).thenReturn(String.valueOf(tripId));

        when(userService.getByLogin(user.getLogin())).thenReturn(user);
        when(tripService.getTripsAmount()).thenReturn(tripAmount);
        when(tripService.userHasTrips(user.getId(), 1, tripAmount)).thenReturn(allTrips);

        when(session.getAttribute("userLogin")).thenReturn(user.getLogin());

        when(userService.totalAmountOfUserTrips(user)).thenReturn(allTrips.size());

        servlet.doPost(req, resp);

        verify(req, never()).getRequestDispatcher("WEB-INF/jsp/menu.jsp");

        verifyPart();

        verify(session, times(1)).setAttribute("balance", user.getBalance());
        verify(session, times(1)).setAttribute("userTrips", allTrips.size());

        verify(req, times(4)).getSession();
        verify(resp, times(1)).sendRedirect(redirectTo);
    }

    private void verifyPart() throws DbException {
        verify(req, times(1)).getParameter("tripCost");
        verify(req, times(1)).getParameter("tripId");
        verify(req, times(1)).getParameter("ticketAmount");

        verify(session, times(1)).getAttribute("userId");
        verify(session, times(1)).getAttribute("userLogin");

        verify(userService, times(1)).getByLogin(user.getLogin());
    }
}