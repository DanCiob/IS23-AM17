package it.polimi.softeng.JSONWriter;

import it.polimi.softeng.customExceptions.IllegalInsertException;
import org.junit.jupiter.api.Test;

import java.io.FileWriter;
import java.io.IOException;

import static it.polimi.softeng.JSONWriter.ChatWriter.chatMessageRegex;
import static it.polimi.softeng.JSONWriter.ChatWriter.writeChatMessage;
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

        JSONChatMessageTest1.write(writeChatMessage("'user1' HI!").toJSONString());
        JSONChatMessageTest2.write(writeChatMessage("'all' HI!").toJSONString());
        JSONChatMessageTest3.write(writeChatMessage("'user2' Good morning!").toJSONString());
        JSONChatMessageTest4.write(writeChatMessage("'u4' test").toJSONString());
        JSONChatMessageTest5.write(writeChatMessage("'user5' HI!").toJSONString());
        JSONChatMessageTest6.write(writeChatMessage("'user6' HI!").toJSONString());

        JSONChatMessageTest1.close();
        JSONChatMessageTest2.close();
        JSONChatMessageTest3.close();
        JSONChatMessageTest4.close();
        JSONChatMessageTest5.close();
        JSONChatMessageTest6.close();
    }
    }