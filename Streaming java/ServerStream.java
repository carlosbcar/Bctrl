/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Prueba2;

import java.io.DataOutputStream;
import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;

import javax.sound.sampled.AudioFormat;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.DataLine;
import javax.sound.sampled.SourceDataLine;
import javax.sound.sampled.TargetDataLine;

//import org.apache.commons.io.output.ByteArrayOutputStream;
public class ServerStream {

    private OutgoingSoudnListener osl = new OutgoingSoudnListener();
    boolean outVoice = true;
    AudioFormat format = getAudioFormat();
    private ServerSocket serverSocket;
    Socket server;

    private AudioFormat getAudioFormat() {
        float sampleRate = 16000.0F;
        int sampleSizeBits = 8;
        int channels = 1;
        boolean signed = true;
        boolean bigEndian = false;

        return new AudioFormat(sampleRate, sampleSizeBits, channels, signed, bigEndian);
    }

    public ServerStream() throws IOException {
        try {
            System.out.println("Creating Socket...");
            serverSocket = new ServerSocket(3000);
            osl.runSender();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    class OutgoingSoudnListener {

        public void runSender() {
            try {
                server = serverSocket.accept();
                System.out.println("Listening from mic.");
                DataOutputStream out = new DataOutputStream(server.getOutputStream());
                DataLine.Info micInfo = new DataLine.Info(TargetDataLine.class, format);
                TargetDataLine mic = (TargetDataLine) AudioSystem.getLine(micInfo);
                mic.open(format);
                System.out.println("Mic open.");
                byte tmpBuff[] = new byte[mic.getBufferSize() / 5];
                mic.start();

                DataLine.Info speakerInfo = new DataLine.Info(SourceDataLine.class, format);
                SourceDataLine speaker = (SourceDataLine) AudioSystem.getLine(speakerInfo);
                speaker.open(format);
                speaker.start();

                while (outVoice) {
                    System.out.println("Reading from mic.");
                    int count = mic.read(tmpBuff, 0, tmpBuff.length);
                    if (count > 0) {
                        System.out.println("Writing buffer to server.");

                        //speaker.write(tmpBuff, 0, tmpBuff.length);
                        out.write(tmpBuff, 0, tmpBuff.length);
                    }
                }
                mic.drain();
                mic.close();
                System.out.println("Stopped listening from mic.");
            } catch (Exception e) {
                e.printStackTrace();
            }
        }

    }

    public static void main(String args[]) throws IOException {
        new ServerStream();

    }

}
