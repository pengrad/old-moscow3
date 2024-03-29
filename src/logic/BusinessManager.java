package logic;

import logic.model.*;
import org.hibernate.Criteria;
import org.hibernate.Session;
import org.hibernate.criterion.Restrictions;
import rzd.model.objects.*;

import java.sql.Timestamp;
import java.util.*;

public class BusinessManager implements BusinessLogic {

    private static BusinessManager instance;

    public synchronized static BusinessManager get() throws HibernateInitializeException {
        if (instance == null) instance = new BusinessManager();
        return instance;
    }

    private BusinessManager() throws HibernateInitializeException {
        SessionManager.init();
    }

    public Session getSession() {
        return SessionManager.getSession();
    }

    public Date getCurrentDate() {
        return HibernateUtils.getDate(getSession());
    }

    public ArrayList<Route> getRoutes() {
        ArrayList<Route> list = null;
        try {
            SessionManager.beginTran();
            Collection<RouteEntity> objects = SessionManager.getAllObjects(new RouteEntity());
            list = new ArrayList<Route>(objects.size());
            for (RouteEntity re : objects) {
                Route route = EntityConverter.convertRoute(re);
                list.add(route);
            }
            SessionManager.commit();
        } catch (Exception e) {
            e.printStackTrace();
            SessionManager.rollback();
            list = null;
        } finally {
            SessionManager.closeSession();
        }
        return list;
    }

    public Route getRouteById(int idRoute) {
        Route route = null;
        try {
            SessionManager.beginTran();
            RouteEntity re = SessionManager.getEntityById(new RouteEntity(), idRoute);
            route = EntityConverter.convertRoute(re);
            SessionManager.commit();
        } catch (Exception e) {
            e.printStackTrace();
            SessionManager.rollback();
            route = null;
        } finally {
            SessionManager.closeSession();
        }
        return route;
    }

    public ArrayList<SheduleType> getSheduleTypes() {
        ArrayList<SheduleType> types = null;
        try {
            SessionManager.beginTran();
            Collection<SheduleTypeEntity> ste = SessionManager.getAllObjects(new SheduleTypeEntity());
            types = new ArrayList<SheduleType>(ste.size());
            for (SheduleTypeEntity s : ste) types.add(EntityConverter.convertSheduleType(s));
            SessionManager.commit();
        } catch (Exception e) {
            e.printStackTrace();
            SessionManager.rollback();
            types = null;
        } finally {
            SessionManager.closeSession();
        }
        return types;
    }

    public boolean addRoute(Route r) {
        try {
            SessionManager.beginTran();
            Session s = getSession();
            RouteEntity re = EntityConverter.convertRoute(r);
            SheduleEntity sfe = re.getSheduleForward();
            SheduleEntity sbe = re.getSheduleBack();
            SessionManager.saveOrUpdateEntities(sfe, sbe, re);
            s.flush();
            if (sfe.getSheduleDays() != null && sfe.getSheduleDays().size() > 0)
                for (SheduleDaysEntity sde : sfe.getSheduleDays()) {
                    sde.setShedule(sfe);
                    s.save(sde);
                }
            if (sbe.getSheduleDays() != null && sbe.getSheduleDays().size() > 0)
                for (SheduleDaysEntity sde : sbe.getSheduleDays()) {
                    sde.setShedule(sbe);
                    s.save(sde);
                }
            if (re.isEnabled()) {
                Date currentDate = getCurrentDate();
                TrainStatusEntity statusPlanned = new TrainStatusEntity(BusinessLogic.PLANNED, "");
                // генерируем отправляющиеся поезда с текущего момента
                for (Timestamp dateFrom : generateDatesOfDeparture(sfe, currentDate, 20)) {
                    Timestamp dateTo = DateUtils.getDatePlusTime(dateFrom, sfe.getHoursInWay(), sfe.getMinutesInWay());
                    // порядок вагонов по умолчанию с головы
                    TrainEntity train = new TrainEntity(null, dateFrom, dateTo, sfe, statusPlanned, true);
                    s.save(train);
                }
                // генерируем прибывающие поезда с момента: текущее время - время в пути. (чтобы прибыл уже ближайший)
                currentDate = DateUtils.getDateMinusTime(currentDate, sbe.getHoursInWay(), sbe.getMinutesInWay());
                for (Timestamp dateFrom : generateDatesOfDeparture(sbe, currentDate, 20)) {
                    Timestamp dateTo = DateUtils.getDatePlusTime(dateFrom, sbe.getHoursInWay(), sbe.getMinutesInWay());
                    // порядок вагонов по умолчанию с головы
                    TrainEntity train = new TrainEntity(null, dateFrom, dateTo, sbe, statusPlanned, true);
                    s.save(train);
                }
            }
            SessionManager.commit();
            return true;
        } catch (Exception e) {
            e.printStackTrace();
            SessionManager.rollback();
            return false;
        } finally {
            SessionManager.closeSession();
        }
    }

