package it.polimi.softeng.connectionProtocol;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class ClientSideTest {

    @Test
    void connectionTest(){
        ClientSide clientSide = new ClientSide();
        ServerSide serverSide = new ServerSide();

        ServerSide.main(null);

        clientSide.setupConnection("socket");
        assertNotNull(clientSide.getSocket());
    }
    @Test
    void sendMessageTest(){

    }
}