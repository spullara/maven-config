package com.sampullara.maven;

import org.codehaus.plexus.util.xml.pull.XmlPullParserException;

import java.io.IOException;

import com.sampullara.maven.commands.Command;

/**
 * Created by IntelliJ IDEA.
 *
 * User: samp
 * Date: Jun 23, 2007
 * Time: 9:49:24 AM
 */
public class MC {

    public static void main(String[] args) throws IOException, XmlPullParserException {
        if (args.length == 0) {
            help();
            return;
        }
        String commandName = args[0];
        Command command = Command.getCommand(commandName);
        if (command == null) {
            System.err.println("Command '" + commandName + "' not found");
            help();
            return;
        }
        command.execute(args);
    }

    private static void help() {
        Command command = Command.getCommand("help");
        command.execute(null);
    }

}