    public boolean updateRoute(Route r) {
        try {
            SessionManager.beginTran();
            Session s = getSession();
            RouteEntity re = SessionManager.getEntityById(new RouteEntity(), r.getId());
            if (re == null) throw new Exception("Route not found");
            SheduleEntity sfe = re.getSheduleForward();
            SheduleEntity sbe = re.getSheduleBack();

            // удалим старые записи дней по которым работает расписание
            if (sfe.getSheduleDays() != null)
                for (SheduleDaysEntity sde : sfe.getSheduleDays()) s.delete(sde);
            if (sbe.getSheduleDays() != null)
                for (SheduleDaysEntity sde : sbe.getSheduleDays()) s.delete(sde);
            // удаляем запланированные поезда, сформированные и поезда в пути
            Integer[] statuses = new Integer[]{BusinessLogic.PLANNED, BusinessLogic.MAKED, BusinessLogic.IN_WAY};
            List tf = s.createQuery("from TrainEntity where shedule in (:sh) and trainStatus.id in (:status)").
                    setParameterList("sh", new Object[]{sfe, sbe}).setParameterList("status", statuses).list();
            // todo выносим удаление в отдельный метод
            for (Object t : tf) {
                TrainEntity train = (TrainEntity) t;
                // удаляем тела.
                for (TrainDetEntity tde : train.getTrainDets()) {
                    CarEntity car = tde.getCar();
                    car.setCarLocation(new CarLocationEntity(BusinessLogic.UNKNOWN, ""));
                    s.update(car);
                    s.delete(tde);
                }
                // историю локаций вагона, где фигурировал этот поезд
                for (CarHistoryEntity che : train.getCarHistories()) s.delete(che);
                // удаляем поезд с пути, если в записи нет вагона - удаляем запись.
                for (RoadDetEntity rde : train.getRoadDets()) {
                    if (rde.getCar() == null) {
                        s.delete(rde);
                    } else {
                        rde.setTrain(null);
                        s.update(rde);
                    }
                }
                // удаляем сам поезд
                s.delete(train);
            }
            // заставляем выполнить делит сейчас же
            s.flush();
            // и очищаем сессию, для удаления одинаковых идентификаторов
            s.clear();
            re = EntityConverter.convertRoute(r, re.getIdRoute(), sfe.getIdShedule(), sbe.getIdShedule());
            sfe = re.getSheduleForward();
            sbe = re.getSheduleBack();
            SessionManager.saveOrUpdateEntities(sbe, sfe, re);
            if (sfe.getSheduleDays() != null)
                for (SheduleDaysEntity sde : sfe.getSheduleDays()) SessionManager.saveOrUpdateEntities(sde);
            if (sbe.getSheduleDays() != null)
                for (SheduleDaysEntity sde : sbe.getSheduleDays()) SessionManager.saveOrUpdateEntities(sde);
            if (re.isEnabled()) {
                Date currentDate = getCurrentDate();
                TrainStatusEntity statusPlanned = new TrainStatusEntity(BusinessLogic.PLANNED, "");
                // генерируем отправляющиеся поезда с текущего момента
                for (Timestamp dateFrom : generateDatesOfDeparture(sfe, currentDate, 20)) {
                    Timestamp dateTo = DateUtils.getDatePlusTime(dateFrom, sfe.getHoursInWay(), sfe.getMinutesInWay());
                    // порядок вагонов по умолчанию с головы
                    TrainEntity train = new TrainEntity(null, dateFrom, dateTo, sfe, statusPlanned, true);
                    s.save(train);
                }
                // генерируем прибывающие поезда с момента: текущее время - время в пути. (чтобы прибыл уже ближайший)
                currentDate = DateUtils.getDateMinusTime(currentDate, sbe.getHoursInWay(), sbe.getMinutesInWay());
                for (Timestamp dateFrom : generateDatesOfDeparture(sbe, currentDate, 20)) {
                    Timestamp dateTo = DateUtils.getDatePlusTime(dateFrom, sbe.getHoursInWay(), sbe.getMinutesInWay());
                    // порядок вагонов по умолчанию с головы
                    TrainEntity train = new TrainEntity(null, dateFrom, dateTo, sbe, statusPlanned, true);
                    s.save(train);
                }
            }
            SessionManager.commit();
            return true;
        } catch (Exception e) {
            e.printStackTrace();
            SessionManager.rollback();
            return false;
        } finally {
            SessionManager.closeSession();
        }
    }

    public ArrayList<Car> getCars() {
        SessionManager.beginTran();
        ArrayList<Car> list = null;
        try {
            SessionManager.beginTran();
            Collection<CarEntity> objects = SessionManager.getAllObjects(new CarEntity());
            list = new ArrayList<Car>(objects.size());
            for (CarEntity ce : objects) {
                Car car = EntityConverter.convertCar(ce);
                list.add(car);
            }
            SessionManager.commit();
        } catch (Exception e) {
            e.printStackTrace();
            SessionManager.rollback();
            list = null;
        } finally {
            SessionManager.closeSession();
        }
        return list;
    }

    public Car getCarByNumber(int carNumber) {
        Car car = null;
        try {
            SessionManager.beginTran();
            CarEntity ce = SessionManager.getEntityById(new CarEntity(), carNumber);
            if (ce == null) throw new Exception("Вагон не найден!");
            car = EntityConverter.convertCar(ce);
            SessionManager.commit();
        } catch (Exception e) {
            e.printStackTrace();
            SessionManager.rollback();
            car = null;
        } finally {
            SessionManager.closeSession();
        }
        return car;
    }

    public ArrayList<CarType> getCarParentTypes() {
        ArrayList<CarType> types = null;
        try {
            SessionManager.beginTran();
            Criteria crit = getSession().createCriteria(CarTypeEntity.class).
                    add(Restrictions.isNull("carParentType"));
            List list = crit.list();
            types = new ArrayList<CarType>(list.size());
            for (Object o : list) {
                types.add(EntityConverter.convertCarType((CarTypeEntity) o));
            }
            SessionManager.commit();
        } catch (Exception e) {
            e.printStackTrace();
            SessionManager.rollback();
            types = null;
        } finally {
            SessionManager.closeSession();
        }
        return types;
    }

