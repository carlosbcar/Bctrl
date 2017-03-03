
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.logging.Level;
import java.util.logging.Logger;

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

/**
 *
 * @author bcar
 */
public class Servidor {
    Menu menu;
    private final int PUERTO = 1234;
    private final String HOST = "localhost";
    private String mensajeServidor;
    private ServerSocket ss;
    private Socket cs;
    private DataInputStream entrada;
    private DataOutputStream salidaServidor, salidaCliente;
    /**
     * Creates new form Servidor
     */
    public Servidor(Menu menu) {
        this.menu = menu;
        esperar();
    }
    
    public void esperar(){
         try {
            ss = new ServerSocket(PUERTO);
            cs = new Socket();

            System.out.println("Esperando...");
            cs = ss.accept();

            entrada = new DataInputStream(cs.getInputStream());

            System.out.println("Cliente en linea");
            salidaCliente = new DataOutputStream(cs.getOutputStream());

            menu.nuevo(ss,entrada);
        } catch (Exception e) {
             try {
                 ss.close();
             } catch (IOException ex) {
                 Logger.getLogger(Servidor.class.getName()).log(Level.SEVERE, null, ex);
             }
            System.out.println("Error " + e.getMessage());
        }
    }
}
