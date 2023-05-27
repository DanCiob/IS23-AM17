package it.polimi.softeng.controller;

import it.polimi.softeng.connectionProtocol.server.ServerSide;

/**
 * This class is used to send chat messages through {@link ServerSide}
 */

public class ChatController {

    /**
     * This method sends a chat message using {@link ServerSide}
     *  The message can be sent to all or to a specific receiver
     * @param receiver is receiver of chat message
     * @param message is chat message
     * @param serverSide is socket communicator
     */
    public void sendChatMessage(String receiver, String message, ServerSide serverSide, String sender){
        if (receiver.equals("all"))//except the sender
            serverSide.sendMessageExcept(message, sender);
        else
            serverSide.sendMessage(message, receiver);
    }
}
