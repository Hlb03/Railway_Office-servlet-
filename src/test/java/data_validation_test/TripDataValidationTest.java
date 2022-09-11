package data_validation_test;
/*
  User: admin
  Cur_date: 09.09.2022
  Cur_time: 20:02
*/

import data_validation.TripDataValidation;
import entity.Train;
import entity.Trip;
import entity.User;
import org.junit.jupiter.api.Test;

import java.math.BigDecimal;
import java.sql.Date;
import java.sql.Time;

import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.*;

public class TripDataValidationTest {

    private final TripDataValidation tripValidation;
    private final TripDataValidation validation = mock(TripDataValidation.class);

    public TripDataValidationTest() {
        tripValidation = TripDataValidation.getInstance();
    }

    @Test
    void checkDataForAllTrainGetTest1() {
        int a = 2;
        int b = 3;

        validation.checkDataForAllTrainsGet(a, b);

        verify(validation, times(1)).checkDataForAllTrainsGet(a, b);
    }

    @Test
    void checkDataForAllTrainGetTest2() {
        int a = 10;
        int b = 3;

        validation.checkDataForAllTrainsGet(a, b);

        verify(validation, times(1)).checkDataForAllTrainsGet(a, b);
    }

    @Test
    void checkDataForGetTripTest1() {
        int tripId = 5;

        validation.checkDataForGetTrip(tripId);

        verify(validation, times(1)).checkDataForGetTrip(tripId);
    }

    @Test
    void checkDataForGetTripTest2() {
        int tripId = 28;

        validation.checkDataForGetTrip(tripId);

        verify(validation, times(1)).checkDataForGetTrip(tripId);
    }

    @Test
    void checkDataForSearchTripByRouteWithLimitTest1() {
        String start = "Kherson";
        String end = "Konotop";
        int a = 5;
        int b = 3;

        validation.checkDataForSearchTripByRouteWithLimit(start, end, a, b);

        verify(validation, times(1)).checkDataForSearchTripByRouteWithLimit(start, end, a, b);
    }

    @Test
    void checkDataForSearchTripByRouteWithLimitTest2() {
        String start = "Kriviy Rih";
        String end = "Lviv";
        int a = 7;
        int b = 1;

        validation.checkDataForSearchTripByRouteWithLimit(start, end, a, b);

        verify(validation, times(1)).checkDataForSearchTripByRouteWithLimit(start, end, a, b);
    }

    @Test
    void checkDataForAmountOfTripsFoundOnRouteTest1() {
        String start = "Chernihiv";
        String end = "Kovel";

        validation.checkDataForAmountOfTripsFoundOnRoute(start, end);

        verify(validation, times(1)).checkDataForAmountOfTripsFoundOnRoute(start, end);
    }

    @Test
    void checkDataForAmountOfTripsFoundOnRouteTest2() {
        String start = "Lutsk";
        String end = "Rivne";

        validation.checkDataForAmountOfTripsFoundOnRoute(start, end);

        verify(validation, times(1)).checkDataForAmountOfTripsFoundOnRoute(start, end);
    }

    @Test
    void checkDataForSearchRouteByRouteAndDateTest() {
        String start = "Dnipro";
        String end = "Zaporizhzhia";
        Date date = Date.valueOf("2022-09-09");

        validation.checkDataForSearchRouteByRouteAndDate(start, end, date);

        verify(validation, times(1)).checkDataForSearchRouteByRouteAndDate(start, end, date);
    }

    @Test
    void checkDataForGettingAllTripsSettlementsTest() {
        int id = 10;

        validation.checkDataForGettingAllTripsSettlements(id);

        verify(validation, times(1)).checkDataForGettingAllTripsSettlements(id);
    }

    @Test
    void checkDataForCreatingNewTripTest() {
        Trip trip = new Trip(Date.valueOf("2022-09-10"), Time.valueOf("20:50:41"), Date.valueOf("2022-09-11"), Time.valueOf("11:01:57"),
                26, new BigDecimal("417.23"), new Train(7));
        int startSettlementId = 11;
        int endSettlementId = 25;
        int[] allSettlementId = {11, 5, 70, 16, 3, 25};
        String msg = "Some msg";

        validation.checkDataForCreatingNewTrip(trip, startSettlementId, endSettlementId, allSettlementId, msg);

        verify(validation, times(1))
                .checkDataForCreatingNewTrip(trip, startSettlementId, endSettlementId, allSettlementId, msg);
    }

