package web.filter.internationalization;

import org.junit.jupiter.api.Test;

import javax.servlet.FilterConfig;
import javax.servlet.ServletException;

import static org.mockito.Mockito.mock;

class SessionLocaleFilterTest {

    private final SessionLocaleFilter filter = new SessionLocaleFilter();
    private final FilterConfig config = mock(FilterConfig.class);

    @Test
    void init() throws ServletException {
        filter.init(config);
    }
}