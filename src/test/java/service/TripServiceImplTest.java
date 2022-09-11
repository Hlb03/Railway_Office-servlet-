package service;

import dao.DbException;
import dao.TripDAO;
import entity.Settlement;
import entity.Train;
import entity.Trip;
import org.junit.jupiter.api.Test;

import java.math.BigDecimal;
import java.sql.Date;
import java.sql.Time;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static org.mockito.Mockito.*;

import static org.junit.jupiter.api.Assertions.*;

class TripServiceImplTest {

    private final TripDAO dao = mock(TripDAO.class);
    private final TripServiceImpl tripService = new TripServiceImpl(dao);

    @Test
    void getTrips() throws DbException {
        List<Trip> allTrips = Arrays.asList(
                new Trip(15), new Trip(21), new Trip(23)
        );

        when(dao.getAllTrips(1, 5)).thenReturn(allTrips);
        assertEquals(allTrips, tripService.getTrips(1, 5));
    }

    @Test
    void getTripsAmount() throws DbException {
        int tripsAmount = 5;

        when(dao.getTripsAmount()).thenReturn(tripsAmount);
        assertEquals(tripsAmount, tripService.getTripsAmount());
    }

    @Test
    void getTrip() throws DbException {
        Trip trip = new Trip(1);

        when(dao.getTrip(trip.getId())).thenReturn(trip);
        assertEquals(trip, tripService.getTrip(trip.getId()));
    }

    @Test
    void getByRoute() throws DbException {
        List<Trip> allTripsOnRoute = Arrays.asList(
                new Trip(8), new Trip(19)
        );

        when(dao.getByRoute("Kyiv", "Kovel", 1, 5)).thenReturn(allTripsOnRoute);
        assertEquals(allTripsOnRoute, tripService.getByRoute("Kyiv", "Kovel", 1, 5));
    }

    @Test
    void amountOfFoundTripsByRoute() throws DbException {
        int amountOnRoute = 3;

        when(dao.amountOfFountTripsByRoute("Lviv", "Odessa")).thenReturn(amountOnRoute);
        assertEquals(amountOnRoute, tripService.amountOfFoundTripsByRoute("Lviv", "Odessa"));
    }

    @Test
    void getByRouteAndDate() throws DbException {
        List<Trip> byRouteAndDate = Arrays.asList(
                new Trip(4), new Trip(5)
        );

        when(dao.getByRouteAndDate(
                "Zhytomyr", "Rivne", Date.valueOf("2022-09-20"))).thenReturn(byRouteAndDate);
        assertEquals(byRouteAndDate,
                tripService.getByRouteAndDate("Zhytomyr", "Rivne", Date.valueOf("2022-09-20")));
    }

    @Test
    void tripContainsSettlements() throws DbException {
        Map<Integer, Settlement> tripSettlements = new HashMap<>();
        tripSettlements.put(1, new Settlement("Kherson"));
        tripSettlements.put(2, new Settlement("Krvyi Rih"));
        tripSettlements.put(3, new Settlement("Konotop"));

        when(dao.getTripSettlements(4)).thenReturn(tripSettlements);
        assertEquals(tripSettlements, tripService.tripContainsSettlements(4));
    }

    @Test
    void createTrip() throws DbException {
        Trip trip = new Trip(Date.valueOf("2022-09-11"), Time.valueOf("12:35:13"), Date.valueOf("2022-09-11"),
                Time.valueOf("23:11:19"), 32, BigDecimal.valueOf(301.55), new Train(21));
        trip.setStartStation("Lutsk");
        trip.setFinalStation("Mukachevo");
        int[] settlementsId = {11, 15, 19};

        tripService.createTrip(trip, settlementsId[0], settlementsId[settlementsId.length - 1], settlementsId);
        verify(dao, times(1))
                .createTrip(trip, settlementsId[0], settlementsId[settlementsId.length - 1], settlementsId);
    }

    @Test
    void deleteTrip() throws DbException {
        Trip trip = new Trip(19);
        trip.setStartStation("Korosten");

        tripService.deleteTrip(trip);
        verify(dao, times(1)).deleteTrip(trip);
    }

    @Test
    void deleteAllOutdatedTrips() throws DbException {
        tripService.deleteAllOutdatedTrips();
        verify(dao, times(1)).deleteAllOutdatedTrips();
    }

    @Test
    void updateTripParameters() throws DbException {
        Trip trip = new Trip(Date.valueOf("2022-09-11"), Time.valueOf("12:35:13"), Date.valueOf("2022-09-11"),
                Time.valueOf("23:11:19"), 32, BigDecimal.valueOf(301.55), new Train(21));
        trip.setStartStation("Lutsk");
        trip.setFinalStation("Mukachevo");

        tripService.updateTripParameters(trip);
        verify(dao, times(1)).updateTripParameters(trip);
    }

    @Test
    void updateAllTripInfo() throws DbException {
        Trip trip = new Trip(Date.valueOf("2022-09-11"), Time.valueOf("12:35:13"), Date.valueOf("2022-09-11"),
                Time.valueOf("23:11:19"), 32, BigDecimal.valueOf(301.55), new Train(21));
        trip.setStartStation("Lutsk");
        trip.setFinalStation("Mukachevo");
        int[] settlementsId = {1, 25, 5, 33};

        tripService.updateAllTripInfo(trip, settlementsId);
        verify(dao, times(1)).updateAllTripInfo(trip, settlementsId);
    }

    @Test
    void userHasTrips() throws DbException {
        int userId = 5;
        List<Trip> userTrips = Arrays.asList(
                new Trip(5), new Trip(12)
        );

        when(dao.userHasTrips(userId, 1, 10)).thenReturn(userTrips);
        assertEquals(userTrips, tripService.userHasTrips(userId, 1, 10));
    }

    @Test
    void userHasTripsAmount() throws DbException {
        int userId = 4;
        int tripsAmount = 2;

        when(dao.userHasTripsAmount(userId)).thenReturn(tripsAmount);
        assertEquals(tripsAmount, tripService.userHasTripsAmount(userId));
    }
}