    @Test
    void checkDataForDeletingTripTest() {
        Trip trip = new Trip(25);

        validation.checkDataForDeletingTrip(trip);

        verify(validation, times(1)).checkDataForDeletingTrip(trip);
    }

    @Test
    void checkDataForUpdatingTripInfoTest() {
        Trip trip = new Trip(Date.valueOf("2022-09-10"), Time.valueOf("20:50:41"), Date.valueOf("2022-09-11"), Time.valueOf("11:01:57"),
                26, new BigDecimal("417.23"), new Train(7));

        validation.checkDataForUpdatingTripInfo(trip);

        verify(validation, times(1)).checkDataForUpdatingTripInfo(trip);
    }

    @Test
    void checkDataForUpdatingTripInfoAndStationsTest() {
        Trip trip = new Trip(Date.valueOf("2022-09-10"), Time.valueOf("20:50:41"), Date.valueOf("2022-09-11"), Time.valueOf("11:01:57"),
                31, new BigDecimal("417.23"), new Train(21));
        int[] allSettlementsId = {25, 22, 11, 9, 3, 5};

        validation.checkDataForUpdatingTripInfoAndStations(trip, allSettlementsId);

        verify(validation, times(1)).checkDataForUpdatingTripInfoAndStations(trip, allSettlementsId);
    }

    @Test
    void checkDataForGettingUsersTripsWithLimitTest() {
        int userId = 8;
        int a = 11;
        int b = 2;

        validation.checkDataForGettingUsersTripsWithLimit(userId, a, b);

        verify(validation, times(1)).checkDataForGettingUsersTripsWithLimit(userId, a, b);
    }

    @Test
    void checkDataForGettingUsersTripAmountTest() {
//        User user = new User(15);
        int userId = 15;

        validation.checkDataForGettingUsersTripAmount(userId);

        verify(validation, times(1)).checkDataForGettingUsersTripAmount(userId);
    }

    @Test
    void checkDataForAllTrainGetTestThrowsException() {
        assertThrows(IllegalArgumentException.class, () -> tripValidation.checkDataForAllTrainsGet(-1, -3));
        assertThrows(IllegalArgumentException.class, () -> tripValidation.checkDataForAllTrainsGet(9, -2));
        assertThrows(IllegalArgumentException.class, () -> tripValidation.checkDataForAllTrainsGet(-19, 6));
    }

    @Test
    void checkDataForGetTripTestThrowsException() {
        assertThrows(IllegalArgumentException.class, () -> tripValidation.checkDataForGetTrip(-10));
        assertThrows(IllegalArgumentException.class, () -> tripValidation.checkDataForGetTrip(0));
    }

    @Test
    void checkDataForSearchTripByRouteWithLimitTestThrowsException() {
        assertThrows(IllegalArgumentException.class,
                () -> tripValidation.checkDataForSearchTripByRouteWithLimit(" ", "", 0, 0));
        assertThrows(IllegalArgumentException.class,
                () -> tripValidation.checkDataForSearchTripByRouteWithLimit(" ", "Another", 2, 5));
        assertThrows(IllegalArgumentException.class,
                () -> tripValidation.checkDataForSearchTripByRouteWithLimit("Kyiv", ":(", 11, 2));
        assertThrows(IllegalArgumentException.class,
                () -> tripValidation.checkDataForSearchTripByRouteWithLimit("Ivano-Frankivsk", "Chernivtsi",
                                                                -1, 2));
        assertThrows(IllegalArgumentException.class,
                () -> tripValidation.checkDataForSearchTripByRouteWithLimit("Ivano-Frankivsk", "Chernivtsi",
                        -1, -12));
    }