    public ArrayList<CarType> getCarSubTypes(CarType parentType) {
        ArrayList<CarType> types = null;
        try {
            SessionManager.beginTran();
            CarTypeEntity cte = SessionManager.getEntityById(new CarTypeEntity(), parentType.getIdType());
            types = new ArrayList<CarType>(cte.getCarSubTypes().size());
            for (CarTypeEntity subType : cte.getCarSubTypes()) {
                types.add(EntityConverter.convertCarType(subType));
            }
            SessionManager.commit();
        } catch (Exception e) {
            e.printStackTrace();
            SessionManager.rollback();
            types = null;
        } finally {
            SessionManager.closeSession();
        }
        return types;
    }

    public CarType getCarParentType(CarType subType) {
        try {
            SessionManager.beginTran();
            CarTypeEntity cte = SessionManager.getEntityById(new CarTypeEntity(), subType.getIdType());
            CarTypeEntity parentType = cte.getCarParentType();
            CarType ct = EntityConverter.convertCarType(parentType);
            SessionManager.commit();
            return ct;
        } catch (Exception e) {
            e.printStackTrace();
            SessionManager.rollback();
            return null;
        } finally {
            SessionManager.closeSession();
        }
    }

    public boolean addCar(Car car) {
        try {
            SessionManager.beginTran();
            CarEntity ce = SessionManager.getEntityById(new CarEntity(), car.getNumber());
            if (ce != null) throw new Exception("Вагон уже существует!");
            ce = EntityConverter.convertCar(car);
            SessionManager.saveOrUpdateEntities(ce);
            SessionManager.commit();
            return true;
        } catch (Exception e) {
            e.printStackTrace();
            SessionManager.rollback();
            return false;
        } finally {
            SessionManager.closeSession();
        }
    }

    public boolean editCar(Car car) {
        try {
            SessionManager.beginTran();
            CarEntity ce = SessionManager.getEntityById(new CarEntity(), car.getNumber());
            if (ce == null) throw new Exception("Вагона не существует!");
            getSession().evict(ce);
            ce = EntityConverter.convertCar(car);
            SessionManager.saveOrUpdateEntities(ce);
            SessionManager.commit();
            return true;
        } catch (Exception e) {
            e.printStackTrace();
            SessionManager.rollback();
            return false;
        } finally {
            SessionManager.closeSession();
        }
    }

    public ArrayList<RoadType> getRoadTypes() {
        ArrayList<RoadType> list = null;
        try {
            SessionManager.beginTran();
            Collection<RoadTypeEntity> types = SessionManager.getAllObjects(new RoadTypeEntity());
            list = new ArrayList<RoadType>(types.size());
            for (RoadTypeEntity rte : types) list.add(new RoadType(rte.getIdType(), rte.getTypeName()));
            SessionManager.commit();
        } catch (Exception e) {
            e.printStackTrace();
            SessionManager.rollback();
            list = null;
        } finally {
            SessionManager.closeSession();
        }
        return list;
    }

    public ArrayList<Road> getRoadsByType(RoadType roadType) {
        ArrayList<Road> list;
        try {
            SessionManager.beginTran();
            RoadTypeEntity rt = SessionManager.getEntityById(new RoadTypeEntity(), roadType.getId());
            Collection<RoadEntity> roads = rt.getRoads();
            list = new ArrayList<Road>(roads.size());
            for (RoadEntity road : roads) {
                list.add(EntityConverter.convertRoad(road));
            }
            SessionManager.commit();
        } catch (Exception e) {
            e.printStackTrace();
            SessionManager.rollback();
            list = null;
        } finally {
            SessionManager.closeSession();
        }
        return list;
    }

    public Train getTrainById(int idTrain) {
        try {
            SessionManager.beginTran();
            TrainEntity te = SessionManager.getEntityById(new TrainEntity(), idTrain);
            Train train = EntityConverter.convertTrain(te);
            SessionManager.commit();
            return train;
        } catch (Exception e) {
            e.printStackTrace();
            SessionManager.rollback();
            return null;
        } finally {
            SessionManager.closeSession();
        }
    }

    public Train getTrainByRoad(Road road) {
        Train train = null;
        try {
            SessionManager.beginTran();
            RoadEntity re = SessionManager.getEntityById(new RoadEntity(), road.getId());
            for (RoadDetEntity rde : re.getRoadDets()) {
                TrainEntity te = rde.getTrain();
                if (te != null) {
                    train = EntityConverter.convertTrain(te);
                    break;
                }
            }
            SessionManager.commit();
        } catch (Exception e) {
            e.printStackTrace();
            SessionManager.rollback();
        } finally {
            SessionManager.closeSession();
        }
        return train;
    }

    public ArrayList<Train> getTrainsOnRoads() {
        try {
            SessionManager.beginTran();
            Collection<RoadEntity> res = SessionManager.getAllObjects(new RoadEntity());
            ArrayList<RoadDetEntity> rdes = new ArrayList<RoadDetEntity>();
            for (RoadEntity re : res) rdes.addAll(re.getRoadDets());
            ArrayList<Train> list = new ArrayList<Train>();
            for (RoadDetEntity rde : rdes) {
                TrainEntity te = rde.getTrain();
                if (te != null) list.add(EntityConverter.convertTrain(te));
            }
            SessionManager.commit();
            return list;
        } catch (Exception e) {
            e.printStackTrace();
            SessionManager.rollback();
            return null;
        } finally {
            SessionManager.closeSession();
        }
    }

