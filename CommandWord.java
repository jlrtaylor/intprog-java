/**
 * Representations for all the valid command words for the game
 * along with a string in a particular language.
 * 
 * @author  James Taylor, Michael Kölling, David J. Barnes
 * @version 2015.03.15
 */

public enum CommandWord
{
    // A value for each command word along with its
    // corresponding user interface string.
    MOVE("move"),
    TURN("turn"),
    
    BACK("back"),
    PICK("pick"),
    DROP("drop"),
    CHECK("check"),
    USE("use"),
    ATTACK("attack"),
    TALK("talk"),
    
    QUIT("quit"),
    HELP("help"),
    UNKNOWN("?");
    
    // The command string.
    private String commandString;
    
    /**
     * Initialise with the corresponding command word.
     * @param commandWord The command string.
     */
    CommandWord(String commandString)
    {
        this.commandString = commandString;
    }
    
    /**
     * @return The command word as a string.
     */
    public String toString()
    {
        return commandString;
    }
}