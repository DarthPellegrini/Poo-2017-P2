package problema.pkg2.servidor;

/**
 *
 * @author Endril
 */
public class Barco {

    private int tam;
    private String nome;
    
    public Barco(int model) {
        switch(model){
            case 1:
                nome = "Submarino";
                tam = 1;
                break;
            case 2:
                nome = "Cargueiro";
                tam = 2;
                break;
            case 3:
                nome = "Contratorpedeiro";
                tam = 3;
                break;
            case 4:
                nome = "Navio-Tanque";
                tam = 4;
                break;
            case 5:
                nome = "Porta-Aviões";
                tam = 5;
                break;
        }
    }

    public int getTam() {
        return tam;
    }

    public void setTam(int tam) {
        this.tam = tam;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }
    
    
}
