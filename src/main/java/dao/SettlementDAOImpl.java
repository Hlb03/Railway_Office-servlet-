package dao;
/*
  User: admin
  Cur_date: 22.08.2022
  Cur_time: 16:44
*/

import entity.Settlement;

import javax.sql.DataSource;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

public class SettlementDAOImpl implements SettlementDAO{

    private final DataSource ds;

    public SettlementDAOImpl(DataSource ds){
        this.ds = ds;
    }

    @Override
    public List<Settlement> getAllSettlements() throws DbException {
        final String GET_ALL_SETTLEMENTS = "SELECT * FROM `settlement` ORDER BY 2";

        List<Settlement> allSettlements = new ArrayList<>();

        try (
                Connection con = ds.getConnection();
                Statement st = con.createStatement();
                ResultSet rs = st.executeQuery(GET_ALL_SETTLEMENTS)
            ){

            while (rs.next()){
                Settlement settlement = new Settlement();
                settlement.setId(rs.getInt(1));
                settlement.setName(rs.getString(2));

                allSettlements.add(settlement);
            }

        }catch (SQLException ex){
            throw new DbException("Failed to get all settlements", ex);
        }

        return allSettlements;
    }

    @Override
    public Settlement getSettlementByName(String name) {
        return null;
    }

    @Override
    public void insertSettlement(String name) {

    }

    @Override
    public void deleteSettlement(String name) {

    }
}
