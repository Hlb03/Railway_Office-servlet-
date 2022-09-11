package web.filter.encoding_filter;

import org.junit.jupiter.api.Test;

import static org.mockito.Mockito.*;

import javax.servlet.*;

class EncodingFilterTest {

    private final EncodingFilter filter = new EncodingFilter();
    private final FilterConfig config = mock(FilterConfig.class);


    @Test
    void init() throws ServletException {
        String encoding = "UTF-8";
        when(config.getInitParameter("encoding")).thenReturn(encoding);

        filter.init(config);

        verify(config, times(1)).getInitParameter("encoding");
    }
}