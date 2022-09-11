package web.servlet;

import dao.DbException;
import entity.User;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
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

import static org.junit.jupiter.api.Assertions.*;


class BalanceReplenishmentServletTest {

    private final BalanceReplenishmentServlet servlet = new BalanceReplenishmentServlet();

    private final BigDecimal balance = BigDecimal.valueOf(320.22);
    private final User user = new User("test@gmail.com", "passTest", "First", "Last");

    private final HttpServletRequest req = mock(HttpServletRequest.class);
    private final HttpServletResponse resp = mock(HttpServletResponse.class);
    private final HttpSession session = mock(HttpSession.class);
    private final ServletConfig config = mock(ServletConfig.class);
    private final ServletContext context = mock(ServletContext.class);
    private final UserService userService = mock(UserService.class);

    @BeforeEach
    void init() throws ServletException {
        user.setId(10);

        when(req.getServletContext()).thenReturn(context);
        when(context.getAttribute("userService")).thenReturn(userService);
        when(req.getParameter("balanceSum")).thenReturn(String.valueOf(balance));

        servlet.init(config);

        when(req.getSession()).thenReturn(session);
        when(session.getAttribute("userId")).thenReturn(user.getId());
    }

    @Test
    void doPostPositiveScenario() throws ServletException, IOException, DbException {
        String redirectTo = "menu";

        when(session.getAttribute("userLogin")).thenReturn(user.getLogin());
        when(userService.getByLogin(user.getLogin())).thenReturn(user);

        servlet.doPost(req, resp);

        verifyPart();

        verify(userService, times(1)).updateBalance(user.getId(), balance);
        verify(session, times(1)).getAttribute("userLogin");
        verify(userService, times(1)).getByLogin(user.getLogin());

        verify(session, times(1)).setAttribute("balance", user.getBalance());

        verify(resp, times(1)).sendRedirect(redirectTo);

        verify(req, never()).getRequestDispatcher("WEB-INF/jsp/menu.jsp");
    }

    @Test
    void doPostExceptionThrowingScenario() throws ServletException, IOException, DbException {
        String redirectTo = "errorHandler";

        doThrow(DbException.class).when(userService).updateBalance(user.getId(), balance);

        servlet.doPost(req, resp);

        verify(session, times(1)).setAttribute("errorMsg", null);
        verify(resp, times(1)).sendRedirect(redirectTo);

        assertThrows(DbException.class, () -> userService.updateBalance(user.getId(), balance));
    }

    private void verifyPart() throws DbException {
        verify(req, times(1)).getServletContext();
        verify(context, times(1)).getAttribute("userService");
        verify(req, times(1)).getParameter("balanceSum");

        verify(userService, times(1)).updateBalance(user.getId(), balance);
        verify(req, times(3)).getSession();
    }
}