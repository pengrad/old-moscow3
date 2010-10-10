/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

/*
 * CarEditInf.java
 *
 * Created on 10.09.2010, 19:57:36
 */
package rzd.carFleet;

import java.awt.Component;
import java.util.ArrayList;
import javax.swing.JCheckBox;
import javax.swing.JComboBox;
import javax.swing.JOptionPane;
import javax.swing.JTextField;

import rzd.model.Model;
import rzd.model.objects.Car;
import rzd.model.objects.CarLocation;
import rzd.model.objects.CarType;

/**
 * @author ЧерныхЕА
 */
public class DCarEdit extends javax.swing.JDialog {

    private Car car;

    /**
     * Creates new form CarEditInf
     */
    public DCarEdit(java.awt.Frame parent, boolean modal) {
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

        pContainer = new javax.swing.JPanel();
        jButton2 = new javax.swing.JButton();
        jButton1 = new javax.swing.JButton();
        jLabel1 = new javax.swing.JLabel();
        fNumberCar = new javax.swing.JFormattedTextField();
        jLabel2 = new javax.swing.JLabel();
        jLabel3 = new javax.swing.JLabel();
        jLabel4 = new javax.swing.JLabel();
        jLabel5 = new javax.swing.JLabel();
        jLabel6 = new javax.swing.JLabel();
        jLabel7 = new javax.swing.JLabel();
        jLabel8 = new javax.swing.JLabel();
        cTypeParent = new javax.swing.JComboBox();
        jLabel9 = new javax.swing.JLabel();
        jLabel10 = new javax.swing.JLabel();
        cEcologicCoilet = new javax.swing.JCheckBox();
        fModelCar = new javax.swing.JTextField();
        fConditioner = new javax.swing.JTextField();
        fGenerator = new javax.swing.JTextField();
        fGeneratorPrivod = new javax.swing.JTextField();
        fAccumulator = new javax.swing.JTextField();
        fElectricDevice = new javax.swing.JTextField();
        fBodyColor = new javax.swing.JTextField();
        jLabel11 = new javax.swing.JLabel();
        fRunNorm = new javax.swing.JFormattedTextField();
        jLabel12 = new javax.swing.JLabel();
        fRun = new javax.swing.JFormattedTextField();
        jLabel13 = new javax.swing.JLabel();
        fRunTozNorn = new javax.swing.JFormattedTextField();
        jLabel14 = new javax.swing.JLabel();
        fRunToz = new javax.swing.JFormattedTextField();
        cTypeChilld = new javax.swing.JComboBox();
        jLabel16 = new javax.swing.JLabel();
        jLabel17 = new javax.swing.JLabel();

        setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);
        setTitle("Создание/редактирование вагона ");
        setResizable(false);

