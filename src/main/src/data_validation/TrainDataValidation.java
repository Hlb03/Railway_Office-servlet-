package data_validation;
/*
  User: admin
  Cur_date: 06.09.2022
  Cur_time: 20:32
*/

import entity.Train;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class TrainDataValidation {

    private static final Logger LOG = LogManager.getLogger(TrainDataValidation.class);

    private static TrainDataValidation VALIDATION;

    private TrainDataValidation(){}

    public static synchronized TrainDataValidation getInstance(){
        if (VALIDATION == null) {
            VALIDATION = new TrainDataValidation();
            LOG.trace("TrainDataValidator was successfully instantiated");
        }

        return VALIDATION;
    }

    public void checkTrainSearchById(String id) {
        if (!id.matches("\\d+")) {
            LOG.debug("Data validation for getting train by its ID was failed");
            throw new IllegalArgumentException("Illegal argument for train search by Id");
        }
    }

    public void checkTrainSearchByName(String name) {
        if (!name.matches("\\d{3}[А-Я]") || name.length() > 4) {
            LOG.debug("Data validation for searching train by name was failed");
            throw new IllegalArgumentException("Illegal argument for train search by name");
        }
    }

    public void checkTrainCreationData(Train train) {
        if (train.getNumber() == null || !train.getNumber().matches("\\d{3}[А-Я]")){
            LOG.debug("Data validation for creating new train wasn't successful");
            throw new IllegalArgumentException("Illegal argument for train creation");
        }
    }

    public void checkDeleteTrainData(Train train) {
        if (train.getNumber() == null || !train.getNumber().matches("\\d{3}[А-Я]")) {
            LOG.debug("Data validation for deleting train was not unaccomplished");
            throw new IllegalArgumentException("Illegal argument for train creation");
        }
    }
}
