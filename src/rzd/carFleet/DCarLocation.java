/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

/*
 * DCarLocation.java
 *
 * Created on 14.10.2010, 23:07:45
 */
package rzd.carFleet;

import java.util.ArrayList;

import logic.BusinessLogic;
import rzd.model.Model;
import rzd.model.objects.Car;
import rzd.model.objects.CarLocation;
import rzd.model.objects.Repair;
import rzd.model.objects.RepairType;
import rzd.model.objects.Road;
import rzd.model.objects.RoadType;
import rzd.model.objects.structure.CarLocationStructure;

/**
 * @author Евгений
 */
public class DCarLocation extends javax.swing.JDialog {

    private CarLocationStructure carLocationStructure;
    private Car car;

    /**
     * Creates new form DCarLocation
     */
    public DCarLocation(java.awt.Frame parent, boolean modal) {
        super(parent, modal);
        initComponents();
    }

    /**
     * This method is called from within the constructor to
     * initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is
     * always regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jPanel1 = new javax.swing.JPanel();
        cLocation = new javax.swing.JComboBox();
        jLabel1 = new javax.swing.JLabel();
        cOnRoad = new javax.swing.JCheckBox();
        jLabel3 = new javax.swing.JLabel();
        cTypeRepair = new javax.swing.JComboBox();
        tComment = new javax.swing.JTextField();
        jLabel5 = new javax.swing.JLabel();
        jLabel4 = new javax.swing.JLabel();
        cTypeRoad = new javax.swing.JComboBox();
        cRoad = new javax.swing.JComboBox();
        jLabel2 = new javax.swing.JLabel();
        bSave = new javax.swing.JButton();
        bCancel = new javax.swing.JButton();

        setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);
        addWindowListener(new java.awt.event.WindowAdapter() {
            public void windowClosing(java.awt.event.WindowEvent evt) {
                formWindowClosing(evt);
            }
        });

        jPanel1.setBackground(new java.awt.Color(250, 250, 250));

        cLocation.addItemListener(new java.awt.event.ItemListener() {
            public void itemStateChanged(java.awt.event.ItemEvent evt) {
                cLocationItemStateChanged(evt);
            }
        });

        jLabel1.setFont(new java.awt.Font("Times New Roman", 2, 13)); // NOI18N
        jLabel1.setText("Местоположение вагона");

        cOnRoad.setBackground(new java.awt.Color(250, 250, 250));
        cOnRoad.setFont(new java.awt.Font("Times New Roman", 2, 13)); // NOI18N
        cOnRoad.setText(" - ремонт на наших путях");
        cOnRoad.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                cOnRoadActionPerformed(evt);
            }
        });

        jLabel3.setFont(new java.awt.Font("Times New Roman", 2, 13)); // NOI18N
        jLabel3.setText("Тип ремонта");

        tComment.setFont(new java.awt.Font("Times New Roman", 0, 14)); // NOI18N

        jLabel5.setFont(new java.awt.Font("Times New Roman", 2, 13)); // NOI18N
        jLabel5.setText("Комментарий");

        jLabel4.setFont(new java.awt.Font("Times New Roman", 2, 13)); // NOI18N
        jLabel4.setText("Тип пути");

        cTypeRoad.addItemListener(new java.awt.event.ItemListener() {
            public void itemStateChanged(java.awt.event.ItemEvent evt) {
                cTypeRoadItemStateChanged(evt);
            }
        });

        cRoad.setFont(new java.awt.Font("Times New Roman", 0, 14)); // NOI18N

        jLabel2.setFont(new java.awt.Font("Times New Roman", 2, 13)); // NOI18N
        jLabel2.setText("Путь");

        bSave.setText("Сохранить");
        bSave.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                bSaveActionPerformed(evt);
            }
        });

        bCancel.setText("Отмена");
        bCancel.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                bCancelActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
                jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                        .addGroup(jPanel1Layout.createSequentialGroup()
                        .addContainerGap()
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                .addComponent(jLabel1, javax.swing.GroupLayout.PREFERRED_SIZE, 210, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGroup(jPanel1Layout.createSequentialGroup()
                                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                                                .addComponent(jLabel2, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                                .addComponent(jLabel4, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, 84, Short.MAX_VALUE))
                                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                        .addGroup(jPanel1Layout.createSequentialGroup()
                                                .addComponent(bSave)
                                                .addGap(18, 18, 18)
                                                .addComponent(bCancel, javax.swing.GroupLayout.PREFERRED_SIZE, 100, javax.swing.GroupLayout.PREFERRED_SIZE))
                                        .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel1Layout.createSequentialGroup()
                                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                                        .addComponent(cTypeRoad, 0, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                        .addComponent(cRoad, 0, 243, Short.MAX_VALUE)))))
                                .addComponent(cOnRoad)
                                .addComponent(jLabel5)
                                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                                .addComponent(tComment, javax.swing.GroupLayout.Alignment.LEADING)
                                .addGroup(javax.swing.GroupLayout.Alignment.LEADING, jPanel1Layout.createSequentialGroup()
                                        .addComponent(jLabel3, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                        .addGap(18, 18, 18)
                                        .addComponent(cTypeRepair, javax.swing.GroupLayout.PREFERRED_SIZE, 231, javax.swing.GroupLayout.PREFERRED_SIZE))
                                .addComponent(cLocation, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.PREFERRED_SIZE, 245, javax.swing.GroupLayout.PREFERRED_SIZE)))
                        .addContainerGap(25, Short.MAX_VALUE))
        );
        jPanel1Layout.setVerticalGroup(
                jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                        .addGroup(jPanel1Layout.createSequentialGroup()
                        .addContainerGap()
                        .addComponent(jLabel1)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(cLocation, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(33, 33, 33)
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                                .addComponent(jLabel3)
                                .addComponent(cTypeRepair, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGap(18, 18, 18)
                        .addComponent(jLabel5)
                        .addGap(2, 2, 2)
                        .addComponent(tComment, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(8, 8, 8)
                        .addComponent(cOnRoad)
                        .addGap(23, 23, 23)
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                                .addComponent(jLabel4)
                                .addComponent(cTypeRoad, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGap(18, 18, 18)
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                                .addComponent(jLabel2)
                                .addComponent(cRoad, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGap(85, 85, 85)
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                                .addComponent(bCancel)
                                .addComponent(bSave))
                        .addContainerGap(25, Short.MAX_VALUE))
        );

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
                layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                        .addComponent(jPanel1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
        );
        layout.setVerticalGroup(
                layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                        .addComponent(jPanel1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void bSaveActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_bSaveActionPerformed
        save();
    }//GEN-LAST:event_bSaveActionPerformed

    private void bCancelActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_bCancelActionPerformed
        close();
    }//GEN-LAST:event_bCancelActionPerformed

    private void cLocationItemStateChanged(java.awt.event.ItemEvent evt) {
        updateLocation((CarLocation) evt.getItem());
    }

    private void cTypeRoadItemStateChanged(java.awt.event.ItemEvent evt) {
        updateRoad((RoadType) evt.getItem());
    }

    private void cOnRoadActionPerformed(java.awt.event.ActionEvent evt) {
        repairOnRoad(cOnRoad.isSelected());
    }

    private void formWindowClosing(java.awt.event.WindowEvent evt) {
        close();
    }

    public CarLocationStructure open(Car car) {
        if (car == null) {
            close();
        }
        this.car = car;
        cOnRoad.setSelected(false);
        ArrayList<CarLocation> carLocations = Model.getModel().getCarLocations();
        cLocation.removeAllItems();
        if (carLocations != null && carLocations.size() > 0) {
            for (CarLocation carLocation : carLocations) {
                if (carLocation.getIdLocation() != BusinessLogic.IN_TRAIN) {
                    cLocation.addItem(carLocation);
                }
            }
            CarLocation carLocation = car.getCarLocation();
            cLocation.setSelectedItem(carLocation);

            if (BusinessLogic.ON_ROAD == carLocation.getIdLocation()) {
                Road road = Model.getModel().getRoadByCar(car);
                if (road != null) {
                    cTypeRoad.setSelectedItem(road.getRoadType());
                    cRoad.setSelectedItem(road);
                }
            }

            if (BusinessLogic.REPAIR == carLocation.getIdLocation()) {
                Repair repair = Model.getModel().getRepairByCar(car);
                if (repair != null) {
                    if (repair.getRoad() != null) {
                        repairOnRoad(true);
                        cTypeRoad.setSelectedItem(repair.getRoad().getRoadType());
                        cRoad.setSelectedItem(repair.getRoad());
                    }
                    tComment.setText(repair.getComment());
                    cTypeRepair.setSelectedItem(repair.getRepairType());
                }
            }
        }
        setVisible(true);
        return this.carLocationStructure;
    }

    private void save() {
        Road road = null;
        Repair repair = null;
        this.car = new Car(
                this.car.getNumber(),
                this.car.getModel(),
                (CarLocation) cLocation.getSelectedItem(),
                this.car.getCarType(),
                this.car.getConditioner(),
                this.car.getGenerator(),
                this.car.getGeneratorPrivod(),
                this.car.getAccumulator(),
                this.car.getElectricDevice(),
                this.car.getBodyColor(),
                this.car.isEcologicCoilet(),
                this.car.getRunNorm(),
                this.car.getRun(),
                this.car.getRunTozNorm(),
                this.car.getRunToz(),
                0
        );
        car.setCarLocation((CarLocation) cLocation.getSelectedItem());
        if (BusinessLogic.ON_ROAD == ((CarLocation) cLocation.getSelectedItem()).getIdLocation()) {
            road = (Road) cRoad.getSelectedItem();
        }
        if (BusinessLogic.REPAIR == ((CarLocation) cLocation.getSelectedItem()).getIdLocation()) {
            System.out.println(tComment.getText().trim());
            if (cOnRoad.isSelected()) {
                repair = new Repair(0, (RepairType) cTypeRepair.getSelectedItem(), car, (Road) cRoad.getSelectedItem(), null, null, tComment.getText().trim());
            } else {
                repair = new Repair(0, (RepairType) cTypeRepair.getSelectedItem(), car, null, null, null, tComment.getText().trim());
            }
        }
        carLocationStructure = new CarLocationStructure(car, road, repair);
        setVisible(false);
    }

    private void updateLocation(CarLocation carLocation) {
        if (BusinessLogic.UNKNOWN == carLocation.getIdLocation()) {
            cOnRoad.setEnabled(false);
            tComment.setEnabled(false);
            cTypeRepair.setEnabled(false);
            cTypeRoad.setEnabled(false);
            cRoad.setEnabled(false);
        }

        if (BusinessLogic.ON_ROAD == carLocation.getIdLocation()) {
            cOnRoad.setEnabled(false);
            tComment.setEnabled(false);
            cTypeRepair.setEnabled(false);
            cTypeRoad.setEnabled(true);
            cRoad.setEnabled(true);
            cTypeRoad.removeAllItems();
            ArrayList<RoadType> roadTypes = Model.getModel().getRoadTypes();
            if (roadTypes != null) {
                for (RoadType roadType : roadTypes) {
                    cTypeRoad.addItem(roadType);
                }
            }
        }
        if (BusinessLogic.REPAIR == carLocation.getIdLocation()) {
            cOnRoad.setEnabled(true);
            tComment.setEnabled(true);
            cTypeRepair.setEnabled(true);
            cTypeRoad.setEnabled(false);
            cRoad.setEnabled(false);
            cOnRoad.setSelected(false);
            cTypeRepair.removeAllItems();
            ArrayList<RepairType> repairTypes = Model.getModel().getRepairTypes();
            if (repairTypes != null) {
                for (RepairType repairType : repairTypes) {
                    cTypeRepair.addItem(repairType);
                }
            }
        }
    }

    private void updateRoad(RoadType roadType) {
        cRoad.removeAllItems();
        ArrayList<Road> roads = Model.getModel().getRoadsByType(roadType);
        if (roads != null) {
            for (Road road : roads) {
                cRoad.addItem(road);
            }
        }
    }

    private void repairOnRoad(boolean b) {
        cOnRoad.setSelected(b);
        cTypeRoad.setEnabled(b);
        cRoad.setEnabled(b);
        if (b) {
            ArrayList<RoadType> roadTypes = Model.getModel().getRoadTypes();
            if (roadTypes != null) {
                for (RoadType roadType : roadTypes) {
                    cTypeRoad.addItem(roadType);
                }
            }
        }
    }

    private void close() {
        this.carLocationStructure = null;
        setVisible(false);
    }

    /**
     * @param args the command line arguments
     */

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton bCancel;
    private javax.swing.JButton bSave;
    private javax.swing.JComboBox cLocation;
    private javax.swing.JCheckBox cOnRoad;
    private javax.swing.JComboBox cRoad;
    private javax.swing.JComboBox cTypeRepair;
    private javax.swing.JComboBox cTypeRoad;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JTextField tComment;
    // End of variables declaration//GEN-END:variables
}
