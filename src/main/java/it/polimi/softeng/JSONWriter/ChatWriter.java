package it.polimi.softeng.JSONWriter;

import org.json.simple.JSONObject;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

import static it.polimi.softeng.Constants.chatREGEX;

//To suppress warning related to JSON simple library structure insert following @SuppressWarning
//@SuppressWarnings("unchecked")
public class ChatWriter {

    /**
     * This function verifies if received gameMove follows regex format for chatMessage
     *
     * @return true if chatMessage follows chatMessageRegex format
     */
    public static boolean chatMessageRegex(String chatMessage) {
        Pattern pattern = Pattern.compile(chatREGEX);
        //Check if chatMessage follows regex
        Matcher matcher = pattern.matcher(chatMessage);

        //Ensures that gameMove follows regex
        return matcher.matches();
    }

    /**
     * @param chatMessage contains string with chatMessage
     * @return a JSONObject containing chatMessage translated in JSON format
     */
    public static JSONObject writeChatMessage(String chatMessage){
        JSONObject ChatJSON = new JSONObject();
        String receiver;
        String message;

        //Double check of syntax correctness
        if (!chatMessageRegex(chatMessage))
            return null;

        String[] separator = chatMessage.split(" ", 2);
        receiver = separator[0];
        message = separator[1];

        ChatJSON.put("receiver", receiver.replace("'", ""));
        ChatJSON.put("message", message);

        return ChatJSON;
    }
}
