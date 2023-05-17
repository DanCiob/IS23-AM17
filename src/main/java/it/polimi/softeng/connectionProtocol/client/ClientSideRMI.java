package it.polimi.softeng.connectionProtocol.client;

import it.polimi.softeng.client.view.CLI.CLI;
import it.polimi.softeng.client.view.CLI.CLITest;
import it.polimi.softeng.connectionProtocol.server.ServerRemoteInterface;

import java.rmi.AlreadyBoundException;
import java.rmi.NotBoundException;
import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;
import java.rmi.server.UnicastRemoteObject;

public class ClientSideRMI {

    private ServerRemoteInterface stub = null;

    public ClientSideRMI(CLI cli ) {
        ClientSideMethods obj = new ClientSideMethods(cli);
        ClientRemoteInterface stub  = null;
        try {
            stub = (ClientRemoteInterface) UnicastRemoteObject.exportObject(obj,0);
        } catch (RemoteException e) {
            throw new RuntimeException(e);
        }
        Registry clientRegistry = null;
        try {
            clientRegistry = LocateRegistry.createRegistry(1099);
        } catch (RemoteException e) {
            throw new RuntimeException(e);
        }
        try {
            clientRegistry.bind("ClientRemoteInterface", stub);
        } catch (RemoteException | AlreadyBoundException e) {
            throw new RuntimeException(e);
        }
        System.out.println("client up !");

        //connection to server
        connect();
    }
    // TODO connection to server
    public ClientSideRMI(String serverIP, CLI cli) {
        ClientSideMethods obj = new ClientSideMethods(cli);
        ClientRemoteInterface stub  = null;
        try {
            stub = (ClientRemoteInterface) UnicastRemoteObject.exportObject(obj,0);
        } catch (RemoteException e) {
            throw new RuntimeException(e);
        }
        Registry clientRegistry = null;
        try {
            clientRegistry = LocateRegistry.createRegistry(1099);
        } catch (RemoteException e) {
            throw new RuntimeException(e);
        }
        try {
            clientRegistry.bind("ClientRemoteInterface", stub);
        } catch (RemoteException | AlreadyBoundException e) {
            throw new RuntimeException(e);
        }
        System.out.println("client up !");

        //connection to server
        connect(serverIP);
    }

    public void connect(){
        Registry registry = null;
        try {
            //needs changing
            registry = LocateRegistry.getRegistry("127.0.0.1",1099);
            System.out.println("connected to server");
        } catch (RemoteException e) {
            e.printStackTrace();
        }

        try {
            stub = (ServerRemoteInterface) registry.lookup("ServerRemoteInterface");
        } catch (RemoteException | NotBoundException e) {
            throw new RuntimeException(e);
        }

    }

    public void connect(String serverIP){
        Registry registry = null;
        try {
            //needs changing
            registry = LocateRegistry.getRegistry(serverIP,1099);
            System.out.println("connected to server");
        } catch (RemoteException e) {
            e.printStackTrace();
        }

        try {
            stub = (ServerRemoteInterface) registry.lookup("ServerRemoteInterface");
        } catch (RemoteException | NotBoundException e) {
            throw new RuntimeException(e);
        }
        //TODO chiamare comando di login
    }

    public ServerRemoteInterface getStub() {
        return stub;
    }


    // version used solely for local testing or rmi
    public ClientSideRMI(int port,CLI cli) {
        ClientSideMethods obj = new ClientSideMethods(cli);
        ClientRemoteInterface stub  = null;
        try {
            stub = (ClientRemoteInterface) UnicastRemoteObject.exportObject(obj,port);
        } catch (RemoteException e) {
            throw new RuntimeException(e);
        }
        Registry clientRegistry = null;
        try {
            clientRegistry = LocateRegistry.createRegistry(port);
        } catch (RemoteException e) {
            throw new RuntimeException(e);
        }
        try {
            clientRegistry.bind("ClientRemoteInterface", stub);
        } catch (RemoteException | AlreadyBoundException e) {
            throw new RuntimeException(e);
        }
        System.out.println("client up !");

        //connection to server
        connect();
    }
}
