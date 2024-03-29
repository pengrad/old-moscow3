/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package rzd.stationFleet;

import java.awt.*;
import java.util.ArrayList;

import rzd.model.objects.Car;
import rzd.model.objects.Train;
import rzd.stationFleet.Figure;
import rzd.stationFleet.GCar;
import rzd.test.ColorHelper;

import javax.swing.*;

/**
 * @author ЧерныхЕА
 */
public class GTrainStation extends JComponent {
    private ControllerStation c;
    private Train train;
    private ArrayList<GCar> gVagons;

    public GTrainStation(Train train, ControllerStation c) {
        this.c = c;
        gVagons = new ArrayList<GCar>();
        setBackground(Color.pink);
        this.train = train;
        // setLayout(new FlowLayout());
        setLayout(new BoxLayout(this, javax.swing.BoxLayout.LINE_AXIS));
        ArrayList<Car> cars = train.getCarsIn();
        if (cars != null && cars.size() > 0) {
              for (int i = 0; i < cars.size(); i++) {
                GCar gc = new GCar((cars.get(i)), c);
                add(gc);
                if (i<(cars.size()-1)) {
                    JLabel l = new JLabel(new javax.swing.ImageIcon(getClass().getResource("/rzd/resurce/connectCar.gif")));
                    l.setFont(new Font("Arial", Font.BOLD, 14));
                    add(l);
                }
                gVagons.add(gc);
            }
        } else {
        setBorder(BorderFactory.createLineBorder(ColorHelper.COLOR_VAGON_BORDER));
            add(new JLabel(" В состав поезда отсутсвуют вагоны "));
        }
    }

    public void addCar(GCar vagon, int position) {
        gVagons.add(position, vagon);
    }

    public void removeCar(GCar vagon) {
        gVagons.remove(vagon);
    }

    public ArrayList<GCar> getCars() {
        return gVagons;
    }

    public Train getTrain() {
        return train;
    }

    public void setSelected(boolean b){
        for(GCar gCar:gVagons){
         gCar.setSelected(b);   
        }
    }


//    public void paint(Graphics2D g2) {
//        g2.setStroke(new BasicStroke(1.0f));
//        if (selected) {
//            g2.setColor(Color.BLUE);
//            g2.fill(new Rectangle(2, 2, getWidth() - 5, getHeight() - 5));
//        } else {
//            g2.setColor(new Color(250, 250, 250));
//            g2.fill(new Rectangle(2, 2, getWidth() - 5, getHeight() - 5));
//            g2.setColor(Color.BLUE);
////        System.out.println(getX() + "   " + getY() + "    " + getWidth() + "    " + getHeight());
//            g2.draw(new Rectangle(2, 2, getWidth() - 5, getHeight() - 5));
//        }
//    }
}
