package problema.pkg2.cliente;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;
import java.util.StringTokenizer;
import javax.swing.JOptionPane;

public class ActionController implements ActionListener{
    Battleships b;
    
    ActionController(Battleships b){
        this.b = b;
    }
    
    @Override
    public void actionPerformed(ActionEvent e) {
        if(e.getSource() == b.jm.jButton1){
            //inicializa o cliente
            b.c.startRunning(b.jm.jTextField1.getText());
            //envia os dados de inserção
            b.c.sendData(b.c.getStoredData());
            for (int i = 1; i <= 10; i++) 
                for (int j = 1; j <= 10; j++) 
                    b.jm.s.array[i][j].addActionListener(this);
            b.jm.jButton1.setEnabled(false);
            b.jm.jTextField1.setEnabled(false);
        }else
            if(e.getSource() == b.is.jButton1){
                b.is.setOrient('h');
            }else
                if(e.getSource() == b.is.jButton2){
                   b.is.setOrient('v');
                }else
                    if(e.getSource() == b.is.jButton3){
                        showMessage("Aguardando endereço IP...");
                        b.is.setVisible(false);
                        b.is.gStart = false;
                        b.jm.jLabel2.setVisible(true);
                        b.jm.s.setVisible(true);
                        b.jm.jButton1.setEnabled(true);
                        for (int i = 1; i <= 10; i++) 
                            for (int j = 1; j <= 10; j++)
                                b.jm.c.array[i][j].removeActionListener(this);
                    }else
                        if(b.is.gStart)
                            setShips(e);
                        else{
                            hitShips(e);
                        }
    }
    
    //atualiza o status
    public final void showMessage(final String text){
        b.jm.jTextArea1.append("\n" + text);        
    }
    
    private void hitShips(ActionEvent e){
        for (int i = 1; i <= 10; i++) {
            for (int j = 1; j <= 10; j++) {
                if(e.getSource() == b.jm.s.array[i][j]){
                    /*
                     * a ação apenas envia os dados, o recebimento é feito na MAIN
                     * fora da Event Dispatched Thread
                     * evitando assim travamentos na interface gráfica
                     */
                    b.c.sendData(i + " " + j);
                    b.jm.s.array[i][j].removeActionListener(this);
                }
            }
        }
    }
    
    private String convertToPos(String type,String i, String j){
        String s = "";
        if(type.equals("E"))
            s += "Errou";
        else
            s += "Acertou";
        
        switch(i){
            case "1":   i = "A";     break;
            case "2":   i = "B";     break;
            case "3":   i = "C";     break;
            case "4":   i = "D";     break;
            case "5":   i = "E";     break;
            case "6":   i = "F";     break;
            case "7":   i = "G";     break;
            case "8":   i = "H";     break;
            case "9":   i = "I";     break;
            case "10":  i = "J";     break;
        }
        
        s += " em " + i + j;
        return s;
    }
    
    //controla os dados que saem e voltam para o cliente, marcando no campo do servidor
    public final void dataManagerC() throws IOException,ClassNotFoundException{
        String type;
        do{
            type = b.c.receiveData();
            StringTokenizer data = new StringTokenizer(type);
            type = data.nextToken();
            int pi = Integer.parseInt(data.nextToken());
            int pj = Integer.parseInt(data.nextToken());
            showMessage("Você: " + convertToPos(type,""+pi,""+pj));
            b.eg.addValue(0);
            switch(type){
                case "E":
                    b.eg.addValue(2);
                    b.jm.s.array[pi][pj].setBackground(java.awt.Color.cyan);
                    b.jm.s.controlField(false);
                    dataManagerS();
                    break;
                case "A":
                    b.eg.addValue(1);
                    b.jm.s.array[pi][pj].setBackground(java.awt.Color.red);
                    break;
                case "D":
                    b.eg.addValue(1);   b.eg.addValue(3);
                    b.jm.s.array[pi][pj].setBackground(java.awt.Color.red);
                    showMessage("O " + data.nextToken()
                    + "\n do adversário foi destruído!\n");
                    break;
                case "EG":
                    b.eg.addValue(1);   b.eg.addValue(3);
                    System.out.println("eg s");
                    b.jm.s.array[pi][pj].setBackground(java.awt.Color.red);
                    showMessage("O " + data.nextToken()
                    + "\n do adversário foi destruído!");
                    showMessage("\n --- VOCÊ GANHOU ---");
                    JOptionPane.showMessageDialog(b, b.eg.returnData("VOCÊ GANHOU!!"), "Fim de jogo", JOptionPane.INFORMATION_MESSAGE);
                    throw new IOException();
            }
        }while(!type.equals("E"));
    }
    
