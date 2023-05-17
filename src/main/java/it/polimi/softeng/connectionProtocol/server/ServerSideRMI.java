package it.polimi.softeng.connectionProtocol.server;

import it.polimi.softeng.connectionProtocol.client.ClientRemoteInterface;
import it.polimi.softeng.connectionProtocol.server.LoginManagerV2;
import it.polimi.softeng.connectionProtocol.server.ServerRemoteInterface;
import it.polimi.softeng.connectionProtocol.server.ServerSide;
import it.polimi.softeng.connectionProtocol.server.ServerSideMethods;

import java.rmi.AlreadyBoundException;
import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;
import java.rmi.server.UnicastRemoteObject;
import java.util.HashMap;
import java.util.Map;

public class ServerSideRMI extends ServerSideMethods {      //maybe we can delete this extends  ?
    private LoginManagerV2 loginManager;
    private Map<String, ClientRemoteInterface> nameToStub = new HashMap<>();

    public ServerSideRMI(LoginManagerV2 loginManager, ServerSide serverSide)  {

        super(loginManager,null,null);   //horrendous, should work
        this.loginManager = loginManager;
        ServerSideMethods obj = new ServerSideMethods(loginManager,this,serverSide);
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
        Registry registry = null;                   //TODO this could be useless
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

    //TODO ping rmi users
    public void addRMIClient(String nickName, ClientRemoteInterface stub){
        nameToStub.put(nickName,stub);
        loginManager.addStub(nickName, stub);
    }

    public void removeRMIClient(String nickName){
        nameToStub.remove(nickName);
    }

    public void pingRMIUsers(){
        Boolean flag = false;
        String playerToBeDeleted = null;
        while(true){
            for(String player : loginManager.getNickNameList()){
                try {
                    if(nameToStub.containsKey(player)) {
                        nameToStub.get(player).ping();
                    }
                } catch (RemoteException e) {
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

            try {
                Thread.sleep(5000);
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
        }
    }

    //////////getter methods

    public Map<String, ClientRemoteInterface> getNameToStub() {
        return nameToStub;
    }
}
