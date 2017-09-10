/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package problema.pkg2.servidor;

import java.io.*;
import java.net.*;
import javax.swing.SwingUtilities;

/**
 *
 * @author super
 */
public class Server {
    private ObjectOutputStream output;
    private ObjectInputStream input;
    private ServerSocket server;
    private Socket connection = null;
    
    //inicia a conexão
    public void startRunning(){
        try{
            server = new ServerSocket(7,10);
            //recebe e aceita a conexão
            waitConnection();
            setupStreams();    
        }catch(IOException ioExp){
            ioExp.printStackTrace();
        }
    }
    
    public final boolean isConnected(){
        return connection != null;
    }
    
    private void waitConnection() throws IOException{
        connection = server.accept();
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
    
    //recebe os dados do Adversário
    public String receiveData() throws IOException,ClassNotFoundException{
        return (String) input.readObject();
    }
    
    //envia os dados para o Adversário
    public void sendData(String data){
        SwingUtilities.invokeLater(
        new Runnable(){
            public void run(){
                try {
                    output.writeObject(data);
                    output.flush();
                } catch (IOException ex) {
                    ex.printStackTrace();
                }
            }
        }
        );
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