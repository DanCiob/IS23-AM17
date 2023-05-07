package it.polimi.softeng.controller;

import it.polimi.softeng.connectionProtocol.ServerSide;

public class ChatController {

    /**
     * Send a chat message to receiver (if 'all' send in broadcast)
     * @param receiver is receiver of chat message
     * @param message is chat message
     * @param serverSide is socket communicator
     */
    public void sendChatMessage(String receiver, String message, ServerSide serverSide, String sender){
        if (receiver.equals("all"))
            serverSide.sendMessageExcept(message, sender);
        //TODO need to avoid send message to sender
        else
            serverSide.sendMessage(message, receiver);
    }
}
