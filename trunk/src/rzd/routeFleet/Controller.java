package rzd.routeFleet;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;

import rzd.ModelTable;


import javax.swing.*;
import java.util.ArrayList;

import rzd.model.Model;
import rzd.model.objects.Route;
import rzd.utils.MakerDefaultTextInField;

/**
 * Created by IntelliJ IDEA.
 * User: ЧерныхЕА
 * Date: 12.09.2010
 * Time: 17:43:47
 * To change this template use File | Settings | File Templates.
 */
public class Controller implements ActionListener, MouseListener {

    private PRoute pRoute;
    private DRouteEdit dEditRoute;
    private JPopupMenu menuRoute;
    private JMenuItem editRoute;
    private JMenuItem deleteRoute;

    public Controller(PRoute p) {
        this.pRoute = p;
        dEditRoute = new DRouteEdit(null, true);

        menuRoute = new JPopupMenu();
        editRoute = new JMenuItem("Редактировать", new ImageIcon(getClass().getResource("/rzd/resurce/bt5.gif")));
        editRoute.addActionListener(this);
        deleteRoute = new JMenuItem("Удалить", new ImageIcon(getClass().getResource("/rzd/resurce/bt12.gif")));
        deleteRoute.addActionListener(this);
        menuRoute.add(editRoute);
        menuRoute.add(deleteRoute);
        new MakerDefaultTextInField("Поиск по номеру маршрута", pRoute.fSearch);
        update();

    }

    public void update() {
        try {
            ((ModelTable) pRoute.tRoute.getModel()).setDate(getRoutesTabView());
        } catch (Exception e) {
            JOptionPane.showMessageDialog(pRoute, e.getMessage());
            e.printStackTrace();  //To change body of catch statement use File | Settings | File Templates.
        }
    }

    public void mouseClicked(MouseEvent e) {
        if (e.getSource() == pRoute.tRoute && e.getButton() == 3) {
            int row = pRoute.tRoute.rowAtPoint(e.getPoint());
            pRoute.tRoute.addRowSelectionInterval(row, row);
            menuRoute.show(pRoute.tRoute, e.getX(), e.getY());
        }
    }

    public void mousePressed(MouseEvent e) {
        //To change body of implemented methods use File | Settings | File Templates.
    }

    public void mouseReleased(MouseEvent e) {
        //To change body of implemented methods use File | Settings | File Templates.
    }

    public void mouseEntered(MouseEvent e) {
        //To change body of implemented methods use File | Settings | File Templates.
    }

    public void mouseExited(MouseEvent e) {
        //To change body of implemented methods use File | Settings | File Templates.
    }

    public void actionPerformed(ActionEvent e) {
        if (e.getSource() == pRoute.bCreateRoute) {
            addRoute();
        } else if (e.getSource() == editRoute) {
            editRoute();
        } else if (e.getSource() == deleteRoute) {
            deleteRoute();
        }
    }

    private void addRoute() {
        dEditRoute.setLocationRelativeTo(pRoute);
        Route data = dEditRoute.open(null, Model.getModel().getSheduleTypes());
        if (data != null) {
            try {
                boolean b = Model.getModel().addRoute(data);
                if (b) {
                    JOptionPane.showMessageDialog(pRoute, "Расписание успешно добавлено.");
                    update();
                } else {
                    JOptionPane.showMessageDialog(pRoute, "Ошибка...попробуйте еще раз.");
                }
            } catch (Exception e) {
                JOptionPane.showMessageDialog(pRoute, e.getMessage());
                e.printStackTrace();
            }
        }
    }

    private void editRoute() {
        int row = pRoute.tRoute.getSelectedRow();
        if (row != -1) {
            //  System.out.println(pRoute.tRoute.getValueAt(row, 0).toString());
            Route data = Model.getModel().getRouteById(new Integer(pRoute.tRoute.getValueAt(row, 0).toString()));
            dEditRoute.setLocationRelativeTo(pRoute);
            data = dEditRoute.open(data, Model.getModel().getSheduleTypes());
            //   System.out.println("editRoute");
            if (data != null) {
                try {
                    //          System.out.println("!!updateRoute");
                    boolean b = Model.getModel().updateRoute(data);
                    if (b) {
                        JOptionPane.showMessageDialog(pRoute, "Расписание успешно изменено.");
                        ((ModelTable) pRoute.tRoute.getModel()).setDate(getRoutesTabView());
                    } else {
                        JOptionPane.showMessageDialog(pRoute, "Ошибка...попробуйте еще раз.");
                    }
                } catch (Exception e) {
                    JOptionPane.showMessageDialog(pRoute, e.getMessage());
                    e.printStackTrace();  //To change body of catch statement use File | Settings | File Templates.
                }
            }
        }
    }

