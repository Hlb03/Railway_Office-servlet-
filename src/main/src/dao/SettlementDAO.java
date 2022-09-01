package dao;

import entity.Settlement;

import java.util.List;

public interface SettlementDAO {
    List<Settlement> getAllSettlements() throws DbException;
    Settlement getSettlementByName(String name) throws DbException;
    void insertSettlement(String name) throws DbException;
    void deleteSettlement(String name) throws DbException;
}