    //controla os dados que chegam do servidor, marcando no campo do cliente
    public final void dataManagerS() throws IOException, ClassNotFoundException{
        String type;
        StringTokenizer data;
        do{
            data = new StringTokenizer(b.c.receiveData());
            type = data.nextToken();
            int pi = Integer.parseInt(data.nextToken());
            int pj = Integer.parseInt(data.nextToken());
            showMessage("Adversário: " + convertToPos(type,"" + pi,"" + pj));
            switch(type){
                case "E":
                    b.jm.c.array[pi][pj].setBackground(java.awt.Color.cyan);
                    b.jm.s.controlField(true);
                    dataManagerC();
                    break;
                case "A":
                    b.jm.c.array[pi][pj].setBackground(java.awt.Color.red);
                    break;
                case "D":
                    b.eg.addValue(4);
                    b.jm.c.array[pi][pj].setBackground(java.awt.Color.red);
                    showMessage("O seu " + data.nextToken()
                    + "\n foi destruído!");
                    break;
                case "EG":
                    b.eg.addValue(4);
                    b.jm.c.array[pi][pj].setBackground(java.awt.Color.red);
                    showMessage("O seu " + data.nextToken()
                    + "\n foi destruído!");
                    showMessage("\n --- VOCÊ PERDEU ---");
                    JOptionPane.showMessageDialog(b, b.eg.returnData("VOCÊ PERDEU!!"), "Fim de jogo", JOptionPane.INFORMATION_MESSAGE);
                    throw new IOException();
            }
        }while(!type.equals("E"));
    }
    
    private void setShips(ActionEvent e){
        for (int i = 1; i <= 10; i++) {
            for (int j = 1; j <= 10; j++) {
                if(e.getSource() == b.jm.c.array[i][j]){
                    boolean set = true;
                    if(b.is.getOrient() == 'v'){
                        if(j + b.is.getRest()> 11){
                            if(testSetBoats(i,(i+1),((j-b.is.getRest())+1),j)){
                                for (int k = j; k > (j-b.is.getRest()); k--) {
                                    /*
                                     * pega a posição correspondente no mapa
                                     * adiciona o barco e remove o ActionListener
                                     */
                                    b.jm.c.array[i][k].setBackground(java.awt.Color.BLACK);
                                    b.jm.c.array[i][k].removeActionListener(this);
                                }
                                b.c.storeData("IV " + i + " " + (j-b.is.getRest()+1) + " " + (j) + " ");
                            }else
                                set = false;
                        }else{
                            if(testSetBoats(i,(i+1),j,(j+b.is.getRest()))){
                                for (int k = j; k < (j+b.is.getRest()); k++) {
                                    b.jm.c.array[i][k].setBackground(java.awt.Color.BLACK);
                                    b.jm.c.array[i][k].removeActionListener(this);
                                }
                                b.c.storeData("IV " + i + " " + j + " " + (j+b.is.getRest()-1) + " ");
                            }else
                                set = false;
                        }
                    }else{
                        if(i + b.is.getRest()> 11){
                            if(testSetBoats(((i-b.is.getRest())+1),i,j,(j+1))){
                                for (int k = i; k > (i-b.is.getRest()); k--) {
                                    b.jm.c.array[k][j].setBackground(java.awt.Color.BLACK);
                                    b.jm.c.array[k][j].removeActionListener(this);
                                }
                                b.c.storeData("IH " + j + " " + (i-b.is.getRest()+1) + " " + (i) + " ");
                            }else
                                set = false;
                        }else{
                            if(testSetBoats(i,((i+b.is.getRest())),j,(j+1))){
                                for (int k = i; k < (i+b.is.getRest()); k++) {
                                    b.jm.c.array[k][j].setBackground(java.awt.Color.BLACK);
                                    b.jm.c.array[k][j].removeActionListener(this);
                                }
                                b.c.storeData("IH " + j + " " + i + " " + (i+b.is.getRest()-1) + " ");
                            }else
                                set = false;
                            
                        }
                    }
                    if(b.is.getRest() == 1){
                        b.is.gStart = false;
                        b.is.jButton3.setEnabled(true);
                    }else
                        if(set){
                            b.is.subRest();
                        }
                }
            }
        }
    }   
    
    private boolean testSetBoats(int k, int kmax, int l, int lmax){
        //verifica se já há barcos em determinadas posições
        for (int i = k; i < kmax; i++) {
            for (int j = l; j < lmax; j++) {
                if(b.jm.c.array[i][j].getBackground() == java.awt.Color.BLACK){
                    return false;
                }
            }
        }
        return true;
    }
    
}