    private void deleteRoute() {
//        int row = pRoute.tRoute.getSelectedRow();
//        if (row != -1) {
//
//            try {
//                boolean b = Model.getModel().removeRoute(data);
//                if (b) {
//                    ((ModelTable) pTrains.tRoute.getModel()).setDate(getRoutesTabView());
//                }
//            } catch (Exception e) {
//                JOptionPane.showMessageDialog(pTrains, e.getMessage());
//                e.printStackTrace();  //To change body of catch statement use File | Settings | File Templates.
//            }
//        }
    }

    //Методы конверторы
    private ArrayList<Object[]> getRoutesTabView() {
        ArrayList<Route> routes = Model.getModel().getRoutes();
        if (routes != null) {
            ArrayList<Object[]> res = new ArrayList<Object[]>(routes.size());
            for (Route r : routes) {
                Object[] o = new Object[5];
                o[0] = r.getId();
                o[1] = r.getNumberForward();
                o[2] = r.getNumberBack();
                o[3] = r.getCityFrom();
                o[4] = r.getCityTo();
                res.add(o);
            }
            res.add(new Object[]{"ID", "Номер маршрута", "Номер обратного маршрута", "Станция отправления", "Станция назначения"});
            return res;
        }
        return null;
    }
//
//    public ArrayList<Object[]> getScheduleTabView() {
//        ArrayList<Shedule> shedules = TestModel.get().getSchedules();
//        if (shedules != null) {
//            ArrayList<Object[]> res = new ArrayList<Object[]>(shedules.size());
//            for (Shedule s : shedules) {
//                Route r = TestModel.get().getRouteBySchedule(s);
//                Object[] o = new Object[7];
//                o[0] = s.getId();
//                o[1] = s.getTimeDeparture();
//                o[2] = s.getTimeDestination();
////                o[3] = s.getDateBegin();
////                o[4] = s.getDayMove();
////                o[5] = s.getDayStop();
//                if (r != null)
//                    o[6] = r.toString();
//                else
//                    o[6] = "Маршрут не назначен";
//                res.add(o);
//            }
//            res.add(new Object[]{"ID", "Время отправления", "Время прибытия", "Начало действия маршрута", "Время в пути", "Время простоя", "Маршрут"});
//            return res;
//        }
//        return null;
//    }

//    public Route getRouteByTabRow(int row) {
//        if (row < 0 || row > pTrains.tRoute.getModel().getRowCount()) return null;
//        return new Route(
//                new Integer(pTrains.tRoute.getModel().getValueAt(row, 0).toString()),
//                pTrains.tRoute.getModel().getValueAt(row, 1).toString(),
//                pTrains.tRoute.getModel().getValueAt(row, 2).toString(),
//                pTrains.tRoute.getModel().getValueAt(row, 2).toString()
//        );
//    }

//    public Shedule getScheduleByTabRow(int row) {
//        if (row < 0 || row > pTrains.tSchedule.getModel().getRowCount()) return null;
//        return new Shedule(
//                new Integer(pTrains.tSchedule.getModel().getValueAt(row, 0).toString()),
//                Utils.convertStrToTime(pTrains.tSchedule.getModel().getValueAt(row, 1).toString()),
//                Utils.convertStrToTime(pTrains.tSchedule.getModel().getValueAt(row, 2).toString()),
//                Utils.convertStrToDate(pTrains.tSchedule.getModel().getValueAt(row, 3).toString()),
//                new Integer(pTrains.tSchedule.getModel().getValueAt(row, 4).toString()),
//                new Integer(pTrains.tSchedule.getModel().getValueAt(row, 5).toString())
//        );
//    }
}
