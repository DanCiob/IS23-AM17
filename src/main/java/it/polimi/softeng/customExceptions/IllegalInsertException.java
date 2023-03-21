package it.polimi.softeng.customExceptions;

public class IllegalInsertException extends Exception{
    /**
     * error message containing the reason for the un-accepted insert
     */
    private String errorMessage;

    /**
     * constructor method for the class that modifies the attribute errorMessage
     * @param errorMessage string passed to the exception containing the reason for the un-accepted insert
     */
    public IllegalInsertException(String errorMessage){
        this.errorMessage = errorMessage;
    }

    /**
     * getter method for the string containing the error message for the exception
     * @return string containing the error message
     */
    public String getMessage(){
        return errorMessage;
    }
}