    public ArrayList<Train> getGoingTrains(int forHours) {
        try {
            SessionManager.beginTran();
            Timestamp time = DateUtils.getDatePlusTime(getCurrentDate(), forHours, 0);
            TrainStatusEntity tse = SessionManager.getEntityById(new TrainStatusEntity(), BusinessLogic.PLANNED);
            List se = getSession().createQuery(
                    "select se from RouteEntity as re join re.sheduleForward as se where re.enabled = true").list();
            Criteria crit = getSession().createCriteria(TrainEntity.class).
                    add(Restrictions.in("shedule", se)).add(Restrictions.eq("trainStatus", tse)).
                    add(Restrictions.le("dateFrom", time));
            ArrayList<Train> list = new ArrayList<Train>();
            for (Object te : crit.list()) {
                list.add(EntityConverter.convertTrain((TrainEntity) te));
            }
            SessionManager.commit();
            return list;
        } catch (Exception e) {
            e.printStackTrace();
            SessionManager.rollback();
            return null;
        } finally {
            SessionManager.closeSession();
        }
    }

    public boolean updateTrainOnRoad(Train train) throws Exception {
        try {
            SessionManager.beginTran();
            Session s = getSession();
            TrainEntity te = SessionManager.getEntityById(new TrainEntity(), train.getId());
            RoadEntity re = SessionManager.getEntityById(new RoadEntity(), train.getRoad().getId());
            if (EntityConverter.isTrainGoing(te) && te.getTrainStatus().getIdStatus() == BusinessLogic.IN_WAY)
                throw new Exception("Поезд уже в пути!");
            if (te.getTrainStatus().getIdStatus() == BusinessLogic.DESTROYED)
                throw new Exception("Поезд уже расформирован!");
            // если этот поезд не стоит на этом пути
            if (te.getRoadDets().size() == 0 || !te.getRoadDets().iterator().next().getRoad().equals(re)) {
                // если на пути стоит поезд - ругаемся
                if (re.getRoadDets() != null && re.getRoadDets().size() > 0) {
                    for (RoadDetEntity rde : re.getRoadDets()) {
                        if (rde.getTrain() != null) throw new Exception("Путь занят!");
                    }
                }
                // удаляем поезд со своего старого пути, если в записи нет вагона - удаляем запись.
                for (RoadDetEntity rde : te.getRoadDets()) {
                    if (rde.getCar() == null) {
                        s.delete(rde);
                    } else {
                        rde.setTrain(null);
                        s.update(rde);
                    }
                }
                // ставим поезд на новый путь
                RoadDetEntity rde = new RoadDetEntity(re, null, te);
                SessionManager.saveOrUpdateEntities(rde);
            }
            // поезд уходящий от нас - статус "Сформирован"
            if (EntityConverter.isTrainGoing(te)) te.setTrainStatus(new TrainStatusEntity(BusinessLogic.MAKED, ""));
                // поезд прибывающий - статус "Прибыл"
            else te.setTrainStatus(new TrainStatusEntity(BusinessLogic.ARRIVED, ""));
            te.setTrainChief(train.getChief());
            te.setCarFromHead(train.isCarFromHead());
            s.saveOrUpdate(te);
            // todo создавать новые запланированные поезда!
            SheduleEntity se = te.getShedule();
            if (EntityConverter.getPlannedTrainsCount(se, s) < 20) {
                int id = EntityConverter.getMaxPlannedTrainId(se, s);
                Date lastDate;
                if (id != te.getIdTrain()) {
                    lastDate = SessionManager.getEntityById(new TrainEntity(), id).getDateFrom();
                } else {
                    lastDate = te.getDateFrom();
                }
                TrainStatusEntity statusPlanned = new TrainStatusEntity(BusinessLogic.PLANNED, "");
                // генерируем 10 новых поездов начиная с даты последней отправки + 1 час.
                for (Timestamp dateFrom : generateDatesOfDeparture(se, DateUtils.getDatePlusTime(lastDate, 1, 0), 10)) {
                    Timestamp dateTo = DateUtils.getDatePlusTime(dateFrom, se.getHoursInWay(), se.getMinutesInWay());
                    // порядок вагонов по умолчанию с головы
                    TrainEntity newTrain = new TrainEntity(null, dateFrom, dateTo, se, statusPlanned, true);
                    s.save(newTrain);
                }
                s.evict(statusPlanned);
            }
            // обновляем вагоны
            Date currentDate = getCurrentDate();
            java.sql.Date date = new java.sql.Date(currentDate.getTime());
            for (Car car : train.getCarsIn()) {
                // признак того что этот вагон уже в составе этого поезда
                boolean isOldCar = false;
                CarEntity ce = SessionManager.getEntityById(new CarEntity(), car.getNumber());
                for (TrainDetEntity tde : ce.getTrainDets()) {
                    // если вагон в составе этого поезда, заполняем признак, для этого вагона мы больше ничего делать не будем.
                    if (tde.getIdTrain() == te.getIdTrain()) {
                        isOldCar = true;
                        s.evict(tde);
                        break;
                        // если статус поезда не расформирован, вагон не может быть использован
                    } else if (tde.getTrain().getTrainStatus().getIdStatus() != BusinessLogic.DESTROYED) {
                        throw new Exception("Вагон " + ce.getCarNumber() + " уже в составе другого поезда!");
                    }
                    s.evict(tde);
                }
                // вагон еще не в составе поезда, редактируем его дислокацию и историю
                if (!isOldCar) {
                    for (RepairEntity rep : ce.getRepairs()) {
                        if (rep.getDateEnd() == null) throw new Exception("Вагон " + ce.getCarNumber() + " в ремонте!");
                    }
                    // удаляем вагон со старого пути, если на нем нет поезда - удаляем запись
                    for (RoadDetEntity rde : ce.getRoadDets()) {
                        if (rde.getTrain() == null) {
                            SessionManager.deleteEntities(rde);
                        } else {
                            rde.setCar(null);
                            SessionManager.saveOrUpdateEntities(rde);
                        }
                    }
                    CarLocationEntity cle = EntityConverter.convertCarLocation(new CarLocation(BusinessLogic.IN_TRAIN, ""));
                    ce.setCarLocation(cle);
                    TrainDetEntity tde = new TrainDetEntity(ce, te, car.getCarNumberInTrain());
                    CarHistoryEntity che = new CarHistoryEntity(date, cle, te, null, ce, null);
                    SessionManager.saveOrUpdateEntities(ce, tde, che);
                } else { // обновляем порядковый номер вагона
                    TrainDetEntity tde = new TrainDetEntity(ce, te, car.getCarNumberInTrain());
                    s.update(tde);
                }
            }
            SessionManager.commit();
            return true;
        } catch (Exception e) {
            e.printStackTrace();
            SessionManager.rollback();
            throw e;
        } finally {
            SessionManager.closeSession();
        }
    }

