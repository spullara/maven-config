package com.sampullara.maven.commands;

/**
 * Created by IntelliJ IDEA.
 * <p/>
 * User: samp
 * Date: Jun 24, 2007
 * Time: 12:35:33 PM
 */
public abstract class NestedCommand extends Command {
    public void execute(String[] args) {
        String commandName = args[0];
        if (args.length < 2) {
            System.err.println("You must have at least one argument to " + commandName + ": " + (args.length - 1));
            help();
            return;
        }
        String type = args[1];
        Command command = getCommand(commandName + type);
        if (command == null) {
            System.err.println("No such " + commandName + " type: " + type);
            help();
            return;
        }
        command.execute(args);
    }
}
