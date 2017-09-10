package problema.pkg2.servidor;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.JOptionPane;

public class ActionController implements ActionListener{
    Battleships b;
    
    ActionController(Battleships b){
        this.b = b;
        b.jm.c.controlField(false);
    }
    
    @Override
    public void actionPerformed(ActionEvent e) {
        
            if(e.getSource() == b.is.jButton1){
                b.is.setOrient('h');
            }else
                if(e.getSource() == b.is.jButton2){
                   b.is.setOrient('v');
                }else
                    if(e.getSource() == b.is.jButton3){
                        showMessage("Esperando Adversário...");
                        b.is.setVisible(false);
                        b.is.gStart = false;
                        b.jm.jLabel2.setVisible(true);
                        b.jm.c.setVisible(true);
                        for (int i = 1; i <= 10; i++) 
                            for (int j = 1; j <= 10; j++)
                                b.jm.s.array[i][j].removeActionListener(this);
                    }else
                        /*
                         * controle de seleção de ações
                         * para que a cada evento de ação
                         * não precise ser verificado as ações dos botões
                         * do jogador 1, pois os mesmo somente serão utilizados
                         * no começo da partida
                         */
                        if(b.is.gStart){
                            setShips(e);
                        }else
                            hitShipsC(e);    
    }
    
    //atualiza o status
    public final void showMessage(final String text){
        b.jm.jTextArea1.append(text);
    }
    
    private void hitShipsC(ActionEvent e){
        for (int i = 1; i <= 10; i++) {
            for (int j = 1; j <= 10; j++) {
                if(e.getSource() == b.jm.c.array[i][j]){
                    String aux,str;
                    b.eg.addValue(0);
                    if(b.jm.c.m.getStatus()[i][j].getStatusC()==0){
                        //avisa que o jogador errou
                        str =("E "+ i + " " + j);
                        showMessage("\nVocê: " + convertToPos("E",""+i,""+j));
                        b.jm.c.array[i][j].setBackground(java.awt.Color.CYAN);
                        b.jm.c.controlField(false);
                        b.s.sendData(str);
                        b.eg.addValue(2);
                    }else{
                        //avisa que o jogador acertou
                        aux = "N";
                        b.jm.c.m.hit(b.jm.c.m.getStatus()[i][j].getBoatSize());
                        b.jm.c.array[i][j].setBackground(java.awt.Color.RED);
                        b.jm.c.m.getStatus()[i][j].setStatusC(2);
                        b.eg.addValue(1);
                        if(shipsDestroyedC(b.jm.c.m.getStatus()[i][j].getBoatSize())){
                            str = "D"; aux = b.jm.c.m.getStatus()[i][j].getBoatType();
                            b.eg.addValue(3);
                        }else
                            str = "A";
                        if(shipsDestroyedC(0))
                            endGame("S",i,j);
                        else{
                            str += " " + i + " " + j + " " + aux;
                            showMessage("\nVocê: " + convertToPos("A",""+i,""+j));
                            if(!aux.equals("N")){
                                showMessage("\n O " + aux
                                        + "\n  do adversário foi destruído!\n");
                            }
                            b.s.sendData(str);
                        }
                    }
                    b.jm.c.array[i][j].removeActionListener(this);
                    return;
                }
            }
        }
    }
    
    public final String hitShipsS(int i, int j){
        String s,aux = " "; //salvará o código de acerto
            if(b.jm.s.m.getStatus()[i][j].getStatusC()==0){
                s = "E";
                b.jm.s.array[i][j].setBackground(java.awt.Color.CYAN);
                b.jm.c.controlField(true);
            }else{
                b.jm.s.m.hit(b.jm.s.m.getStatus()[i][j].getBoatSize());
                b.jm.s.array[i][j].setBackground(java.awt.Color.RED);
                b.jm.s.m.getStatus()[i][j].setStatusC(2);
                if(shipsDestroyedS(b.jm.s.m.getStatus()[i][j].getBoatSize())){
                    s = "D";    b.eg.addValue(4);
                    aux += b.jm.s.m.getStatus()[i][j].getBoatType();
                }else
                    s = "A";
            }
            b.ac.showMessage("\nAdversário: " + b.ac.convertToPos(s,(""+i),(""+j)));
            if(shipsDestroyedS(0))
                    endGame("C",i,j);
            s += " " + i + " " + j + aux;
        return s;
    }
    
