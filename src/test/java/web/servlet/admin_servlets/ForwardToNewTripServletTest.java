package web.servlet.admin_servlets;

import dao.DbException;
import entity.Settlement;
import entity.Train;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import service.SettlementService;
import service.TrainService;

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

import static org.mockito.Mockito.*;

import static org.junit.jupiter.api.Assertions.*;


class ForwardToNewTripServletTest {

    private final ForwardToNewTripServlet servlet = new ForwardToNewTripServlet();

    private final HttpServletRequest req = mock(HttpServletRequest.class);
    private final HttpServletResponse resp = mock(HttpServletResponse.class);
    private final ServletContext context = mock(ServletContext.class);
    private final ServletConfig config = mock(ServletConfig.class);

    private final TrainService trainService = mock(TrainService.class);
    private final SettlementService settlementService = mock(SettlementService.class);

    @BeforeEach
    void init() throws ServletException {
        servlet.init(config);

        when(req.getServletContext()).thenReturn(context);
        when(context.getAttribute("trainService")).thenReturn(trainService);
        when(context.getAttribute("settlementService")).thenReturn(settlementService);

    }

    @Test
    void doGetPositiveScenario() throws ServletException, IOException, DbException {
        String forwardTo = "WEB-INF/jsp/new_trip_form.jsp";

        List<Train> allTrains = Arrays.asList(
                new Train("011A"), new Train("155Д"), new Train("097K")
        );

        List<Settlement> allSettlements = Arrays.asList(
                new Settlement("Kyiv"), new Settlement("Lviv"), new Settlement("Ivano-Frankivksk"),
                new Settlement("Rivne"), new Settlement("Zhytomyr")
        );

        RequestDispatcher dispatcher = mock(RequestDispatcher.class);

        when(trainService.getTrains()).thenReturn(allTrains);
        when(settlementService.getSettlements()).thenReturn(allSettlements);

        when(req.getRequestDispatcher(forwardTo)).thenReturn(dispatcher);

        servlet.doGet(req, resp);

        verify(req, never()).getSession();
        verify(resp, never()).sendRedirect("newTrip");

        verifyPart();

        verify(settlementService, times(1)).getSettlements();

        verify(req, times(1)).setAttribute("allTrains", allTrains);
        verify(req, times(1)).setAttribute("allSettlements", allSettlements);

        verify(req, times(1)).getRequestDispatcher(forwardTo);
        verify(dispatcher, times(1)).forward(req, resp);
    }

    @Test
    void doGetExceptionThrownScenario() throws ServletException, IOException, DbException {
        String redirectTo = "errorHandler";
        HttpSession session = mock(HttpSession.class);

        doThrow(DbException.class).when(trainService).getTrains();
        when(req.getSession()).thenReturn(session);

        servlet.doGet(req, resp);

        verifyPart();

        verify(resp, times(1)).sendRedirect(redirectTo);
        verify(session, times(1)).setAttribute("errorMsg", null);
        verify(resp, times(1)).sendRedirect(redirectTo);

        assertThrows(DbException.class, trainService::getTrains);
    }

    private void verifyPart() throws DbException {
        verify(req, times(2)).getServletContext();
        verify(context, times(1)).getAttribute("trainService");
        verify(context, times(1)).getAttribute("settlementService");

        verify(trainService, times(1)).getTrains();
    }
}