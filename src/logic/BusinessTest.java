package logic;

import logic.model.SheduleEntity;
import logic.model.TrainEntity;
import rzd.model.objects.*;

import java.sql.Time;
import java.util.*;

/**
 * User: Стас
 * Date: 28.09.2010
 * Time: 23:46:25
 */

public class BusinessTest {

    static BusinessManager manager;

    static {
        try {
            manager = BusinessManager.get();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static void main(String[] args) throws HibernateInitializeException {
//        testGetRoutes();
//        testGetSheduleTypes();
//        testAddRoute();
//        testUpdateRoute();
//        testGetParentsTypes();
//        testAddUpdateCar();
//        testRoads();
//        testDate();
//        test();
//        testGoingTrains();
//        testArrivingTrains();
//        testMakeTrainForGoing();
        testGetTrainsOnRoads();
    }


    public static void test() {
        ArrayList l = null;
        for(Object o : l) {
            System.out.println(o);
        }
    }

    public static void testGetRoutes() {
        ArrayList<Route> r = manager.getRoutes();
        for(Route rr : r) {
            System.out.println(rr.getCityFrom() + " - " + rr.getCityTo() + " : " + rr.getNumberForward() + "/" + rr.getNumberBack());
        }
    }

    public static void testGetSheduleTypes() {
        ArrayList<SheduleType> r = manager.getSheduleTypes();
        for(SheduleType rr : r) {
            System.out.println(rr.getId() + " - " + rr.getName());
        }
    }

    public static void testAddRoute() {
        Time time = new Time(new Date().getTime());
        GregorianCalendar gc = new GregorianCalendar();
        gc.add(Calendar.HOUR_OF_DAY, 5);
        Time tback = new Time(gc.getTimeInMillis());
        SheduleType st = new SheduleType(1, "nonPair");
        Shedule sf = new Shedule(9, time, time, 10, 11, st, null);//new int[]{1,2,34,4});
        Shedule sb = new Shedule(9, tback, tback, 11, 20, st, null);//new int[]{0,6,1,3});
        Route r = new Route(9, "тест", "тест", "Москва", "Питер", sf, sb, true, 101, 101);
        manager.addRoute(r);
    }

    public static void testUpdateRoute() {
        Time time = new Time(new Date().getTime());
        SheduleType st = new SheduleType(1, "nonPair");
        Shedule sf = new Shedule(9, time, time, 38, 11, st, new int[]{1, 2, 3, 4});
        Shedule sb = new Shedule(9, time, time, 36, 66, st, null);
        Route r = new Route(5, "love!!!", "hate!!!", "Evgen", "Ekaterina", sf, sb, true, 101, 101);
        manager.updateRoute(r);
    }

    public static void testGetParentsTypes() {
        for(CarType ct : manager.getCarParentTypes()) {
            System.out.println(ct.getType());
        }
    }

    public static void testAddUpdateCar() {
        Car car = new Car(12312345, "1", new CarLocation(1, "1"), new CarType(1, "1"), "c", "g", "gp", "a", "ed", "red", true, 101, 100, 101, 100); 
        System.out.println(manager.addCar(car));
        car.setBodyColor("green");
        System.out.println(manager.editCar(car));
    }

    public static void testRoads() {
        for(RoadType rt : manager.getRoadTypes()) {
            System.out.println(rt.getId() + " : " + rt.getName());
            for(Road r : manager.getRoadsByType(new RoadType(rt.getId(), rt.getName()))) {
                System.out.println("    " + r.getName());
            }
        }
    }

    public static void testDate() {
        System.out.println(manager.getCurrentDate().toString());
    }

    public static void testGoingTrains() {
        for(Train te : manager.getGoingTrains(1)) {
            System.out.println(te.getDtDeparture() + " " + te.getDtDestination());
        }
    }

    public static void testArrivingTrains() {
        for(Train te : manager.getArrivingTrains(1)) {
            System.out.println(te.getDtDeparture() + " " + te.getDtDestination());
        }
    }

    public static void testMakeTrainForGoing() {
        try {
            manager.makeTrainForGoing(new Train(127, new Road(15), "Petrov", null));
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static void testGetTrainsOnRoads() {
        for(Train t :manager.getTrainsOnRoads()) {
            System.out.println(t.getChief() + " - " + t.getRoad().getName());
        }
    }
}
