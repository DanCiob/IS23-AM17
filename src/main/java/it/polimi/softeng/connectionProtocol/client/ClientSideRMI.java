package it.polimi.softeng.connectionProtocol.client;

import it.polimi.softeng.client.view.UI;
import it.polimi.softeng.connectionProtocol.CommunicationProtocolParser;
import it.polimi.softeng.connectionProtocol.server.ServerRemoteInterface;

import java.rmi.AlreadyBoundException;
import java.rmi.NotBoundException;
import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;
import java.rmi.server.UnicastRemoteObject;


/**
 * Class used when the client wants to establish an RMI connection
 */
public class ClientSideRMI {
    /**
     * attribute containing server stub
     */
    private ServerRemoteInterface stub = null;
    /**
     * reference to the methods the client will expose for the server
     */
    ClientSideMethods obj ;
    /**
     * base port from which the client will start searching an open port
     */
    int port = 1099;

    /**
     * constructor method; this one is to be called when you use the base server IP (ie the JSON file called clientConfig in the same package as this file)
     * @param ui the ui which creates the clientSide object
     */
    public ClientSideRMI(UI ui) {
        //opening the registry with clients methods
        obj = new ClientSideMethods(ui);

        openRegistry(obj);
        System.out.println("client up !");

        //connection to server
        connect();

        Thread t = new Thread(() -> pingServer());
        t.start();
    }

    /**
     * constructor method; this one is to be called when you use the user-chosen server IP
     * @param serverIP string representing the server ip (of form "xxx.xxx.xxx.xxx")
     * @param ui the ui which creates the clientside object
     */
    public ClientSideRMI(String serverIP, UI ui) {
        //opening the registry with clients methods
        obj = new ClientSideMethods(ui);

        openRegistry(obj);
        System.out.println("client up !");

        //connection to server
        connect(serverIP);
        Thread t = new Thread(() -> pingServer());
        t.start();
    }

    /**
     * method used to connect to server and get its stub when the server IP is the one in the json files
     */
    private void connect(){
        //getting the base server ip from the config files
        CommunicationProtocolParser communicationProtocolParser = new CommunicationProtocolParser();
        communicationProtocolParser.parser("client");
        String serverIP = communicationProtocolParser.getHostName() ;

        getServerStub(serverIP);
    }

    /**
     * method used to connect to server and get its stub when the server IP is user selected
     * @param serverIP the server ip address
     */
    private void connect(String serverIP){
        getServerStub(serverIP);
    }


    /**
     * this methods creates and uploads the client methods registry
     * @param obj this is the ClientSideMethods obj with the clients methods
     */
    private void openRegistry(ClientSideMethods obj){
        Registry clientRegistry = null;
        boolean portNotFound = true;
        while(portNotFound){
            try {
                clientRegistry = LocateRegistry.createRegistry(port);
                System.out.println("opened on port " + port + " successfully !");
                portNotFound = false;
            } catch (RemoteException e) {
                System.out.println("opening on port " + port + " failed !");
                port++;
            }
        }

        ClientRemoteInterface stub;
        System.out.println(obj);
        try {
            stub = (ClientRemoteInterface) UnicastRemoteObject.exportObject(obj,port);
        } catch (RemoteException e) {
            throw new RuntimeException(e);
        }

        try {
            clientRegistry.bind("ClientRemoteInterface", stub);
        } catch (RemoteException | AlreadyBoundException e) {
            throw new RuntimeException(e);
        }
    }

    /**
     * this method creates gets the server stub
     * @param serverIP string containing the server ip
     */
    private void getServerStub(String serverIP){
        System.out.println(serverIP);
        System.setProperty("java.rmi.server.hostname",serverIP);

        try {
            System.out.println("getting stub");
            stub = (ServerRemoteInterface) LocateRegistry.getRegistry(serverIP,1099).lookup("ServerRemoteInterface");
            System.out.println("connected to server");
        } catch (RemoteException | NotBoundException e) {
            throw new RuntimeException(e);
        }
    }

    /**
     * method running on its owm thread that pings the server
     */
    private void pingServer(){
        while(true){
            try{
                stub.ping();
            }catch (RemoteException e) {
                System.out.println("cannot reach server, relaunch my shelfie and retry with same nickname");
                System.exit(0);
            }
            try {
                Thread.sleep(1000);
            } catch (InterruptedException e) {
                throw new RuntimeException(e);

            }
        }

    }
    /**
     * getter method
     * @return server stub
     */
    public ServerRemoteInterface getStub() {
        return stub;
    }

    /**
     * getter method
     * @return the port on which the client object is placed
     */
    public int getPort() {
        return port;
    }
}
