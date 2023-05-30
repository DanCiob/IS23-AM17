package it.polimi.softeng.connectionProtocol;
import org.json.simple.*;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.*;

import java.io.FileReader;
import java.io.Reader;

/**
 * class used to read the json config files for client and server
 */
public class CommunicationProtocolParser{
    private int portNumber;
    private String hostName;

    /**
     * parser method that parses the config files
     * @param mode "client" for client use, "server" for server use
     */
    public void parser(String mode){
        String directory;
        JSONParser parser = new JSONParser();

        //to be refactored with the usage of "resources" folder
        //sets up the file to be picked up for setup
        switch (mode){
            case "server" ->{
                directory = "src/main/java/it/polimi/softeng/connectionProtocol/server/serverConfig.json";
            }
            case "client" ->{
                directory = "src/main/java/it/polimi/softeng/connectionProtocol/client/clientConfig.json";
            }
            default -> {
                directory = null;           //should not happen
            }
        }
        JSONObject jsonObject = null;
        try(Reader reader = new FileReader(directory)){
            jsonObject = (JSONObject) parser.parse(reader);
        }catch(IOException e){
            e.printStackTrace();
        }catch(ParseException e){
            e.printStackTrace();
        }
        String port = (String) jsonObject.get("portNumber");
        portNumber = Integer.parseInt(port);

        if(mode.equals("client")){
            hostName = (String) jsonObject.get("hostName");
        }
    }

    /**
     * getter method
     * @return portnumber extracted from the json config file
     */
    public int getPortNumber() {
        return portNumber;
    }

    /**
     * getter method; if used in server mode this getter will return null
     * @return string containing the ip extracted from the json file
     */
    public String getHostName() {
        return hostName;
    }
}
