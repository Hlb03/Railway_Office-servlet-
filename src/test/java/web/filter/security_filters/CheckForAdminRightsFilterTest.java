package web.filter.security_filters;

import org.junit.jupiter.api.Test;

import javax.servlet.FilterConfig;
import javax.servlet.ServletException;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.mock;

class CheckForAdminRightsFilterTest {

    private final CheckForAdminRightsFilter filter = new CheckForAdminRightsFilter();
    private final FilterConfig config = mock(FilterConfig.class);

    @Test
    void init() throws ServletException {
        filter.init(config);
    }
}