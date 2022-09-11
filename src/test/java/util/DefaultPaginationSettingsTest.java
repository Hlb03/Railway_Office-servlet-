package util;

import org.junit.jupiter.api.Test;
import static org.mockito.Mockito.*;

import javax.servlet.http.HttpServletRequest;

import static org.junit.jupiter.api.Assertions.*;

class DefaultPaginationSettingsTest {

    @Test
    void paginationDefaultSetting() {
        String currPage = "3";
        String recordsPerPage = "6";

        HttpServletRequest req = mock(HttpServletRequest.class);

        when(req.getParameter("currentPage")).thenReturn(currPage);
        when(req.getParameter("recordsPerPage")).thenReturn(recordsPerPage);

        assertArrayEquals(new int[]{Integer.parseInt(currPage), Integer.parseInt(recordsPerPage)},
                                        DefaultPaginationSettings.paginationDefaultSetting(req));
    }
}