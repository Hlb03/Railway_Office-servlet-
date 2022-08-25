package service;

import dao.DbException;
import entity.Settlement;

import java.util.List;

public interface SettlementService {
    List<Settlement> getSettlements() throws DbException;
    Settlement getSettlement(String name) throws DbException;
    void createSettlement(String name) throws DbException;
    void deleteSettlement(String name) throws DbException;
}