    public ArrayList<Train> getArrivingTrains(int forHours) {
        try {
            SessionManager.beginTran();
            Timestamp time = DateUtils.getDatePlusTime(getCurrentDate(), forHours, 0);
            TrainStatusEntity tse = SessionManager.getEntityById(new TrainStatusEntity(), BusinessLogic.IN_WAY);
            List se = getSession().createQuery(
                    "select se from RouteEntity as re join re.sheduleBack as se where re.enabled = true").list();
            Criteria crit = getSession().createCriteria(TrainEntity.class).
                    add(Restrictions.in("shedule", se)).add(Restrictions.eq("trainStatus", tse)).
                    add(Restrictions.le("dateTo", time));
            ArrayList<Train> list = new ArrayList<Train>();
            for (Object te : crit.list()) {
                list.add(EntityConverter.convertTrain((TrainEntity) te));
            }
            SessionManager.commit();
            return list;
        } catch (Exception e) {
            e.printStackTrace();
            SessionManager.rollback();
            return null;
        } finally {
            SessionManager.closeSession();
        }
    }

    public ArrayList<CarLocation> getCarLocations() {
        try {
            SessionManager.beginTran();
            Collection<CarLocationEntity> cles = SessionManager.getAllObjects(new CarLocationEntity());
            ArrayList<CarLocation> list = new ArrayList<CarLocation>(cles.size());
            for (CarLocationEntity cle : cles) {
                list.add(EntityConverter.convertCarLocation(cle));
            }
            SessionManager.commit();
            return list;
        } catch (Exception e) {
            e.printStackTrace();
            SessionManager.rollback();
            return null;
        } finally {
            SessionManager.closeSession();
        }
    }

    public boolean setCarLocation(Car car, Road road, Repair repair) throws Exception {
        try {
            SessionManager.beginTran();
            CarEntity ce = SessionManager.getEntityById(new CarEntity(), car.getNumber());
            Date currentDate = getCurrentDate();
            java.sql.Date date = new java.sql.Date(currentDate.getTime());
            Timestamp time = new Timestamp(currentDate.getTime());
            Session s = getSession();
            // заканчиваем ремонты
            List list = s.createQuery("from RepairEntity as re where re.car = :car and re.dateEnd is null")
                    .setParameter("car", ce).list();
            for (Object o : list) {
                RepairEntity rep = (RepairEntity) o;
                rep.setDateEnd(time);
                s.update(rep);
            }
            // удаляем вагон с пути, если на нем нет поезда - удаляем запись
            for (RoadDetEntity rde : ce.getRoadDets()) {
                if (rde.getTrain() == null) {
                    SessionManager.deleteEntities(rde);
                } else {
                    rde.setCar(null);
                    SessionManager.saveOrUpdateEntities(rde);
                }
            }
            // удаляем вагон из текущего поезда, из прибывших и расформированных не удаляем, для истории
            Integer[] statuses = new Integer[]{BusinessLogic.DESTROYED};
            list = s.createQuery("from TrainDetEntity as td where td.car = :c and td.train.trainStatus.id not in (:s)")
                    .setParameter("c", ce).setParameterList("s", statuses).list();
            for (Object o : list) s.delete(o);
            // новая запись в истории и новая дислокация.
            CarHistoryEntity che = null;
            CarLocationEntity newCle = null;
            // записи о пути и ремонте, если они будут заполнены по условию новой локации вагона, мы их сохраним
            RepairEntity rep = null;
            RoadDetEntity rde = null;
            switch (car.getCarLocation().getIdLocation()) {
                case BusinessLogic.IN_TRAIN:
                    throw new Exception("Вагон нельзя включить в поезд!");
                case BusinessLogic.ON_ROAD:
                    RoadEntity re = EntityConverter.convertRoad(road);
                    rde = new RoadDetEntity(re, ce, null);
                    newCle = new CarLocationEntity(BusinessLogic.ON_ROAD, "");
                    che = new CarHistoryEntity(date, newCle, null, re, ce, null);
                    break;
                case BusinessLogic.UNKNOWN:
                    newCle = new CarLocationEntity(BusinessLogic.UNKNOWN, "");
                    che = new CarHistoryEntity(date, newCle, null, null, ce, null);
                    break;
                case BusinessLogic.REPAIR:
                    rep = EntityConverter.convertRepair(repair);
                    // идентификатор в налл, он должен сохраниться в базе, а геша туда мог какую нибудь ню записать.
                    rep.setIdRepair(null);
                    rep.setDateBegin(time);
                    rep.setDateEnd(null);
                    // путь на котором прозводится ремонт
                    if (repair.getRoad() != null) {
                        RoadEntity reproad = EntityConverter.convertRoad(repair.getRoad());
                        rde = new RoadDetEntity(reproad, ce, null);
                    }
                    newCle = EntityConverter.convertCarLocation(new CarLocation(BusinessLogic.REPAIR, ""));
                    che = new CarHistoryEntity(date, newCle, null, null, ce, rep);
                    break;
            }
            ce.setCarLocation(newCle);
            SessionManager.saveOrUpdateEntities(ce);
            if (rep != null) SessionManager.saveOrUpdateEntities(rep);
            if (rde != null) SessionManager.saveOrUpdateEntities(rde);
            SessionManager.saveOrUpdateEntities(che);
            SessionManager.commit();
            return true;
        } catch (Exception e) {
            e.printStackTrace();
            SessionManager.rollback();
            throw e;
        } finally {
            SessionManager.closeSession();
        }
    }

