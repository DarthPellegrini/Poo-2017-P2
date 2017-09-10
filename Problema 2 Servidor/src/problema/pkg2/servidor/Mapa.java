package problema.pkg2.servidor;

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

/**
 *
 * @author Endril
 */
public class Mapa {
    private Coor[][] status;
    private int[] rest; //quantidade restante de barcos e de pedaços de cada barco
    
    public Mapa() {
       status = new Coor[11][11];
       initCoor();
       rest = new int[6];
    }

    private void initCoor(){
        for(int i=1;i<=10;i++){
            for(int j=1;j<=10;j++){
                status[i][j] = new Coor();
            }
        }
    }
    
    public Coor[][] getStatus() {
        return status;
    }
    
    public void setStatus(Coor[][] status) {
        this.status = status;
    }
    
    public void setRest(int pos){
        rest[pos] = pos;
        rest[0]++;
    }
    
    public int getRest(int pos){
        return rest[pos];
    }
    
    public void hit(int pos){
        if(rest[pos] > 0)
            rest[pos]--;
        if(rest[pos] == 0){
            rest[0]--;
        }
    }
}