    public final void endGame(String p, int i, int j){
        if(p.equals("S")){
            showMessage("\n\n --- VOCÊ GANHOU ---");
            for (int pi = 1; pi <= 10; pi++) 
                for (int pj = 1; pj <= 10; pj++)
                    b.jm.c.array[pi][pj].removeActionListener(this);
            b.s.sendData("EG " + i + " " + j + " " + b.jm.c.m.getStatus()[i][j].getBoatType());
            JOptionPane.showMessageDialog(b, b.eg.returnData("VOCÊ GANHOU!!"), "Fim de jogo", JOptionPane.INFORMATION_MESSAGE);
        }else{
            showMessage("\n\n --- VOCÊ PERDEU ---");
            b.s.sendData("EG " + i + " " + j + " " + b.jm.s.m.getStatus()[i][j].getBoatType());
            JOptionPane.showMessageDialog(b, b.eg.returnData("VOCÊ PERDEU!!"), "Fim de jogo", JOptionPane.INFORMATION_MESSAGE);
            
        }
    }
    
    public final boolean shipsDestroyedS(int pos){
        return b.jm.s.m.getRest(pos) == 0;
    }
    
    private boolean shipsDestroyedC(int pos){
        return b.jm.c.m.getRest(pos) == 0;
    }
    
    public final String convertToPos(String type,String i, String j){
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
    
    private void setShips(ActionEvent e){
        for (int i = 1; i <= 10; i++) {
            for (int j = 1; j <= 10; j++) {
                if(e.getSource() == b.jm.s.array[i][j]){
                    boolean set = true;
                    if(b.is.getOrient() == 'v'){
                        if(j + b.is.getRest()> 11){
                            if(testSetBoats(i,(i+1),((j-b.is.getRest())+1),j))
                                for (int k = j; k > (j-b.is.getRest()); k--) {
                                    /*
                                     * pega a posição correspondente no mapa
                                     * adiciona o barco e remove o ActionListener
                                     */
                                    b.jm.s.m.getStatus()[i][k].addBoat(b.is.getRest());
                                    b.jm.s.array[i][k].setBackground(java.awt.Color.BLACK);
                                    b.jm.s.array[i][k].removeActionListener(this);
                                }
                            else
                                set = false;
                        }else{
                            if(testSetBoats(i,(i+1),j,(j+b.is.getRest())))
                                for (int k = j; k < (j+b.is.getRest()); k++) {
                                    b.jm.s.m.getStatus()[i][k].addBoat(b.is.getRest());
                                    b.jm.s.array[i][k].setBackground(java.awt.Color.BLACK);
                                    b.jm.s.array[i][k].removeActionListener(this);
                                }
                            else
                                set = false;
                        }
                    }else{
                        if(i + b.is.getRest()> 11){
                            if(testSetBoats(((i-b.is.getRest())+1),i,j,(j+1)))
                                for (int k = i; k > (i-b.is.getRest()); k--) {
                                    b.jm.s.m.getStatus()[k][j].addBoat(b.is.getRest());
                                    b.jm.s.array[k][j].setBackground(java.awt.Color.BLACK);
                                    b.jm.s.array[k][j].removeActionListener(this);
                                }
                            else
                                set = false;
                        }else{
                            if(testSetBoats(i,((i+b.is.getRest())),j,(j+1)))
                                for (int k = i; k < (i+b.is.getRest()); k++) {
                                    b.jm.s.m.getStatus()[k][j].addBoat(b.is.getRest());
                                    b.jm.s.array[k][j].setBackground(java.awt.Color.BLACK);
                                    b.jm.s.array[k][j].removeActionListener(this);
                                }
                            else
                                set = false;
                            
                        }
                    }
                    b.jm.s.m.setRest(b.is.getRest());
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
                if(b.jm.s.m.getStatus()[i][j].getStatusC()==1){
                    return false;
                }
            }
        }
        return true;
    }   
}