    public Road getRoadByCar(Car car) {
        Road road = null;
        try {
            SessionManager.beginTran();
            CarEntity ce = SessionManager.getEntityById(new CarEntity(), car.getNumber());
            for (RoadDetEntity rde : ce.getRoadDets()) {
                if (rde != null) {
                    road = EntityConverter.convertRoad(rde.getRoad());
                    break;
                }
            }
            SessionManager.commit();
        } catch (Exception e) {
            e.printStackTrace();
            SessionManager.rollback();
        } finally {
            SessionManager.closeSession();
        }
        return road;
    }

    public ArrayList<RepairType> getRepairTypes() {
        try {
            SessionManager.beginTran();
            Collection<RepairTypeEntity> rtes = SessionManager.getAllObjects(new RepairTypeEntity());
            ArrayList<RepairType> list = new ArrayList<RepairType>(rtes.size());
            for (RepairTypeEntity rte : rtes) {
                list.add(EntityConverter.convertRepairType(rte));
            }
            SessionManager.commit();
            return list;
        } catch (Exception e) {
            e.printStackTrace();
            SessionManager.rollback();
            return null;
        } finally {
            SessionManager.closeSession();
        }
    }

    public Repair getRepairByCar(Car car) {
        try {
            SessionManager.beginTran();
            Repair repair = null;
            CarEntity ce = SessionManager.getEntityById(new CarEntity(), car.getNumber());
            for (RepairEntity re : ce.getRepairs()) {
                if (re.getDateEnd() == null) repair = EntityConverter.convertRepair(re);
            }
            SessionManager.rollback();
            return repair;
        } catch (Exception e) {
            e.printStackTrace();
            SessionManager.rollback();
            return null;
        } finally {
            SessionManager.closeSession();
        }
    }

    public boolean updateRepair(Repair repair) {
        try {
            SessionManager.beginTran();
            Session s = getSession();
            RepairEntity re = SessionManager.getEntityById(new RepairEntity(), repair.getIdRepair());
            // удаляем старую запись о пути, вдруг путь обновился! хотя с чего бы, но геша просит
            if (re.getRoad() != null)
                for (RoadDetEntity rde : re.getRoad().getRoadDets()) {
                    if (rde.getCar() != null && rde.getCar().equals(re.getCar())) {
                        if (rde.getTrain() == null) {
                            s.delete(rde);
                        } else {
                            rde.setCar(null);
                            s.update(rde);
                        }
                    }
                }

            s.flush();
            // клирим сессию для очистки идентификаторов, т.к. мы не обновляем поля set'ами, а создаем новый объект.
            s.clear();
            re = EntityConverter.convertRepair(repair);
            RoadEntity newRoad = re.getRoad();
            if (newRoad != null) {
                s.save(new RoadDetEntity(newRoad, re.getCar(), null));
            }
            s.update(re);
            SessionManager.commit();
            return true;
        } catch (Exception e) {
            e.printStackTrace();
            SessionManager.rollback();
            return false;
        } finally {
            SessionManager.closeSession();
        }
    }

    public ArrayList<Car> getCarsOnRoad(Road road) {
        try {
            SessionManager.beginTran();
            RoadEntity re = SessionManager.getEntityById(new RoadEntity(), road.getId());
            ArrayList<Car> list = new ArrayList<Car>();
            for (RoadDetEntity rde : re.getRoadDets()) {
                if (rde.getCar() != null) list.add(EntityConverter.convertCar(rde.getCar()));
            }
            SessionManager.commit();
            return list;
        } catch (Exception e) {
            e.printStackTrace();
            SessionManager.rollback();
            return null;
        } finally {
            SessionManager.closeSession();
        }
    }

    public Train getTrainByCar(Car car) {
        Train train = null;
        try {
            SessionManager.beginTran();
            CarEntity ce = SessionManager.getEntityById(new CarEntity(), car.getNumber());
            for (TrainDetEntity tde : ce.getTrainDets()) {
                if (tde.getTrain().getTrainStatus().getIdStatus() != BusinessLogic.DESTROYED) {
                    train = EntityConverter.convertTrain(tde.getTrain());
                    break;
                }
            }
            SessionManager.commit();
        } catch (Exception e) {
            e.printStackTrace();
            SessionManager.rollback();
        } finally {
            SessionManager.closeSession();
        }
        return train;
    }

