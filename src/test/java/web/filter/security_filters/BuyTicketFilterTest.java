package web.filter.security_filters;

import org.junit.jupiter.api.Test;

import javax.servlet.FilterConfig;
import javax.servlet.ServletException;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.mock;

class BuyTicketFilterTest {

    private final BuyTicketFilter filter = new BuyTicketFilter();
    private final FilterConfig config = mock(FilterConfig.class);

    @Test
    void init() throws ServletException {
        filter.init(config);
    }
}