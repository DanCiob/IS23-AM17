package it.polimi.softeng.JSONWriter;

import it.polimi.softeng.customExceptions.IllegalInsertException;
import org.junit.jupiter.api.Test;

import java.io.FileWriter;
import java.io.IOException;

import static it.polimi.softeng.JSONWriter.ChatWriter.chatMessageRegex;
import static it.polimi.softeng.JSONWriter.ChatWriter.writeChatMessage;
import static it.polimi.softeng.JSONWriter.SignatureWriter.signObject;
import static junit.framework.Assert.*;

class ChatWriterTest {

    @Test
    void chatMessageRegexTest() {

        assertTrue(chatMessageRegex("'all' HI!"));
        assertTrue(chatMessageRegex("'user1' Test with spaces"));
        assertFalse(chatMessageRegex("No receiver"));
        assertFalse(chatMessageRegex("'Empty message'"));
    }

    @Test
    void writeChatMessageTest() throws IllegalInsertException, IOException {

        FileWriter JSONChatMessageTest1;
        FileWriter JSONChatMessageTest2;
        FileWriter JSONChatMessageTest3;
        FileWriter JSONChatMessageTest4;
        FileWriter JSONChatMessageTest5;
        FileWriter JSONChatMessageTest6;
        try {
            JSONChatMessageTest1 = new FileWriter("src/main/java/it/polimi/softeng/JSONMessages/Test/ChatMessage1.json");
            JSONChatMessageTest2 = new FileWriter("src/main/java/it/polimi/softeng/JSONMessages/Test/ChatMessage2.json");
            JSONChatMessageTest3 = new FileWriter("src/main/java/it/polimi/softeng/JSONMessages/Test/ChatMessage3.json");
            JSONChatMessageTest4 = new FileWriter("src/main/java/it/polimi/softeng/JSONMessages/Test/ChatMessage4.json");
            JSONChatMessageTest5 = new FileWriter("src/main/java/it/polimi/softeng/JSONMessages/Test/ChatMessage5.json");
            JSONChatMessageTest6 = new FileWriter("src/main/java/it/polimi/softeng/JSONMessages/Test/ChatMessage6.json");
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

        JSONChatMessageTest1.write(signObject(writeChatMessage("'user1' HI!"), "@CHAT", "requester").toJSONString());
        JSONChatMessageTest2.write(signObject(writeChatMessage("'all' HI!"), "@CHAT", "requester").toJSONString());
        JSONChatMessageTest3.write(signObject(writeChatMessage("'user2' Good morning!"), "@CHAT", "requester").toJSONString());
        JSONChatMessageTest4.write(signObject(writeChatMessage("'u4' test"), "@CHAT", "requester").toJSONString());
        JSONChatMessageTest5.write(signObject(writeChatMessage("'user5' HI!"), "@CHAT", "requester").toJSONString());
        JSONChatMessageTest6.write(signObject(writeChatMessage("'user6' HI!"), "@CHAT", "requester").toJSONString());

        JSONChatMessageTest1.close();
        JSONChatMessageTest2.close();
        JSONChatMessageTest3.close();
        JSONChatMessageTest4.close();
        JSONChatMessageTest5.close();
        JSONChatMessageTest6.close();
    }
    }