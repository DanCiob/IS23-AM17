package it.polimi.softeng.controller;

public class LoginController {
    private String IP_address;
    private String Nickname;

    public void gameParser(String s)
    {
        int positionSeparator = 0;
        for (int i = 0; i < s.length(); i++)
        {
            if (s.charAt(i) != '-')
                positionSeparator++;
            else
                break;
        }


        Nickname = s.substring(positionSeparator, s.length());
        //Call to Player Interface
    }
}
