/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

/*
 * PDispStation.java
 *
 * Created on 13.09.2010, 16:15:22
 */

package rzd.dispStatinonFleet;

import rzd.ModelTable;

/**
 * @author ЧерныхЕА
 */
public class PDispStation extends javax.swing.JPanel {
    private ControllerDispSt c;

    /**
     * Creates new form PDispStation
     */
    public PDispStation() {
        initComponents();
        c = new ControllerDispSt(this);
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

        jTabbedPane1 = new javax.swing.JTabbedPane();
        jScrollPane1 = new javax.swing.JScrollPane();
        tArrivingTrains = new javax.swing.JTable();
        jTabbedPane2 = new javax.swing.JTabbedPane();
        jScrollPane2 = new javax.swing.JScrollPane();
        tGoingTrains = new javax.swing.JTable();
        jTabbedPane3 = new javax.swing.JTabbedPane();
        jScrollPane3 = new javax.swing.JScrollPane();
        tTrainOnRoad = new javax.swing.JTable();
        jLabel1 = new javax.swing.JLabel();
        cTimeBeforeArrivingTrains = new javax.swing.JComboBox();
        jLabel2 = new javax.swing.JLabel();
        cTimeBeforeGoingTrains = new javax.swing.JComboBox();

        jTabbedPane1.setForeground(new java.awt.Color(0, 0, 204));
        jTabbedPane1.setFont(new java.awt.Font("Tahoma", 1, 11));

        tArrivingTrains.setModel(new ModelTable() );
        jScrollPane1.setViewportView(tArrivingTrains);

        jTabbedPane1.addTab("Прибывающие поезда", jScrollPane1);

        jTabbedPane2.setForeground(new java.awt.Color(0, 0, 204));
        jTabbedPane2.setFont(new java.awt.Font("Tahoma", 1, 11));

        tGoingTrains.setModel(new ModelTable());
        jScrollPane2.setViewportView(tGoingTrains);

        jTabbedPane2.addTab("Отправляемые поезда", jScrollPane2);

        jTabbedPane3.setBackground(new java.awt.Color(0, 51, 204));
        jTabbedPane3.setForeground(new java.awt.Color(0, 0, 204));
        jTabbedPane3.setFont(new java.awt.Font("Tahoma", 1, 11));

        tTrainOnRoad.setModel(new ModelTable() );
        jScrollPane3.setViewportView(tTrainOnRoad);

        jTabbedPane3.addTab("Поезда на путях", jScrollPane3);

        jLabel1.setText("Отображать за часов до прибытия");

        cTimeBeforeArrivingTrains.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "5", "10", "20", "30" }));

        jLabel2.setText("Отображать за часов до прибытия");

        cTimeBeforeGoingTrains.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "5", "10", "20", "30" }));

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(this);
        this.setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jTabbedPane3, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, 622, Short.MAX_VALUE)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                .addContainerGap(350, Short.MAX_VALUE)
                .addComponent(jLabel1)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(cTimeBeforeArrivingTrains, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap())
            .addComponent(jTabbedPane1, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, 622, Short.MAX_VALUE)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                .addContainerGap(350, Short.MAX_VALUE)
                .addComponent(jLabel2)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(cTimeBeforeGoingTrains, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap())
            .addComponent(jTabbedPane2, javax.swing.GroupLayout.DEFAULT_SIZE, 622, Short.MAX_VALUE)
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addComponent(jTabbedPane3, javax.swing.GroupLayout.DEFAULT_SIZE, 206, Short.MAX_VALUE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(cTimeBeforeArrivingTrains, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel1))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jTabbedPane1, javax.swing.GroupLayout.DEFAULT_SIZE, 194, Short.MAX_VALUE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(cTimeBeforeGoingTrains, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel2))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jTabbedPane2, javax.swing.GroupLayout.DEFAULT_SIZE, 192, Short.MAX_VALUE))
        );
    }// </editor-fold>//GEN-END:initComponents

    public  ControllerDispSt getController(){
        return c;
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    public javax.swing.JComboBox cTimeBeforeArrivingTrains;
    public javax.swing.JComboBox cTimeBeforeGoingTrains;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JScrollPane jScrollPane2;
    private javax.swing.JScrollPane jScrollPane3;
    private javax.swing.JTabbedPane jTabbedPane1;
    private javax.swing.JTabbedPane jTabbedPane2;
    private javax.swing.JTabbedPane jTabbedPane3;
    public javax.swing.JTable tArrivingTrains;
    public javax.swing.JTable tGoingTrains;
    public javax.swing.JTable tTrainOnRoad;
    // End of variables declaration//GEN-END:variables

}
