package problema.pkg2.servidor;

import java.io.IOException;
import java.util.StringTokenizer;

public class Battleships extends javax.swing.JFrame {
    JPanelMain jm;
    InsertShips is;
    ActionController ac;
    EndGameData eg;
    Server s;
    
    @SuppressWarnings("OverridableMethodCallInConstructor")
    public Battleships() {
        super("Batalha Naval");
        initComponents();
        jm = new JPanelMain();
        is = new InsertShips();
        s = new Server();
        eg = new EndGameData();
        this.getContentPane().add(is);
        this.is.move(640, 100);
        this.getContentPane().add(jm);
        
        ac = new ActionController(this);
        
        is.jButton1.addActionListener(ac);
        is.jButton2.addActionListener(ac);
        is.jButton3.addActionListener(ac);
        for (int i = 1; i <= 10; i++) 
            for (int j = 1; j <= 10; j++)
                jm.s.array[i][j].addActionListener(ac);
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
    @SuppressWarnings("CallToPrintStackTrace")
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
        } catch (ClassNotFoundException | InstantiationException | IllegalAccessException | javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(Battleships.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>
        //</editor-fold>
        
        //</editor-fold>
        //</editor-fold>
        
        /* Create and display the form */
        Battleships b = new Battleships();
        java.awt.EventQueue.invokeLater(() -> {
            b.setVisible(true);
        });
        
        /*
         * inicializando o servidor e recebendo dados do cliente fora da thread da interface gráfica
         * para que a mesma não seja afetada pelo processo demorado de recebimento de dados
         */
        b.s.startRunning();
        
        try{
            //inserindo os navios inimigos no mapa assim que os dados forem recebidos
            b.setShipsC();
            b.ac.showMessage("\nAdversário Conectado!");
            //e inicializa o campo do adversário
            b.jm.c.controlField(true);
            for (int i = 1; i <= 10; i++) 
                for (int j = 1; j <= 10; j++) 
                    b.jm.c.array[i][j].addActionListener(b.ac);
            b.clientAction();
        }catch(IOException | ClassNotFoundException ex){
            b.ac.showMessage("\n\nEncerrando conexões...");
        }
    }
    
    @SuppressWarnings("InfiniteRecursion")
    private void clientAction() throws IOException, ClassNotFoundException{
        String str,aux = "N";
        str = s.receiveData();
        //espera até que o cliente receba os dados para encerrar o programa
        if (str.equals("end"))
            throw new IOException();
        //str recebe os dados do cliente
        StringTokenizer st = new StringTokenizer(str);
        //converte as posições recebidas para Inteiro
        int si = Integer.parseInt(st.nextToken());
        int sj = Integer.parseInt(st.nextToken());
        //verifica se o cliente acertou
        str = ac.hitShipsS(si,sj);
        //e envia os dados para o cliente
        s.sendData(str + " " + aux);
        /*
         * chama o método novamente indefinidamente
         * sendo parado somente quando o jogo terminar
         */
        clientAction();
    }
    
    private void setShipsC() throws IOException, ClassNotFoundException{
        String shipsPos = s.receiveData();
        int pos,sta,end,size;
        StringTokenizer st = new StringTokenizer(shipsPos);
        while(st.hasMoreTokens()){
            if(st.nextToken().equals("IH")){
                pos = Integer.parseInt(st.nextToken());
                sta = Integer.parseInt(st.nextToken());
                end = Integer.parseInt(st.nextToken());
                size = (end+1)-sta;
                for (int i = sta; i <= end; i++){
                    jm.c.m.getStatus()[i][pos].setStatusC(1);
                    jm.c.m.getStatus()[i][pos].addBoat(size);
                }
            }else{
                pos = Integer.parseInt(st.nextToken());
                sta = Integer.parseInt(st.nextToken());
                end = Integer.parseInt(st.nextToken());
                size = (end+1)-sta;
                for (int i = sta; i <= end; i++){
                    jm.c.m.getStatus()[pos][i].setStatusC(1);
                    jm.c.m.getStatus()[pos][i].addBoat(size);
                }
            }
            jm.c.m.setRest(size);
        }
    }
    
    // Variables declaration - do not modify//GEN-BEGIN:variables
    // End of variables declaration//GEN-END:variables
}