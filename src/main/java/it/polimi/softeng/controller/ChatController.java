package it.polimi.softeng.controller;

import it.polimi.softeng.connectionProtocol.ServerSide;

import javax.naming.InvalidNameException;

public class ChatController {

    /**
     * Send a chat message to receiver (if 'all' send in broadcast)
     * @param receiver is receiver of chat message
     * @param message is chat message
     * @param serverSide is socket communicator
     */
    public void sendChatMessage(String receiver, String message, ServerSide serverSide) throws InvalidNameException {
        if (receiver.equals("all"))
            serverSide.sendMessageToAll(message);
        else
            serverSide.sendMessage(message, receiver);
    }
}