    @Test
    void checkDataForAmountOfTripsFoundOnRouteTestThrowsException() {
        assertThrows(IllegalArgumentException.class, () -> tripValidation.checkDataForAmountOfTripsFoundOnRoute(" ", "___"));
        assertThrows(IllegalArgumentException.class, () -> tripValidation.checkDataForAmountOfTripsFoundOnRoute(null, "Town"));
        assertThrows(IllegalArgumentException.class, () -> tripValidation.checkDataForAmountOfTripsFoundOnRoute("New York", null));
        assertThrows(IllegalArgumentException.class, () -> tripValidation.checkDataForAmountOfTripsFoundOnRoute("Test123", "Kahovka"));
    }

    @Test
    void checkDataForSearchRouteByRouteAndDateTestThrowsException() {
        assertThrows(IllegalArgumentException.class,
                () -> tripValidation.checkDataForSearchRouteByRouteAndDate(" ", "", Date.valueOf(" ")));
        assertThrows(IllegalArgumentException.class,
                () -> tripValidation.checkDataForSearchRouteByRouteAndDate("Kherson", "", Date.valueOf(" ")));
        assertThrows(IllegalArgumentException.class,
                () -> tripValidation.checkDataForSearchRouteByRouteAndDate("Odessa", "Mykilaiv", Date.valueOf(" ")));
        assertThrows(IllegalArgumentException.class,
                () -> tripValidation.checkDataForSearchRouteByRouteAndDate("Kyiv", "Chernihiv", Date.valueOf("2022:10:11")));
        assertThrows(IllegalArgumentException.class,
                () -> tripValidation.checkDataForSearchRouteByRouteAndDate(null, "Chernivtsi", Date.valueOf("2022:09:10")));
        assertThrows(IllegalArgumentException.class,
                () -> tripValidation.checkDataForSearchRouteByRouteAndDate("Zhytomyr", null, Date.valueOf("2022:10:10")));
        assertThrows(IllegalArgumentException.class,
                () -> tripValidation.checkDataForSearchRouteByRouteAndDate("Korosten", "Chernivtsi", null));
    }

    @Test
    void checkDataForGettingAllTripsSettlementsTestThrowsException() {
        assertThrows(IllegalArgumentException.class, () -> tripValidation.checkDataForGettingAllTripsSettlements(0));
        assertThrows(IllegalArgumentException.class, () -> tripValidation.checkDataForGettingAllTripsSettlements(-3));
    }

    @Test
    void checkDataForCreatingNewTripTestThrowsException() {
        assertThrows(IllegalArgumentException.class, () -> tripValidation.checkDataForCreatingNewTrip(
                new Trip(Date.valueOf(""), Time.valueOf(" "), Date.valueOf("--"), Time.valueOf("//"), -100,
                        new BigDecimal(""), new Train(-11)),
                -10, -2, new int[]{-1, 12, 0},"Another msg"
        ));
        assertThrows(IllegalArgumentException.class, () -> tripValidation.checkDataForCreatingNewTrip(
                new Trip(Date.valueOf("2022-11-03"), Time.valueOf("20:19:33"), Date.valueOf("2022:01:19"), Time.valueOf("01:15:39"),
                        100,  new BigDecimal("293.25"), new Train(2)),
                10, 2, new int[]{-1, 12, 0},"Another msg"
        ));
        assertThrows(IllegalArgumentException.class, () -> tripValidation.checkDataForCreatingNewTrip(
                new Trip(Date.valueOf("2022:11:03"), Time.valueOf("20:19:33"), Date.valueOf("2022:01:19"), Time.valueOf("01:15:39"),
                        100,  new BigDecimal("293.25"), new Train(2)),
                10, 2, new int[]{1, 12, 25},"Another msg"
        ));
        assertThrows(IllegalArgumentException.class, () -> tripValidation.checkDataForCreatingNewTrip(
                new Trip(Date.valueOf("2022-11-03"), Time.valueOf("20-19-33"), Date.valueOf("2022:01:19"), Time.valueOf("01:15:39"),
                        100,  new BigDecimal("293.25"), new Train(2)),
                10, 2, new int[]{1, 12, 25},"Another msg"
        ));
        assertThrows(IllegalArgumentException.class, () -> tripValidation.checkDataForCreatingNewTrip(
                new Trip(Date.valueOf("2022:11:03"), Time.valueOf("20:19:33"), Date.valueOf("2022\\01\\19"), Time.valueOf("01:15:39"),
                        100,  new BigDecimal("293.25"), new Train(2)),
                10, 2, new int[]{1, 12, 25},"Another msg"
        ));
        assertThrows(IllegalArgumentException.class, () -> tripValidation.checkDataForCreatingNewTrip(
                new Trip(Date.valueOf("2022-11-03"), Time.valueOf("20:19:33"), null, Time.valueOf("01:15:39"),
                        100,  new BigDecimal("293.25"), new Train(2)),
                10, 2, new int[]{1, 12, 25},"Another msg"
        ));
        assertThrows(IllegalArgumentException.class, () -> tripValidation.checkDataForCreatingNewTrip(
                new Trip(Date.valueOf("2022-11-03"), Time.valueOf("20:19:33"), null, Time.valueOf("01:15:39"),
                        100,  null, new Train(2)),
                10, 2, new int[]{1, 12, 25},"Another msg"
        ));
    }

