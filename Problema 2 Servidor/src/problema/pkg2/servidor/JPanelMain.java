package problema.pkg2.servidor;

import java.net.InetAddress;
import java.net.UnknownHostException;

/**
 *
 * @author leonardo
 */
public class JPanelMain extends javax.swing.JPanel {
    Grid s;
    Grid c;
    /**
     * Creates new form JPanelMain
     */
    public JPanelMain() {
        s = new Grid();
        this.add(s);
        s.setBounds(190, 100, 436, 436);
        c = new Grid();
        this.add(c);
        c.setBounds(640, 100, 436, 436);
        initComponents();
        try {
            jTextArea2.append("Batalha Naval em LAN\n"
                    + "\nPara jogar: "
                    + "\n Posicione seus navios"
                    + "\n utilizando os comandos"
                    + "\n e botões informados."
                    + "\n\n E então forneça este" 
                    + "\n endereço IP ao Adversário:\n IP: "
                    + InetAddress.getLocalHost().getHostAddress() 
                    + "\n\nPara atacar:"
                    + "\n Pressione o quadrante "
                    + "\n que deseja atacar."
                    + "\n\nQuando errar:"
                    + "\n Aguarde, o adversário atacará"
                    + "\n e, quando ele errar, "
                    + "\n você pode atacar novamente."
                    + "\n\nIdentificador de Status:"
                    + "\n Azul = Zona desconhecida."
                    + "\n Vermelho = Navio acertado."
                    + "\n Preto = Seus navios."
                    + "\n Ciano = Água."
                    + "\n\nPara encerrar o jogo "
                    + "\n feche esta janela."
            );
        } catch (UnknownHostException ex) {
            jTextArea1.append("Erro no Endereço de Host.\n");
        }
        jTextArea2.setEditable(false);
        jLabel2.setVisible(false);
        c.setVisible(false);
        this.setSize(1280,600);
    }

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jScrollPane1 = new javax.swing.JScrollPane();
        jTextArea1 = new javax.swing.JTextArea();
        jLabel2 = new javax.swing.JLabel();
        jLabel1 = new javax.swing.JLabel();
        jScrollPane2 = new javax.swing.JScrollPane();
        jTextArea2 = new javax.swing.JTextArea();
        jLabel3 = new javax.swing.JLabel();
        jLabel4 = new javax.swing.JLabel();

        setMinimumSize(new java.awt.Dimension(1280, 600));
        setLayout(null);

        jTextArea1.setColumns(20);
        jTextArea1.setRows(5);
        jScrollPane1.setViewportView(jTextArea1);

        add(jScrollPane1);
        jScrollPane1.setBounds(1086, 50, 190, 520);

        jLabel2.setText("Adversário");
        add(jLabel2);
        jLabel2.setBounds(840, 70, 75, 15);

        jLabel1.setText("Você");
        add(jLabel1);
        jLabel1.setBounds(377, 71, 75, 15);

        jTextArea2.setColumns(20);
        jTextArea2.setRows(5);
        jScrollPane2.setViewportView(jTextArea2);

        add(jScrollPane2);
        jScrollPane2.setBounds(10, 30, 180, 540);

        jLabel3.setText("Status de jogo:");
        add(jLabel3);
        jLabel3.setBounds(1140, 30, 90, 20);

        jLabel4.setFont(new java.awt.Font("Dialog", 1, 48)); // NOI18N
        jLabel4.setText("Batalha Naval");
        add(jLabel4);
        jLabel4.setBounds(470, 10, 350, 60);
    }// </editor-fold>//GEN-END:initComponents


    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JLabel jLabel1;
    public javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JScrollPane jScrollPane2;
    public javax.swing.JTextArea jTextArea1;
    public javax.swing.JTextArea jTextArea2;
    // End of variables declaration//GEN-END:variables
}
