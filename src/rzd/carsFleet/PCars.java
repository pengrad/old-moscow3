/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

/*
 * PCarFleet.java
 *
 * Created on 10.09.2010, 16:49:33
 */
package rzd.carsFleet;
import rzd.ModelTable;
/**
 *
 * @author ЧерныхЕА
 */
public class PCars extends javax.swing.JPanel {

    private Controller c;

    /** Creates new form PCarFleet */
    public PCars() {
        c = new Controller(this);
        initComponents();
        tCars.addMouseListener(c);
        bAddCar.addActionListener(c);
        fSearch.addActionListener(c);
    }

    /** This method is called from within the constructor to
     * initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is
     * always regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        fSearch = new javax.swing.JTextField();
        jScrollPane3 = new javax.swing.JScrollPane();
        tCars = new javax.swing.JTable();
        bAddCar = new javax.swing.JButton();

        fSearch.setFont(new java.awt.Font("Tahoma", 2, 11));
        fSearch.setText("Поиск по номеру вагона");

        tCars.setModel(new ModelTable() );
        tCars.setSelectionMode(javax.swing.ListSelectionModel.SINGLE_SELECTION);
        jScrollPane3.setViewportView(tCars);

        bAddCar.setText("Добавить вагон");

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(this);
        this.setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(fSearch, javax.swing.GroupLayout.PREFERRED_SIZE, 136, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 129, Short.MAX_VALUE)
                .addComponent(bAddCar)
                .addContainerGap())
            .addComponent(jScrollPane3, javax.swing.GroupLayout.DEFAULT_SIZE, 400, Short.MAX_VALUE)
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(fSearch, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(bAddCar))
                .addGap(18, 18, 18)
                .addComponent(jScrollPane3, javax.swing.GroupLayout.DEFAULT_SIZE, 248, Short.MAX_VALUE))
        );
    }// </editor-fold>//GEN-END:initComponents

    // Variables declaration - do not modify//GEN-BEGIN:variables
    public javax.swing.JButton bAddCar;
    public javax.swing.JTextField fSearch;
    private javax.swing.JScrollPane jScrollPane3;
    public javax.swing.JTable tCars;
    // End of variables declaration//GEN-END:variables
}
