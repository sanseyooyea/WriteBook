package maxmc.company.writebook.writebook;

/**
 * @author SanseYooyea
 */

public enum selfCommand {
    /**
     *
     */
    help("help"),auto("auto"),in("in"),clear("clear"),
    settile("settitle"),setauthor("setauthor");

    private final String commandText;

    public String getCommandText() {
        return commandText;
    }

    private selfCommand(String commandText) {
        this.commandText = commandText;
    }

}
