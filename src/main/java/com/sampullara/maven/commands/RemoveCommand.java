package com.sampullara.maven.commands;

/**
 * Created by IntelliJ IDEA.
 * <p/>
 * User: samp
 * Date: Jun 24, 2007
 * Time: 12:37:36 PM
 */
public class RemoveCommand extends NestedCommand {
    public void help() {
        System.err.println("remove [type]  remove a new stanza of the following type");
        System.err.println("  dependency   remove a new dependency to this project");
    }
}
