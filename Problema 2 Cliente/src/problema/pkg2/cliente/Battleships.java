/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package problema.pkg2.cliente;

import java.io.IOException;

/**
 *
 * @author super
 */
public class Battleships extends javax.swing.JFrame {
    JPanelMain jm;
    ActionController ac;
    InsertShips is;
    Client c;
    EndGameData eg;
    String data;
    /**
     * Creates new form Battleships
     */
    @SuppressWarnings("OverridableMethodCallInConstructor")
    public Battleships() {
        super("Batalha Naval");
        initComponents();
        jm = new JPanelMain();
        is = new InsertShips();
        c = new Client();
        eg = new EndGameData();
        
        jm.jButton1.setEnabled(false);
        this.getContentPane().add(is);
        this.is.move(640, 100);
        this.getContentPane().add(jm);
        
        ac = new ActionController(this);
        
        jm.jButton1.addActionListener(ac);
        is.jButton1.addActionListener(ac);
        is.jButton2.addActionListener(ac);
        is.jButton3.addActionListener(ac);
        for (int i = 1; i <= 10; i++) 
            for (int j = 1; j <= 10; j++)
                jm.c.array[i][j].addActionListener(ac);
        
    }
    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 1280, Short.MAX_VALUE)
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 600, Short.MAX_VALUE)
        );

        setBounds(0, 0, 1296, 639);
    }// </editor-fold>//GEN-END:initComponents

    /**
     * @param args the command line arguments
     */
    @SuppressWarnings({"empty-statement", "SleepWhileInLoop", "CallToPrintStackTrace"})
    public static void main(String args[]) {
        /* Set the Nimbus look and feel */
        //<editor-fold defaultstate="collapsed" desc=" Look and feel setting code (optional) ">
        /* If Nimbus (introduced in Java SE 6) is not available, stay with the default look and feel.
         * For details see http://download.oracle.com/javase/tutorial/uiswing/lookandfeel/plaf.html 
         */
        try {
            for (javax.swing.UIManager.LookAndFeelInfo info : javax.swing.UIManager.getInstalledLookAndFeels()) {
                if ("Nimbus".equals(info.getName())) {
                    javax.swing.UIManager.setLookAndFeel(info.getClassName());
                    break;
                }
            }
        } catch (ClassNotFoundException ex) {
            java.util.logging.Logger.getLogger(Battleships.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(Battleships.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(Battleships.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(Battleships.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>

        /* Create and display the form */
        Battleships b = new Battleships();
        java.awt.EventQueue.invokeLater(() -> {
            b.setVisible(true);
        });
        
        //o programa espera até que o cliente esteja conectado
        while(!b.c.isConnected()){
            try {
                //espera para verificar novamente
                Thread.sleep(1000);
            } catch (InterruptedException ex) {
                ex.printStackTrace();
            }
        }
        try {
            b.ac.showMessage("Você está conectado!");
                /*
                 * recebendo dados do servidor fora da thread da interface gráfica
                 * para que a mesma não seja afetada pelo processo demorado de recebimento de dados
                 */
            b.ac.dataManagerS();            
        } catch (IOException | ClassNotFoundException ex) {
            b.c.sendData("end"); //avisa o servidor que o jogo acabou
            b.ac.showMessage("\nEncerrando conexões...");
            for (int i = 1; i <= 10; i++) 
                for (int j = 1; j <= 10; j++)
                    b.jm.s.array[i][j].removeActionListener(b.ac);
                
        } finally{
            b.c.closeAll();
        }
    }
    
    private void waitUntilConnected(){
        
    }
    // Variables declaration - do not modify//GEN-BEGIN:variables
    // End of variables declaration//GEN-END:variables
}
