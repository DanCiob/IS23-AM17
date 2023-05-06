package it.polimi.softeng.connectionProtocol;

import it.polimi.softeng.client.view.MessageHandler;
import org.json.simple.parser.ParseException;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;

public class ClientSide {
    private String hostName;
    private int portNumber;
    private Socket socket;
    private PrintWriter out;
    private BufferedReader in;

    private MessageHandler messageHandler;

    /**
     * Used for testing
     */
    public ClientSide(MessageHandler messageHandler){

        CommunicationProtocolParser communicationProtocolParser = new CommunicationProtocolParser();
        communicationProtocolParser.parser("client");
        hostName = communicationProtocolParser.getHostName();
        portNumber = communicationProtocolParser.getPortNumber();
        this.messageHandler = messageHandler;

        try {
            socket = new Socket(hostName, portNumber);
            System.out.println("connection established !");
            out = new PrintWriter(socket.getOutputStream(), true);
            in = new BufferedReader(new InputStreamReader(socket.getInputStream()));
        }catch(IOException e) {
            e.printStackTrace();
        }

        Thread t = new Thread(() -> readMessage(in));
        t.start();

    }

    public ClientSide(String hostName, int portNumber, MessageHandler messageHandler){

        this.hostName = hostName;
        this.portNumber = portNumber;
        this.messageHandler = messageHandler;

        try {
            socket = new Socket(hostName, portNumber);
            System.out.println("connection established !");
            out = new PrintWriter(socket.getOutputStream(), true);
            in = new BufferedReader(new InputStreamReader(socket.getInputStream()));
        }catch(IOException e) {
            e.printStackTrace();
        }

        Thread t = new Thread(() -> readMessage(in));
        t.start();

    }

    public void readMessage(BufferedReader in){
        String s = "";
        try {
            while ((s = in.readLine()) != null) {
                System.out.println(s);
                messageHandler.parsingMessage(s);
            }
        } catch (IOException e) {
            e.printStackTrace();
        } catch (ParseException e) {
            throw new RuntimeException(e);
        }
    }
    public void sendMessage(String message){
        out.println(message);
    }
}
