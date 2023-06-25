package it.polimi.softeng.connectionProtocol.client;

import it.polimi.softeng.client.view.MessageHandler;
import it.polimi.softeng.connectionProtocol.CommunicationProtocolParser;
import org.json.simple.parser.ParseException;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;

/**
 * Class used to manage tcp connection client side
 */
public class ClientSide {
    /**
     * server ip
     */
    private String hostName;
    /**
     * server port
     */
    private int portNumber;
    /**
     * client's socket
     */

    private Socket socket;
    /**
     * flag used to say that the clientHandler got the pong from server
     */
    Boolean pong = false;
    /**
     * client's output to server
     */
    private PrintWriter out;
    /**
     * client's input from server
     */
    private BufferedReader in;
    /**
     * message handler for received messages
     */
    private MessageHandler messageHandler;

    /**
     * constructor used when you want to connect to the default server (ie the one specified in the json files)
     * @param messageHandler client's message handler
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
    /**
     * constructor method used when you want to connect to a user-chosen server
     * @param hostName server ip
     * @param portNumber server port
     * @param messageHandler client's message handler
     */
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

        t = new Thread(() -> pingServer());
        t.start();
    }

    /**
     * method used to read a message from the input,manages ping
     * @param in client's input from server
     */
    public void readMessage(BufferedReader in){
        String s = "";
        try {
            while ((s = in.readLine()) != null) {
                //part responsible for answering a ping from the server
                if(s.equals("ping")){

                }
                else messageHandler.parsingMessage(s);
            }
        } catch (IOException e) {
            System.out.println("cannot reach server, relaunch my shelfie and retry with same nickname");
            System.exit(0);
        } catch (ParseException e) {
            throw new RuntimeException(e);
        }
    }
    public void sendMessage(String message){
        out.println(message);
    }

    private void pingServer(){
        while(true){
            try {
                Thread.sleep(1000);
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
            out.println("ping"); //TODO this doesnt throw an error
        }
    }

    public Socket getSocket() {
        return socket;
    }
}
