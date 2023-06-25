package it.polimi.softeng.connectionProtocol;
import org.json.simple.*;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

import java.io.*;

/**
 * Class used to read the json config files for client and server
 */
public class CommunicationProtocolParser{
    /**
     * variable holding the port number found in the json config file
     */
    private int portNumber;
    /**
     * variable holding the host name found in the json config file
     */
    private String hostName;

    /**
     * parser method that parses the config files
     * @param mode "client" for client use, "server" for server use
     */
    public void parser(String mode){
        String directory;
        JSONParser parser = new JSONParser();
        JSONObject jsonObject = null;

        //to be refactored with the usage of "resources" folder
        //sets up the file to be picked up for setup
        switch (mode){
            case "server" ->{
                try(Reader reader = new InputStreamReader(getClass()
                        .getResourceAsStream("/configs/serverConfig.json"))){
                    jsonObject = (JSONObject) parser.parse(reader);
                }catch(IOException | ParseException e){
                    e.printStackTrace();
                }
                directory = "src/main/resources/serverConfig.json";
            }
            case "client" ->{
                try(Reader reader = new InputStreamReader(getClass()
                        .getResourceAsStream("/configs/clientConfig.json"))){
                    jsonObject = (JSONObject) parser.parse(reader);
                }catch(IOException | ParseException e){
                    e.printStackTrace();
                }
                directory = "src/main/resources/clientConfig.json";
            }
            default -> {
                directory = null;           //should not happen
            }
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
