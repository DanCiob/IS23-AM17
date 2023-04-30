package it.polimi.softeng.connectionProtocol;
import java.io.*;
import java.io.IOException;
import java.io.PrintWriter;
import java.net.Socket;
import java.net.UnknownHostException;
import java.util.*;

public class ClientSide {
    private Socket socket = null;
    private BufferedReader in = null;
    private PrintWriter out = null;

    public void setupConnection(String method){
        switch(method){
            case ("socket") ->{
                setupConnectionSocket();
            }
            case ("RMI") ->{
                setupConnectionRMI();
            }
        }
    }
    public void setupConnection(String method,String hostName, int portNumber){
        switch(method){
            case ("socket") ->{
                setupConnectionSocket(hostName, portNumber);
            }
            case ("RMI") ->{
                setupConnectionRMI(hostName, portNumber);
            }
        }
    }


    private void setupConnectionSocket(){
        String hostName;
        int portNumber;


        CommunicationProtocolParser communicationProtocolParser = new CommunicationProtocolParser();
        communicationProtocolParser.parser("client");

        hostName = communicationProtocolParser.getHostName();
        portNumber = communicationProtocolParser.getPortNumber();

        try{
            socket = new Socket(hostName, portNumber);
            System.out.println("connection established !");
        }catch(IOException e){
            e.printStackTrace();
            System.out.println("connection to host failed !");
        }


        try {
            in = new BufferedReader(new InputStreamReader(socket.getInputStream()));
            System.out.println("input established !");
        } catch (IOException | NullPointerException e) {
            e.printStackTrace();
        }

        try {
            out = new PrintWriter(socket.getOutputStream());
            System.out.println("output established");
        } catch (IOException e) {
            e.printStackTrace();
        }
        //block managing the read, to be redifined
        /*String s = "";
        try {
            while ((s = in.readLine()) != null) {
                System.out.println(s);
            }
        }catch(IOException | NullPointerException e){
            e.printStackTrace();
        }*/
    }
    //da ricontrollare
    private void setupConnectionSocket(String hostName, int portNumber){
        Socket socket = null;
        try{
            socket = new Socket(hostName, portNumber);
            System.out.println("connection established !");
        }catch(IOException e){
            e.printStackTrace();
            System.out.println("connection to host failed !");
        }


        try {
            in = new BufferedReader(new InputStreamReader(socket.getInputStream()));
            System.out.println("input established !");
        } catch (IOException | NullPointerException e) {
            e.printStackTrace();
        }

        try {
            out = new PrintWriter(socket.getOutputStream());
            System.out.println("output established");
        } catch (IOException e) {
            e.printStackTrace();
        }

        /*String s = "";
        try {
            while ((s = in.readLine()) != null) {
                System.out.println(s);
            }
        }catch(IOException | NullPointerException e){
            e.printStackTrace();
        }*/
    }

    private void setupConnectionRMI(){

    }

    private void setupConnectionRMI(String hostName, int portNumber){

    }

    public void sendMessage(String message){
        System.out.println("sending message "+ message);
        out.println(message);
        System.out.println("message sent");
        try {
            socket.close();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

    }

    public Socket getSocket() {
        return socket;
    }

    /*public static void main(String[] args){

        //Connection block
        ////////////////////////////////////////////////////////////////////////////////////////////////////////////////
        String hostName;
        int portNumber;

        System.out.println("Client started !");

        //written this way because who uses mains in java right
        if (args != null && args.length == 2){
            hostName = args[0];
            portNumber = Integer.parseInt(args[1]);
        }
        else{
            CommunicationProtocolParser communicationProtocolParser = new CommunicationProtocolParser();
            communicationProtocolParser.parser("client");

            hostName = communicationProtocolParser.getHostName();
            portNumber = communicationProtocolParser.getPortNumber();
        }

        Socket socket = null;
        try{
            socket = new Socket(hostName, portNumber);
            System.out.println("connection established !");
        }catch(IOException e){
            e.printStackTrace();
            System.out.println("connection to host failed !");
        }

        BufferedReader in = null;
        try {
            in = new BufferedReader(new InputStreamReader(socket.getInputStream()));
            System.out.println("input established !");
        } catch (IOException e) {
            e.printStackTrace();
        }

        String s = "";
        try {
            while ((s = in.readLine()) != null) {
                System.out.println(s);
            }
        }catch(IOException e){
            //e.printStackTrace();
        }catch(NullPointerException e ){

        }
        ////////////////////////////////////////////////////////////////////////////////////////////////////////////////

        //TUI setup
        Scanner input = new Scanner(System.in);

        System.out.println("-- MyShelfie --");
        System.out.println("Please, insert nickname: ");
        String currentNickname = input.nextLine();  // Read user input

        //Check uniqueness

        System.out.println("Welcome "+ currentNickname + "!");

        /*while(true) {
        //Player is active
            System.out.println("State of gameboard:");
            //stateBoard();
            System.out.println("State of your shelfie:");
            //stateShelfie();

            System.out.println("-- Available actions --");
            System.out.println("1: Game move");
            System.out.println("2: Send chat message");
            int currentAction = Integer.parseInt(input.nextLine());

        //Player is not active
            System.out.println("-- Available actions --");
            System.out.println("1: Send chat message");
            System.out.println("Digit number of desired action:");
            int currentAction = Integer.parseInt(input.nextLine());

            switch (currentAction)
            {
                case 1:
                    System.out.println("Type 'all' to send to global chat, or receiver name to send personal message");
                    String receiver = input.nextLine();

                    //Check existence of receiver
                    //Send message
            }
        }
    }*/
}
