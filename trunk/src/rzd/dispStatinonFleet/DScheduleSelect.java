/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

/*
 * DScheduleSelect.java
 *
 * Created on 18.09.2010, 21:51:28
 */
package rzd.dispStatinonFleet;

import rzd.model.objects.Shedule;

import java.util.ArrayList;


/**
 *
 * @author Евгений
 */
public class DScheduleSelect extends javax.swing.JDialog {

    private Shedule shedule;

    /** Creates new form DScheduleSelect */
    public DScheduleSelect(java.awt.Frame parent, boolean modal) {
        super(parent, modal);
        initComponents();
    }

    /** This method is called from within the constructor to
     * initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is
     * always regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jScrollPane1 = new javax.swing.JScrollPane();
        lSchedule = new javax.swing.JList();

        setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);

        lSchedule.addListSelectionListener(new javax.swing.event.ListSelectionListener() {
            public void valueChanged(javax.swing.event.ListSelectionEvent evt) {
                lScheduleValueChanged(evt);
            }
        });
        jScrollPane1.setViewportView(lSchedule);

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jScrollPane1, javax.swing.GroupLayout.DEFAULT_SIZE, 296, Short.MAX_VALUE)
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jScrollPane1, javax.swing.GroupLayout.DEFAULT_SIZE, 101, Short.MAX_VALUE)
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void lScheduleValueChanged(javax.swing.event.ListSelectionEvent evt) {//GEN-FIRST:event_lScheduleValueChanged
        shedule = (Shedule) lSchedule.getSelectedValue();
        setVisible(false);
    }//GEN-LAST:event_lScheduleValueChanged

    /**
     * @param args the command line arguments
     */
    public static void main(String args[]) {
        java.awt.EventQueue.invokeLater(new Runnable() {

            public void run() {
                DScheduleSelect dialog = new DScheduleSelect(new javax.swing.JFrame(), true);
                dialog.addWindowListener(new java.awt.event.WindowAdapter() {

                    public void windowClosing(java.awt.event.WindowEvent e) {
                        System.exit(0);
                    }
                });
                dialog.setVisible(true);
            }
        });
    }

    public Shedule open(ArrayList<Shedule> shedules) {
        shedule = null;
        setVisible(true);
        return shedule;
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JList lSchedule;
    // End of variables declaration//GEN-END:variables
}