package it.polimi.softeng.connectionProtocol.server;

import it.polimi.softeng.connectionProtocol.client.ClientRemoteInterface;
import it.polimi.softeng.connectionProtocol.server.LoginManagerV2;
import it.polimi.softeng.connectionProtocol.server.ServerRemoteInterface;
import it.polimi.softeng.connectionProtocol.server.ServerSide;
import it.polimi.softeng.connectionProtocol.server.ServerSideMethods;
import it.polimi.softeng.controller.Controller;

import java.rmi.AlreadyBoundException;
import java.rmi.ConnectException;
import java.rmi.ConnectIOException;
import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;
import java.rmi.server.UnicastRemoteObject;
import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

/**
 * Class used to accept and manage rmi clients server side
 */
public class ServerSideRMI extends ServerSideMethods {
    /**
     * needed to manage logins and disconnection
     */
    private LoginManagerV2 loginManager;
    /**
     * map that connects the identity of a player to it's method stub
     */
    private Map<String, ClientRemoteInterface> nameToStub = new HashMap<>();
    /**
     * server's methods object that will be exposed to clients on the registry
     */
    private ServerSideMethods obj;

    /**
     * constructor method for serverSideRMI that creates the registry and starts the ping function to assert player's connection
     * @param loginManager login manager of the match
     * @param serverSide serverside of the match
     * @param controller controller of the match
     */
    public ServerSideRMI(LoginManagerV2 loginManager, ServerSide serverSide, Controller controller)  {

        super(loginManager,null,null, controller);
        this.loginManager = loginManager;
        //System.setProperty("sun.rmi.transport.tcp.responseTimeout", String.valueOf(1000));
        obj = new ServerSideMethods(loginManager,this,serverSide,controller);
        ServerRemoteInterface stub  = null;
        try {
            stub = (ServerRemoteInterface) UnicastRemoteObject.exportObject(obj,0);
        } catch (RemoteException e) {
            throw new RuntimeException(e);
        }
        try {
            LocateRegistry.createRegistry(1099); //TODO make this parametric
        } catch (RemoteException e) {
            throw new RuntimeException(e);
        }
        Registry registry = null;
        try {
            registry = LocateRegistry.getRegistry("127.0.0.1");
        } catch (RemoteException e) {
            throw new RuntimeException(e);
        }
        System.out.println(registry);
        try {
            registry.bind("ServerRemoteInterface", stub);
        } catch (RemoteException | AlreadyBoundException e) {
            throw new RuntimeException(e);
        }

        System.out.println("server up !");

        Thread t = new Thread(() -> pingRMIUsers());
        t.start();
    }

    /**
     * method used to add an RMI client and connect its identity to its method stub
     * @param nickName nickname of the player to login
     * @param stub player's stub
     */
    public void addRMIClient(String nickName, ClientRemoteInterface stub){
        nameToStub.put(nickName,stub);
        System.out.println(stub);
        try {
            System.out.println(stub.ping());
        } catch (RemoteException e) {
            e.printStackTrace();
        }
    }

    /**
     * method to remove the player from map nameToStub
     * @param nickName
     */
    public void removeRMIClient(String nickName){
        nameToStub.remove(nickName);
    }

    /**
     * method that pings users every 5s to assert they're connection to the server
     */
    public void pingRMIUsers(){
        Boolean flag = false;
        String playerToBeDeleted = null;
        while(true){
            synchronized (loginManager.getNickNameList()){
                for(String player : loginManager.getNickNameList()){
                    try {
                        if(nameToStub.containsKey(player)) { //todo this should be a list of players to be removed
                            nameToStub.get(player).ping();
                        }
                    } catch (RemoteException e ) {
                        //e.printStackTrace();
                        playerToBeDeleted = player;
                        flag = true;
                    }
                }
                if(flag){
                    loginManager.addDisconnectedPlayer(playerToBeDeleted);
                    removeRMIClient(playerToBeDeleted);
                    flag = false;
                    playerToBeDeleted = null;
                }
            }

            try {
                Thread.sleep(500);
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
        }
    }

    //////////getter methods

    /**
     * getter method
     * @return map nameToStub
     */
    public Map<String, ClientRemoteInterface> getNameToStub() {
        return nameToStub;
    }
}
