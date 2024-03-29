/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

/*
 * PStationFleet.java
 *
 * Created on 11.09.2010, 17:34:53
 */
package rzd.stationFleet;

/**
 * @author ЧерныхЕА
 */
public class PStationFleet extends javax.swing.JPanel {

    private ControllerStation c;
    private String strSearch;

    /**
     * Creates new form PStationFleet
     */
    public PStationFleet() {
        initComponents();
        strSearch = fSearchCarByNumber.getText();
        this.c = new ControllerStation(this);
    }

     public ControllerStation getController(){
         return c;
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

        jToggleButton1 = new javax.swing.JToggleButton();
        jPanel1 = new javax.swing.JPanel();
        fSearchCarByNumber = new javax.swing.JTextField();
        bSearch = new javax.swing.JButton();
        tabbedStation = new javax.swing.JTabbedPane();

        jToggleButton1.setText("jToggleButton1");

        fSearchCarByNumber.setFont(new java.awt.Font("Tahoma", 2, 11));
        fSearchCarByNumber.setToolTipText("Поиск по номеру вагона");

        bSearch.setIcon(new javax.swing.ImageIcon(getClass().getResource("/rzd/resurce/search.gif"))); // NOI18N
        bSearch.setBorder(javax.swing.BorderFactory.createEtchedBorder());
        bSearch.setBorderPainted(false);
        bSearch.setContentAreaFilled(false);
        bSearch.setFocusPainted(false);

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addGap(7, 7, 7)
                .addComponent(fSearchCarByNumber, javax.swing.GroupLayout.PREFERRED_SIZE, 144, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(bSearch)
                .addContainerGap(221, Short.MAX_VALUE))
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel1Layout.createSequentialGroup()
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(bSearch, javax.swing.GroupLayout.PREFERRED_SIZE, 16, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(fSearchCarByNumber, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(17, 17, 17))
        );

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(this);
        this.setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jPanel1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
            .addComponent(tabbedStation, javax.swing.GroupLayout.DEFAULT_SIZE, 402, Short.MAX_VALUE)
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addComponent(jPanel1, javax.swing.GroupLayout.PREFERRED_SIZE, 39, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(tabbedStation, javax.swing.GroupLayout.DEFAULT_SIZE, 287, Short.MAX_VALUE))
        );
    }// </editor-fold>//GEN-END:initComponents

    // Variables declaration - do not modify//GEN-BEGIN:variables
    public javax.swing.JButton bSearch;
    public javax.swing.JTextField fSearchCarByNumber;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JToggleButton jToggleButton1;
    public javax.swing.JTabbedPane tabbedStation;
    // End of variables declaration//GEN-END:variables
}
