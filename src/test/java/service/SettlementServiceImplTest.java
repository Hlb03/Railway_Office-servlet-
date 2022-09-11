package service;

import dao.DbException;
import dao.SettlementDAO;
import dao.SettlementDAOImpl;
import entity.Settlement;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import static org.mockito.Mockito.*;

import javax.naming.Context;
import javax.naming.NamingException;
import javax.sql.DataSource;
import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;


class SettlementServiceImplTest {

    private final SettlementDAO dao = mock(SettlementDAO.class);
    private final SettlementServiceImpl settlementService = new SettlementServiceImpl(dao);
    private final Settlement settlement = new Settlement("Kovel");



    @Test
    void getSettlements() throws DbException {
        List<Settlement> allSettlements = Arrays.asList(
                new Settlement("Kyiv"), new Settlement("Odessa"), new Settlement("Lviv")
        );

        when(dao.getAllSettlements()).thenReturn(allSettlements);
        assertEquals(allSettlements, settlementService.getSettlements());
    }

    @Test
    void getSettlement() throws DbException {
        when(dao.getSettlementByName(settlement.getName())).thenReturn(settlement);
        assertEquals(settlement, settlementService.getSettlement(settlement.getName()));
    }

    @Test
    void createSettlement() throws DbException {
        settlementService.createSettlement(settlement.getName());
        verify(dao, times(1)).insertSettlement(settlement.getName());
    }

    @Test
    void deleteSettlement() throws DbException {
        settlementService.deleteSettlement(settlement.getName());
        verify(dao, times(1)).deleteSettlement(settlement.getName());
    }
}