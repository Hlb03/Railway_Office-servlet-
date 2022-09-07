package service;
/*
  User: admin
  Cur_date: 22.08.2022
  Cur_time: 16:49
*/

import dao.DbException;
import dao.SettlementDAO;
import data_validation.SettlementDataValidation;
import entity.Settlement;

import java.util.List;

public class SettlementServiceImpl implements SettlementService {

    private final SettlementDAO dao;

    private final static SettlementDataValidation VALIDATION = SettlementDataValidation.getInstance();

    public SettlementServiceImpl(SettlementDAO dao){
        this.dao = dao;
    }

    @Override
    public List<Settlement> getSettlements() throws DbException {
        return dao.getAllSettlements();
    }

    @Override
    public Settlement getSettlement(String name) throws DbException {
        VALIDATION.checkSettlementName(name);

        return dao.getSettlementByName(name);
    }

    @Override
    public void createSettlement(String name) throws DbException {
        VALIDATION.checkSettlementName(name);

        dao.insertSettlement(name);
    }

    @Override
    public void deleteSettlement(String name) throws DbException {
        VALIDATION.checkSettlementName(name);

        dao.deleteSettlement(name);
    }
}
