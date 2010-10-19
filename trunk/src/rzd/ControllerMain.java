/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package rzd;

import rzd.MainFrame;
import rzd.carFleet.PCar;
import rzd.carFleet.PCarInformation;
import rzd.dispStatinonFleet.PDispStation;
import rzd.dispStatinonFleet.PTrainInformation;
import rzd.model.objects.Car;
import rzd.model.objects.Train;
import rzd.routeFleet.PRoute;
import rzd.scheduleFleet.PSchedule;
import rzd.stationFleet.PStationFleet;

import javax.swing.*;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;

/**
 * @author ЧерныхЕА
 */
public class ControllerMain implements ChangeListener, ActionListener, MouseListener {

    private final static ControllerMain cm = new ControllerMain();
    private MainFrame mf;
    private PStationFleet pStationFleet;
    private PSchedule pSchedule;
    private PDispStation pDispStation;
    private PCar pCars;
    private PRoute pRoute;
    private JPopupMenu popCarInf;
    private PCarInformation pCarInformation;
    private JPopupMenu popTrainInf;
    private PTrainInformation pTrainInformation;
    private DLoading dLoading;

    public static ControllerMain getInstans() {
        return cm;
    }

    private ControllerMain() {
    }

    public void init() {
        mf = new MainFrame();
        mf.setVisible(true);
        dLoading = new DLoading(mf, true);
        pStationFleet = new PStationFleet();
        pSchedule = new PSchedule();
        pDispStation = new PDispStation();
        pCars = new PCar();
        pRoute = new PRoute();
        mf.tabbedtMain.add("Станция", pStationFleet);
        mf.tabbedtMain.add("Расписание", pSchedule);
        mf.tabbedtMain.add("Диспетчер станции", pDispStation);
        mf.tabbedtMain.add("Парк вагонов", pCars);
        mf.tabbedtMain.add("Маршруты", pRoute);
        popCarInf = new JPopupMenu();
        pCarInformation = new PCarInformation();
        popCarInf.add(pCarInformation);
        popTrainInf = new JPopupMenu();
        pTrainInformation = new PTrainInformation();
        popTrainInf.add(pTrainInformation);
    }


    public void stateChanged(ChangeEvent e) {
        Component c = mf.tabbedtMain.getSelectedComponent();
        if (c instanceof PSchedule) {
            update(pSchedule.getController());
        }
        if (c instanceof PStationFleet) {
            update(pStationFleet.getController());
        }
        if (c instanceof PDispStation) {
            update(pDispStation.getController());
        }
        if (c instanceof PCar) {
            update(pCars.getController());
        }

        if (c instanceof PRoute) {
            update(pRoute.getController());
        }
    }

    public void actionPerformed(ActionEvent e) {
        Component c = mf.tabbedtMain.getSelectedComponent();
        if (c instanceof PSchedule) {
            update(pSchedule.getController());
        }
        if (c instanceof PStationFleet) {
            update(pStationFleet.getController());
        }
        if (c instanceof PDispStation) {
            update(pDispStation.getController());
        }
        if (c instanceof PCar) {
            update(pCars.getController());
        }

        if (c instanceof PRoute) {
            update(pRoute.getController());
        }
    }


    public void mouseClicked(MouseEvent e) {

    }

    public void mousePressed(MouseEvent e) {

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

    public void showCarInf(Component c, int x, int y, Car car) {
        pCarInformation.setData(car);
        popCarInf.show(c, x, y);
    }

    public void showCarInf(JComponent c, int x, int y, int numberCar) {
        pCarInformation.setData(numberCar);
        popCarInf.show(c, x, y);
    }

    public void showTrainInf(Component c, int x, int y, Train train) {
        pTrainInformation.setData(train);
        popTrainInf.show(c, x, y);
    }

    public void showTrainInf(JComponent c, int x, int y, int idTrain) {
        pTrainInformation.setData(idTrain);
        popTrainInf.show(c, x, y);
    }

    public boolean searchCar(int numberCar) {
        boolean search = pStationFleet.getController().searchCar(numberCar);
        if (search) {
            mf.tabbedtMain.setSelectedComponent(pStationFleet);
            pStationFleet.getController().searchCar(numberCar);
        }
        return search;
    }

    public boolean searchTrain(int idTrain) {
        boolean search = pStationFleet.getController().searchTrain(idTrain);
        if (search) {
            mf.tabbedtMain.setSelectedComponent(pStationFleet);
            pStationFleet.getController().searchTrain(idTrain);
        }
        return search;
    }

    public static void main(String[] args) {
        ControllerMain.getInstans();
    }

    public void update(final Updateble source) {
        dLoading.setLocationRelativeTo(mf);
        new SwingWorker() {
            @Override
            protected Object doInBackground() throws Exception {
                source.update();
                return null;
            }

            public void done() {
                dLoading.setVisible(false);
            }
        }.execute();
        dLoading.setVisible(true);

    }


}
