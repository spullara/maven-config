package com.sampullara.maven.commands;

/**
 * Created by IntelliJ IDEA.
 *
 * User: samp
 * Date: Jun 23, 2007
 * Time: 4:35:03 PM
 */
public class HelpCommand extends Command {
    @Override
    public void execute(String[] args) {
        if (args == null || args.length == 1) {
            System.err.println("help [command]            help for a command");
            System.err.println("add [type]                add a new thing");
            System.err.println("list [types]              list things");
            System.err.println("create                    create a pom.xml file in the current directory");
            System.err.println("set [variable] [value]    set top level variables on the project");
        } else {
            Command command = Command.getCommand(args[1]);
            if (command != null) {
                command.help();
            } else {
                help();
            }
        }
    }

    public void help() {
        execute(null);
    }
}