    public ArrayList<CarHistory> getCarHistory(Car car) {
        try {
            SessionManager.beginTran();
            List l = getSession().createQuery("from CarHistoryEntity as che where che.car.id = :car order by che.date")
                    .setInteger("car", car.getNumber()).list();
            ArrayList<CarHistory> list = new ArrayList<CarHistory>(l.size());
            for (Object che : l) {
                list.add(EntityConverter.convertCarHistory((CarHistoryEntity) che));
            }
            SessionManager.commit();
            return list;
        } catch (Exception e) {
            e.printStackTrace();
            SessionManager.rollback();
            return null;
        } finally {
            SessionManager.closeSession();
        }
    }

    public ArrayList<Train> getTrainsForPeriod(Date dBegin, Date dEnd) {
        try {
            SessionManager.beginTran();
            List l = getSession().createQuery("from TrainEntity as te where te.dateFrom >= :dFrom and te.dateTo < :dTo")
                    .setTimestamp("dFrom", dBegin).setTimestamp("dTo", dEnd).list();
            ArrayList<Train> list = new ArrayList<Train>(l.size());
            for (Object train : l) {
                list.add(EntityConverter.convertTrain((TrainEntity) train));
            }
            SessionManager.commit();
            return list;
        } catch (Exception e) {
            e.printStackTrace();
            SessionManager.rollback();
            return null;
        } finally {
            SessionManager.closeSession();
        }
    }

    public ArrayList<Car> getFreeCars() {
        ArrayList<Car> list = null;
        try {
            SessionManager.beginTran();
            Integer[] locations = new Integer[]{BusinessLogic.UNKNOWN, BusinessLogic.ON_ROAD};
            List l = getSession().createQuery("from CarEntity as c where c.carLocation.id in (:loc)")
                    .setParameterList("loc", locations).list();
            list = new ArrayList<Car>(l.size());
            for (Object car : l) list.add(EntityConverter.convertCar((CarEntity) car));
            SessionManager.commit();
        } catch (Exception e) {
            e.printStackTrace();
            SessionManager.rollback();
            list = null;
        } finally {
            SessionManager.closeSession();
        }
        return list;
    }

    public boolean isRoadReadyForTrain(Train train, Road road) {
        boolean isReady = true;
        try {
            SessionManager.beginTran();
            RoadEntity re = SessionManager.getEntityById(new RoadEntity(), road.getId());
            for (RoadDetEntity rde : re.getRoadDets()) {
                // если на пути есть поезд, и это не наш поезд, значит путь не свободен.
                if (rde.getTrain() != null && rde.getTrain().getIdTrain() != train.getId()) isReady = false;
            }
            SessionManager.commit();
        } catch (Exception e) {
            e.printStackTrace();
            SessionManager.rollback();
        } finally {
            SessionManager.closeSession();
        }
        return isReady;
    }

    public boolean destroyTrain(Train train) throws Exception {
        boolean ok = true;
        try {
            SessionManager.beginTran();
            TrainEntity te = SessionManager.getEntityById(new TrainEntity(), train.getId());
            if (te.getTrainStatus().getIdStatus() != BusinessLogic.ARRIVED) throw new Exception("Поезд еще не прибыл!");
            // удаляем поезд с пути, если в записи нет вагона - удаляем запись.
            for (RoadDetEntity rde : te.getRoadDets()) {
                if (rde.getCar() == null) {
                    getSession().delete(rde);
                } else {
                    rde.setTrain(null);
                    getSession().update(rde);
                }
            }
            // освобождаем вагоны - ставим дислокацию "Неизвестно"
            for (TrainDetEntity tde : te.getTrainDets()) {
                CarEntity ce = tde.getCar();
                ce.setCarLocation(new CarLocationEntity(BusinessLogic.UNKNOWN, ""));
                getSession().update(ce);
            }
            // ставим статус расформирован
            te.setTrainStatus(new TrainStatusEntity(BusinessLogic.DESTROYED, ""));
            getSession().update(te);
            SessionManager.commit();
        } catch (Exception e) {
            e.printStackTrace();
            ok = false;
            SessionManager.rollback();
            throw e;
        } finally {
            SessionManager.closeSession();
        }
        return ok;
    }

    public boolean deleteCar(Car car) {
        boolean ok = true;
        try {
            SessionManager.beginTran();
            CarEntity ce = SessionManager.getEntityById(new CarEntity(), car.getNumber());
            Session s = getSession();
            for (CarHistoryEntity che : ce.getCarHistories()) s.delete(che);
            for (RepairEntity re : ce.getRepairs()) s.delete(re);
            for (RoadDetEntity rde : ce.getRoadDets()) {
                if (rde.getTrain() == null) {
                    s.delete(rde);
                } else {
                    rde.setCar(null);
                    s.update(rde);
                }
            }
            for (TrainDetEntity tde : ce.getTrainDets()) s.delete(tde);
            s.delete(ce);
            SessionManager.commit();
        } catch (Exception e) {
            e.printStackTrace();
            ok = false;
            SessionManager.rollback();
        } finally {
            SessionManager.closeSession();
        }
        return ok;
    }

