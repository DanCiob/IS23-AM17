package it.polimi.softeng.controller;

import it.polimi.softeng.JSONWriter.ChatWriter;
import org.json.simple.JSONObject;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class ChatControllerTest {

    @Test
    void sendChatMessage1Test() {
        ChatWriter chatWriter;
        JSONObject obj = ChatWriter.writeChatMessage("Test message");
    }
}