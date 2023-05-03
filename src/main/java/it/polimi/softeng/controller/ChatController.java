package it.polimi.softeng.controller;

import java.util.Objects;

public class ChatController {

    /**
     * Send a chat message to receiver (if 'all' send in broadcast)
     * @param receiver is receiver of chat message
     * @param message is chat message
     */
    public static void sendChatMessage(String receiver, String message) {

        if (Objects.equals(receiver, "all"))
            //sendMessageToAll(message);
            System.out.println("Send message");
        else
        {
               //sendMessageToPlayer(receiver, message)
        }
    }
}
