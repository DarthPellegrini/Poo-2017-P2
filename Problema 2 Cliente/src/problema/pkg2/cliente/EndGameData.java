package problema.pkg2.cliente;

import java.text.DecimalFormat;

/**
 *
 * @author Leonardo
 */
public class EndGameData {
    private double[] data;
    
    public EndGameData(){
        data = new double[5];
        for (int i = 0; i < 5; i++)
            data[i] = 0;
    }
    
    public final String returnData(String message){
        String str = message;
        str += ("\nTotal de Disparos: " +(int)data[0]);
        if(data[0]==0) data[0]=1; //previne divisão por 0
        str +=("\nTotal de Acertos: "+(int)data[1]);
        str +=(" ("+new DecimalFormat("0.##%)").format(data[1]/data[0]));
        str +=("\nTotal de Erros: "+(int)data[2]);
        str +=(" ("+new DecimalFormat("0.##%)").format(data[2]/data[0]));
        str +=("\nNavios Inimigos Destruídos: "+(int)data[3]);
        str +=("\nNavios Aliados Destruídos: "+(int)data[4]);
        return str;
    }
    
    public final void addValue(int where){
        //shots = 0, hits = 1, misses = 2, enemyDown = 3, allyDown = 4
        data[where]++;
    }
}
