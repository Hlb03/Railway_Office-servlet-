package web.servlet;

import dao.DbException;
import entity.Trip;
import entity.User;
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


class UserTripInfoServletTest {

    private final UserTripInfoServlet servlet = new UserTripInfoServlet();

    private final HttpServletRequest req = mock(HttpServletRequest.class);
    private final HttpServletResponse resp = mock(HttpServletResponse.class);
    private final HttpSession session = mock(HttpSession.class);
    private final ServletContext context = mock(ServletContext.class);
    private final ServletConfig config = mock(ServletConfig.class);
    private final TripService tripService = mock(TripService.class);
    private final User user = new User(13);

    @BeforeEach
    void init() throws ServletException {
        servlet.init(config);

        when(servlet.getServletContext()).thenReturn(context);
        when(context.getAttribute("tripService")).thenReturn(tripService);
        when(req.getSession()).thenReturn(session);

        when(session.getAttribute("userId")).thenReturn(user.getId());
    }

    @Test
    void doGetPositiveScenario() throws ServletException, IOException, DbException {
        String forwardTo = "WEB-INF/jsp/userBasket.jsp";
        int currentPage = 1;
        int recordsPerPage = 5;
        int amountOfUsersTrips = 7;
        user.setLogin("testTripInfo@gmail.com");
        List<Trip> userAlreadyBoughtTrips = Arrays.asList(
                new Trip(13), new Trip(7), new Trip(51), new Trip(9)
        );

        RequestDispatcher dispatcher = mock(RequestDispatcher.class);

        when(tripService.userHasTripsAmount(user.getId())).thenReturn(amountOfUsersTrips);
        when(tripService.userHasTrips(user.getId(), currentPage, recordsPerPage)).thenReturn(userAlreadyBoughtTrips);

        when(session.getAttribute("userLogin")).thenReturn(user.getLogin());
        when(req.getRequestDispatcher(forwardTo)).thenReturn(dispatcher);

        servlet.doGet(req, resp);

        verify(resp, never()).sendRedirect("userBasketInfo");

        verifyPart();

        verify(session, times(1)).getAttribute("userLogin");

        verify(tripService, times(1)).userHasTrips(user.getId(), currentPage, recordsPerPage);

        verify(req, times(1)).setAttribute("pagesAmount", 2);
        verify(req, times(1)).setAttribute("recordsPerPage", recordsPerPage);
        verify(req, times(1)).setAttribute("currentPage", currentPage);
        verify(req, times(1)).setAttribute("userBoughtTrips", userAlreadyBoughtTrips);
    }

    @Test
    void doGetExceptionThrowingScenario() throws ServletException, IOException, DbException {
        String redirectTo = "errorHandler";

        doThrow(DbException.class).when(tripService).userHasTripsAmount(user.getId());

        servlet.doGet(req, resp);

        verifyPart();
//        verify(req, times(2)).getSession();
        verify(session, times(1)).setAttribute("errorMsg", null);
        verify(resp, times(1)).sendRedirect(redirectTo);

        assertThrows(DbException.class, () -> tripService.userHasTripsAmount(user.getId()));
    }

    private void verifyPart() throws DbException {
        verify(context, times(1)).getAttribute("tripService");
        verify(req, times(2)).getSession();

        verify(session, times(1)).getAttribute("userId");

        verify(tripService, times(1)).userHasTripsAmount(user.getId());
    }
}