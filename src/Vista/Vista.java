package Vista;


public class Vista extends javax.swing.JFrame {

    int cont;
    Grafo.Grafo grafo;

    public Vista() {
        initComponents();
        cont = 64;
        ((Panel) panelFondo).setImagen("../Recursos/MapaCiudad.jpg");
        this.cargar();
    }

    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        panelFondo = new Panel();
        chbxHabDesN = new javax.swing.JCheckBox();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        setTitle("La Ciudad Del 5");
        setPreferredSize(new java.awt.Dimension(1130, 780));
        setResizable(true);

        panelFondo.setBorder(new javax.swing.border.LineBorder(new java.awt.Color(0, 0, 0), 1, true));
        panelFondo.setOpaque(false);
        panelFondo.setPreferredSize(new java.awt.Dimension(915, 715));
        panelFondo.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                panelFondoMouseClicked(evt);
            }
        });

        chbxHabDesN.setText("Hab/Des Nodos");

        javax.swing.GroupLayout panelFondoLayout = new javax.swing.GroupLayout(panelFondo);
        panelFondo.setLayout(panelFondoLayout);
        panelFondoLayout.setHorizontalGroup(
            panelFondoLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(panelFondoLayout.createSequentialGroup()
                .addComponent(chbxHabDesN)
                .addGap(0, 724, Short.MAX_VALUE))
        );
        panelFondoLayout.setVerticalGroup(
            panelFondoLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(panelFondoLayout.createSequentialGroup()
                .addComponent(chbxHabDesN)
                .addGap(0, 589, Short.MAX_VALUE))
        );

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(panelFondo, javax.swing.GroupLayout.DEFAULT_SIZE, 825, Short.MAX_VALUE)
                .addContainerGap())
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(panelFondo, javax.swing.GroupLayout.DEFAULT_SIZE, 614, Short.MAX_VALUE)
                .addContainerGap())
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void panelFondoMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_panelFondoMouseClicked
        for(Grafo.Nodo nodo:this.grafo.getListNodos()){
            if(nodo.getArea().contains(evt.getX(), evt.getY()))
                System.out.println("id: " + nodo.getId() + ", x: " + nodo.getX() + ", y: " + nodo.getY());
        }
        System.out.println(evt.getX() + ", " + evt.getY());
    }//GEN-LAST:event_panelFondoMouseClicked

    public static void main(String args[]) {
        java.awt.EventQueue.invokeLater(new Runnable() {

            public void run() {
                Vista v = new Vista();
                v.setLocationRelativeTo(null);
                v.setVisible(true);
            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JCheckBox chbxHabDesN;
    public javax.swing.JPanel panelFondo;
    // End of variables declaration//GEN-END:variables

    private void cargar() {
        this.grafo = new Grafo.Grafo(108);
    }

}