    @Test
    void checkDataForDeletingTripTestThrowsException() {
        assertThrows(IllegalArgumentException.class, () -> tripValidation.checkDataForDeletingTrip(new Trip()));
        assertThrows(IllegalArgumentException.class, () -> tripValidation.checkDataForDeletingTrip(new Trip(-5)));
        assertThrows(IllegalArgumentException.class, () -> tripValidation.checkDataForDeletingTrip(new Trip(0)));
    }

    @Test
    void checkDataForUpdatingTripInfoTestThrowsException() {
        assertThrows(IllegalArgumentException.class, () -> tripValidation.checkDataForUpdatingTripInfo(
                new Trip(Date.valueOf(""), Time.valueOf(" "), Date.valueOf("--"), Time.valueOf("//"), -100,
                        new BigDecimal(""), new Train(-11))));
        assertThrows(IllegalArgumentException.class, () -> tripValidation.checkDataForUpdatingTripInfo(
                new Trip(null, Time.valueOf("23:15:28"), Date.valueOf("2022:10:15"), Time.valueOf("03:05:23"), 100,
                        new BigDecimal("275.56"), new Train(11))));
        assertThrows(IllegalArgumentException.class, () -> tripValidation.checkDataForUpdatingTripInfo(
                new Trip(null, Time.valueOf("23:15:28"), Date.valueOf("2022-10-15"), Time.valueOf("03:05:23"), 100,
                        new BigDecimal("-275.56"), new Train(11))));
    }

    @Test
    void checkDataForUpdatingTripInfoAndStationsTestThrowsException() {
        assertThrows(IllegalArgumentException.class, () -> tripValidation.checkDataForUpdatingTripInfoAndStations(
                new Trip(Date.valueOf(""), Time.valueOf(" "), Date.valueOf("--"), Time.valueOf("//"), -100,
                        new BigDecimal(""), new Train(-11)), new int[]{15, 3, 2}));
        assertThrows(IllegalArgumentException.class, () -> tripValidation.checkDataForUpdatingTripInfoAndStations(
                new Trip(Date.valueOf("2022-06:03"), Time.valueOf("21:53:00"), Date.valueOf("2022:02:20"), Time.valueOf("11:03:17"),
                        100, new BigDecimal("251.55"), new Train(-11)), new int[]{15, 3, 0}));
    }

    @Test
    void checkDataForGettingUsersTripsWithLimitTestThrowsException() {
        assertThrows(IllegalArgumentException.class,
                () -> tripValidation.checkDataForGettingUsersTripsWithLimit(-4, 1, 5));
        assertThrows(IllegalArgumentException.class,
                () -> tripValidation.checkDataForGettingUsersTripsWithLimit(2, -11, 15));
        assertThrows(IllegalArgumentException.class,
                () -> tripValidation.checkDataForGettingUsersTripsWithLimit(2, 13, -2));
    }

    @Test
    void checkDataForGettingUsersTripAmountTestThrowsException() {
//        assertThrows(IllegalArgumentException.class, () -> tripValidation.checkDataForGettingUsersTripAmount(null));
        assertThrows(IllegalArgumentException.class, () -> tripValidation.checkDataForGettingUsersTripAmount(-2));
    }
}
