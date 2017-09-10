package problema.pkg2.cliente;

import java.io.*;
import java.net.*;

/**
 *
 * @author Leonardo
 */
public class Client {
    private ObjectOutputStream output;
    private ObjectInputStream input;
    private Socket connection = null;
    private String shipsData = "";
    
    //inicia a conexão
    public void startRunning(String ip){
        try{
            connectToServer(ip);
            setupStreams();
        }catch(IOException ioExp){
            ioExp.printStackTrace();
        }
    }
    
    //se conecta com o servidor
    private void connectToServer(String ip) throws IOException{
        connection = new Socket(InetAddress.getByName(ip),7);
    }
    
    //verifica se está conectado
    public final boolean isConnected(){
        return connection != null;
    }
    
    
    //prepara as Streams de saída e entrada
    private void setupStreams() throws IOException{
        //para enviar dados ao outro computador
        output = new ObjectOutputStream(connection.getOutputStream());
        //limpa o buffer do output
        output.flush();
        //para receber os dados do outro computador
        input = new ObjectInputStream(connection.getInputStream());
    }
    
    //salva os dados de inserção de cada navio
    public void storeData(String shipsData){
        this.shipsData += shipsData;
    }
    
    //retorna os dados de cada navio
    public String getStoredData(){
        return shipsData;
    }
    
    //recebe os dados do Adversário
    public String receiveData() throws IOException,ClassNotFoundException{
        return (String) input.readObject();
    }
    
    //envia os dados para o Adversário
    public void sendData(String data){
        try {
            output.writeObject(data);
            output.flush();
        } catch (IOException ex) {
            ex.printStackTrace();
        }
    }
    
    //fecha as Streams e os Sockets
    public final void closeAll(){
        try{
            output.close();
            input.close();
            connection.close();
        }catch(IOException ioExp){
            System.out.println(ioExp);
        }
    }
}