    public boolean deleteRoute(Route route) throws Exception {
        boolean ok = true;
        try {
            SessionManager.beginTran();
            RouteEntity re = SessionManager.getEntityById(new RouteEntity(), route.getId());
            Session s = getSession();
            SheduleEntity se = re.getSheduleForward();
            SheduleEntity sbe = re.getSheduleBack();
            s.delete(re);
            // прогоняем 2 раза, сначала для прямого расписания, потом для обратного
            for (int i = 0; i < 2; i++) {
                for (SheduleDaysEntity sde : se.getSheduleDays()) s.delete(sde);
                for (TrainEntity te : se.getTrains()) {
                    int status = te.getTrainStatus().getIdStatus();
                    if (status == BusinessLogic.ARRIVED) throw new Exception("Сначала расформируйте прибывший поезд!");
                    for (TrainDetEntity tde : te.getTrainDets()) {
                        if (status != BusinessLogic.DESTROYED) {
                            tde.getCar().setCarLocation(new CarLocationEntity(BusinessLogic.UNKNOWN, ""));
                            s.update(tde.getCar());
                        }
                        s.delete(tde);
                    }
                    for (CarHistoryEntity che : te.getCarHistories()) s.delete(che);
                    for (RoadDetEntity rde : te.getRoadDets()) {
                        if (rde.getCar() == null) {
                            s.delete(rde);
                        } else {
                            rde.setTrain(null);
                            s.update(rde);
                        }
                    }
                    s.delete(te);
                }
                s.delete(se);
                // прогоняем для обратного расписания то же самое
                se = sbe;
            }
            SessionManager.commit();
        } catch (Exception e) {
            e.printStackTrace();
            ok = false;
            SessionManager.rollback();
        } finally {
            SessionManager.closeSession();
        }
        return ok;
    }

    public ArrayList<Car> getPlanCarForTrain(Train train) {
        ArrayList<Car> list = null;
        try {
            SessionManager.beginTran();
            TrainEntity te = SessionManager.getEntityById(new TrainEntity(), train.getId());
            SheduleEntity se = te.getShedule();
            // расписание обратное нашему
            SheduleEntity seSource;
            if (EntityConverter.isTrainGoing(te)) {
                seSource = se.getRoutesBySheduleForward().toArray(new RouteEntity[]{null})[0].getSheduleBack();
            } else seSource = se.getRoutesBySheduleBack().toArray(new RouteEntity[]{null})[0].getSheduleForward();
            // ищем ближайший прибывший или расформированный поезд.
            Integer[] statuses = new Integer[]{BusinessLogic.ARRIVED, BusinessLogic.DESTROYED};
            Object id = getSession().createQuery("select max(te.idTrain) from TrainEntity as te where te.shedule.id " +
                    "= :idSE and te.trainStatus.id in (:status) and te.dateTo < :dFrom").
                    setInteger("idSE", seSource.getIdShedule()).
                    setParameterList("status", statuses).
                    setParameter("dFrom", te.getDateFrom()).uniqueResult();
            if (id == null) list = null;
            else {
                TrainEntity teSource = SessionManager.getEntityById(new TrainEntity(), (Integer) id);
                list = new ArrayList<Car>(teSource.getTrainDets().size());
                for (TrainDetEntity tde : teSource.getTrainDets()) {
                    CarEntity ce = tde.getCar();
                    int idLoc = ce.getCarLocation().getIdLocation();
                    // вагоны в поезде и в ремонте не показываем
                    if (idLoc != BusinessLogic.IN_TRAIN && idLoc != BusinessLogic.REPAIR)
                        list.add(EntityConverter.convertCar(ce));
                }
            }
            SessionManager.commit();
        } catch (Exception e) {
            e.printStackTrace();
            SessionManager.rollback();
            list = null;
        } finally {
            SessionManager.closeSession();
        }
        return list;
    }

    public Collection<Timestamp> generateDatesOfDeparture(SheduleEntity shedule, Date dateBegin, int count) {
        GregorianCalendar firstDate = new GregorianCalendar();
        firstDate.setTime(dateBegin);
        firstDate.set(Calendar.HOUR_OF_DAY, DateUtils.getHours(shedule.getTimeFrom()));
        firstDate.set(Calendar.MINUTE, DateUtils.getMinutes(shedule.getTimeFrom()));
        if (dateBegin.getTime() > firstDate.getTimeInMillis()) firstDate.add(Calendar.DAY_OF_MONTH, 1);
        ArrayList<Integer> days = new ArrayList<Integer>();
        int calendarType = Calendar.DAY_OF_MONTH;
        switch (shedule.getSheduleType().getIdSheduleType()) {
            case BusinessLogic.NONPAIR:
                List<Integer> d = Arrays.asList(1, 3, 5, 7, 9, 11, 13, 15, 17, 19, 21, 23, 25, 27, 29, 31);
                days.addAll(d);
                break;
            case BusinessLogic.PAIR:
                List<Integer> dd = Arrays.asList(2, 4, 6, 8, 10, 12, 14, 16, 18, 20, 22, 24, 26, 28, 30);
                days.addAll(dd);
                break;
            case BusinessLogic.DAYS_WEEK:
                calendarType = Calendar.DAY_OF_WEEK;
            case BusinessLogic.DAYS_MONTH:
                for (SheduleDaysEntity sd : shedule.getSheduleDays()) {
                    days.add(sd.getDay());
                }
                break;
        }
        return DateUtils.getDates(firstDate.getTime(), days, count, calendarType);
    }

    public void test() {
        SheduleEntity e = (SheduleEntity) SessionManager.getAllObjects(new SheduleEntity()).toArray()[0];
        GregorianCalendar c = new GregorianCalendar();
        c.setTime(new Date());
        c.set(Calendar.HOUR_OF_DAY, DateUtils.getHours(e.getTimeFrom()));
        c.set(Calendar.MINUTE, DateUtils.getMinutes(e.getTimeFrom()));
        System.out.println(c.getTime());
    }
}