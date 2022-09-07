package data_validation;
/*
  User: admin
  Cur_date: 06.09.2022
  Cur_time: 20:22
*/


import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class SettlementDataValidation {

    private static final Logger LOG = LogManager.getLogger(SettlementDataValidation.class);
    private static SettlementDataValidation VALIDATION;

    private SettlementDataValidation(){}

    public synchronized static SettlementDataValidation getInstance() {
        if (VALIDATION == null){
            VALIDATION = new SettlementDataValidation();
            LOG.trace("SettlementDataValidator was successfully instantiated");
        }

        return VALIDATION;
    }

    public void checkSettlementName(String settlementName) {
        if (settlementName.length() > 46 || !settlementName.matches("[A-Za-zА-Яа-я-']+")) {
            LOG.debug("Data validation for settlement name was failed.");
            throw new IllegalArgumentException("Illegal argument for settlement name");
        }
    }
}
