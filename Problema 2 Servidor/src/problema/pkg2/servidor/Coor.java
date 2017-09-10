package problema.pkg2.servidor;

/**
 *
 * @author Endril
 */
public class Coor {
    
    private Barco boat;
    private int statusC; //0 para vazio | 1 para com barco | 2 para com barco acertado 
                         //int vai ser util na hora de usar a interface gráfica
    public Coor() {
        statusC = 0;
    }
    
    public Coor(Barco boat){
        this.boat = boat;
        statusC = 1;
    }

    public int getBoatSize() {
        return boat.getTam();
    }
    
    public String getBoatType(){
        return boat.getNome();
    }

    public int getStatusC() {
        return statusC;
    }
    
    public void setStatusC(int statusC){
        this.statusC = statusC;
    }
    
    public void addBoat(int model) {
        this.boat = new Barco(model);
        this.statusC = 1;
    }
}
