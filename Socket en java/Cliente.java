
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
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
public class Cliente extends javax.swing.JFrame {
    private final int PUERTO = 1234;
  private final String HOST = "localhost";
  private Socket cs;
  private DataOutputStream salidaServidor;
    /**
     * Creates new form Cliente
     */
  /*
  Runnable r = new Runnable() {
            @Override
            public void run() {
                new Cliente().setVisible(true);
            }
        };
        new Thread(r).start();
  */
    public Cliente() {
       datos();
        
    }
    public void datos(){
        try {
            cs = new Socket(HOST, PUERTO);
            DataInputStream entrada = new DataInputStream(cs.getInputStream());
            salidaServidor = new DataOutputStream(cs.getOutputStream());
            salidaServidor.writeUTF("personaje2.png");
        } catch (Exception e) {
            System.out.println("Error cliente: " + e.getMessage());
        }
    }

    public static void main(String args[]) {
    }
}
