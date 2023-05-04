package it.polimi.softeng.JSONWriter;

import it.polimi.softeng.customExceptions.IllegalInsertException;
import org.json.simple.JSONArray;
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
    public static JSONObject writeChatMessage(String chatMessage) throws IllegalInsertException {
        JSONObject ChatJSON = new JSONObject();
        String receiver;
        String message;

        //Double check of syntax correcteness
        if (!chatMessageRegex(chatMessage))
            throw new IllegalInsertException("Error syntax not respected!");

        String[] separator = chatMessage.split(" ");
        receiver = separator[0];
        message = separator[1];

        ChatJSON.put("receiver", receiver.replace("'", ""));
        ChatJSON.put("message", message);

        return ChatJSON;
    }
}