package it.polimi.softeng.JSONWriter;

import it.polimi.softeng.customExceptions.IllegalInsertException;
import org.junit.jupiter.api.Test;

import java.io.FileWriter;
import java.io.IOException;

import static it.polimi.softeng.JSONWriter.GameMoveWriter.*;
import static it.polimi.softeng.JSONWriter.SignatureWriter.signObject;
import static junit.framework.Assert.*;

class GameMoveWriterTest {

    /**
     * Series of test for Regex parser of gameMove
     */
    @Test
    void gameMoveRegexTest() {
        assertTrue(gameMoveRegex("(0,1),(2,1),(3,3),4"));
        assertTrue(gameMoveRegex("(3,3),4"));
        assertTrue(gameMoveRegex("(2,3),(9,1),1"));
        assertFalse(gameMoveRegex("(9,1),(2,3),(10,3),4"));
        assertFalse(gameMoveRegex("(0,1),(2,1),(),4"));
        assertFalse(gameMoveRegex("(),2"));
        assertFalse(gameMoveRegex(""));
        assertFalse(gameMoveRegex("(0,1),(0,1),(0,1),2"));
        assertFalse(gameMoveRegex("(0,1 ),(0, 1),2"));
        assertFalse(gameMoveRegex("(0,10),(10,1),(20,1),2"));
        assertFalse(gameMoveRegex("(0,1),(0,1),(0,1)"));
        assertFalse(gameMoveRegex("(0,1),(0,1),(0,1),(0,1),(0,1),(0,1),2"));
        assertFalse(gameMoveRegex("(0,1),(0,1),(0,1),(0,1),(0,1),(0,1),2"));
        assertFalse(gameMoveRegex("2"));
        assertFalse(gameMoveRegex("(0,1),(0,1),(0,1),(0,1),(0,1),(0,1),6)"));
        assertFalse(gameMoveRegex("testregex"));
        assertFalse(gameMoveRegex("((0),)),(8,1),(0,1),(0,1),(0,1),(0,1),6)"));
    }

    /**
     * Be sure to delete files for testing before running test
     */
    @Test
    void writeGameMoveTest() throws IOException, IllegalInsertException {

        FileWriter JSONGameMoveTest1;
        FileWriter JSONGameMoveTest2;
        FileWriter JSONGameMoveTest3;
        FileWriter JSONGameMoveTest4;
        FileWriter JSONGameMoveTest5;
        FileWriter JSONGameMoveTest6;
        try {
            JSONGameMoveTest1 = new FileWriter("src/main/java/it/polimi/softeng/JSONMessages/Test/GameMove1.json");
            JSONGameMoveTest2 = new FileWriter("src/main/java/it/polimi/softeng/JSONMessages/Test/GameMove2.json");
            JSONGameMoveTest3 = new FileWriter("src/main/java/it/polimi/softeng/JSONMessages/Test/GameMove3.json");
            JSONGameMoveTest4 = new FileWriter("src/main/java/it/polimi/softeng/JSONMessages/Test/GameMove4.json");
            JSONGameMoveTest5 = new FileWriter("src/main/java/it/polimi/softeng/JSONMessages/Test/GameMove5.json");
            JSONGameMoveTest6 = new FileWriter("src/main/java/it/polimi/softeng/JSONMessages/Test/GameMove6.json");
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

        JSONGameMoveTest1.write(signObject(writeGameMove("(0,1 ),3 "), "@GAME", "requester").toJSONString());
        JSONGameMoveTest2.write(signObject(writeGameMove("(0,1),(2,4),2"), "@GAME", "requester").toJSONString());
        JSONGameMoveTest3.write(signObject(writeGameMove("(0,1),(2,4),(6,3),2"), "@GAME", "requester").toJSONString());
        JSONGameMoveTest4.write(signObject(writeGameMove("(0,5 ),3 "), "@GAME", "requester").toJSONString());
        JSONGameMoveTest5.write(signObject(writeGameMove("(4,2),(3,1),0"), "@GAME", "requester").toJSONString());
        JSONGameMoveTest6.write(signObject(writeGameMove("(1,2),(2,4),(5,3),1"), "@GAME", "requester").toJSONString());

        JSONGameMoveTest1.close();
        JSONGameMoveTest2.close();
        JSONGameMoveTest3.close();
        JSONGameMoveTest4.close();
        JSONGameMoveTest5.close();
        JSONGameMoveTest6.close();
    }
}