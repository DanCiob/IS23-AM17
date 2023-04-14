package it.polimi.softeng.connectionProtocol;
import java.io.*;
import java.io.IOException;
import java.io.PrintWriter;
import java.net.ServerSocket;
import java.net.Socket;
import java.net.UnknownHostException;
import java.util.*;

public class ClientSide {
    public static void main(String[] args){

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
    }
}