        jButton2.setText("Отмена");
        jButton2.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                bCancel(evt);
            }
        });

        jButton1.setText("Сохранить");
        jButton1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                bSave(evt);
            }
        });

        jLabel1.setFont(new java.awt.Font("Tahoma", 2, 13));
        jLabel1.setText("Номер вагона");

        fNumberCar.setBackground(new java.awt.Color(255, 255, 204));
        try {
            fNumberCar.setFormatterFactory(new javax.swing.text.DefaultFormatterFactory(new javax.swing.text.MaskFormatter("########")));
        } catch (java.text.ParseException ex) {
            ex.printStackTrace();
        }

        jLabel2.setFont(new java.awt.Font("Tahoma", 2, 13));
        jLabel2.setText("Тип");

        jLabel3.setFont(new java.awt.Font("Tahoma", 2, 13));
        jLabel3.setText("Модель");

        jLabel4.setFont(new java.awt.Font("Tahoma", 2, 13));
        jLabel4.setText("Вент/конд");

        jLabel5.setFont(new java.awt.Font("Tahoma", 2, 13));
        jLabel5.setText("Генератор");

        jLabel6.setFont(new java.awt.Font("Tahoma", 2, 13));
        jLabel6.setText("Привод генератора");

        jLabel7.setFont(new java.awt.Font("Tahoma", 2, 13));
        jLabel7.setText("Аккумулятор");

        jLabel8.setFont(new java.awt.Font("Tahoma", 2, 13));
        jLabel8.setText("Электрообоудование");

        cTypeParent.setModel(new javax.swing.DefaultComboBoxModel(new String[]{"Item 1", "Item 2", "Item 3", "Item 4"}));
        cTypeParent.addItemListener(new java.awt.event.ItemListener() {
            public void itemStateChanged(java.awt.event.ItemEvent evt) {
                cTypeParentItemStateChanged(evt);
            }
        });

        jLabel9.setFont(new java.awt.Font("Tahoma", 2, 13));
        jLabel9.setText("Окраска кузова");

        jLabel10.setFont(new java.awt.Font("Tahoma", 2, 13));
        jLabel10.setText("Эколог., туалет");

        jLabel11.setFont(new java.awt.Font("Tahoma", 2, 13));
        jLabel11.setText("Прбег (км) норма от пл. р.");

        fRunNorm.setBackground(new java.awt.Color(255, 255, 204));
        fRunNorm.setFormatterFactory(new javax.swing.text.DefaultFormatterFactory(new javax.swing.text.NumberFormatter(new java.text.DecimalFormat("#0"))));

        jLabel12.setFont(new java.awt.Font("Tahoma", 2, 13));
        jLabel12.setText("Прбег (км) от пл. р.");

        fRun.setBackground(new java.awt.Color(255, 255, 204));
        fRun.setFormatterFactory(new javax.swing.text.DefaultFormatterFactory(new javax.swing.text.NumberFormatter(new java.text.DecimalFormat("#0"))));

        jLabel13.setFont(new java.awt.Font("Tahoma", 2, 13));
        jLabel13.setText("Прбег (км) норма от ТОЗ.");

        fRunTozNorn.setBackground(new java.awt.Color(255, 255, 204));
        fRunTozNorn.setFormatterFactory(new javax.swing.text.DefaultFormatterFactory(new javax.swing.text.NumberFormatter(new java.text.DecimalFormat("#0"))));

        jLabel14.setFont(new java.awt.Font("Tahoma", 2, 13));
        jLabel14.setText("Прбег (км) от ТОЗ");

        fRunToz.setBackground(new java.awt.Color(255, 255, 204));
        fRunToz.setFormatterFactory(new javax.swing.text.DefaultFormatterFactory(new javax.swing.text.NumberFormatter(new java.text.DecimalFormat("#0"))));

        cTypeChilld.setModel(new javax.swing.DefaultComboBoxModel(new String[]{"Item 1", "Item 2", "Item 3", "Item 4"}));

        jLabel16.setFont(new java.awt.Font("Tahoma", 2, 13));
        jLabel16.setText("Подтип");

        javax.swing.GroupLayout pContainerLayout = new javax.swing.GroupLayout(pContainer);
        pContainer.setLayout(pContainerLayout);
        pContainerLayout.setHorizontalGroup(
                pContainerLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                        .addGroup(pContainerLayout.createSequentialGroup()
                                .addGap(560, 560, 560)
                                .addComponent(jLabel17)
                                .addContainerGap(55, Short.MAX_VALUE))
                        .addGroup(pContainerLayout.createSequentialGroup()
                        .addGap(31, 31, 31)
                        .addGroup(pContainerLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                .addGroup(pContainerLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                        .addGroup(pContainerLayout.createSequentialGroup()
                                                .addGroup(pContainerLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                                        .addGroup(pContainerLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                                                                .addComponent(jLabel3)
                                                                .addComponent(jLabel4)
                                                                .addComponent(jLabel5)
                                                                .addComponent(jLabel6)
                                                                .addComponent(jLabel7)
                                                                .addComponent(jLabel8)
                                                                .addComponent(jLabel9)
                                                                .addComponent(jLabel10)
                                                                .addComponent(jLabel11, javax.swing.GroupLayout.DEFAULT_SIZE, 178, Short.MAX_VALUE)
                                                                .addComponent(jLabel13))
                                                        .addComponent(jLabel1)
                                                        .addComponent(jLabel2)
                                                        .addComponent(jLabel16))
                                                .addGap(27, 27, 27)
                                                .addGroup(pContainerLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                                                        .addComponent(fConditioner)
                                                        .addComponent(fModelCar)
                                                        .addComponent(cTypeChilld, 0, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                                        .addComponent(cTypeParent, 0, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                                        .addComponent(fNumberCar, javax.swing.GroupLayout.DEFAULT_SIZE, 276, Short.MAX_VALUE)
                                                        .addComponent(fGenerator)
                                                        .addComponent(fGeneratorPrivod)
                                                        .addComponent(fAccumulator)
                                                        .addComponent(fElectricDevice)
                                                        .addComponent(fBodyColor))
                                                .addGap(83, 83, 83))
                                        .addGroup(pContainerLayout.createSequentialGroup()
                                        .addGap(205, 205, 205)
                                        .addGroup(pContainerLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                                .addComponent(cEcologicCoilet)
                                                .addGroup(pContainerLayout.createSequentialGroup()
                                                        .addComponent(fRunNorm, javax.swing.GroupLayout.PREFERRED_SIZE, 103, javax.swing.GroupLayout.PREFERRED_SIZE)
                                                        .addGap(18, 18, 18)
                                                        .addComponent(jLabel12, javax.swing.GroupLayout.PREFERRED_SIZE, 128, javax.swing.GroupLayout.PREFERRED_SIZE))
                                                .addGroup(pContainerLayout.createSequentialGroup()
                                                .addComponent(fRunTozNorn, javax.swing.GroupLayout.PREFERRED_SIZE, 103, javax.swing.GroupLayout.PREFERRED_SIZE)
                                                .addGap(18, 18, 18)
                                                .addComponent(jLabel14, javax.swing.GroupLayout.PREFERRED_SIZE, 128, javax.swing.GroupLayout.PREFERRED_SIZE)))
                                        .addGap(5, 5, 5)
                                        .addGroup(pContainerLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                        .addComponent(fRun, 0, 0, Short.MAX_VALUE)
                                        .addComponent(fRunToz, javax.swing.GroupLayout.PREFERRED_SIZE, 105, javax.swing.GroupLayout.PREFERRED_SIZE))))
                                .addGroup(pContainerLayout.createSequentialGroup()
                                .addGap(195, 195, 195)
                                .addComponent(jButton1)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                .addComponent(jButton2, javax.swing.GroupLayout.PREFERRED_SIZE, 93, javax.swing.GroupLayout.PREFERRED_SIZE)))
                        .addGap(20, 20, 20))
        );
        pContainerLayout.setVerticalGroup(
                pContainerLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                        .addGroup(pContainerLayout.createSequentialGroup()
                        .addContainerGap()
                        .addComponent(jLabel17)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(pContainerLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                                .addComponent(jLabel1)
                                .addComponent(fNumberCar, javax.swing.GroupLayout.PREFERRED_SIZE, 24, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(pContainerLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                                .addComponent(jLabel2)
                                .addComponent(cTypeParent, javax.swing.GroupLayout.PREFERRED_SIZE, 24, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(pContainerLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                                .addComponent(cTypeChilld, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addComponent(jLabel16))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(pContainerLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                                .addComponent(jLabel3)
                                .addComponent(fModelCar, javax.swing.GroupLayout.PREFERRED_SIZE, 24, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(pContainerLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                                .addComponent(jLabel4)
                                .addComponent(fConditioner, javax.swing.GroupLayout.PREFERRED_SIZE, 24, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(pContainerLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                                .addComponent(jLabel5)
                                .addComponent(fGenerator, javax.swing.GroupLayout.PREFERRED_SIZE, 24, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(pContainerLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                                .addComponent(jLabel6)
                                .addComponent(fGeneratorPrivod, javax.swing.GroupLayout.PREFERRED_SIZE, 24, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(pContainerLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                                .addComponent(jLabel7)
                                .addComponent(fAccumulator, javax.swing.GroupLayout.PREFERRED_SIZE, 24, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(pContainerLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                                .addComponent(jLabel8)
                                .addComponent(fElectricDevice, javax.swing.GroupLayout.PREFERRED_SIZE, 24, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(pContainerLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                                .addComponent(jLabel9)
                                .addComponent(fBodyColor, javax.swing.GroupLayout.PREFERRED_SIZE, 24, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(pContainerLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                .addComponent(jLabel10)
                                .addGroup(pContainerLayout.createSequentialGroup()
                                .addComponent(cEcologicCoilet, javax.swing.GroupLayout.PREFERRED_SIZE, 24, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addGroup(pContainerLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                                        .addComponent(fRunNorm, javax.swing.GroupLayout.PREFERRED_SIZE, 24, javax.swing.GroupLayout.PREFERRED_SIZE)
                                        .addComponent(jLabel11, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                        .addComponent(jLabel12, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                        .addComponent(fRun, javax.swing.GroupLayout.PREFERRED_SIZE, 24, javax.swing.GroupLayout.PREFERRED_SIZE))
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addGroup(pContainerLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                                .addComponent(fRunTozNorn, javax.swing.GroupLayout.PREFERRED_SIZE, 24, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addComponent(jLabel13, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                .addComponent(fRunToz, javax.swing.GroupLayout.PREFERRED_SIZE, 24, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addComponent(jLabel14, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))))
                        .addGap(38, 38, 38)
                        .addGroup(pContainerLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                                .addComponent(jButton2)
                                .addComponent(jButton1))
                        .addGap(28, 28, 28))
        );

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
                layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                        .addComponent(pContainer, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
        );
        layout.setVerticalGroup(
                layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                        .addComponent(pContainer, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void bCancel(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_bCancel
        car = null;
        setVisible(false);
    }//GEN-LAST:event_bCancel

    private void bSave(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_bSave
        save();
    }//GEN-LAST:event_bSave

    private void cTypeParentItemStateChanged(java.awt.event.ItemEvent evt) {//GEN-FIRST:event_cTypeParentItemStateChanged
        if (cTypeParent.getSelectedItem() != null) {
            if (cTypeChilld.getItemCount() > 0)
                cTypeChilld.removeAllItems();
            ArrayList<CarType> carTypes = Model.getModel().getCarSubTypes((CarType) cTypeParent.getSelectedItem());
            if (carTypes != null) {
                for (CarType carType : carTypes) {
                    cTypeChilld.addItem(carType);
                }
            }
        }
    }//GEN-LAST:event_cTypeParentItemStateChanged

    /**
     * @param args the command line arguments
     */
    public static void main(String args[]) {
        java.awt.EventQueue.invokeLater(new Runnable() {

            public void run() {
                DCarEdit dialog = new DCarEdit(new javax.swing.JFrame(), true);
                dialog.addWindowListener(new java.awt.event.WindowAdapter() {

                    public void windowClosing(java.awt.event.WindowEvent e) {
                        System.exit(0);
                    }
                });
                dialog.setVisible(true);
            }
        });
    }

    public Car open(Car car) {
        this.car = car;
        if (cTypeParent.getItemCount() > 0)
            cTypeParent.removeAllItems();
        ArrayList<CarType> carTypes = Model.getModel().getCarParentTypes();
        if (carTypes != null)
            for (CarType carType : carTypes) {
                cTypeParent.addItem(carType);
            }
//        cTypeChilld.removeAllItems();
//        carTypes = Model.getModel().getCarSubTypes((CarType) cTypeParent.getSelectedItem());
//        if (carTypes != null) {
//            for (CarType carType : carTypes) {
//                cTypeChilld.addItem(carType);
//            }
//        }
        if (car == null) {
            Component[] components = pContainer.getComponents();
            for (int i = 0; i < components.length; i++) {
                if (components[i] instanceof JTextField) {
                    ((JTextField) components[i]).setText("");
                } else if (components[i] instanceof JComboBox) {
//                    ((JComboBox) components[i]).removeAllItems();
                } else if (components[i] instanceof JCheckBox) {
                    ((JCheckBox) components[i]).setSelected(false);
                }
            }
        } else {
            fNumberCar.setText(new Integer(car.getNumber()).toString());
            CarType carType = car.getCarType();
            CarType carParentType = Model.getModel().getCarParentType(carType);
            if (carParentType == null) {
                cTypeParent.setSelectedItem(carType);
//              cTypeChilld.removeAllItems();
            } else {
                cTypeParent.setSelectedItem(carParentType);
                cTypeChilld.setSelectedItem(carType);
            }
            fModelCar.setText(car.getModel());
            fConditioner.setText(car.getConditioner());
            fGenerator.setText(car.getGenerator());
            fGeneratorPrivod.setText(car.getGeneratorPrivod());
            fAccumulator.setText(car.getAccumulator());
            fElectricDevice.setText(car.getElectricDevice());
            fBodyColor.setText(car.getBodyColor());
            cEcologicCoilet.setSelected(car.isEcologicCoilet());
            fRunNorm.setText(new Integer(car.getRunNorm()).toString());
            fRun.setText(new Integer(car.getRun()).toString());
            fRunTozNorn.setText(new Integer(car.getRunTozNorm()).toString());
            fRunToz.setText(new Integer(car.getRunToz()).toString());

        }
        setVisible(true);
        return this.car;
    }

    private void save() {
        if (testCorrectInputData()) {
            car = new Car(
                    new Integer(fNumberCar.getText().trim()),
                    fModelCar.getText().trim(),
                    new CarLocation(1, "jnkj"),
                    (cTypeChilld.getSelectedItem() != null ? (CarType) cTypeChilld.getSelectedItem() : (CarType) cTypeParent.getSelectedItem()),
                    fConditioner.getText().trim(),
                    fGenerator.getText().trim(),
                    fGeneratorPrivod.getText().trim(),
                    fAccumulator.getText().trim(),
                    fElectricDevice.getText().trim(),
                    fBodyColor.getText().trim(),
                    cEcologicCoilet.isSelected(),
                    new Integer(fRunNorm.getText().trim()),
                    new Integer(fRun.getText().trim()),
                    new Integer(fRunTozNorn.getText().trim()),
                    new Integer(fRunToz.getText().trim()));
            setVisible(false);
            System.out.println("save!!!");
        } else {
            JOptionPane.showMessageDialog(this, "Заполните все поля");
        }
    }
//
//    private void setCarChildType(CarType parent) {
//        cTypeChilld.removeAllItems();
//        ArrayList<CarType> carTypes = Model.getModel().getCarSubTypes((CarType) cTypeParent.getSelectedItem());
//        for (CarType carType : carTypes) {
//            cTypeChilld.addItem(carType);
//        }
//    }

    private boolean testCorrectInputData() {
        if (!fNumberCar.getText().trim().equals("") &&
                !fRunNorm.getText().trim().equals("") &&
                !fRun.getText().trim().equals("") &&
                !fRunTozNorn.getText().trim().equals("") &&
                !fRunToz.getText().trim().equals("")) {
            return true;
        } else {
            return false;
        }
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JCheckBox cEcologicCoilet;
    private javax.swing.JComboBox cTypeChilld;
    private javax.swing.JComboBox cTypeParent;
    private javax.swing.JTextField fAccumulator;
    private javax.swing.JTextField fBodyColor;
    private javax.swing.JTextField fConditioner;
    private javax.swing.JTextField fElectricDevice;
    private javax.swing.JTextField fGenerator;
    private javax.swing.JTextField fGeneratorPrivod;
    private javax.swing.JTextField fModelCar;
    private javax.swing.JFormattedTextField fNumberCar;
    private javax.swing.JFormattedTextField fRun;
    private javax.swing.JFormattedTextField fRunNorm;
    private javax.swing.JFormattedTextField fRunToz;
    private javax.swing.JFormattedTextField fRunTozNorn;
    private javax.swing.JButton jButton1;
    private javax.swing.JButton jButton2;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel10;
    private javax.swing.JLabel jLabel11;
    private javax.swing.JLabel jLabel12;
    private javax.swing.JLabel jLabel13;
    private javax.swing.JLabel jLabel14;
    private javax.swing.JLabel jLabel16;
    private javax.swing.JLabel jLabel17;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JLabel jLabel6;
    private javax.swing.JLabel jLabel7;
    private javax.swing.JLabel jLabel8;
    private javax.swing.JLabel jLabel9;
    private javax.swing.JPanel pContainer;
    // End of variables declaration//GEN-END:variables
}